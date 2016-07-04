//
//  TTBaseNavViewController.m
//  TT_lottery(彩票)
//
//  Created by TT on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBaseNavViewController.h"

@interface TTBaseNavViewController ()

@end

@implementation TTBaseNavViewController
//只会调用一次
+ (void)initialize
{
    UINavigationBar *navBar = [UINavigationBar appearance];
    UIBarButtonItem *barItem = [UIBarButtonItem appearance];
    //导航栏背景
    NSString *navBarBg = nil;
    if (iOS7) {
//        navBarBg = @"Nav_Bar64";
        
        //设置白色
        navBar.tintColor = [UIColor whiteColor];
        UIImage *img = [UIImage imageNamed:@"top_bg"];// navi_stretch_bg
//        img = [img resizableImageWithCapInsets:UIEdgeInsetsMake(2, 1, 2, 0)];
        //[[UINavigationBar appearance] setBackgroundImage:img forBarMetrics:UIBarMetricsDefault];
        [[UINavigationBar appearanceWhenContainedIn:[TTBaseNavViewController class], nil] setBackgroundImage:img forBarMetrics:UIBarMetricsDefault];
        

    }else{/*IOS 6及以下*/
        navBarBg = @"Nav_Bar";
        [UIApplication sharedApplication].statusBarStyle = UIStatusBarStyleBlackOpaque;
        [barItem setBackgroundImage:[UIImage imageNamed:@"Nav_Button"] forState:UIControlStateNormal barMetrics:UIBarMetricsDefault];
        [barItem setBackgroundImage:[UIImage imageNamed:@"Nav_ButtonPressed"] forState:UIControlStateHighlighted barMetrics:UIBarMetricsDefault];

        [barItem setBackButtonBackgroundImage:[UIImage imageNamed:@"Nav_BackButton"] forState:UIControlStateNormal barMetrics:UIBarMetricsDefault];
        [barItem setBackButtonBackgroundImage:[UIImage imageNamed:@"Nav_BackButtonPressed"] forState:UIControlStateHighlighted barMetrics:UIBarMetricsDefault];
    }
    
    [navBar setBackgroundImage:[UIImage imageNamed:navBarBg] forBarMetrics:UIBarMetricsDefault];
    
    [navBar setTitleTextAttributes:@{UITextAttributeTextColor: [UIColor whiteColor],
                                     UITextAttributeFont :TTNavTitleFont,
                                     }]
    ;
}
- (UIStatusBarStyle)preferredStatusBarStyle
{
    // 白色样式
    return UIStatusBarStyleLightContent;
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
       
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

@end
