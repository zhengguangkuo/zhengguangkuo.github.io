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
@end
