//
//  NSObject(Dictionary).h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSObject(Dictionary)
- (NSDictionary*)ConvertToDicionary;
- (id)getObjectInternal:(id)obj;
- (NSString *)className;
- (id)initWithDictionary:(NSDictionary*)dict;
- (void)objectFromDictionary:(NSDictionary*)dict;
- (void)objectFromObject:(id)obj;
@end