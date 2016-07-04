//
//  TTCoupon.h
//  Miteno
//
//  Created by wg on 14-6-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <Foundation/Foundation.h>
#import "TTBaseModel.h"
@class TTCouponDetail;
@interface TTCoupon : TTBaseModel

@property (nonatomic, copy) NSString   *    actId;              //优惠券活动ID
@property (nonatomic, copy) NSString   *    actName;            //优惠券活动名称
@property (nonatomic, copy) NSString   *    merchName;          //商户简称
@property (nonatomic, copy) NSString   *    issuedCnt;          //已领用数量
@property (nonatomic, copy) NSString   *    picPath;            //图标地址
@property (nonatomic, copy) NSString   *    sendType ;          //图标地址?请求类型
@property (nonatomic, copy) NSString   *    payType;            //付费类型
@end
