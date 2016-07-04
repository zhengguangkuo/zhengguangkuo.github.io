//
//  DownloadDelegate.m
//  iPhoneFTP
//
//  Created by Zhou Weikuan on 10-6-15.
//  Copyright 2010 sino. All rights reserved.
//

#import "DownloadDelegate.h"
#import "FileInfo.h"
#import "CFNetwork/CFFTPStream.h"


@implementation DownloadDelegate
@synthesize serverPath;
@synthesize userName;
@synthesize passWord;
@synthesize localPath;
@synthesize fileName;
@synthesize ftpStream;
@synthesize fileStream;
@synthesize strStatus;
@synthesize serverSize;
@synthesize localSize;

-(id)initWithServerPath:(NSString *)serverStr withLocal:(NSString*)localStr
			withName:(NSString*)theName withPass:(NSString*)thePass {
	self = [super init];
	if (self == nil)
		return nil;
	[self parseServerUrl: serverStr withLocal:localStr];
	
	self.userName = theName;
	self.passWord = thePass;
	
	return self;
}

-(id)initWithServerPath:(NSString *)serverStr withLocal:(NSString*)localStr {
	return [self initWithServerPath:serverStr withLocal:localStr withName:nil withPass:nil];
}

// assume everything is right
- (void)parseServerUrl:(NSString*)serverStr withLocal:(NSString*)localStr {
	self.serverPath = serverStr;
	self.fileName  = [serverStr lastPathComponent];
	self.localPath = [FileInfo pathForDocument];
	self.localPath = [self.localPath stringByAppendingPathComponent:localStr];
	NSString * name = [self.localPath lastPathComponent];
	if ([name compare:self.fileName] != NSOrderedSame){
		self.localPath = [self.localPath stringByAppendingPathComponent: self.fileName];
	}
	
	// get local file size
	self.localSize = [FileInfo getFileSize: self.localPath];
	NSLog(@"Downloading %@ from %@ to %@", self.fileName, self.serverPath, self.localPath);
}

- (void)start {
	[NSThread detachNewThreadSelector:@selector(threadMain:) toTarget:self withObject:nil];
}

- (void)threadMain:(id)arg {
	NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];

	runLoop = CFRunLoopGetCurrent();
	void * p = runLoop;
	NSLog(@" the current thread's loop is %p", p);
	
	[self resume];	
	// CFRunLoopRunInMode(kCFRunLoopDefaultMode, 1000, NO);
	CFRunLoopRun();

	NSLog(@"thread exiting...");
	[pool release];

}

