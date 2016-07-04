//
//  SystemDialog + Show.m
//  firework
//
//  Created by guorong on 14-2-17.
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
