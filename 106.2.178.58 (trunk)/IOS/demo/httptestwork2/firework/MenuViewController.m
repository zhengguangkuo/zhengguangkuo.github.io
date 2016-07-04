//
//  MenuViewController.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//
#import <CommonCrypto/CommonDigest.h>
#import <Foundation/Foundation.h>
#import <Security/Security.h>
#import "MenuViewController.h"
#import "AFNetworking.h"
#import "Config.h"


#define  kBaseURL  @"http://app.modepay.cn/miteno-mobile/"

//#define WEB_SERVICE_ENV_VAR @"http://app.modepay.cn/miteno-mobile/"

#define WEB_SERVICE_ENV_VAR @"http://app.modepay.cn/miteno-mobile/"




@interface MenuViewController ()

@end

@implementation MenuViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"MenuPage"];

//
//    
//    NSString* password1 =  @"123456{smiler123}";//@"263200{solidernb14}";
//    
//    NSString* password = [self md5:password1];
//    
//    NSLog(@"password = %@",password);
//    
//    NSDictionary* PostDic = [[NSDictionary alloc] initWithObjectsAndKeys:
//                             @"smiler123",@"j_username",
//                             password,@"j_password",
//                             nil];
//    
//    NSURL* testurl = [NSURL URLWithString:kBaseURL];
//
//    AFHTTPClient *client = [AFHTTPClient clientWithBaseURL:testurl];
//      
//    // 2.封装请求
//    NSURLRequest *request = [client requestWithMethod:@"POST" path:@"mpayFront/j_spring_security_check" parameters:PostDic];
//    
//    NSString* bodystr = [[NSString alloc] initWithData:request.HTTPBody encoding:NSUTF8StringEncoding];
//    
//    NSLog(@"body str = %@",bodystr);
//    
////    
////    
////    
////    // 3.发送请求
//    AFHTTPRequestOperation *operation = [client HTTPRequestOperationWithRequest:request success:^(AFHTTPRequestOperation *operation, id responseObject) {
//        
//        NSLog(@"success");
//        
//       NSDictionary* dic = [NSJSONSerialization JSONObjectWithData:responseObject options:NSJSONReadingMutableLeaves error:nil];
//        NSLog(@"dic = %@",dic);
//        
//        }
//     failure:^(AFHTTPRequestOperation *operation, NSError *error) {
//        NSLog(@"error %@",error);
//    }];
//    [operation start];
//    
////      [self RequestTestService];
////  
      [self RequestLogin];
    //[self RequestRegistService];
}



-(void)RequestRegistService
{
    NSString*  testURL =  [NSString stringWithFormat:@"%@mpayFront/reg",  WEB_SERVICE_ENV_VAR];

    NSURL* testurl = [NSURL URLWithString:testURL];

    AFHTTPClient *client = [AFHTTPClient clientWithBaseURL:testurl];
  
    NSDictionary* PostDic = [[NSDictionary alloc] initWithObjectsAndKeys:
                             @"smiler123",@"User_id",
                             @"123456",@"password",
                             @"13972798304",@"mobile",
                             @"beijing",@"city",
                             @"box123@163.com",@"email",
                             @"689718",@"valid_code",
                             nil];
    

    
    
    // 2.封装请求
    NSURLRequest *request = [client requestWithMethod:@"POST" path:@"" parameters:PostDic];

    NSString* bodystr = [[NSString alloc] initWithData:request.HTTPBody encoding:NSUTF8StringEncoding];

    NSLog(@"body str = %@",bodystr);

//
//
//
//    // 3.发送请求
    AFHTTPRequestOperation *operation = [client HTTPRequestOperationWithRequest:request success:^(AFHTTPRequestOperation *operation, id responseObject) {
        
        NSLog(@"success");

       NSDictionary* dic = [NSJSONSerialization JSONObjectWithData:responseObject options:NSJSONReadingMutableLeaves error:nil];
        NSLog(@"dic = %@",dic);

        }
     failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        NSLog(@"error %@",error);
    }];
    [operation start];

}










-(void)RequestTestService
{
    NSString*  testURL =  [NSString stringWithFormat:@"%@mpayFront/reg",  WEB_SERVICE_ENV_VAR];
    NSLog(@"testURL = %@",testURL);
    NSDictionary* PostDic = [[NSDictionary alloc] initWithObjectsAndKeys:
                             @"mine123",@"User_id",
                             @"123456",@"password",
                             @"13870798304",@"mobile",
                             @"beijing",@"city",
                             @"solider1288@163.com",@"email",
                             @"499798",@"valid_code",
                             nil];
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:PostDic
                                                       withHud:NO];
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"response str = %@",data);
     }
     ];
    
    [tempservice startOperation];
}


-(void)RequestLogin
{
    //NSString* password1 =  @"123456{mt009}";
    NSString* password1 =  @"123456{mt009}";
    
    
    NSString* password = [self md5:password1];
    
    NSLog(@"password = %@",password);
    
    NSString*  testURL =  [NSString stringWithFormat:@"%@mpayFront/j_spring_security_check",  WEB_SERVICE_ENV_VAR];
    NSLog(@"testURL = %@",testURL);
    NSDictionary* PostDic = [[NSDictionary alloc] initWithObjectsAndKeys:
                             @"mt009",@"j_username",
                             password,@"j_password",
                             nil];
    
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:PostDic
                                                       withHud:NO];
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"response login str = %@",data);
     }
     ];
    
    [tempservice startOperation];

    
}















- (NSString *)md5:(NSString*)url
{
        const char *original_str = [url UTF8String];
        unsigned char result[CC_MD5_DIGEST_LENGTH];
        CC_MD5(original_str, strlen(original_str), result);
        NSMutableString *hash = [NSMutableString string];
        for (int i = 0; i < 16; i++)
            [hash appendFormat:@"%02X", result[i]];
        
        return [hash lowercaseString];
}




-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
}



-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:YES];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
