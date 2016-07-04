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

@protocol PartPostdelegate <NSObject>
-(void)generateDataFromText:(NSString*)name  datatext:(NSString*)text;

-(void)generateDataFromObject:(NSString*)name
                         filename:(NSString*)filename
                         type:(NSString*)type
                         data:(NSData*)data;

-(void)generateDataFromFile:(NSString*)name
                         path:(NSString*)path
                         type:(NSString*)type;

-(void)generateDateFromDictionary:(NSDictionary*)dic;

@end


@interface MergePostData : NSObject<PartPostdelegate>
@property (nonatomic, retain)   NSMutableData*  PostData;
-(id)initdata;
@end


@interface HttpService : NSObject <NSURLConnectionDelegate>

@property (nonatomic, copy)     void (^errorHandler)(NSError *error);
@property (nonatomic, copy)     void (^dataHandler)(NSString *data);
@property (nonatomic, copy)     void (^bytyesHandler)(NSData *data);
@property (nonatomic, retain)   NSMutableURLRequest* HttpRequest;
@property (nonatomic, retain)   NSMutableData*  RequestBody;
@property (nonatomic, assign)   int  nTaskID;
@property (nonatomic, retain)   NSString*  ProgressValue;

+(id)HttpInitWithUrl:(NSString*)url withHud:(BOOL)hudFlag;

+(id)HttpInitWithPost:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag;

+(id)HttpInitMutPost:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag;

+(id)HttpInitPostForm:(NSString*)url  body:(NSDictionary*)dic withHud:(BOOL)hudFlag;


-(void)startOperation;

@end


#endif