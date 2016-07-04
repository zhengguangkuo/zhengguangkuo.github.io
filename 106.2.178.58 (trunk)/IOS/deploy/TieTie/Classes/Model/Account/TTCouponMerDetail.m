//
//  TTCouponMerDetail.m
//  Miteno
//
//  Created by wg on 14-7-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponMerDetail.h"

@implementation TTCouponMerDetail
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.Address = dict[@"Address"];
        self.Distance = dict[@"Distance"];
        self.latitude = dict[@"latitude"];
        self.longitude = dict[@"longitude"];
        self.merchId = dict[@"merchId"];
        self.merchName = dict[@"merchName"];
    }
    return self;
}
@end
