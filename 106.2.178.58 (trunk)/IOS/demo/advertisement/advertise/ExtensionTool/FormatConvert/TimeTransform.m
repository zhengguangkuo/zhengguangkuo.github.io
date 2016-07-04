//
//  TimeTransform.m
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import "TimeTransform.h"

@implementation TimeTransform


//获取中国地区的时区格式
+(NSDateFormatter*)LocalFormat:(NSString*)Format
{
    NSDateFormatter *dayFormatter=[[NSDateFormatter alloc]init];
    [dayFormatter setDateFormat:Format];
    NSTimeZone*timeZone=[NSTimeZone timeZoneWithName:@"Asia/Hong_Kong"];
    [dayFormatter setTimeZone:timeZone];
    return dayFormatter;
}

//从字符串转化为时间
+(NSString *)FromDateToString:(NSDate *)date withFormat:(NSString*)Format
{
    NSDateFormatter *dayFormatter=[TimeTransform LocalFormat:Format];
    NSString* string = [dayFormatter stringFromDate:date];
    return string;
}

//转为年月日类型的字符串
+(NSString *)FromYmdToString:(NSDate *)date
{
    return [TimeTransform FromDateToString:date
            withFormat:@"yyyy-MM-dd"];
}

//转为年月日时分秒类型的字符串
+(NSString *)FromYmdHmsToString:(NSDate *)date
{
    return [TimeTransform FromDateToString:date
                                withFormat:@"yyyy-MM-dd HH:mm:ss"];
}

//字符串转为时间
+(NSDate *)FromStringToDate:(NSString *)string withFormat:(NSString*)Format
{
    NSDateFormatter *dayFormatter=[TimeTransform LocalFormat:Format];
    NSDate *date=[dayFormatter dateFromString:string];
    return date;
}


//转为年月日类型的时间
+(NSDate *)FromStringToYmd:(NSString *)string
{
    return [TimeTransform FromStringToDate:string
                                withFormat:@"yyyy-MM-dd"];
}

//转为年月日时分秒类型的时间
+(NSDate *)FromStringToYmdHms:(NSString *)string
{
    return [TimeTransform FromStringToDate:string
                                withFormat:@"yyyy-MM-dd HH:mm:ss"];
}

//根据时间间隔获取日期字符串
+(NSString *)FromTimeIntervalToDate:(NSTimeInterval)interval
{
    NSDate*date=[NSDate dateWithTimeIntervalSince1970:interval/1000];
    NSDateFormatter*  dayFormatter = [TimeTransform LocalFormat:@"yyyy-MM-dd HH:mm:ss"];
    
    NSString*timeString= [dayFormatter stringFromDate:date];

    return timeString;
}

//根据字符串间隔获取日期字符串
+(NSString *)FromStringIntervalToDate:(NSString *)interval
{
    long long  mSec=[interval longLongValue];
    return [TimeTransform FromTimeIntervalToDate:mSec];
}

//从获取的时间间隔转为倒数的几小时几分钟几秒钟
+(NSString *)CountDownFromTimeInterval:(NSTimeInterval)interval
{
    NSDate*now=[NSDate date];

    long long mSecToNow = (long long)[now timeIntervalSince1970]*1000;

    long long seconds=(interval-mSecToNow)/1000.0;


    int day=seconds/60/60/24;
    int hour=seconds/60/60-24*day;
    int minute=seconds/60-24*day*60-hour*60;
    int second=seconds-day*24*3600-hour*60*60-minute*60;

    return [NSString stringWithFormat:@"%02d:%02d:%02d",hour,minute,second];
}

//从获取的字符串间隔转为倒数的几小时几分钟几秒钟
+(NSString *)CountDownFromStringInterval:(NSString *)interval
{
    long long timerinterval = [interval longLongValue];
    return [self CountDownFromTimeInterval:timerinterval];
}



@end
