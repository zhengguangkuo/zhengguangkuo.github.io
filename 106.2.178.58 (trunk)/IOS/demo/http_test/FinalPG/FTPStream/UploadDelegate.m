//
//  UploadDelegate.m
//  iPhoneFTP
//
//  Created by Zhou Weikuan on 10-6-15.
//  Copyright 2010 sino. All rights reserved.
//

#import "UploadDelegate.h"
#import "FileInfo.h"
#import "CFNetwork/CFFTPStream.h"

void theWriteCallBack (CFWriteStreamRef stream,
					  CFStreamEventType eventType,
					  void *clientCallBackInfo) {
	UploadDelegate * ud = (UploadDelegate*)clientCallBackInfo;
	switch (eventType) {
        case kCFStreamEventOpenCompleted: {
            ud.strStatus = @"Opened connection";
			NSLog(@"connection opened for %@", ud.serverPath);
			BOOL success = (ud.serverSize > 0);
			
			
			// Open a stream for the file we're going to receive into.
			if (ud.serverSize < ud.localSize){
				ud.fileStream = [NSInputStream inputStreamWithFileAtPath: ud.localPath];
				[ud.fileStream open];
				if (success){
					uint64_t lsize = ud.serverSize;
					[ud.fileStream setProperty:[NSNumber numberWithInt:lsize] forKey:NSStreamFileCurrentOffsetKey];
					ud.strStatus = @"server file existing, appending the data";
				} else {
					ud.strStatus = @"uploading the file from the starting point";
				}
				assert(ud.fileStream != nil);
			} else {
				ud.strStatus = @"local file size <= server file, aborting...";
				[ud stopWithStatus:@"file size not match!"];
			}
        } break;
        case kCFStreamEventCanAcceptBytes: {
           // If we don't have any data buffered, go read the next chunk of data.
            if (ud.bufferOffset == ud.bufferLimit) {
				uint8_t * buffer = ud.buffer;
                NSInteger bytesRead = [ud.fileStream read:buffer maxLength:kUploadBufferSize];
                if (bytesRead == -1) {
                    [ud stopWithStatus:@"local file read error!"];
                } else if (bytesRead == 0) {
                    [ud stopWithStatus:nil];
                } else {
                    ud.bufferOffset = 0;
                    ud.bufferLimit  = bytesRead;
                }
            }
            
            // If we're not out of data completely, send the next chunk.
            if (ud.bufferOffset != ud.bufferLimit) {
                NSInteger   bytesWritten;
				uint8_t		* buffer = ud.buffer;
				bytesWritten = CFWriteStreamWrite(ud.ftpStream, &(buffer[ud.bufferOffset]), ud.bufferLimit - ud.bufferOffset);
                assert(bytesWritten != 0);
                if (bytesWritten == -1) {
                    [ud stopWithStatus:@"Network write error"];
                } else {
                    ud.bufferOffset += bytesWritten;
					ud.serverSize += bytesWritten;
                }
            }
			ud.strStatus = [NSString stringWithFormat:@"sending %llu/%llu", ud.serverSize, ud.localSize];
        } break;
        case kCFStreamEventErrorOccurred: {
//			CFErrorRef error = CFWriteStreamCopyError(ud.ftpStream);
//			CFStringRef  desc = CFErrorCopyDescription(error);
//			CFStringRef reason = CFErrorCopyFailureReason(error);
//			CFStringRef suggest= CFErrorCopyRecoverySuggestion(error);
//			NSLog(@"write stream error %@: reason:%@, suggest:%2", 
//						(NSString*)desc,
//						(NSString*)reason,
//						(NSString*)suggest);
			
            [ud stopWithStatus:@"Stream open error"];
			break;
        } break;
        default: {
            assert(NO);
        } break;
    }
} 

@implementation UploadDelegate
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
@synthesize bufferOffset;
@synthesize bufferLimit;

- (uint8_t *) buffer {
	return self->_buffer;
}

- (id)initWithLocalPath:(NSString *)localStr withServer:(NSString*)serverStr
			withName:(NSString*)theName withPass:(NSString*)thePass {
	self = [super init];
	if (self == nil)
		return nil;
	[self parseLocalPath:localStr withServer:serverStr];
	
	self.userName = theName;
	self.passWord = thePass;
	
	self.bufferOffset = self.bufferLimit = 0;
	
	return self;
}

