//
//  TTConponItem.m
//  Miteno
//
//  Created by wg on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponItem.h"

@implementation TTCouponItem
+ (id)couponItemWithIcon:(NSString *)icon title:(NSString *)title
{
    TTCouponItem *coupon = [[self alloc] init];
    coupon.icon = icon;
    coupon.title = title;
    return coupon;
    
}
+ (id)couponItemWithTitle:(NSString *)title
{
    return [self couponItemWithIcon:nil title:title];
}
@end
