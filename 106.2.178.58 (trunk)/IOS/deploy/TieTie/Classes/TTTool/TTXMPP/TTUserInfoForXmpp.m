//
//  TTUserInfoForXmpp.m
//  Miteno
//
//  Created by zhengguangkuo on 14-7-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTUserInfoForXmpp.h"

@implementation TTUserInfoForXmpp

+ (id)sharedInstance
{
    static TTUserInfoForXmpp *info;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        info = [[TTUserInfoForXmpp alloc]init];
    });
    return info;
}
@end
