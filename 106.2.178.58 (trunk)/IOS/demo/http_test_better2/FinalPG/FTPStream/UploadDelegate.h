//
//  UploadDelegate.h
//  iPhoneFTP
//
//  Created by Zhou Weikuan on 10-6-15.
//  Copyright 2010 sino. All rights reserved.
//

#import <Foundation/Foundation.h>

enum {
	kUploadBufferSize = 8000,
};

@interface UploadDelegate : NSObject <NSStreamDelegate> {
	NSString *					userName;
	NSString *					passWord;
	
	NSString *					serverPath;
	uint64_t					serverSize;
	
	NSString *                  localPath;
	NSString *					fileName;
    uint64_t					localSize;
	
	uint64_t					bufferOffset;
	uint64_t					bufferLimit;
	uint8_t						_buffer[kUploadBufferSize];
	
    CFWriteStreamRef			ftpStream;
    NSInputStream       *       fileStream;
	
	NSString *					strStatus;
	
	CFRunLoopRef		        runLoop;
}

- (uint8_t *)buffer;

- (id)initWithLocalPath:(NSString *)localStr withServer:(NSString*)serverStr withName:(NSString*)theName withPass:(NSString*)thePass;
- (id)initWithLocalPath:(NSString *)localStr withServer:(NSString*)serverStr;

- (void)parseLocalPath:(NSString*)localStr withServer:(NSString*)serverStr;

- (void)start;
- (void)threadMain:(id)arg;
- (void)resume;
- (void)resumeRead;
- (void)stopWithStatus:(NSString *)statusString;

// - (void)stream:(NSStream *)aStream handleEvent:(NSStreamEvent)eventCode;

@property (nonatomic, retain) NSString * serverPath;
@property (nonatomic, retain) NSString * userName;
@property (nonatomic, retain) NSString * passWord;
@property (nonatomic, retain) NSString * localPath;
@property (nonatomic, retain) NSString * fileName;
@property (nonatomic, assign) CFWriteStreamRef ftpStream;
@property (nonatomic, retain) NSInputStream * fileStream;
@property (nonatomic, retain) NSString * strStatus;

@property (nonatomic, assign) uint64_t   serverSize;
@property (nonatomic, assign) uint64_t   localSize;
@property (nonatomic, assign) uint64_t   bufferOffset;
@property (nonatomic, assign) uint64_t   bufferLimit;

@end
