//
//  NSObject(Dictionary).m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
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
            TTLog(@"failure of object to dic");
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
        [self reflectDataFromOtherObject:dict];
//        [self objectFromDictionary:dict];
    return self;
}

- (NSArray*)propertyKeys
{
    unsigned int outCount, i;
    objc_property_t *properties = class_copyPropertyList([self class], &outCount);
    NSMutableArray *keys = [[NSMutableArray alloc]initWithCapacity:outCount];
    
    for (i = 0; i < outCount; i++) {
        objc_property_t property = properties[i];
        NSString *propertyName = [[NSString alloc] initWithCString:property_getName(property) encoding:NSUTF8StringEncoding];
        [keys addObject:propertyName];
    }
    free(properties);
    return keys;
}

- (BOOL)reflectDataFromOtherObject:(NSObject *)objectData
{
    BOOL ret = NO;
    for (NSString *key in [self propertyKeys]) {
        if ([objectData isKindOfClass:[NSDictionary class]]) {
            ret = ([objectData valueForKey:key] == nil)? NO : YES;
        } else {
            ret = [objectData respondsToSelector:NSSelectorFromString(key)];
        }
        
        if (ret) {
            id propertyValue = [objectData valueForKey:key];
            if (![propertyValue isKindOfClass:[NSNull class]] && propertyValue != nil) {
                [self setValue:propertyValue forKey:key];
            }
        }
    }
    return ret;
}


@end