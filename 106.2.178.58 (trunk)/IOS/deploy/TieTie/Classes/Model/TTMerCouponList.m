//
//  TTMerCouponList.m
//  Miteno
//
//  Created by wg on 14-8-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMerCouponList.h"

@implementation TTMerCouponList
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.merchId = dict[@"merchId"];
        self.merchName = dict[@"merchName"];
        self.Image = dict[@"Image"];
        
    }
    
    return self;
    
}
@end
