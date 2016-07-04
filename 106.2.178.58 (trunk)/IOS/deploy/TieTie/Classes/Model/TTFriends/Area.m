//
//  Area.m
//  Miteno
//
//  Created by wg on 14-8-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Area.h"

@implementation Area
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super init]) {
        self.superArea = dict[@"superArea"];
        self.areaName = dict[@"areaName"];
        self.areaCode = dict[@"areaCode"];
        self.areaLevel = dict[@"areaLevel"];
    }
    return self;
}

@end
