//
//  TTSettingTool.h
//  Miteno
//
//  Created by wg on 14-6-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TTSettingTool : NSObject
+ (id)objectForKey:(NSString *)defaultName;
+ (void)setObject:(id)value forKey:(NSString *)defaultName;

+ (BOOL)boolForKey:(NSString *)defaultName;
+ (void)setBool:(BOOL)value forKey:(NSString *)defaultName;

+ (float)floatForKey:(NSString *)defaultName;
+ (void)setFloat:(float)value forKey:(NSString *)defaultName;

+ (NSInteger)integerForKey:(NSString *)defaultName;
+ (void)setInteger:(NSInteger)value forKey:(NSString *)defaultName;

+ (void)removeObjectForKey:(NSString *)defaultName;
@end
