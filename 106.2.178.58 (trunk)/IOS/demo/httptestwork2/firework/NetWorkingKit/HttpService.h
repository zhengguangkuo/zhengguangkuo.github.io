//
//  HttpService.h
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#ifndef __HTTPSERVICE_DEF__
#define __HTTPSERVICE_DEF__

@class HttpService;

@interface HttpServiceQueue:NSOperationQueue
- (id)init;
@end



//post表单中多条记录的拼接
@protocol PartPostdelegate <NSObject>
//生成一个文本记录
-(void)generateDataFromText:(NSString*)name  datatext:(NSString*)text;
//生成一个对象记录
-(void)generateDataFromObject:(NSString*)name
                     filename:(NSString*)filename
                         type:(NSString*)type
                         data:(NSData*)data;
//生成一个文件记录
-(void)generateDataFromFile:(NSString*)name
                       path:(NSString*)path
                       type:(NSString*)type;

@end



@protocol ConnectFailureDelegate <NSObject>
@optional
- (void)ShowAlertMsg:(NSString *)WarningMsg;
@end



@interface MergePostData : NSObject<PartPostdelegate>
@property (nonatomic, strong)   NSMutableData*  PostData;
-(id)init;
@end


@interface HttpService : NSOperation <NSURLConnectionDelegate>
////回调失败，返回警告信息
//@property (nonatomic, copy)     void (^errorHandler)(NSError *error);
@property (nonatomic, assign)  id<ConnectFailureDelegate> delegate;
//回调成功，返回的json串
@property (nonatomic, copy)    void (^dataHandler)(NSString *data);
//回调成功，返回的二进制码，方便生成图片文件
@property (nonatomic, copy)    void (^bytyesHandler)(NSData *data);
@property (nonatomic, retain)  NSMutableURLRequest* HttpRequest;
@property (nonatomic, retain)  NSMutableData*  RequestBody;
@property (nonatomic, assign)  NSInteger activeMethodID;
@property (nonatomic, strong)  NSString*  ProgressValue;

//HudFlag:是否加入遮屏的可选项

//初始化一个http get请求，参数为url
+(id)HttpInitWithUrl:(NSString*)url withHud:(BOOL)hudFlag;

//初始化一个http post请求，参数为url和一个字典作为post对象(支持json)
+(id)HttpInitWithPostJson:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag;

//初始化一个http post请求，参数为url和一个多重表单对象
+(id)HttpInitPostMutForm:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag;

//初始化一个http post请求，参数为url和一个由多文本组成的表单对象
+(id)HttpInitPostForm:(NSString*)url  body:(NSDictionary*)dic withHud:(BOOL)hudFlag;

//任务开始，当任务没有被添加到外部队列时，由共享队列执行时，需添加这句
-(void)startOperation;
//连接失败或者业务异常弹出警告框
-(void)showConnectionFailure:(NSString*)WarningMsg;
@end


#endif