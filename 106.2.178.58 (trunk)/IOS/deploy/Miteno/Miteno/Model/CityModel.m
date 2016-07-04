//
//  CityModel.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import "CityModel.h"
@implementation CityModel
- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super init]) {
        self.cities = dict[@"cities"];
        self.state = dict[@"state"];
     
    }
    return self;
}
@end
