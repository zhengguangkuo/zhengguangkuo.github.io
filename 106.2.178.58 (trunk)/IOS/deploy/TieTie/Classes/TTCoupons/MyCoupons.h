//
//  Coupons.h
//  TieTie
//
//  Created by zhengguangkuo on 14-6-5.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import "Base.h"

typedef NS_ENUM(NSInteger, NSEnumEx_flag)
{
    COUPONS_UNUSED      = 1,
    COUPONS_USED        = 2,
    COUPONS_OUTDATE     = 3,
};

typedef NS_ENUM(NSInteger, NSEnumSendType)
{
    SEND_GETUSED     = 1,//领用
    SEND_CONSUMPITON = 2,//消费后派发
    SEND_SPECIAL     = 3,//定向派发
};

@interface MyCoupons :Base

@property (nonatomic,copy) NSString *merchName;
@property (nonatomic,copy) NSString *actName;
@property (nonatomic,copy) NSString *actId;
@property (nonatomic) NSInteger couponNum;

@property (nonatomic,copy) NSString *startDate;
@property (nonatomic,copy) NSString *endDate;
@property (nonatomic,copy) NSString *picPath;
@property (nonatomic) NSEnumEx_flag ex_flag;

@property (nonatomic) NSEnumSendType sendType;
@end
