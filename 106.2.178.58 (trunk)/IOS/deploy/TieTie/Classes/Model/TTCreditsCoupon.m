//
//  TTCreditsCoupon.m
//  Miteno
//
//  Created by wg on 14-8-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCreditsCoupon.h"
#import "TTCouponMerDetail.h"
@implementation TTCreditsCoupon
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.activityId = dict[@"activityId"];
        self.activityName = dict[@"activityName"];
        self.endDate = dict[@"endDate"];
        self.instName = dict[@"instName"];
        self.picPath = dict[@"picPath"];
        self.startDate = dict[@"startDate"];
        
        self.saasLogo = dict[@"saasLogo"];
        self.detail = dict[@"detail"];
        self.creditRule = dict[@"creditRule"];
        self.cashRule = dict[@"cashRule"];
        self.cashRule = dict[@"merchName"];
        self.merchsList = dict[@"merchsList"];
        self.total = dict[@"total"];
        self.page = dict[@"page"];
        if (self.merchsList> 0) {
            for (NSDictionary *dict in self.merchsList) {
                
                self.merCoupon = [[TTCouponMerDetail alloc] initWithDict:dict];
            }
        }
    }

    return self;

}
@end
