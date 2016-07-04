//
//  TTBaseNavViewController.m
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBaseNavViewController.h"

@interface TTBaseNavViewController ()

@end

@implementation TTBaseNavViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
+ (void)initialize
{
    UINavigationBar *navBar = [UINavigationBar appearance];
    UIBarButtonItem *barItem = [UIBarButtonItem appearance];
    //导航栏背景
    NSString *navBarBg = nil;
    if (iOS7) {
        //        navBarBg = @"Nav_Bar64";
        
        //设置
        navBar.tintColor = [UIColor whiteColor];
        barItem.tintColor = [UIColor whiteColor];
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
    [barItem setTitleTextAttributes:@{
                                      UITextAttributeFont :[UIFont systemFontOfSize:16]}
 forState:UIControlStateNormal|UIControlStateHighlighted];

}
//- (UIStatusBarStyle)preferredStatusBarStyle
//{
//    // 白色样式
//    return UIStatusBarStyleLightContent;
//}

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
//- (void)pushViewController:(UIViewController *)viewController animated:(BOOL)animated
//{
//    TTLog(@"%@",viewController);
//    viewController.hidesBottomBarWhenPushed     = YES;
//    [super pushViewController:viewController animated:animated];
//}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