- (id)initWithLocalPath:(NSString *)localStr withServer:(NSString*)serverStr {
	return [self initWithLocalPath:localStr withServer:serverStr withName:nil withPass:nil];
}

// assume everything is right
- (void)parseLocalPath:(NSString*)localStr withServer:(NSString*)serverStr {
	localStr = [localStr stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
	self.localPath = [FileInfo pathForDocument];
	self.localPath = [self.localPath stringByAppendingPathComponent: localStr];
	self.serverPath = serverStr;
	self.fileName  = [localStr lastPathComponent];
	NSString * name = [serverStr lastPathComponent];
	if ([name compare:self.fileName] != NSOrderedSame){
		self.serverPath = [self.serverPath stringByAppendingPathComponent: self.fileName];
	}
	
	// get local file size
	self.localSize = [FileInfo getFileSize: self.localPath];
	NSLog(@"Uploading %@ from %@ to %@", self.fileName, self.localPath, self.serverPath);
}

- (void)start {
	[NSThread detachNewThreadSelector:@selector(threadMain:) toTarget:self withObject:nil];
}

- (void)threadMain:(id)arg {
	NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];
	
	runLoop = CFRunLoopGetCurrent();
	void * p = runLoop;
	NSLog(@" the current thread's loop is %p", p);
	
	[self resumeRead];	
	// [self resume];
	// CFRunLoopRunInMode(kCFRunLoopDefaultMode, 1000, NO);
	CFRunLoopRun();
	
	NSLog(@"thread exiting...");
	[pool release];
	
}

