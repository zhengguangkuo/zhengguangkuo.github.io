//
//  NSDictionary(JSON).h
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSDictionary(JSON)
+(NSDictionary*)dictionaryWithString:(NSString*)stringContnent;
-(NSString*)toJSON;
@end