//
//  TTCoupon.m
//  Miteno
//
//  Created by wg on 14-6-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCoupon.h"

@implementation TTCoupon
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.merImage = dict[@"merImage"];
        self.merName = dict[@"merName"];
        self.merActive = dict[@"merActive"];
        self.merActiveDate = dict[@"merActiveDate"];
        self.merRebate = dict[@"merRebate"];
        self.merDetial = dict[@"merDetial"];
        
        
        self.actId = dict[@"actId"];
        self.actName = dict[@"actName"];
        self.issuedCnt = dict[@"issuedCnt"];
        self.merchName = dict[@"merchName"];
        self.payType = dict[@"payType"];
        self.picPath = dict[@"picPath"];
        self.sendType = dict[@"sendType"];

        
    }
    return self;
}
+ (TTCoupon *)coupon
{

    TTCoupon *coupon = [[TTCoupon alloc] init];
    coupon.merImage = @"http://c.hiphotos.baidu.com/image/w%3D230/sign=1bed9accc1cec3fd8b3ea076e689d4b6/faedab64034f78f055c9fdf37b310a55b2191ceb.jpg";
    coupon.merName = @"天福号";
    coupon.merActive = @"满200送电影票2张";
    coupon.merActiveDate = @"112";
        coupon.merDetial = @"满200送电影票2张满200送电影票2张满200送电影票2张满200送电影票2张满200送电影票2张满200送电影票2张";
    coupon.merRebate = @"领";
    return coupon;
}
+ (TTCoupon *)coupon1
{
    TTCoupon *coupon = [[TTCoupon alloc] init];
    coupon.merImage = @"http://img8.house365.com/wxhome/news/2013/08/12/137629253552088eb74526b.jpg";
    coupon.merName = @"金凤呈祥";
    coupon.merActive = @"广发银行信用卡9.5折";
    coupon.merDetial = @"金凤呈祥广发银行信用卡9.5折广发银行信用卡9.5折广发银行信用卡9.5折广发银行信用卡9.5折广发银行信用卡9.5折广发银行信用卡9.5折";
    coupon.merActiveDate = @"2014.06.10-2014.06.13";
    return coupon;
}
+ (TTCoupon *)coupon2
{
    
    TTCoupon *coupon = [[TTCoupon alloc] init];
    coupon.merImage = @"http://c.hiphotos.baidu.com/image/w%3D230/sign=1bed9accc1cec3fd8b3ea076e689d4b6/faedab64034f78f055c9fdf37b310a55b2191ceb.jpg";
    coupon.merName = @"味多美";
    coupon.merActive = @"200积分换50积分活动";
    coupon.merDetial = @"味多美200积分换50积分活动200积分换50积分活动200积分换50积分活动200积分换50积分活动200积分换50积分活动200积分换50积分活动";
    coupon.merActiveDate = @"2014.06.10-2014.06.14";
    return coupon;
}
@end
