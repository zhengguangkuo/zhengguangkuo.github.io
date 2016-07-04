//
//  CityModel.m
//  Miteno
//
//  Created by wg on 14-7-14.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCityModel.h"

@implementation TTCityModel
//- (id)initWithDict:(NSDictionary *)dict
//{
//    if (self = [super init]) {
//        self.superArea = dict[@"superArea"];
//        self.areaName = dict[@"areaName"];
//        self.areaLevel = dict[@"areaLevel"];
//        self.areaCode = dict[@"areaCode"];
//    }
//    return self;
//}

- (id)initWithCoder:(NSCoder *)decoder
{
    if (self = [super init]) {
        self.superArea = [decoder decodeObjectForKey:TTsuperArea];
        self.areaName = [decoder decodeObjectForKey:TTareaName];
        self.areaLevel = [decoder decodeObjectForKey:TTareaLevel];
        self.areaCode = [decoder decodeObjectForKey:TTareaCode];
    }
    return self;
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
    [encoder encodeObject:self.superArea forKey:TTsuperArea];
    [encoder encodeObject:self.areaName forKey:TTareaName];
    [encoder encodeObject:self.areaLevel forKey:TTareaLevel];
    [encoder encodeObject:self.areaCode forKey:TTareaCode];
}
@end
