//
//  TTMainViewController.m
//  TTBarDemo
//
//  Created by TT on 14-6-3.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMainViewController.h"
#import "UINavigationItem+TTItem.h"
#import "TTCouponViewController.h"
#import "TTCommercialViewController.h"
#import "TTHomeViewController.h"
#import "TTPushMsgListViewController.h"
#import "TTSettingViewController.h"
#import "TTTabar.h"
#import "TTTieCoupponsViewController.h"
#import "TTBaseNavViewController.h"

#import "TTLoginViewController.h"
#import "TTCouponDetailViewController.h"
#import "TTAlertView.h"
#import "TTSettingLogOutViewController.h"

#define kTabarHeight 50
#define kPushBtn     @"pushBtn"
#define kRecordVC    @"recordVC"

#define kChildsVc(newNameVc) [[newNameVc alloc] init]
#define kTabarFrame CGRectMake(0,self.view.frame.size.height-kTabarHeight, self.view.frame.size.width, kTabarHeight)

@interface TTMainViewController() <TTTabarDelegate,UINavigationControllerDelegate>
{
    TTTabar                   * _tabar;
    UINavigationController    * _selectViewController;
    UIViewController          * _backViewController;
    NSString                  * _pushMeg;
    UIButton                  * _pushMegBtn;
}

@end

@implementation TTMainViewController

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    __unsafe_unretained TTMainViewController *main = self;
    static dispatch_once_t token;
    dispatch_once(&token, ^{
        //必须在显示第一个视图前添加，不然位置可能偏差。
        [main addTabar];
        
    });
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    //创建所有的C
    [self createChildControllers];

#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;

    }
#endif
    //刷新视图
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(againLoadView) name:TTUpdateHomeData object:nil];
    
    //返回上一级VC的通知
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(backViewController) name:TTSettingTabarBackVc object:nil];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateTabbarItemWithBadge) name:KNOTIFICATIONCENTER_UPDATEUITABBARITEM object:nil];
    
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(updateTabbarItemWithBadge) name:KNOTIFICATIONCENTER_MESSAGEFROMXMPP object:nil];
    
}
- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:TTSettingTabarBackVc object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:TTUpdateHomeData object:nil];
}
#pragma mark -导航控制器代理
- (void)navigationController:(UINavigationController *)navigationController willShowViewController:(UIViewController *)viewController animated:(BOOL)animated;
{
    UIViewController *root = navigationController.childViewControllers[0];
    //隐藏tabar操作
    if (viewController != root) {
        if ([viewController isKindOfClass:[TTCouponViewController class]]) {
            _tabar.frame = kTabarFrame;
            [self.view addSubview:_tabar];
        }else{
            navigationController.view.frame = self.view.bounds;
            [_tabar removeFromSuperview];
            [root.view addSubview:_tabar];
        }

    }else{

        navigationController.view.frame = CGRectMake(0,0, self.view.frame.size.width,self.view.frame.size.height-kTabarHeight);;
        [_tabar removeFromSuperview];
        _tabar.frame = kTabarFrame;
        [self.view addSubview:_tabar];
    }
    
    //主页更新操作
    if (viewController == root) {
        //移除
        [_pushMegBtn removeFromSuperview];
        if([TTAccountTool sharedTTAccountTool].currentAccount != nil)
        {
//            if ([root isKindOfClass:[TTHomeViewController class]]) {
//                
//                    root.navigationItem.title = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
//                    
//                    //主页重新刷新视图数据
//                    TTHomeViewController *home =(TTHomeViewController *)root;
//                    [home upDateHomeView];
//                }
                if (_pushMeg.length>0) {
                    if ([[TTSettingTool objectForKey:kPushBtn] boolValue]) return;
                    //消息通知
                    NSInteger   itemMeg = 4; //第几个item
                    _pushMegBtn = [UIButton buttonWithType:UIButtonTypeCustom];
                    [_pushMegBtn setBackgroundImage:[UIImage imageNamed:@"default_header_image"] forState:UIControlStateNormal];
                    _pushMegBtn.tag = 999;
                    //64 44
                    CGFloat megH = 20;
                    _pushMegBtn.frame = CGRectMake((64*itemMeg)-megH, 0, megH, megH);
                    _pushMegBtn.titleLabel.font = [UIFont systemFontOfSize:10];
                    
                    [_pushMegBtn setTitle:_pushMeg forState:UIControlStateNormal];
                    [_pushMegBtn addTarget:self action:@selector(hiddenBtn:) forControlEvents:UIControlEventTouchUpInside];
                    [_tabar addSubview:_pushMegBtn];
                }
            
        }else{
//                if ([root isKindOfClass:[TTHomeViewController class]]) {
//                    [_pushMegBtn removeFromSuperview];
//                    //主页重新刷新视图数据
//                    TTHomeViewController *home =(TTHomeViewController *)root;
//                    [home upDateHomeView];
//                    root.navigationItem.title = @"游客";
//
//                }
        }
    }


}
- (void)hiddenBtn:(UIButton *)sender
{
    UIButton *btn = (UIButton *)sender;
    btn.hidden = YES;
    [TTSettingTool setBool:btn.hidden forKey:kPushBtn];
}
//登录
- (void)gotoLogin
{
    TTLoginViewController *login = [[TTLoginViewController alloc] init];
    [_selectViewController pushViewController:login animated:YES];
}
//刷新视图
- (void)againLoadView
{
    if ([_tabar.delegate respondsToSelector:@selector(tabar:didSelectButtonto:)]) {
//        _tabar.didSelectDefault = ^NSInteger(void){
//            return 3;
//        };
        _tabar.didSelectDefault =^NSInteger(void){
            return 0;
        };
    }
}
//需要返回的视图
- (void)backViewController
{
    NSInteger   to = 0;

    if ([TTAccountTool sharedTTAccountTool].currentAccount) {
        to = [[TTSettingTool objectForKey:kUndoneLogin] integerValue];
        [TTSettingTool removeObjectForKey:kUndoneLogin];
    }else{
        to = [[TTSettingTool objectForKey:TTSettingTabarBackVc] integerValue];
    }
    if ( to>=0 && [_tabar.delegate respondsToSelector:@selector(tabar:didSelectButtonto:)]) {
        _tabar.didSelectDefault =^NSInteger(void){
            return to;
        };
    }
}
#pragma mark -andTabar
- (void)addTabar
{
    _tabar = [[TTTabar alloc] init];
    _tabar.frame = CGRectMake(0,self.view.frame.size.height-kTabarHeight, self.view.frame.size.width, kTabarHeight);
    _tabar.tag = 10000;
    _tabar.delegate = self;
    NSArray *items = @[@"主页",@"优惠",@"商圈",@"消息",@"更多"];
    
    [_tabar andTabBarButton:@"bt_home_normal" selectIcon:@"bt_home_highlighted" titile:items[0]];
    [_tabar andTabBarButton:@"bt_-tickit_normal" selectIcon:@"bt_-tickit_highlighted" titile:items[1]];
    [_tabar andTabBarButton:@"bt_-mplace_normal" selectIcon:@"bt_-mplace_highlighted" titile:items[2]];
    [_tabar andTabBarButton:@"bt_meg_normal" selectIcon:@"bt_meg_highlighted" titile:items[3]];
    [_tabar andTabBarButton:@"bt_more_normal" selectIcon:@"bt_more_highlighted" titile:items[4]];
    
    [self.view addSubview:_tabar];
    
    _tabar.didSelectDefault =^NSInteger(void){
        return 0;
    };
    
}
- (void)addChildViewController:(UIViewController *)childController
{
    TTBaseNavViewController *nav = [[TTBaseNavViewController alloc] initWithRootViewController:childController];
    nav.delegate = self;
    [super addChildViewController:nav];
}

