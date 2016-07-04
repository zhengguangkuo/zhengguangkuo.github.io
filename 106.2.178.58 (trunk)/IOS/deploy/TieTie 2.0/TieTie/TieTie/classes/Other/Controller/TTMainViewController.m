//
//  TTMainViewController.m
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMainViewController.h"
#import "TTBottomDock.h"
#import "TTMarketViewController.h"
#import "TTFriendsViewController.h"
#import "TTBusinessViewController.h"
#import "TTMineViewController.h"
#import "UINavigationItem+TTItem.h"
#import "TTBaseNavViewController.h"
#import "TTHomeViewController.h"
#import "TTLoginViewController.h"
@interface TTMainViewController ()<TTBottomDockDelegate,UINavigationControllerDelegate>




@end

@implementation TTMainViewController
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
       
        [self.tabBar removeFromSuperview];
        
//        for (UIControl *btn in self.childViewControllers) {
//            [btn removeFromSuperview];
//        }
        
        [self addBottomDock];
    });
}
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
    self.edgesForExtendedLayout = UIRectEdgeNone;
    self.view.backgroundColor = [UIColor whiteColor];
    
    [self addAllChildViewController];
}
- (void)addBottomDock
{
    TTBottomDock *bottomDock = [TTBottomDock bottomDock];
    bottomDock.autoresizingMask = UIViewAutoresizingFlexibleTopMargin|UIViewAutoresizingFlexibleWidth;
    bottomDock.delegate = self;
    bottomDock.frame = CGRectMake(0,self.view.frame.size.height-kBottomDockH, self.view.frame.size.width,kBottomDockH);
    
    [bottomDock andBottomButton:@"icon_tab_wode_normal" selectIcon:@"icon_tab_wode_highlight" title:@"首页"];
    [bottomDock andBottomButton:@"icon_tab_wode_normal" selectIcon:@"icon_tab_wode_highlight" title:@"市场"];
    [bottomDock andBottomButton:@"icon_tab_wode_normal" selectIcon:@"icon_tab_wode_highlight" title:@"贴友"];
    [bottomDock andBottomButton:@"icon_tab_wode_normal" selectIcon:@"icon_tab_wode_highlight" title:@"商圈"];
    [bottomDock andBottomButton:@"icon_tab_wode_normal" selectIcon:@"icon_tab_wode_highlight" title:@"我的"];
    
    [self.view addSubview:bottomDock];


}
- (void)bottomDock:(TTBottomDock *)bottomDock btnClickFrom:(int)from to:(int)to
{
    if (to!=0) {
        [[UIApplication sharedApplication].keyWindow viewWithTag:100].hidden = YES;
    }
    
    self.selectedIndex = to;
    
    UIViewController *newVC = self.childViewControllers[to];
    [self.navigationItem copyFromItem:newVC.navigationItem];
}
- (void)addAllChildViewController
{
    TTHomeViewController *home = creatVc(TTHomeViewController);
    TTMarketViewController *market = creatVc(TTMarketViewController);
    TTFriendsViewController*friend = creatVc(TTFriendsViewController);
    TTBusinessViewController *business = creatVc(TTBusinessViewController);
    TTMineViewController *mine = creatVc(TTMineViewController);
    [self addChildViewController:home];
    [self addChildViewController:market];
    [self addChildViewController:friend];
    [self addChildViewController:business];
    [self addChildViewController:mine];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
