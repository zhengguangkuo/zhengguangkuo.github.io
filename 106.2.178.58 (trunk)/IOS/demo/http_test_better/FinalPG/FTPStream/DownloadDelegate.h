//
//  DownloadDelegate.h
//  iPhoneFTP
//
//  Created by Zhou Weikuan on 10-6-15.
//  Copyright 2010 sino. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface DownloadDelegate : NSObject <NSStreamDelegate> {
	NSString *					userName;
	NSString *					passWord;
	
	NSString *					serverPath;
	uint64_t					serverSize;
	
	NSString *                  localPath;
	NSString *					fileName;
    uint64_t					localSize;
	
    NSInputStream  *            ftpStream;
    NSOutputStream *            fileStream;
	
	NSString *					strStatus;
	
	CFRunLoopRef		        runLoop;
}

-(id)initWithServerPath:(NSString *)serverStr withLocal:(NSString*)localStr withName:(NSString*)theName withPass:(NSString*)thePass;
-(id)initWithServerPath:(NSString *)serverStr withLocal:(NSString*)localStr;

- (void)parseServerUrl:(NSString*)serverStr withLocal:(NSString*)localStr;

- (void)start;
- (void)threadMain:(id)arg;
- (void)resume;
- (void)stopWithStatus:(NSString *)statusString;

// - (void)stream:(NSStream *)aStream handleEvent:(NSStreamEvent)eventCode;

@property (nonatomic, retain) NSString * serverPath;
@property (nonatomic, retain) NSString * userName;
@property (nonatomic, retain) NSString * passWord;
@property (nonatomic, retain) NSString * localPath;
@property (nonatomic, retain) NSString * fileName;
@property (nonatomic, assign) NSInputStream * ftpStream;
@property (nonatomic, retain) NSOutputStream * fileStream;
@property (nonatomic, retain) NSString * strStatus;

@property (nonatomic, assign) uint64_t   serverSize;
@property (nonatomic, assign) uint64_t   localSize;

@end
