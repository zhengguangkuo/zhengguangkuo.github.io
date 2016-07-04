//
//  TTSettingTool.m
//  Miteno
//
//  Created by wg on 14-6-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//
#define TTUserDefaults [NSUserDefaults standardUserDefaults]
#import "TTSettingTool.h"

@implementation TTSettingTool
+ (id)objectForKey:(NSString *)defaultName
{
    return [TTUserDefaults objectForKey:defaultName];
}

+ (void)setObject:(id)value forKey:(NSString *)defaultName
{
    [TTUserDefaults setObject:value forKey:defaultName];
    [TTUserDefaults synchronize];
}

+ (BOOL)boolForKey:(NSString *)defaultName
{
    return [TTUserDefaults boolForKey:defaultName];
}

+ (void)setBool:(BOOL)value forKey:(NSString *)defaultName
{
    [TTUserDefaults setBool:value forKey:defaultName];
    [TTUserDefaults synchronize];
}


+ (void)setFloat:(float)value forKey:(NSString *)defaultName
{
    [TTUserDefaults setFloat:value forKey:defaultName];
    [TTUserDefaults synchronize];
}
+ (float)floatForKey:(NSString *)defaultName
{
    return [TTUserDefaults floatForKey:defaultName];
}

+ (NSInteger)integerForKey:(NSString *)defaultName;
{
    return [TTUserDefaults integerForKey:defaultName];
}
+ (void)setInteger:(NSInteger)value forKey:(NSString *)defaultName
{
    [TTUserDefaults setInteger:value forKey:defaultName];
    [TTUserDefaults synchronize];
}

+ (void)removeObjectForKey:(NSString *)defaultName
{
    return [TTUserDefaults removeObjectForKey:defaultName];
}
@end
