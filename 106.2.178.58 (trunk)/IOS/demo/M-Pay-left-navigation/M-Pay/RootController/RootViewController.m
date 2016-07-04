//
//  RootViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/NSObject.h>
#import "RootViewController.h"
#import "Config.h"


@interface RootViewController ()

@end


@implementation RootViewController

#define  FULL_SCREEN_Frame     [UIScreen mainScreen].applicationFrame

#define RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:1]

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

#pragma mark-  viewcontroller life circle

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self SetNaviationBgImg:[UIImage imageNamed:@"main_top_bg"]];
     //[self SetNaviationTitleName:@""];
    [self NavigationViewBackBtn];
}


- (void)viewDidUnload
{
    [super viewDidUnload];
    
    
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewWillAppear:NO];
}


- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:NO];
}

#pragma mark-  custom method for encapsulation navigationbar
- (void)SetNaviationTitleName:(NSString*)str
{
     UILabel*    titleLabel =  [[UILabel alloc] initWithFrame:CGRectMake(127, 16, 100, 32)];
    
    [titleLabel setTextColor:[UIColor whiteColor]];
    [titleLabel setBackgroundColor:[UIColor clearColor]];
    [titleLabel setFont:[UIFont boldSystemFontOfSize:16.0f]];
    NSLog(@"in 1111");
    [self.navigationItem setTitleView:titleLabel];
    [titleLabel setText:str];
}

- (CGFloat)GetNavigationBarPosY
{
    return 64.0f;
}




-(void)NavigationViewBackBtn
{
    UIButton* barbutton=[UIButton buttonWithType:UIButtonTypeCustom];
    UIImage*  iconImage = [UIImage imageNamed:@"gb_button"];
    [barbutton setImage:iconImage forState:UIControlStateNormal];
    [barbutton setFrame:CGRectMake(-12, 7, 30, 30)];
    [barbutton addTarget:self action:@selector(backToPrevious) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem* item = [[UIBarButtonItem alloc] initWithCustomView:barbutton];
    [self.navigationItem setLeftBarButtonItem:item];
    
}


- (void)backToPrevious
{
    NSLog(@"backPrevious");
    [self.navigationController  popViewControllerAnimated:YES];
}


-(void)NavigationHiddenBack
{
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:nil];

}




-(void)NavigationTitleColor:(UIColor*)color
{
    UILabel*  TitleLabel = (UILabel*)self.navigationItem.titleView;
    [TitleLabel setTextColor:color];
}



- (void)SetNaviationBgImg:(UIImage *)image
{
   [self.navigationController SetBackgroundImage:image];
}


- (void)PopBoxMsg:(NSString *)WarningMsg
{
    UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:WarningMsg message:nil delegate:self cancelButtonTitle:@"取消" otherButtonTitles:nil];
    [alertView show];
}


@end