- (void)resumeRead {
	// First get and check the URL.
    NSURL * url = [FileInfo smartURLForString: self.serverPath];
    BOOL success = (url != nil);
	self.serverSize = 0;
	
    if ( ! success) {
        self.strStatus = @"Invalid URL";
    } else {
		// Open a CFFTPStream for the URL.
        CFReadStreamRef fs = CFReadStreamCreateWithFTPURL(NULL, (CFURLRef) url);
        assert(fs != NULL);
		self.fileStream = (NSInputStream *) fs;
        
		if (self.userName != 0) {
			success = [self.fileStream setProperty:self.userName forKey:(id)kCFStreamPropertyFTPUserName];
			assert(success);
			success = [self.fileStream setProperty:self.passWord forKey:(id)kCFStreamPropertyFTPPassword];
			assert(success);
		}
		
		success = [self.fileStream setProperty:(id)kCFBooleanTrue forKey:(id)kCFStreamPropertyFTPFetchResourceInfo];
		
        self.fileStream.delegate = self;
        [self.fileStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        [self.fileStream open];
		
		CFRelease(fs);
    }	
}


- (void)resume {
	// First get and check the URL.
    NSURL * url = [FileInfo smartURLForString: self.serverPath];
    BOOL success = (url != nil);

    if ( ! success) {
        self.strStatus = @"Invalid URL";
    } else {
		// Open a CFFTPStream for the URL.
        self.ftpStream = CFWriteStreamCreateWithFTPURL(NULL, (CFURLRef) url);

		if (self.userName != 0) {
            success = CFWriteStreamSetProperty(self.ftpStream, kCFStreamPropertyFTPUserName, self.userName);
            assert(success);
            success = CFWriteStreamSetProperty(self.ftpStream, kCFStreamPropertyFTPPassword, self.passWord);
            assert(success);
        }
        success = CFWriteStreamSetProperty(self.ftpStream, kCFStreamPropertyFTPFileTransferOffset, [NSNumber numberWithUnsignedLongLong: self.serverSize]);
		success = CFWriteStreamSetProperty(self.ftpStream, kCFStreamPropertyAppendToFile, kCFBooleanTrue);
		
		CFStreamClientContext context = {
			0,  (void*)self, NULL, NULL, NULL
		};
		CFOptionFlags flag = kCFStreamEventOpenCompleted | kCFStreamEventCanAcceptBytes | kCFStreamEventErrorOccurred;
		CFWriteStreamSetClient(self.ftpStream, flag, theWriteCallBack, &context);
		
		CFWriteStreamScheduleWithRunLoop (self.ftpStream, CFRunLoopGetCurrent(), kCFRunLoopDefaultMode);
		CFWriteStreamOpen(self.ftpStream);
    }	
}

- (void)stream:(NSStream *)aStream handleEvent:(NSStreamEvent)eventCode
// An NSStream delegate callback that's called when events happen on our 
// network stream.
{
    switch (eventCode) {
        case NSStreamEventOpenCompleted: {
			if (aStream == self.fileStream) {
				// get the server size from ftp
				self.serverSize = 0;
				// check server file's size
				NSNumber * cfSize = [self.fileStream propertyForKey:(id)kCFStreamPropertyFTPResourceSize];
				if (cfSize != nil) {
					uint64_t size = [cfSize unsignedLongLongValue];
					self.strStatus = [NSString stringWithFormat:@"Existing server size is %llu", size];
					self.serverSize = size;
				} else {
					self.serverSize = 0;
				}
				[self.fileStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
				self.fileStream.delegate = nil;
				[self.fileStream close];
				self.fileStream = nil;
				
				// OK, let's start uploading now.
				[self resume];
				return;
			}
			
			self.strStatus = @"Opened connection";
			NSLog(@"connection opened for %@", self.serverPath);

			// Open a stream for the file we're going to receive into.
			if (self.serverSize < self.localSize){
				self.fileStream = [NSInputStream inputStreamWithFileAtPath: self.localPath];
				[self.fileStream open];
				assert(self.fileStream != nil);
				uint64_t lsize = self.serverSize;
				[self.fileStream setProperty:[NSNumber numberWithUnsignedLongLong:lsize] forKey:NSStreamFileCurrentOffsetKey];
				self.strStatus = [NSString stringWithFormat:@"write to file from %llu", lsize];
				
			} else {
				self.strStatus = @"local file size <= server file, aborting...";
				[self stopWithStatus:@"file size not match!"];
			}
			
			
        } break;
        case NSStreamEventHasBytesAvailable: {
            assert(NO);     // should never happen for the output stream
        } break;
        case NSStreamEventHasSpaceAvailable: {
			if (aStream == self.fileStream)
				return;
            uint8_t  * buf = self.buffer;
            // If we don't have any data buffered, go read the next chunk of data.
            
            if (self.bufferOffset == self.bufferLimit) {
                NSInteger   bytesRead;
                
                bytesRead = [self.fileStream read:buf maxLength:kUploadBufferSize];
                
                if (bytesRead == -1) {
                    [self stopWithStatus:@"File read error"];
                } else if (bytesRead == 0) {
                    [self stopWithStatus:nil];
					return;
                } else {
                    self.bufferOffset = 0;
                    self.bufferLimit  = bytesRead;
                }
            }
            
            // If we're not out of data completely, send the next chunk.
            if (self.bufferOffset != self.bufferLimit) {
                NSInteger   bytesWritten;
				NSOutputStream*  fStream = (NSOutputStream*)self.ftpStream;
                bytesWritten = [fStream write:&buf[self.bufferOffset] maxLength:self.bufferLimit - self.bufferOffset];
                assert(bytesWritten != 0);
                if (bytesWritten == -1) {
                    [self stopWithStatus:@"Network write error"];
					return;
                } else {
                    self.bufferOffset += bytesWritten;
					self.serverSize += bytesWritten;
                }
            }
			self.strStatus = [NSString stringWithFormat:@"sending %llu/%llu", self.serverSize, self.localSize];
        } break;
        case NSStreamEventErrorOccurred: {
			if (aStream == self.fileStream)
				return;
            [self stopWithStatus:@"Stream open error"];
        } break;
        case NSStreamEventEndEncountered: {
			if (aStream == self.fileStream)
				return;
            [self stopWithStatus:@"Upload Completed."];
        } break;
        default: {
            assert(NO);
        } break;
    }
}


- (void)stopWithStatus:(NSString *)statusString
{
    if (self.ftpStream != NULL) {
		CFWriteStreamUnscheduleFromRunLoop(self.ftpStream, CFRunLoopGetCurrent(), kCFRunLoopDefaultMode);
        CFWriteStreamClose(self.ftpStream);
		CFRelease(self.ftpStream);
        self.ftpStream = NULL;
    }
    if (self.fileStream != NULL) {
		[self.fileStream close];
        self.fileStream = NULL;
    }
	if (statusString != nil) {
		self.strStatus = statusString;
	} else {
		self.strStatus = @"Uploading Completed.";
	}
	CFRunLoopStop(runLoop);
}

- (void)dealloc {
	[userName release];
	[passWord release];
	
	[serverPath release];
	[localPath release];
	[fileName release];
	
	[strStatus release];
	
	[super dealloc];
}


@end
