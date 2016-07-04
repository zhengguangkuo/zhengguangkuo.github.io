//
//  HttpService.h
//  
//
//  Created by guorong on 13-11-10.
//  Copyright 2013年 __MyCompanyName__. All rights reserved.
//

#ifndef __HTTPSERVICE_DEF__
#define __HTTPSERVICE_DEF__

@class HttpService;

@interface HttpServiceQueue:NSOperationQueue
- (id)custom_init;
@end




@protocol PartPostdelegate <NSObject>
-(void)AppendPostData:(NSString*)name filename:(NSString*)filename content_type:(NSString*)content_type data:(NSData*)data;
@end


@interface MergePostData : NSObject<PartPostdelegate>
@property (nonatomic, retain)   NSMutableData*  PostData;
-(id)initdata;
@end


@interface HttpService : NSOperation <NSURLConnectionDelegate>

@property (nonatomic, copy) void (^errorHandler)(NSError *error);
@property (nonatomic, copy) void (^dataHandler)(NSString *data);
@property (nonatomic, copy) void (^bytyesHandler)(NSData *data);
@property (nonatomic, retain) NSMutableURLRequest* HttpRequest;
@property (nonatomic, retain)   NSMutableData*  RequestBody;
@property (nonatomic, assign) int  nTaskID;

+(id)HttpInitWithUrl:(NSString*)url withHud:(BOOL)hudFlag;

+(id)HttpInitWithPost:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag;

+(id)HttpInitMutPost:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag;

-(void)startOperation;

@end


#endif