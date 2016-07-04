//
//  TTCoupon.h
//  Miteno
//
//  Created by wg on 14-6-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <Foundation/Foundation.h>
#import "Base.h"
@class TTCouponDetail;
@interface TTCoupon : Base
@property (nonatomic, copy) NSString   *    merImage;
@property (nonatomic, copy) NSString   *    merName;
@property (nonatomic, copy) NSString   *    merActive;
@property (nonatomic, copy) NSString   *    merActiveDate;
@property (nonatomic, copy) NSString   *    merRebate;
@property (nonatomic, copy) NSString   *    merDetial;

//----- 最新
@property (nonatomic, copy) NSString   *    actId;              //优惠券活动ID
@property (nonatomic, copy) NSString   *    actName;            //优惠券活动名称
@property (nonatomic, copy) NSString   *    merchName;          //商户简称
@property (nonatomic, copy) NSString   *    issuedCnt;          //已领用数量
@property (nonatomic, copy) NSString   *    picPath;            //图标地址
@property (nonatomic, copy) NSString   *    sendType ;          //图标地址?请求类型
@property (nonatomic, copy) NSString   *    payType;            //付费类型



@property (nonatomic, strong) TTCouponDetail    * couponDetail;

- (id)initWithDict:(NSDictionary *)dict;

+ (TTCoupon *)coupon;
+ (TTCoupon *)coupon1;
+ (TTCoupon *)coupon2;
@end
