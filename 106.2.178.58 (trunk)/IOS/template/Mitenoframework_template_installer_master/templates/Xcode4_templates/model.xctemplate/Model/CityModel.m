//
//  CityModel.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
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
