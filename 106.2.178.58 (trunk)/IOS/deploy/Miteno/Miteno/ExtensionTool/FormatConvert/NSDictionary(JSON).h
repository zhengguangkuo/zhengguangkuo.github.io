//
//  NSDictionary(JSON).h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSDictionary(JSON)
+(id)dictionaryWithString:(NSString*)stringContnent;
-(NSString*)toJSON;
@end