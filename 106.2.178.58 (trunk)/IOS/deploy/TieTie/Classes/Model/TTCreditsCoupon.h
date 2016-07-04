//
//  TTCreditsCoupon.h
//  Miteno
//
//  Created by wg on 14-8-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Base.h"
//优惠积分列表
@class TTCouponMerDetail;
@interface TTCreditsCoupon : Base
@property (nonatomic, copy) NSString   *    picPath;                //积分活动图片
@property (nonatomic, copy) NSString   *    activityId;             //积分活动ID
@property (nonatomic, copy) NSString   *    activityName;           //积分活动名称
@property (nonatomic, copy) NSString   *    instName;               //机构名称
@property (nonatomic, copy) NSString   *    startDate;              //活动开始时间
@property (nonatomic, copy) NSString   *    endDate;                //活动结束时间

//详情增加字段
@property (nonatomic, copy) NSString   *    saasLogo;               //Saas机构logo
@property (nonatomic, copy) NSString   *    detail;                 //活动介绍
@property (nonatomic, copy) NSString   *    creditRule;             //积分规则
@property (nonatomic, copy) NSString   *    cashRule;               //抵现规则

@property (nonatomic, strong) NSMutableArray   *    merchsList;             //商家列表
@property (nonatomic, copy) NSString    *    total;                  //商家个数
@property (nonatomic, copy) NSString    *    page;                   //商家页数
//商家

@property (nonatomic, strong)   TTCouponMerDetail *   merCoupon;         //商家
@end
