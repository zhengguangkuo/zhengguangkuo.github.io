//
//  UIButton+Bg.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "UIButton+Bg.h"

@implementation UIButton (Bg)
- (CGSize)setAllStateBg:(NSString *)icon
{
    UIImage *normal = [UIImage stretchImageWithName:icon];
    UIImage *highlighted = [UIImage stretchImageWithName:[icon filenameAppend:@"_highlighted"]];
    
    [self setBackgroundImage:normal forState:UIControlStateNormal];
    [self setBackgroundImage:highlighted forState:UIControlStateHighlighted];
    return normal.size;
}

+ (UIButton *)backButtonWithBackgroudImage:(UIImage *)bgImage
                                            andTitle:(NSString *)title
                                            andImage:(UIImage *)img
                                           addTarget:(id)target
                                           addAction:(SEL)action
{
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    CGSize btnSize = CGSizeMake(58, 34);//bgImage.size;
    btn.bounds = (CGRect){CGPointZero, btnSize};
    
    if (bgImage) {
        [btn setBackgroundImage:bgImage forState:UIControlStateNormal];
    }
    
    if (title) {
        [btn setTitle:title forState:UIControlStateNormal];
        btn.titleLabel.font = [UIFont systemFontOfSize:15];
    }
    
    if (img) {
        [btn setImage:img forState:UIControlStateNormal];
    }
    
    [btn addTarget:target action:action forControlEvents:UIControlEventTouchUpInside];
    return btn;
}
@end
