//
//  Utilities.h
//  Miteno
//
//  Created by zhengguangkuo on 14-4-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>

@interface Utilities : NSObject

+ (CLLocationCoordinate2D)stringToCLLocationCoordinate2D:(NSString *)string;
+ (NSString*)CLLocationCoordinate2DToString:(CLLocationCoordinate2D)coordinate;
+ (BOOL)isEmpty:(NSString *)string;

@end