- (void)createChildControllers
{
    /*
     * 0.主页（默认加载了）
     */
    TTHomeViewController *home = kChildsVc(TTHomeViewController);
    [self addChildViewController:home];
    
    /*
     * 1. 优惠劵 (未加载)
     */
    TTCouponViewController *coupon = kChildsVc(TTCouponViewController);
    [self addChildViewController:coupon];
    
    /*
     * 2.商圈（加载了）
     */
    TTCommercialViewController *commercial = kChildsVc(TTCommercialViewController);
    [self addChildViewController:commercial];
    
    /*
     * 3.消息(未加载)
     */
    TTPushMsgListViewController*  push = kChildsVc(TTPushMsgListViewController);
    [self addChildViewController:push];
    
    /*
     * 4.(更多)设置（加载了）
     */
    TTSettingViewController *setting = kChildsVc(TTSettingViewController);
    [self addChildViewController:setting];
    
    /*
     * 5.(更多)设置（加载了）
     */
    TTSettingLogOutViewController *settingLogout = kChildsVc(TTSettingLogOutViewController);
    [self addChildViewController:settingLogout];

}

#pragma mark -tabarDelegate监听点击
- (void)tabar:(TTTabar *)tabar didSelectButtonto:(NSUInteger)to
{
    if (to==3) {
        for (UIButton *btn in tabar.subviews) {
            if (btn.tag == 999) {
                [TTSettingTool setBool:1 forKey:kPushBtn];
            }
        }
    }
    UINavigationController *newVc = nil;
    if ([TTAccountTool sharedTTAccountTool].currentAccount == nil && to == 4) {
        newVc = self.childViewControllers[to+1];

    } else {
        newVc = self.childViewControllers[to];
    }
    
    if (![newVc.viewControllers[0] isKindOfClass:[TTSettingLogOutViewController class]]) {
        
        [TTSettingTool setInteger:to forKey:kUndoneLogin];
    }
    
    [self.navigationItem copyFromItem:newVc.navigationItem];

    if (newVc == _selectViewController) return;
    
    
    if ([TTAccountTool sharedTTAccountTool].currentAccount == nil) {
        if ([newVc.viewControllers[0] isKindOfClass:[TTPushMsgListViewController class]]||[newVc.viewControllers[0] isKindOfClass:[TTSettingViewController class]]) {

            [self gotoLogin];
            return;
        }
    }
    [_selectViewController.view removeFromSuperview];

    [TTSettingTool setInteger:to forKey:TTSettingTabarBackVc];
//    for (TTBaseNavViewController *baseNav in self.childViewControllers) {
//        if (baseNav == newVc) {
//
//        }
//    }

    
    newVc.view.frame = CGRectMake(0,0, self.view.frame.size.width,self.view.frame.size.height-kTabarHeight);
    [self.view addSubview:newVc.view];
        
    _selectViewController = newVc;
}
- (void)updateTabbarItemWithBadge
{
    if (_tabar.selectIndex == 3) {
        [SystemDialog alert:@"新消息来啦！请刷新"];
    } else {
        self.tabBarItem.badgeValue = @"N";
        _pushMeg = self.tabBarItem.badgeValue;
        /*
         *  0未开 1为开
         */
        [TTSettingTool setBool:0 forKey:kPushBtn];
    }
    
}

@end