//
//  TTCouponMerDetail.h
//  Miteno
//
//  Created by wg on 14-7-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Base.h"

@interface TTCouponMerDetail : Base
@property (nonatomic, copy)NSString    *   Address;               //商家地址
@property (nonatomic, copy)NSString    *   Distance;              //商家距离
@property (nonatomic, copy)NSString    *   latitude;              //纬度
@property (nonatomic, copy)NSString    *   longitude;             //经度
@property (nonatomic, copy)NSString    *   merchId;               //商家ID
@property (nonatomic, copy)NSString    *   merchName;             //商家名称

//优惠积分详情增加字段
@property (nonatomic, copy)NSString    *   Image;             //商家图片


@end
