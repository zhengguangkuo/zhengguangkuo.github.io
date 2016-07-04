//
//  MTMainViewController.m
//  MTBarDemo
//
//  Created by MT on 14-6-3.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "MTMainViewController.h"
#import "UINavigationItem+MTItem.h"
#import "HallViewController.h"
#import "AwardInfoViewController.h"
#import "UnionBuyViewController.h"
#import "LuckyNumViewController.h"
#import "MineViewController.h"
#import "MTTabar.h"

#define kTabarHeight 50

#define kChildsVc(newNameVc) [[newNameVc alloc] init]

@interface MTMainViewController() <MTTabarDelegate>
{
    MTTabar *_tabar;
    UIViewController    *_selectViewController;
}
@end

@implementation MTMainViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    //创建所有的C
    [self createChildControllers];
    
    //添加tabar
    [self addTabar];
}
- (void)addTabar
{
    _tabar = [[MTTabar alloc] init];
    _tabar.frame = CGRectMake(0,self.view.frame.size.height-kTabarHeight-(isIOS7), self.view.frame.size.width, kTabarHeight);
    _tabar.delegate = self;
    for (int i = 1; i<=5; i++) {
        NSString *normal = [NSString stringWithFormat:@"TabBar%d", i];
        NSString *selected = [normal stringByAppendingString:@"Sel"];
//        NSString *title = [NSString stringWithFormat:@"%d号",i];
//        [_tabar andTabBarButton:normal selectIcon:selected titile:title];
        [_tabar andTabBarButton:normal selectIcon:selected];
    }
    [self.view addSubview:_tabar];
    _tabar.didSelectDefault =^NSInteger(void){
        return 2;
    };
}
- (void)createChildControllers
{
    /*
     * group样式 手动创建
     */
    //购彩大厅
    HallViewController *hall = kChildsVc(HallViewController);
    hall.view.backgroundColor = [UIColor redColor];
    [self addChildViewController:hall];
    //合买跟单
    UnionBuyViewController *unionBuy = kChildsVc(UnionBuyViewController);
    unionBuy.view.backgroundColor = [UIColor blueColor];
    [self addChildViewController:unionBuy];
    //开奖信息
    AwardInfoViewController *awardInfo = kChildsVc(AwardInfoViewController);
    awardInfo.view.backgroundColor = [UIColor greenColor];
    [self addChildViewController:awardInfo];
    //幸运选号
    LuckyNumViewController *luckyNum = kChildsVc(LuckyNumViewController);
    luckyNum.view.backgroundColor = [UIColor grayColor];
    [self addChildViewController:luckyNum];
    //我的彩票
    MineViewController *main = kChildsVc(MineViewController);
    main.view.backgroundColor = [UIColor blackColor];
    [self addChildViewController:main];
}
#pragma mark -tabarDelegate监听点击
- (void)tabar:(MTTabar *)tabar didSelectButtonto:(NSUInteger)to
{
    //取出新的控制器
    UITableViewController *newVc = self.childViewControllers[to];
    [self.navigationItem copyFromItem:newVc.navigationItem];
    if (newVc == _selectViewController) return;
    
    [_selectViewController.view removeFromSuperview];
    
    newVc.view.frame = CGRectMake(0,0, self.view.frame.size.width, self.view.frame.size.height-kTabarHeight);
    
    [self.view addSubview:newVc.view];
    
    _selectViewController = newVc;
}

@end