//
//  RootViewController.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <Foundation/NSObject.h>
#import "RootViewController.h"
#import "Config.h"


@interface RootViewController ()

@property  (nonatomic, strong)  MainNavigation* navigationbar;

- (void)SetLeftButton;

- (void)SetBackgroundImage;

- (void)SetNaviationTitle;

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
    self.navigationController.navigationBarHidden = YES;
    self.navigationbar = [[MainNavigation alloc] init];
    [self.navigationbar setFrame:self.navigationController.navigationBar.bounds];
    [self.view addSubview:self.navigationbar];
    [self SetBackgroundImage];
    [self SetNaviationTitle];
    [self SetLeftButton];
    
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
    [self.navigationbar NavigationTitleText:str];
}


- (void)SetNaviationRightButtons:(NSArray*)buttons
{
    [self.navigationbar NavigationViewRightBtns:buttons];
}


- (void)SetLeftButton
{
    if(self.navigationController.topViewController!=self)
    {
        UIButton* barbutton=[UIButton buttonWithType:UIButtonTypeCustom];
        
        [barbutton setFrame:CGRectMake(10, 0, 60, 36)];
        UIImage*  iconImage = [UIImage imageNamed:@"top_bt_bg"];
        [barbutton setBackgroundImage:iconImage forState:UIControlStateNormal];
        
        UIImage*  iconImage2 = [[UIImage imageNamed:@"gb_button"] stretchableImageWithLeftCapWidth:1 topCapHeight:1];
        [barbutton setImage:iconImage2 forState:UIControlStateNormal];
        
        [barbutton addTarget:self action:@selector(backToPrevious) forControlEvents:UIControlEventTouchUpInside];
        
        [self.navigationbar NavigationViewBackBtn:barbutton];
    }
}


- (void)backToPrevious
{
    [self.navigationController  popViewControllerAnimated:YES];
}


- (void)SetNaviationTitle
{
    [self.navigationbar NavigationTitle];
}


- (void)SetBackgroundImage
{
    [self.navigationbar NavigationBgImage:@"main_top_bg"];
}


- (void)PopBoxMsg:(NSString *)WarningMsg
{
    UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:WarningMsg message:nil delegate:self cancelButtonTitle:@"取消" otherButtonTitles:nil];
    [alertView show];
}


@end
