//
//  CustomDialog.h
//  Mpay
//
//  Created by HWG on 13-12-19.
//  Copyright (c) 2013年 miteno. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CustomDialog : UIView
@property (nonatomic, assign) BOOL isHidden;
@property (nonatomic, strong) UIImageView *dialogBackgroud;
@property (nonatomic, assign) BOOL isTouchHidden; //点击空白处关闭dialog;

//显示
- (void)show;

//隐藏
- (void)hidden;
@end
