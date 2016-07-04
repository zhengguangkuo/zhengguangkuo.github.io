//
//  SystemDialog + Show.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "SystemDialog+Show.h"

@implementation SystemDialog (Show)
+ (void)alert:(NSString *)msg
{
    SystemDialog *dialog = [[SystemDialog alloc] initWithText:msg];
    [dialog show];
}
@end
