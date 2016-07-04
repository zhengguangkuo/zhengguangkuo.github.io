//
//  NSDictionary(JSON).h
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSDictionary(JSON)
+(NSDictionary*)dictionaryWithString:(NSString*)stringContnent;
-(NSString*)toJSON;
@end