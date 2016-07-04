//
//  HttpService.m
//
//
//  Created by guorong on 13-11-10.
//  Copyright 2013年 __MyCompanyName__. All rights reserved.
//

#import "HttpService.h"
#import "UIProgressHud.h"
#import "NSDictionary(JSON).h"

static HttpServiceQueue* sharedQueue;

@implementation HttpServiceQueue
- (id)custom_init
{
    self = [super init];
    if(self){
     [self addObserver:self forKeyPath:@"operationCount" options:0 context:NULL];
    }
    return self;
}


-(void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if (object == self && [keyPath isEqualToString:@"operationCount"])
    {
        if (0 == self.operations.count)
        {
            NSLog(@"task finished");
            [self  cancelAllOperations];
            UIProgressHud* hud = [UIProgressHud sharedUIProgressHud];
            [hud stopWaiting];
        }
    }
}


-(void)dealloc
{
    [super dealloc];

}

@end


@implementation MergePostData
-(id)initdata
{
     self = [super init];
     if(self)
  {
      _PostData = [[NSMutableData alloc] initWithCapacity:10];
  }
    return  self;
}

-(void)AppendPostData:(NSString*)name filename:(NSString*)filename content_type:(NSString*)content_type data:(NSData*)data
{
    // Generate the post header:
    // Content-Type:video/3gpp,image/jpeg,application/json and so on
    NSString *postHead = [NSString stringWithFormat:@"--AaB03x\r\nContent-Disposition: form-data; name=\"%@\"; filename=\"%@\"\r\nContent-Type: %@\r\n\r\n",name,filename,content_type];
    
    NSData* postHeadData = [postHead dataUsingEncoding:NSUTF8StringEncoding];
    
    NSData* postEndData = [@"\r\n--AaB03x--" dataUsingEncoding:NSUTF8StringEncoding];
    
    [self.PostData appendData:postHeadData];
    
    [self.PostData appendData:data];
    
    [self.PostData appendData:postEndData];
}
@end



@interface HttpService()
@property (nonatomic, retain) NSURLConnection *connection;
@property (nonatomic, retain) NSMutableData *responseData;
@property (nonatomic, retain) NSString *responseStr;
@property (nonatomic, retain) NSString *message;
@property (nonatomic, retain) UIProgressHud* hud;
@property NSInteger httpStatusCode;


-(id)initWithURLString:(NSString*)url withHud:(BOOL)hudFlag;


-(id)initWithPostURLString:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag;


-(id)initWithMutPost:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag;


- (void)finishReceive;

- (void)cancelReceive;

@end




@implementation HttpService

-(id)init{
    self = [super init];
    if (self)
    {
		_message = @"";
        _connection = nil;
        _responseData = [[NSMutableData alloc] initWithCapacity: 0];
        _responseStr = [[NSString alloc] init];
        _RequestBody = [[NSMutableData alloc]initWithCapacity:10];
        _hud = nil;
    }
    return self;
}


+(id)HttpInitWithUrl:(NSString*)url  withHud:(BOOL)hudFlag
{
    return [[[HttpService alloc] initWithURLString:url withHud:hudFlag] autorelease];
}


+(id)HttpInitWithPost:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag
{
    return   [[[HttpService alloc] initWithPostURLString:url Body:dic withHud:hudFlag] autorelease];
}



+(id)HttpInitMutPost:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag
{
      return   [[[HttpService alloc] initWithMutPost:url BodyWithBlock:block withHud:hudFlag] autorelease];
}


-(id)initWithURLString:(NSString*)url withHud:(BOOL)hudFlag
{
    self = [self init];
    if (self)
    {
        _HttpRequest = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
    
         if(hudFlag)
       {
           self.hud = [UIProgressHud sharedUIProgressHud];
       }
        
        [_HttpRequest setHTTPMethod: @"GET"];
        
        [_HttpRequest setValue:@"application/xml" forHTTPHeaderField:@"Content-Type"];
    }
    return self;
}



