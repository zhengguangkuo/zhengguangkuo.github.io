//
//  TTUserInfoForXmpp.h
//  Miteno
//
//  Created by zhengguangkuo on 14-7-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TTUserInfoForXmpp : NSObject
@property (nonatomic,copy)NSString *userId;
@property (nonatomic,copy)NSString *name;
@property (nonatomic,copy)NSString *password;
@property (nonatomic,copy)NSString *resource;
@property (nonatomic,copy)NSString *service;

+ (id)sharedInstance;
@end
