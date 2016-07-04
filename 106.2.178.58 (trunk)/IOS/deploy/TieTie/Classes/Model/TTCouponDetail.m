//
//  TTCouponDetail.m
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponDetail.h"

@implementation TTCouponDetail
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.Rule = dict[@"Rule"];
        self.actDetail = dict[@"actDetail"];
        self.actId = dict[@"actId"];
        self.actName = dict[@"actName"];
        self.picPath = dict[@"picPath"];
        self.sendType = dict[@"sendType"];
        self.issuedCnt = dict[@"issuedCnt"];
        self.IconPic = dict[@"IconPic"];
    }
    return self;
}


+ (TTCouponDetail *)couponDetail
{
    TTCouponDetail *detail = [[TTCouponDetail alloc] init];
    return detail;
}
@end