- (void)resume {
	// First get and check the URL.
    NSURL * url = [FileInfo smartURLForString: self.serverPath];
    BOOL success = (url != nil);
	
    if ( ! success) {
        self.strStatus = @"Invalid URL";
    } else {
		// Open a CFFTPStream for the URL.
        CFReadStreamRef fs = CFReadStreamCreateWithFTPURL(NULL, (CFURLRef) url);
        assert(fs != NULL);
		self.ftpStream = (NSInputStream *) fs;
        
		if (self.userName != 0) {
			success = [self.ftpStream setProperty:self.userName forKey:(id)kCFStreamPropertyFTPUserName];
			assert(success);
			success = [self.ftpStream setProperty:self.passWord forKey:(id)kCFStreamPropertyFTPPassword];
			assert(success);
		}
		
		success = [self.ftpStream setProperty:(id)kCFBooleanTrue forKey:(id)kCFStreamPropertyFTPFetchResourceInfo];
		uint64_t lsize = self.localSize;
		success = [self.ftpStream setProperty:[NSNumber numberWithUnsignedLongLong:lsize] forKey:(id)kCFStreamPropertyFTPFileTransferOffset];

        self.ftpStream.delegate = self;
        [self.ftpStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        [self.ftpStream open];
		
		CFRelease(fs);
    }	
}

- (void)stream:(NSStream *)aStream handleEvent:(NSStreamEvent)eventCode
// An NSStream delegate callback that's called when events happen on our 
// network stream.
{
#pragma unused(aStream)
    assert(aStream == self.ftpStream);
	
    switch (eventCode) {
        case NSStreamEventOpenCompleted: {
			self.strStatus = @"Opened connection";
			NSLog(@"connection opened for %@", self.serverPath);
			BOOL success = NO;
			
			self.serverSize = -1;
			// check server file's size
			NSNumber * cfSize = [self.ftpStream propertyForKey:(id)kCFStreamPropertyFTPResourceSize];
			if (cfSize != nil) {
				uint64_t size = [cfSize unsignedLongLongValue];
				self.strStatus = [NSString stringWithFormat:@"File size is %llu", size];
				self.serverSize = size;
				success = YES;
			} else {
				self.strStatus = @"File size is unknown. Uh oh.";
				self.serverSize = -1;
			}
			
			// Open a stream for the file we're going to receive into.
			if (self.serverSize > self.localSize){
				if (success){
					self.fileStream = [NSOutputStream outputStreamToFileAtPath:self.localPath append:YES];
					NSLog(@"local file existing, appending the data");
				} else {
					self.fileStream = [NSOutputStream outputStreamToFileAtPath:self.localPath append:NO];
					self.localSize = 0;
					NSLog(@"downloading the file from the starting point");
				}
				assert(self.fileStream != nil);
				[self.fileStream open];
			} else {
				NSLog(@"local file size >= server file, aborting...");
				[self stopWithStatus:@"file size not match!"];
			}
        } break;
        case NSStreamEventHasBytesAvailable: {
            NSInteger       bytesRead;
            uint8_t         buffer[8000];
			
            // Pull some data off the network.
            
            bytesRead = [self.ftpStream read:buffer maxLength:sizeof(buffer)];
            if (bytesRead == -1) {
                [self stopWithStatus:@"Network read error"];
				return;
            } else if (bytesRead == 0) {
                [self stopWithStatus:nil];
				return;
            } else {
                NSInteger   bytesWritten;
                NSInteger   bytesWrittenSoFar;
                
                // Write to the file.
                
                bytesWrittenSoFar = 0;
                do {
                    bytesWritten = [self.fileStream write:&buffer[bytesWrittenSoFar] maxLength:bytesRead - bytesWrittenSoFar];
                    assert(bytesWritten != 0);
                    if (bytesWritten == -1) {
                        [self stopWithStatus:@"File write error"];
                        break;
                    } else {
                        bytesWrittenSoFar += bytesWritten;
                    }
                } while (bytesWrittenSoFar != bytesRead);
            }
			self.localSize += bytesRead;
			self.strStatus = [NSString stringWithFormat:@"receiving %llu/%llu", self.localSize, self.serverSize];
        } break;
        case NSStreamEventHasSpaceAvailable: {
            assert(NO);     // should never happen for the output stream
        } break;
        case NSStreamEventErrorOccurred: {
            [self stopWithStatus:@"Stream open error"];
        } break;
        case NSStreamEventEndEncountered: {
            [self stopWithStatus:@"Download Completed."];
        } break;
        default: {
            assert(NO);
        } break;
    }
}


- (void)stopWithStatus:(NSString *)statusString
{
    if (self.ftpStream != nil) {
		[self.ftpStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        self.ftpStream.delegate = nil;
        [self.ftpStream close];
        self.ftpStream = nil;
    }
    if (self.fileStream != nil) {
        [self.fileStream close];
        self.fileStream = nil;
    }
	if (statusString != nil) {
		self.strStatus = statusString;
	} else {
		self.strStatus = @"Downloading Completed.";
	}
	CFRunLoopStop(runLoop);
}

- (void)dealloc {
	[userName release];
	[passWord release];
	
	[serverPath release];
	[localPath release];
	[fileName release];

    [fileStream release];
	
	[strStatus release];
	
	[super dealloc];
}

@end
