//
//  TTCouponDetailDisMeg.h
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
/*
 * 商家 领 返 折扣信息 （头部）
 */
@interface TTCouponDetailDisMeg : NSObject
@property (nonatomic, copy) NSString   * couTitleLabel;   //折扣信息
@property (nonatomic, copy) NSString   * couSubTitleLabel;
@property (nonatomic, copy) NSString   * couDisContent;   //折扣内容

+ (TTCouponDetailDisMeg *)couponDetailDisMeg;
@end
