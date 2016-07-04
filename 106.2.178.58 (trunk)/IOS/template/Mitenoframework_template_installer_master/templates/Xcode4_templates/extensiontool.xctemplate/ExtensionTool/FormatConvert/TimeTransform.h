//
//  TimeTransform.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
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
