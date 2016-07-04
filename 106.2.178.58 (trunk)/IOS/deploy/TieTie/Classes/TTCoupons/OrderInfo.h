//
//  OrderInfo.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Base.h"

@interface OrderInfo : Base

//[{"order_no":"订单号","act_id":"优惠劵活动ID","act_name":"优惠券活动名称","num":"赠送劵数量","from_userid":"送出劵用户标识","to_userid":"接受劵用户标识","start_date":"生成日期","start_time":"生成时间","close_date":"关闭日期","close_time":"关闭时间","status":"状态","input_date":"插入时间"},{"order_no":"订单号","act_id":"优惠劵活动ID","act_name":"优惠券活动名称","num":"赠送劵数量","from_userid":"送出劵用户标识","to_userid":"接受劵用户标识","start_date":"生成日期","start_time":"生成时间","close_date":"关闭日期","close_time":"关闭时间","status":"状态","input_date":"插入时间"},{"order_no":"订单号","act_id":"优惠劵活动ID","act_name":"优惠券活动名称","num":"赠送劵数量","from_userid":"送出劵用户标识","to_userid":"接受劵用户标识","start_date":"生成日期","start_time":"生成时间","close_date":"关闭日期","close_time":"关闭时间","status":"状态","input_date":"插入时间"}]

@property (nonatomic,copy)NSString *order_no;//订单号
@property (nonatomic,copy)NSString *act_id;//优惠劵活动ID
@property (nonatomic,copy)NSString *act_name;//优惠券活动名称
@property (nonatomic,copy)NSString *num;//赠送劵数量
@property (nonatomic,copy)NSString *from_mobile;//送出劵用户标识
@property (nonatomic,copy)NSString *to_mobile;//接受劵用户标识
@property (nonatomic,copy)NSString *start_date;//生成日期
@property (nonatomic,copy)NSString *start_time;//生成时间
@property (nonatomic,copy)NSString *close_date;//关闭日期
@property (nonatomic,copy)NSString *close_time;//关闭时间
@property (nonatomic,copy)NSString *status;//状态
@property (nonatomic,copy)NSString *input_date;//插入时间
@property (nonatomic,copy)NSString *picPath;

//@property (nonatomic,copy)NSString *couponImageUrl;
//@property (nonatomic,copy)NSString *couponName;
//@property (nonatomic,copy)NSString *couponContent;
//@property (nonatomic,copy)NSString *couponOldMoney;
//@property (nonatomic,copy)NSString *couponNewMoney;
//@property (nonatomic,copy)NSString *couponUsedValidity;
//@property (nonatomic,copy)NSString *couponCode;
//@property (nonatomic)BOOL isUsed;
//
//@property (nonatomic,copy)NSString *merchantName;
//@property (nonatomic,copy)NSString *merchantContent;
//
//@property (nonatomic,copy)NSString *couponValidity;
//@property (nonatomic,copy)NSString *couponUseTime;
//@property (nonatomic,copy)NSString *reservation;
//@property (nonatomic,copy)NSString *instructions;
//
//
//@property (nonatomic,copy)NSString *orderCode;
//@property (nonatomic,copy)NSString *orderCount;
//@property (nonatomic,copy)NSString *payTime;
//@property (nonatomic,copy)NSString *totalMoney;




- (id)initWithDict:(NSDictionary *)dict;

@end
