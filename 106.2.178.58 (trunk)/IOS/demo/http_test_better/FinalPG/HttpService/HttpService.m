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

static UIProgressHud*  sharedHud;

static int nOperationsCount;

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
            
        }
    }
}


-(void)dealloc
{
    [super dealloc];
}

@end

#define  boundary  @"0xKhTmLbOuNdArY"
@implementation MergePostData

-(id)initdata
{
     self = [super init];
     if(self)
  {
      _PostData = [[NSMutableData alloc] init];
  }
    return  self;
}




//Content-Type: video/3gpp, application/json, image/jpeg
//post 字符串
-(void)generateDataFromText:(NSString*)name  datatext:(NSString*)text
{
    NSString *thisFieldString = [NSString stringWithFormat:
                                 @"--%@\r\nContent-Disposition: form-data; name=\"%@\"\r\n\r\n%@",
                                 boundary, name,text];
    [self.PostData appendData:[thisFieldString dataUsingEncoding:NSUTF8StringEncoding]];
    [self.PostData appendData:[@"\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
}

//post Json对象
-(void)generateDataFromObject:(NSString*)name
                         filename:(NSString*)filename
                         type:(NSString*)type
                         data:(NSData*)data
{
    NSString *thisFieldString = [NSString stringWithFormat:
                                 @"--%@\r\nContent-Disposition: form-data; name=\"%@\"; filename=\"%@\"\r\nContent-Type: %@\r\nContent-Transfer-Encoding: binary\r\n\r\n",
                                 boundary,
                                 name,
                                 filename,
                                 type];
    [self.PostData appendData:[thisFieldString dataUsingEncoding:NSUTF8StringEncoding]];
    [self.PostData appendData:data];
    [self.PostData appendData:[@"\r\n" dataUsingEncoding:NSUTF8StringEncoding]];

}

//post dictionary多个键值对
-(void)generateDateFromDictionary:(NSDictionary*)dic
{
    if(![dic isKindOfClass:[NSNull class]])
    {
        [dic enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
            [self generateDataFromText:key datatext:obj];
        }];
    }
}


//post 文件
-(void)generateDataFromFile:(NSString*)name
                       path:(NSString*)path
                       type:(NSString*)type
{
    NSString *thisFieldString = [NSString stringWithFormat:
                                 @"--%@\r\nContent-Disposition: form-data; name=\"%@\"; filename=\"%@\"\r\nContent-Type: %@\r\nContent-Transfer-Encoding: binary\r\n\r\n",
                                 boundary,
                                 name,
                                 [path lastPathComponent],
                                 type];
    
    [self.PostData appendData:[thisFieldString dataUsingEncoding:NSUTF8StringEncoding]];
    [self.PostData appendData: [NSData dataWithContentsOfFile:path]];
    [self.PostData appendData:[@"\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
}

@end



@interface HttpService()
@property (nonatomic, retain) NSURLConnection *connection;
@property (nonatomic, retain) NSMutableData *responseData;
@property (nonatomic, retain) NSString *responseStr;
@property (nonatomic, retain) NSString *message;
@property (nonatomic, retain) UIProgressHud* hud;
@property NSInteger httpStatusCode;
@property (nonatomic, assign) long long ContentLength;
@property (nonatomic, assign) long long ReceiveLength;


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
        _RequestBody = [[NSMutableData alloc]init];
        _hud = nil;
        _ProgressValue = nil;
        _ContentLength = 0.0000f;
        _ReceiveLength = 0.0000f;
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


+(id)HttpInitPostForm:(NSString*)url  body:(NSDictionary*)dic withHud:(BOOL)hudFlag
{
    return   [[[HttpService alloc] initWithMutPost:url BodyWithBlock:^(id<PartPostdelegate> formData) {
        [formData generateDateFromDictionary:dic];
    } withHud:hudFlag] autorelease];
}


-(id)initWithURLString:(NSString*)url withHud:(BOOL)hudFlag
{
    self = [self init];
    if (self)
    {
        _HttpRequest = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
    
         if(hudFlag)
       {
           //self.hud = [UIProgressHud sharedUIProgressHud];
           if(!sharedHud)
           {
               static dispatch_once_t oncePredicate2;
               dispatch_once(&oncePredicate2, ^{
                   sharedHud = [[UIProgressHud alloc] init];
                   nOperationsCount = 0;
               });
           }
           _hud = sharedHud;
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
        NSLog(@"url = %@",url);
        [self.RequestBody  appendData:[[dic toJSON] dataUsingEncoding:NSUTF8StringEncoding]];
          
        [_HttpRequest setHTTPBody:self.RequestBody];
          
        [_HttpRequest setHTTPMethod: @"POST"];  
          
        [_HttpRequest setTimeoutInterval:30.0f];
          
        [_HttpRequest setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
        }
    }
}
    return self;
}


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
        
          
        if([formData.PostData length]>1)
      {
        [formData.PostData appendData:[[NSString stringWithFormat:@"--%@--\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];

        self.RequestBody = formData.PostData;
          
          NSString*  postbodystr = [[NSString alloc] initWithData:self.RequestBody encoding:NSUTF8StringEncoding];
          
          NSLog(@"former str = %@",postbodystr);
          
          
        NSString *charset = (NSString *)CFStringConvertEncodingToIANACharSetName(CFStringConvertNSStringEncodingToEncoding(NSUTF8StringEncoding));

        [_HttpRequest setHTTPBody:self.RequestBody];
          
        [_HttpRequest setTimeoutInterval:30.0f];
          
        [_HttpRequest setValue:[NSString stringWithFormat:@"%lu", (unsigned long) [self.RequestBody length]] forHTTPHeaderField:@"Content-Length"];

        [_HttpRequest setHTTPMethod: @"POST"];
          
        [_HttpRequest setValue:[NSString stringWithFormat:@"multipart/form-data; charset=%@; boundary=%@", charset, boundary] forHTTPHeaderField:@"Content-Type"];
      }
     
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
            _ReceiveLength = 0.0000f;
            
            _ContentLength = 0.0000f;
            
            _connection = [[NSURLConnection connectionWithRequest:_HttpRequest delegate:self ]retain];
        
            [_responseData setLength:0];
            
            if(_hud)
            {
              if(nOperationsCount==0)
              [_hud startWaiting];
                ++nOperationsCount;
            }
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
    
    self.ContentLength = response.expectedContentLength;
    
    if(_ContentLength<0)
   {
       NSLog(@"无法获取字节长度!");
   }
    
    NSLog(@"content--length is = %lld",_ContentLength);
}


// A delegate method called by the NSURLConnection as data arrives.  We just 
// write the data to the file.
- (void)connection:(NSURLConnection *)theConnection didReceiveData:(NSData *)data
{
    
    NSAssert(theConnection == self.connection,@"连接断开");
    
    NSLog(@"begin receivedata");
    
    [self.responseData appendData: data];
    
    NSLog(@"length of data length = %d",[data length]);
    
    _ReceiveLength+=[data length];
   
    float persentvalue =  (float)_ReceiveLength/(float)_ContentLength;
    NSLog(@"persentvalue = %.2f",persentvalue);

    if(_ContentLength>0)
    {
        self.ProgressValue = [NSString stringWithFormat:@"%.0f",persentvalue*100];
        NSLog(@"progressValue = %@%%",_ProgressValue);
    }
}


- (void)connection:(NSURLConnection *)theConnection didFailWithError:(NSError *)error
{
    [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
	   
    NSLog(@"connection failed. error: [%@ - %d]", [error localizedDescription],[error code]);
   
    [self cancelReceive];
    
    [self cancel];
    
    if(_hud)
   {
      if(nOperationsCount>0)
     {
         --nOperationsCount;
     }
       if(nOperationsCount==0)
       [_hud stopWaiting];
   }
    
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
    
    if(_hud)
    {
        if(nOperationsCount>0)
        {
            --nOperationsCount;
        }
        if(nOperationsCount==0)
            [_hud stopWaiting];
    }

    self.responseStr = [[NSString alloc] initWithData:self.responseData encoding:NSUTF8StringEncoding];

    
    if(self.bytyesHandler)
        self.bytyesHandler(self.responseData);
    

    //[delegate handleSuccessWithData:self data:dataStr];
    if(self.dataHandler)
        self.dataHandler(self.responseStr);
}

//========response callback=========>>

@end
