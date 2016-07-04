//
//  TieTieTool.m
//  Miteno
//
//  Created by wg on 14-6-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TieTieTool.h"
#import "TTCoupon.h"
//#import "NSString(Additions).h"
@implementation TieTieTool

+ (void)tietieWithParameterMarked:(NSString *)parameterMarked dict:(NSDictionary *)dict succes:(void (^)(id))succes fail:(void (^)(NSError*))fail
{
    NSMutableDictionary *dictionary = [NSMutableDictionary dictionary];
    if (dict!=nil) {
        NSString *parameters = [dict JSONString];
        [dictionary setObject:parameters forKey:jsonStr];
    }
    [dictionary setValue:parameterMarked forKey:marked];
    //-2147482063
    AFHTTPRequestOperationManager *manager = [AFHTTPRequestOperationManager manager];
    manager.responseSerializer.acceptableContentTypes = [NSSet setWithObject:@"text/json"];
    manager.requestSerializer.timeoutInterval = 15;
    [manager POST:TTBaseURL parameters:dictionary
          success:^(AFHTTPRequestOperation *operation,id responseObject) {
              if (succes) {
                  succes(responseObject);
              }
              
          }failure:^(AFHTTPRequestOperation *operation,NSError *error) {
              if (fail) {
                  fail(error);
//                  [SystemDialog alert:@"连接超时！网络状况不佳！"];
              }
          }];
    
}
+ (void)tietieWitheEncodParameterMarked:(NSString *)parameterMarked dict:(NSDictionary *)dict succes:(void (^)(id))succes fail:(void (^)(NSError*))fail
{
    NSMutableDictionary *dictionary = [NSMutableDictionary dictionary];
    if (dict!=nil) {
        NSString *parameters = [dict JSONString];
        [dictionary setObject:parameters forKey:jsonStr];
    }
    NSMutableString *body = [NSMutableString stringWithFormat:@"%@=%@",marked,parameterMarked];
    [dictionary enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop) {
        [body appendFormat:@"&%@=%@", key, obj];
    }];

     NSMutableURLRequest *urlRequest = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:TTBaseURL]];
    urlRequest.timeoutInterval = 15;
     [urlRequest setHTTPMethod: @"POST"];
    NSStringEncoding gbkEncoding = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
     [urlRequest setHTTPBody:[body dataUsingEncoding:gbkEncoding]];
     [urlRequest setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
     AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc]initWithRequest:urlRequest];
     [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
         if (succes) {
             succes(responseObject);
         }
     } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
         if (fail) {
             fail(error);
         }
     }];
     [operation start];
 
}

//add by zgk
+ (void)startTimer:(UIButton*)btn
{
    __block int timeout=30; //倒计时时间
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
    dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0),1.0*NSEC_PER_SEC, 0); //每秒执行
    dispatch_source_set_event_handler(_timer, ^{
        if(timeout<=0){ //倒计时结束，关闭
            dispatch_source_cancel(_timer);
            dispatch_async(dispatch_get_main_queue(), ^{
                //设置界面的按钮显示 根据自己需求设置
                [btn setTitle:@"发送验证码" forState:UIControlStateNormal];
                btn.userInteractionEnabled = YES;
            });
        }else{
            //            int minutes = timeout / 60;
            int seconds = timeout % 60;
            NSString *strTime = [NSString stringWithFormat:@"%.2d", seconds];
            dispatch_async(dispatch_get_main_queue(), ^{
                //设置界面的按钮显示 根据自己需求设置
                TTLog(@"____%@",strTime);
                [btn setTitle:[NSString stringWithFormat:@"%@秒后重发",strTime] forState:UIControlStateNormal];
                btn.userInteractionEnabled = NO;
                
            });
            timeout--;
            
        }
    });
    dispatch_resume(_timer);
    
}

@end
