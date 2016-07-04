//
//  TTDESEncrypt.m
//  Miteno
//
//  Created by zhengguangkuo on 14-9-3.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTDESEncrypt.h"
#import "lib.h"

@implementation TTDESEncrypt
+ (NSString*)desencrypt:(NSString *)plaintext
{
    plaintext = [NSString stringWithFormat:@"%@FFFFFFFFFF",plaintext];
    UCHAR *plaintextChar = (UCHAR *)[plaintext cStringUsingEncoding:NSASCIIStringEncoding];
    
//    UCHAR plaintextC[] = "123456FFFFFFFFFF";
    UCHAR keyC[] = "1A8F01BA670E04B90E230B57197F61C2";
    UCHAR typeC = '2';
    UCHAR ciphertext[17] = {0};
    
    
    tDesAsc(plaintextChar, keyC, typeC, &ciphertext);

    TTLog(@"%s",ciphertext);
    
    return [NSString stringWithFormat:@"%s",ciphertext];;
//
//    UCHAR plainte[33] = {0};
//    _tDesAsc(ciphertext, keyC, typeC, &plainte);
//    
//    NSLog(@"%s",plainte);
}
@end
