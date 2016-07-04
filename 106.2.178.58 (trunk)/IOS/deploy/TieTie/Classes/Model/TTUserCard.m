//
//  TTUserCard.m
//  Miteno
//
//  Created by wg on 14-8-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTUserCard.h"

@implementation TTUserCard
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.mail = dict[@"mail"];
        self.name = dict[@"name"];
        self.nickName = dict[@"nickName"];
        self.phone = dict[@"phone"];
        self.tel = dict[@"tel"];
        self.title = dict[@"title"];
        self.company = dict[@"company"];
        self.image = dict[@"image"];
//      self.image =  [[NSString alloc] initWithData:dict[@"image"] encoding:NSUTF8StringEncoding];
    }
    
    return self;
    
}
@end
