//
//  HelpView.m
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "MsgPopBox.h"

@implementation MsgPopBox

@synthesize leftimage;

@synthesize storeName;

@synthesize ActiveName;

@synthesize SaleCount;

@synthesize Expiredate;


- (void)awakeFromNib
{
    self.layer.cornerRadius = 5;
    self.layer.masksToBounds = YES;
    self.center = CGPointMake(ScreenWidth/2, ScreenHeight/2);
}

+ (instancetype)msgpopbox
{
    return [[NSBundle mainBundle] loadNibNamed:@"MsgPopBox" owner:nil options:nil][0];
}


- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)show:(UIViewController *)ctrl
{
    [ctrl.view addSubview:self];
    _bgView = [[UIView alloc] init];
    _bgView.bounds = [UIScreen mainScreen].bounds;
    _bgView.center = CGPointMake(self.window.width/2, self.window.height/2);
    //    _bgView.frame = [UIScreen mainScreen].applicationFrame;
    [_bgView setBackgroundColor:kGlobalBg];
    [ctrl.view.window insertSubview:_bgView atIndex:ctrl.view.window.subviews.count];
    [ctrl.view.window insertSubview:self aboveSubview:_bgView];
    //添加手势
    UITapGestureRecognizer *singleRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeFrom:)];
    singleRecognizer.delegate = self;
    singleRecognizer.numberOfTouchesRequired = 1;
    [_bgView addGestureRecognizer:singleRecognizer];
}

- (void)handleSwipeFrom:(UITapGestureRecognizer*)recognizer {
    
    [self clickCancel];
    //底下是刪除手势的方法
    [_bgView removeGestureRecognizer:recognizer];
}
- (void)clickCancel
{
    [UIView animateWithDuration:0.2 animations:^{
        [_bgView removeFromSuperview];
        [self removeFromSuperview];
    }];
    
}

- (IBAction)ViewDetail:(id)sender {
}

- (IBAction)reject:(id)sender {
    [self clickCancel];
    
}

- (IBAction)Accept:(id)sender {
    [self clickCancel];
}
@end
