//
//  SystemDialog+Show.m
//  Toast
//
//  Created by HWG on 14-1-26.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "SystemDialog+Show.h"

@implementation SystemDialog (Show)
+ (void)alert:(NSString *)msg
{
    SystemDialog *dialog = [[SystemDialog alloc] initWithText:msg];
    [dialog show];
}
@end