-(id)initWithPostURLString:(NSString*)url Body:(NSDictionary*)dic withHud:(BOOL)hudFlag{
    self = [self initWithURLString:url withHud:hudFlag];
    if (self)
{
        if(_HttpRequest)
    {
        if(dic!=nil)
      {
          
        [self.RequestBody  appendData:[[dic toJSON] dataUsingEncoding:NSUTF8StringEncoding]];
       
        [_HttpRequest setHTTPMethod: @"POST"];
          
        [_HttpRequest setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        }
    }
}
    return self;
}


/*
[HttpService HttpInitMutPost:@"222.334.666.77" BodyWithBlock:^(id<PartPostdelegate> formData)
{
        [formData AppendPostData:@"sss"
         filename:@"bbbb"
         content_type:@"ccc"
         data:[@"helloworld" dataUsingEncoding:NSUTF8StringEncoding]
        ];
} withHud:NO];
*/
-(id)initWithMutPost:(NSString*)url   BodyWithBlock:(void (^)(id <PartPostdelegate> formData))block  withHud:(BOOL)hudFlag
{
    self = [self initWithURLString:url withHud:hudFlag];
    
    if (self)
    {
        if(_HttpRequest)
      {
          __block MergePostData *formData = [[MergePostData alloc] initdata];
          
          if(block)
        {
            block(formData);
        }
        
        self.RequestBody = formData.PostData;
          
        [_HttpRequest setHTTPBody:self.RequestBody];
              
        [_HttpRequest setValue:[NSString stringWithFormat:@"%d",[_RequestBody length]]  forHTTPHeaderField:@"Content-Length"];
          
        [_HttpRequest setHTTPMethod: @"POST"];
          
        [_HttpRequest setValue:@"multipart/form-data; boundary=AaB03x" forHTTPHeaderField:@"Content-Type"];
      }
    }
    return self;
}

// 非并发处理的主方法
//- (void)main
//{
//    [self HttpRequestForUrl];
//}


// 并发处理的主方法
- (void)start
{
    if(![self isCancelled]){
    
        [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
        
        if(_HttpRequest!=nil)
        {
            
            _connection = [[NSURLConnection connectionWithRequest:_HttpRequest delegate:self ]retain];
        
            [_responseData setLength:0];
            
            if(self.hud)
                [self.hud startWaiting];

            
            while(self.connection!=nil)
            {
                [[NSRunLoop currentRunLoop] runMode:NSDefaultRunLoopMode beforeDate:[NSDate distantFuture]];
            }
        }
   }
}


-(void)startOperation
{
    if(!sharedQueue)
  {
      static dispatch_once_t oncePredicate;
      dispatch_once(&oncePredicate, ^{
          sharedQueue = [[HttpServiceQueue alloc] custom_init];
          [sharedQueue setMaxConcurrentOperationCount:6];
      });
  }
    [sharedQueue addOperation:self];
}


-(BOOL)isConcurrent
{
    //返回yes表示支持异步调用，否则为支持同步调用
    return YES;
}


- (BOOL)isExecuting
{
    return self.connection == nil;
}


- (BOOL)isFinished
{
    return self.connection == nil;
}


-(void)cancelReceive
{
    self.connection = nil;
}

-(void)finishReceive
{
    self.connection = nil;
}


- (void)dealloc
{
    [_connection release];
    [_responseData release];
    [_message release];
    [_errorHandler release];
    [_dataHandler release];
    [_bytyesHandler release];
    [_RequestBody release];
    [_responseStr release];
    [super dealloc];
}


// A delegate method called by the NSURLConnection when the request/response 
// exchange is complete.  We look at the response to check that the HTTP 
- (void)connection:(NSURLConnection *)theConnection didReceiveResponse:(NSURLResponse *)response
{
    NSHTTPURLResponse * httpResponse;
    
    NSAssert(theConnection == self.connection,@"连接断开");
    
    httpResponse = (NSHTTPURLResponse *) response;
    assert( [httpResponse isKindOfClass:[NSHTTPURLResponse class]] );
	
    self.httpStatusCode = httpResponse.statusCode;
    
    self.message = [NSString stringWithFormat:@"网络连接失败"];
    

}


// A delegate method called by the NSURLConnection as data arrives.  We just 
// write the data to the file.
- (void)connection:(NSURLConnection *)theConnection didReceiveData:(NSData *)data
{
    
    NSAssert(theConnection == self.connection,@"连接断开");
    
    NSLog(@"begin receivedata");
    
    [self.responseData appendData: data];
}


- (void)connection:(NSURLConnection *)theConnection didFailWithError:(NSError *)error
{
    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
	   
    NSLog(@"connection failed. error: [%@ - %d]", [error localizedDescription],[error code]);
   
    [self cancelReceive];
    
    [self cancel];
    
//    if(self.hud)
//    [self.hud stopWaiting];
    
    if (self.errorHandler)
        self.errorHandler(error);
    
    NSLog(@"receive  data2");
}


// A delegate method called by the NSURLConnection when the connection has been 
- (void)connectionDidFinishLoading:(NSURLConnection *)theConnection
{
    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    
    NSAssert(theConnection == self.connection,@"连接断开");
	
    NSLog(@"finish loading");
    
    [self finishReceive];
    
    [self cancel];
    
//    if(self.hud)
//    [self.hud stopWaiting];

    self.responseStr = [[NSString alloc] initWithData:self.responseData encoding:NSUTF8StringEncoding];

    
    if(self.bytyesHandler)
        self.bytyesHandler(self.responseData);
    

    //[delegate handleSuccessWithData:self data:dataStr];
    if(self.dataHandler)
        self.dataHandler(self.responseStr);
}

//========response callback=========>>

@end
