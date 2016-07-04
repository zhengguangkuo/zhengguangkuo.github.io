//
//  TimeTransform.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TimeTransform : NSObject

//nsdate to string
+(NSString *)FromDateToString:(NSDate *)date withFormat:(NSString*)Format;

+(NSString *)FromYmdToString:(NSDate *)date;

+(NSString *)FromYmdHmsToString:(NSDate *)date;

//nsstring to date
+(NSDate *)FromStringToDate:(NSString *)string withFormat:(NSString*)Format;

+(NSDate *)FromStringToYmd:(NSString *)string;

+(NSDate *)FromStringToYmdHms:(NSString *)string;

//interval  to  string date
+(NSString *)FromTimeIntervalToDate:(NSTimeInterval)interval;

+(NSString *)FromStringIntervalToDate:(NSString *)interval;

//interval  to  count down HH:mm:ss
+(NSString *)CountDownFromTimeInterval:(NSTimeInterval)interval;

+(NSString *)CountDownFromStringInterval:(NSString *)interval;


@end
