//
//  CustomDialog.m
//  Mpay
//
//  Created by HWG on 13-12-19.
//  Copyright (c) 2013年 miteno. All rights reserved.
//

#import "CustomDialog.h"

@implementation CustomDialog

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _isTouchHidden = FALSE;
        self.frame = CGRectMake(0.0f, 0.0f, 320.0f, ScreenHeight);
        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"dialogBigBg.png"]];
        self.opaque = 0.5;
        UIView *backGround = [[UIView alloc] initWithFrame:self.frame];
        backGround.backgroundColor = [UIColor clearColor];
        backGround.userInteractionEnabled = YES;
        backGround.tag = 100000;
        backGround.alpha = 1.0;
        [self addSubview:backGround];
        
        _isHidden = TRUE;
        
        UIImage *image = [[UIImage imageNamed:@"didlogBigBg.png"]stretchableImageWithLeftCapWidth:30.0f topCapHeight:30.0f];
        
        _dialogBackgroud = [[UIImageView alloc] initWithImage:image];
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
