//
//  NSString(Additions).h
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CommonCrypto/CommonDigest.h>

@interface NSString(Additions)

+(BOOL)checkFormat:(NSString*)string withformat:(NSString*)format;

+(BOOL)checkLength:(NSString*)string
              over:(int)mini
              less:(int)max;

+(BOOL)checkEqual:(NSString*)src dest:(NSString*)dest;

+(BOOL)checkInclude:(NSString*)mainstr
             substr:(NSString*)string;

+(BOOL)checkUserName:(NSString*)username;

+(BOOL)checkEmail:(NSString*)email;

+(BOOL)checkPhone:(NSString*)number;

-(NSString *)trimString;

-(NSString *)subString:(int)pos for:(int)length;

-(NSString *)md5;

@end