//
//  TwoItoastAndHUDViewController.m
//  HUDAndiToast
//
//  Created by HWG on 14-1-26.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TwoItoastAndHUDViewController.h"
#import "Toast+UIView.h"
@interface TwoItoastAndHUDViewController ()

@property (assign, nonatomic) BOOL isShowingActivity;
@end

@implementation TwoItoastAndHUDViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark -显示文字 make itoast
- (IBAction)showText:(id)sender {
    [self.view makeToast:@"只显示文字。。"];
}

#pragma mark - 文字标题 和 文字说明
- (IBAction)showTitleAndText:(id)sender {
    
    [self.view makeToast:@"我是标题"
                duration:3.0
                position:@"center"
                   title:@"标题文字说明。。。"];
}

#pragma mark -图片 和 文字
- (IBAction)showImageText:(id)sender {
    [self.view makeToast:@"你想了解图片信息，我告诉你."
                duration:3.0
                position:@"center"
                   image:[UIImage imageNamed:@"toast.png"]];
}

#pragma mark -显示标题、文字、图片、
- (IBAction)showTitleWithTextAndImage:(id)sender {
    [self.view makeToast:@"标题 & 图片"
                duration:3.0
                position:@"bottom"
                   title:@"T标题"
                   image:[UIImage imageNamed:@"toast.png"]];
}

#pragma mark -显示view
- (IBAction)showViews:(id)sender {
    UIView *customView = [[UIView alloc] initWithFrame:CGRectMake(0, 50, 80, 30)];
    [customView setAutoresizingMask:(UIViewAutoresizingFlexibleLeftMargin | UIViewAutoresizingFlexibleRightMargin | UIViewAutoresizingFlexibleTopMargin | UIViewAutoresizingFlexibleBottomMargin)];    [customView setBackgroundColor:[UIColor orangeColor]];
    
    [self.view showToast:customView
                duration:2.0
                position:@"center"];
}

#pragma mark - 切换按钮文字
- (IBAction)showLoad:(id)sender {
    if (!_isShowingActivity) {
//        [_activityButton setTitle:@"Hide Activity" forState:UIControlStateNormal];
            [_activityButton setTitle:@"显示我" forState:UIControlStateNormal];
        [self.view makeToastActivity];
    } else {
//        [_activityButton setTitle:@"Show Activity" forState:UIControlStateNormal];
        [_activityButton setTitle:@"隐藏我" forState:UIControlStateNormal];
        [self.view hideToastActivity];
    }
    _isShowingActivity = !_isShowingActivity;
}

#pragma mark -显示图片 (根据坐标 设置图片point)
- (IBAction)showImage:(id)sender {
    UIImageView *toastView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"toast.png"]];
    
    [self.view showToast:toastView
                duration:2.0
                position:[NSValue valueWithCGPoint:CGPointMake(100, 150)]];
}

#pragma mark -
@end
