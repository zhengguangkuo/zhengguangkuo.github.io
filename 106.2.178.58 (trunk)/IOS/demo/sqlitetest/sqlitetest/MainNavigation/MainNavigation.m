//
//  MainNavigation.m
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import "MainNavigation.h"
@interface MainNavigation()

-(void)customizeNavigationBar;

@end


@implementation MainNavigation


-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
}


-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:NO];
}


-(void)viewDidLoad
{
    [super viewDidLoad];
    [self customizeNavigationBar];
}


- (void)customizeNavigationBar
{
    
}


-(void)NavigationBgImage:(NSString*)imageName
{
    UIImage* bgimage = [UIImage imageNamed:imageName];
    UINavigationBar *navBar = self.navigationBar;
    navBar.barStyle = UIBarStyleBlackTranslucent;
    navBar.backgroundColor = [UIColor clearColor];
    
    if ([navBar respondsToSelector:@selector(setBackgroundImage:forBarMetrics:)])
    {
        [navBar setBackgroundImage:bgimage forBarMetrics:UIBarMetricsDefault];
    }
    else
    {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:bgimage];
        imageView.frame = navBar.bounds;
        imageView.backgroundColor = [UIColor whiteColor];
        [navBar insertSubview:imageView atIndex:0];
    }
}


-(void)NavigationViewBackBtn:(UIButton*)back
{
    [back addTarget:self action:@selector(backToPrevious) forControlEvents:UIControlEventTouchUpInside];

    UIBarButtonItem *toggleLeft=[[UIBarButtonItem alloc]initWithCustomView:back];
    
    self.visibleViewController.navigationItem.leftBarButtonItem=toggleLeft;
}


-(void)backToPrevious
{
    [self.visibleViewController.navigationController popViewControllerAnimated:YES];
    NSLog(@"hello test");
}


-(void)NavigationViewRightBtns:(NSArray*)buttons
{
    NSMutableArray* buttonArray =  [[NSMutableArray alloc] initWithCapacity:10];
    for(UIButton*  tempbutton in buttons)
{
   UIBarButtonItem *toggleRight=[[UIBarButtonItem alloc]initWithCustomView:tempbutton];
   [buttonArray addObject:toggleRight];
}
    self.visibleViewController.navigationItem.rightBarButtonItems=buttonArray;
}


-(void)NavigationTitle:(NSString*)title
{
    UILabel* titleLabel =  [[UILabel alloc] initWithFrame:CGRectMake(130, 0, 100, 32)];
    [titleLabel setText:title];
    [titleLabel setTextColor:[UIColor whiteColor]];
    [titleLabel setBackgroundColor:[UIColor clearColor]];
    [titleLabel setFont:[UIFont systemFontOfSize:14.0f]];
    self.visibleViewController.navigationItem.titleView = titleLabel;
}

@end
