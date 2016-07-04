//
//  CustomDialog.h
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

#define  ScreenWidth   [UIScreen mainScreen].bounds.size.width

#define  ScreenHeight  [UIScreen mainScreen].bounds.size.height


@interface CustomDialog : UIView
@property (nonatomic, assign) BOOL isHidden;
@property (nonatomic, strong) UIImageView *dialogBackgroud;
@property (nonatomic, assign) BOOL isTouchHidden; //点击空白处关闭dialog;

//显示
- (void)show;

//隐藏
- (void)hidden;
@end
