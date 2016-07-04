//
//  cateGory.m
//  Miteno
//
//  Created by wg on 14-8-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "cateGory.h"

@implementation cateGory
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super init]) {
        self.superCat = dict[@"superCat"];
        self.catCode = dict[@"catCode"];
        self.catName = dict[@"catName"];
        self.catLevel = dict[@"catLevel"];
    }
    return self;
}
@end
