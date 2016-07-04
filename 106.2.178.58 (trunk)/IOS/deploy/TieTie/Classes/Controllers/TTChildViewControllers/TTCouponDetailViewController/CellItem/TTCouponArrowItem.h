//
//  TTCouponArrowItem.h
//  Miteno
//
//  Created by wg on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
// 标题 箭头 

#import "TTCouponItem.h"

@interface TTCouponArrowItem : TTCouponItem
@property (nonatomic, copy) void (^operations)(NSInteger);
@end
