//
//  Utilities.m
//  Miteno
//
//  Created by zhengguangkuo on 14-4-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Utilities.h"

#define  ChNil(XXX)  [(NSNull*)XXX isKindOfClass:[NSNull class]]?YES:NO

@implementation Utilities

+ (CLLocationCoordinate2D)stringToCLLocationCoordinate2D:(NSString *)coordinateString
{
    CLLocationCoordinate2D coordinate;
    if (![self isEmpty:coordinateString]) {
        NSArray *array = [coordinateString componentsSeparatedByString:@","];
        if (array && [array count]==2) {
            coordinate.longitude = [[array objectAtIndex:0] doubleValue];
            coordinate.latitude = [[array objectAtIndex:1] doubleValue];
        }
    }
    return coordinate;
}

+ (NSString*)CLLocationCoordinate2DToString:(CLLocationCoordinate2D)coordinate
{
    NSString *coordinateStr = [NSString stringWithFormat:@"%f,%f",coordinate.longitude,coordinate.latitude];
    return coordinateStr;
}

+ (BOOL)isEmpty:(NSString *)string
{
    if ((!ChNil(string)) && string && (string.length > 0)) {
        return NO;
    }
    return YES;
}

@end
