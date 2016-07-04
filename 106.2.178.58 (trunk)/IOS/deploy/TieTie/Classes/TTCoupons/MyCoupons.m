//
//  Coupons.m
//  TieTie
//
//  Created by zhengguangkuo on 14-6-5.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import "MyCoupons.h"
@implementation MyCoupons

- (id)initWithDict:(NSDictionary *)dict
{
    self = [super initWithDict:dict];
    if (self) {
        self.merchName = dict[@"merchName"];
        self.actName = dict[@"actName"];
        self.actId = dict[@"actId"];
        self.couponNum = [dict[@"couponNum"] integerValue];
        
        self.startDate = dict[@"startDate"];
        self.endDate = dict[@"endDate"];
        self.picPath = dict[@"picPath"];
        self.ex_flag = [dict[@"ex_flag"] integerValue];
        
        self.sendType = [dict[@"sendType"] integerValue];
    }
    return self;
}
@end
