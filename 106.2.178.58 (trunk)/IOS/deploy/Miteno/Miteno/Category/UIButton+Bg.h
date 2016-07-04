//
//  UIButton+Bg.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIButton (Bg)
// 设置按钮所有状态下的背景
- (CGSize)setAllStateBg:(NSString *)icon;

+ (UIButton *)backButtonWithBackgroudImage:(UIImage *)bgImage
                                            andTitle:(NSString *)title
                                            andImage:(UIImage *)img
                                           addTarget:(id)target
                                           addAction:(SEL)action;
@end
