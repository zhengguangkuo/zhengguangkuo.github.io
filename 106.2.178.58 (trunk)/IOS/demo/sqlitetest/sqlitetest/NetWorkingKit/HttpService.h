//
//  HttpService.h
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#ifndef __HTTPSERVICE_DEF__
#define __HTTPSERVICE_DEF__

@class HttpService;

@interface HttpServiceQueue:NSOperationQueue

- (id)init;
@end




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
@property (nonatomic, strong)   NSMutableData*  PostData;
-(id)init;
@end


@interface HttpService : NSOperation <NSURLConnectionDelegate>

@property (nonatomic, copy)     void (^errorHandler)(NSError *error);
@property (nonatomic, copy)     void (^dataHandler)(NSString *data);
@property (nonatomic, copy)     void (^bytyesHandler)(NSData *data);
@property (nonatomic, retain)   NSMutableURLRequest* HttpRequest;
@property (nonatomic, retain)   NSMutableData*  RequestBody;
@property (nonatomic, assign)   int  nTaskID;
@property (nonatomic, strong)   NSString*  ProgressValue;

+(id)HttpInitWithUrl:(NSString*)url withHud:(BOOL)hudFlag;

+(id)HttpInitWithPost:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag;

+(id)HttpInitMutPost:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag;

+(id)HttpInitPostForm:(NSString*)url  body:(NSDictionary*)dic withHud:(BOOL)hudFlag;


-(void)startOperation;

@end


#endif