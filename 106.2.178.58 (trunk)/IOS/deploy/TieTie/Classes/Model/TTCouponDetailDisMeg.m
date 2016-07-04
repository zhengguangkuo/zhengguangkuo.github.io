//
//  TTCouponDetailDisMeg.m
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponDetailDisMeg.h"

@implementation TTCouponDetailDisMeg
+ (TTCouponDetailDisMeg *)couponDetailDisMeg
{
    TTCouponDetailDisMeg *detail = [[TTCouponDetailDisMeg alloc] init];
    detail.couTitleLabel = @"7.5折";
    detail.couSubTitleLabel = @"已经领取122张";
    detail.couDisContent = @"到店消费西城区积水潭新街口鼓啊岸上dASD楼大街西城区积水潭新街口阿城区积水潭新街城区积水潭新街";
    return detail;

}
@end
