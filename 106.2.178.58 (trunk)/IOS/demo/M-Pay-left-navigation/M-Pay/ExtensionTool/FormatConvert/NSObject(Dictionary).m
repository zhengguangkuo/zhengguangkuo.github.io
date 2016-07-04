//
//  NSObject(Dictionary).m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "NSObject(Dictionary).h"
#import <objc/runtime.h>
@implementation NSObject(Dictionary)

//对象转为字典
- (NSDictionary*)ConvertToDicionary
{
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    unsigned int propsCount;
    objc_property_t *props = class_copyPropertyList([self class], &propsCount);
    for(int i = 0;i < propsCount; i++) {
        objc_property_t prop = props[i];
        id value = nil;
        
        @try {
            NSString *propName = [NSString stringWithUTF8String:property_getName(prop)];
            value = [self getObjectInternal:[self valueForKey:propName]];
            if(value != nil) {
                [dic setObject:value forKey:propName];
            }
        }
        @catch (NSException *exception) {
            NSLog(@"failure of object to dic");
        }
        
    }
    return dic;
}


- (id)getObjectInternal:(id)obj
{
    if(!obj
       || [obj isKindOfClass:[NSString class]]
       || [obj isKindOfClass:[NSNumber class]]
       || [obj isKindOfClass:[NSNull class]]) {
        return obj;
    }
    
    if([obj isKindOfClass:[NSArray class]]) {
        NSArray *objarr = obj;
        NSMutableArray *arr = [NSMutableArray arrayWithCapacity:objarr.count];
        for(int i = 0;i < objarr.count; i++) {
            [arr setObject:[self getObjectInternal:[objarr objectAtIndex:i]] atIndexedSubscript:i];
        }
        return arr;
    }
    
    if([obj isKindOfClass:[NSDictionary class]]) {
        NSDictionary *objdic = obj;
        NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithCapacity:[objdic count]];
        for(NSString *key in objdic.allKeys) {
            [dic setObject:[self getObjectInternal:[objdic objectForKey:key]] forKey:key];
        }
        return dic;
    }
    return [self ConvertToDicionary];
}

- (void)objectFromObject:(id)obj
{
    unsigned int propsCount;
    objc_property_t *props = class_copyPropertyList([self class], &propsCount);
    for(int i = 0;i < propsCount; i++) {
        objc_property_t prop = props[i];
        id value = nil;
        @try {
            NSString *propName = [NSString stringWithUTF8String:property_getName(prop)];
            value = [obj valueForKeyPath:propName];
            if(value != nil) {
                [self setValue:value forKeyPath:propName];
            }
        }
        @catch (NSException *exception) {
            NSLog(@"Object to Object");
        }
    }
}

//获取类名
-(NSString *) className {
    return NSStringFromClass([self class]);
}

- (void)objectFromDictionary:(NSDictionary*) dict {
    unsigned int propCount, i;
    
    if([dict isKindOfClass:[NSNull class]])
    return;
    
    objc_property_t* props = class_copyPropertyList([self class], &propCount);
    
    for (i = 0; i < propCount; i++) {
        objc_property_t prop = props[i];
        
        NSString *propName = [NSString stringWithUTF8String:property_getName(prop)];

            id obj = [dict objectForKey:propName];
            if (!obj)
                continue;
            if ([obj isKindOfClass:[NSString class]]||[obj isKindOfClass:[NSNumber class]]
            ) {
                [self setValue:obj forKeyPath:propName];
            } else if ([obj isKindOfClass:[NSDictionary class]]) {
                id subObj = [self valueForKey:propName];
                if (subObj)
                    [subObj objectFromDictionary:obj];
            }
    }
}

//用字典初始化一个对象
- (id)initWithDictionary:(NSDictionary*) dict {
    self = [self init];
    if(self && dict)
        [self objectFromDictionary:dict];
    return self;
}

@end