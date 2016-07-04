//
//  UserInfo.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Base.h"
#import "Singleton.h"


@interface UserInfo : Base {
    
    NSString  *returnVal;
    NSString  *userId;
    NSString  *sessionKey;
	NSString  *userMobile;
    NSString  *userName; 
}

singleton_for_interface(UserInfo)

@property (nonatomic, copy)    NSString *returnVal;
@property (nonatomic, copy)    NSString *userId;
@property (nonatomic, copy)    NSString *sessionKey;
@property (nonatomic, copy)    NSString *userMobile;
@property (nonatomic, copy)    NSString *userName;

- (id)init;

//登录成功的状态下缓存帐户
- (void)loginAccout:(NSDictionary*)dic;

//获取登录状态
- (BOOL)isLogIn;

//登录退出的情况下重启帐户
- (void)resetAccout;

//将对象读入缓存,自动登录
- (void)readAcount:(NSString*)filename;

//将帐户写入文件
- (void)saveAccout:(NSString*)filename;

@end

