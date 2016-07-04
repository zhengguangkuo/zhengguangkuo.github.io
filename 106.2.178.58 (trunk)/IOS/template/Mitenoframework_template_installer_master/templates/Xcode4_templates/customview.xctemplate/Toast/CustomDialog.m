//
//  CustomDialog.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "CustomDialog.h"

@implementation CustomDialog

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _isTouchHidden = FALSE;
        self.frame = CGRectMake(0.0f, 0.0f, ScreenWidth, ScreenHeight);
        self.opaque = 0.5;
        UIView *backGround = [[UIView alloc] initWithFrame:self.frame];
        backGround.backgroundColor = [UIColor clearColor];
        backGround.userInteractionEnabled = YES;
        backGround.tag = 100000;
        backGround.alpha = 1.0;
        [self addSubview:backGround];
        
        _isHidden = TRUE;
        
        _dialogBackgroud = [[UIImageView alloc] initWithImage:nil];
        [_dialogBackgroud setBackgroundColor:[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.5]];
        _dialogBackgroud.frame = frame;
        _dialogBackgroud.userInteractionEnabled = YES;
        _dialogBackgroud.tag = 100001;
        [self addSubview:_dialogBackgroud];
    }
    return self;
}

- (void)addSubview:(UIView *)view {
    if (view.tag==100000 || view.tag==100001) {
        [super addSubview:view];
    }
    else{
        [_dialogBackgroud addSubview:view];
    }
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event{
    if (_isTouchHidden) {
        [self hidden];
    }
}

- (void)show {
    if (_isHidden) {
        UIView *view = [UIApplication sharedApplication].keyWindow;
        [view addSubview:self];
        [view bringSubviewToFront:self];
        
        _isHidden = FALSE;
    }
}

- (void)hidden {
    if (!_isHidden) {
        [self removeFromSuperview];
        _isHidden = TRUE;
    }
}

@end
