//
//  SystemDialog + Show.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "SystemDialog+Show.h"

@implementation SystemDialog (Show)
+ (void)alert:(NSString *)msg
{
    SystemDialog *dialog = [[SystemDialog alloc] initWithText:msg];
    [dialog show];
}
@end
