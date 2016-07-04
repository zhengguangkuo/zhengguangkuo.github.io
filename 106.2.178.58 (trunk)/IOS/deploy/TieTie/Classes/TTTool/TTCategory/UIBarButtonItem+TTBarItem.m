//
//  UIBarButtonItem+TTBarItem.m
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "UIBarButtonItem+TTBarItem.h"
#import "UIButton+NavBtn.h"
@implementation UIBarButtonItem (TTBarItem)

+ (UIBarButtonItem *)barButtonItemWithIcon:(NSString *)normalIcon target:(id)target action:(SEL)action direction:(ItemDirection)direction
{
    UIButton *item = [UIButton barButtonWithIcon:normalIcon];
    item.imageEdgeInsets = (direction==ItemDirectionLeft)?btnEdgeLeft:btnEdgeRight;
    [item addTarget:target action:action forControlEvents:UIControlEventTouchUpInside];
    return [[UIBarButtonItem alloc] initWithCustomView:item];
}

+ (UIBarButtonItem *)barButtonItemWithleftIcon:(NSString *)leftIcon rightIcon:(NSString *)rightIcon  target:(id)target actionLeft:(SEL)actionLeft  actionRitht:(SEL)actionRitht
{
    UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, kNavItgemH*2, kNavItgemH)];
    UIButton *left = [UIButton barButtonWithIcon:leftIcon];
    [left addTarget:target action:actionLeft forControlEvents:UIControlEventTouchUpInside];
    left.imageEdgeInsets = btnEdgeRight;
    UIButton *right = [UIButton barButtonWithIcon:rightIcon];
    right.frame = (CGRect){{kNavItgemH,0},{kNavItgemH,kNavItgemH}};
    right.imageEdgeInsets = btnEdgeRight;
    [right addTarget:target action:actionRitht forControlEvents:UIControlEventTouchUpInside];
    [customView addSubview:left];
    [customView addSubview:right];
     return [[UIBarButtonItem alloc] initWithCustomView:customView];
}
@end
