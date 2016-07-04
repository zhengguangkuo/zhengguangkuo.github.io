//
//  MenuViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "MenuViewController.h"
#import "AboutViewController.h"
#import "AccountViewController.h"
#import "AssistViewController.h"
#import "AppManagerController.h"
#import "BasicSettingController.h"
#import "HelpViewController.h"
#import "MenuViewController.h"
#import "PointViewController.h"
#import "updateViewController.h"
#import "MenuChooseController.h"
#import "MenuView.h"
#import "UIImage(addition).h"
#import "AppDelegate.h"

#define  New(XXX) [[UINavigationController alloc]initWithRootViewController:[[XXX##Controller alloc]init] ]

@interface MenuViewController ()

@property (nonatomic, strong) UIView* clearDeckView;

@property (nonatomic, strong) NSDictionary* dictionay;

@property (nonatomic, strong) NSArray*  nameArray;


@end

@implementation MenuViewController

@synthesize clearDeckView;

@synthesize dictionay;

@synthesize nameArray;


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    UIImage* bgMenuImage = [UIImage UIImageScretchImage:@"leather_kafei_bg"];
    //[self NavigationTitleColor:[UIColor whiteColor]];
    [self SetNaviationBgImg:bgMenuImage];
    [self SetNaviationTitleName:@"我的设置"];
    [self NavigationHiddenBack];
    [self LoadSetingPage];
    
    
    
    self.viewDeckController.delegate = self;

    self.clearDeckView = [[UIView  alloc] init];
    
    UITapGestureRecognizer*tap=[[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(closeRightChouTi)];
    [clearDeckView addGestureRecognizer:tap];
}


-(void)closeRightChouTi
{
    [self.viewDeckController toggleLeftViewAnimated:YES];
}

- (void)viewDeckController:(IIViewDeckController*)viewDeckController didOpenViewSide:(IIViewDeckSide)viewDeckSide animated:(BOOL)animated
{
    CGRect framee=viewDeckController.slidingController.view.frame;
    
    clearDeckView.frame=CGRectMake(framee.origin.x, framee.origin.y+44, framee.size.width, framee.size.height);
    
    AppDelegate* app = [AppDelegate getApp];
    if(viewDeckController.centerController!=app.HomeNavigation)
  {
    viewDeckController.centerController=app.HomeNavigation;
    viewDeckController.leftController=app.MenuNavigation;
  }
    
    

    
    
    
    
    [viewDeckController.slidingController.view endEditing:YES];
    
    [viewDeckController.slidingController.view addSubview:clearDeckView];
}


- (void)viewDeckController:(IIViewDeckController*)viewDeckController didCloseViewSide:(IIViewDeckSide)viewDeckSide animated:(BOOL)animated
{
    [clearDeckView removeFromSuperview];
    
    NSLog(@"dfdfclose");
}





- (void)LoadSetingPage
{
    self.nameArray = [NSArray arrayWithObjects:@"我的帐户",@"积分查询",@"基卡设置",@"套餐选择",@"应用管理",@"辅助功能",@"帮助",@"检查更新",@"关于",nil];
    
    self.dictionay = [[NSDictionary alloc] initWithObjectsAndKeys:
          New(AccountView),@"我的帐户",
          New(PointView),@"积分查询",
          New(BasicSetting),@"基卡设置",
          New(MenuChoose),@"套餐选择",
          New(AppManager),@"应用管理",
          New(AssistView),@"辅助功能",
          New(HelpView),@"帮助",
          New(updateView),@"检查更新",
          New(AboutView),@"关于",
      nil];
    
    
MenuView*  menu  = [[MenuView alloc]
                        initWithFrame:CGRectMake(0, 0, kScreenWidth, kScreenHeight)  dic:dictionay array:nameArray bg:[UIColor colorWithRed:163/255.0 green:153/255.0 blue:153/255.0 alpha:1] selectedBlock:^(int nIndex)
                {
                      NSString* keyName = nameArray[nIndex];
                    UINavigationController* nav = (UINavigationController*)dictionay[keyName];
                    
                    self.viewDeckController.centerController=nav;
                          
                        [self.viewDeckController
                                 closeLeftViewAnimated:YES completion:^(IIViewDeckController *controller, BOOL success) {
                                     
                                }];
                       }];
    
   

    [self.view addSubview:menu];
}







-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
}



-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:YES];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
