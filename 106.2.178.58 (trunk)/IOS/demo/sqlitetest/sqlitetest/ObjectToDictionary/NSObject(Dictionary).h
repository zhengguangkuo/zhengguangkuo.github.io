//
//  NSDictionary(JSON).h
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSObject(Dictionary)
- (NSDictionary*)ConvertToDicionary;
- (id)getObjectInternal:(id)obj;
- (NSString *)className;
- (id)initWithDictionary:(NSDictionary*)dict;
@end