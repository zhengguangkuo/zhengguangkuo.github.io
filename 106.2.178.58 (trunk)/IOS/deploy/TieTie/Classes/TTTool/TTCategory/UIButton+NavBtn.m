//
//  UIButton+NavBtn.m
//  Miteno
//
//  Created by wg on 14-7-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "UIButton+NavBtn.h"
#define TTStrAppend(icon,appendName) [icon stringByAppendingString:appendName]
@implementation UIButton (NavBtn)
+ (UIButton *)barButtonWithIcon:(NSString *)icon
{
    UIButton *item = [UIButton buttonWithType:UIButtonTypeCustom];
    item.frame = (CGRect){CGPointZero,{kNavItgemH,kNavItgemH}};
    [item setImage:[UIImage imageNamed:TTStrAppend(icon, @"normal")] forState:UIControlStateNormal];
    [item setImage:[UIImage imageNamed:TTStrAppend(icon,@"highlight")] forState:UIControlStateHighlighted];
    [item setImage:[UIImage imageNamed:TTStrAppend(icon,@"disabled")] forState:UIControlStateDisabled];
    return item;
}
@end
