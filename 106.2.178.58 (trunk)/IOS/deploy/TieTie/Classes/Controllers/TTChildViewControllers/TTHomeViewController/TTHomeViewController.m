//
//  TTHomeViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//
#import "TTHomeViewController.h"
#import "CycleScrollView.h"
#import "TTGridView.h"
#import "TTGridBtn.h"
#import "TTLoginViewController.h"
#import "TTTieCoupponsViewController.h"
#import "TTTieFenViewController.h"
#import "TTAccount.h"
#import "TTCouponViewController.h"
#import "TTPushMsgListViewController.h"
//#import "TTFriendViewController.h"
#import "TTAlertView.h"
#import "NavItemView.h"
#import "UIImage(addition).h"
#import "TTFriendsViewController.h"

#define kBillBoardHeight 230
#define kTitleEgeInset UIEdgeInsetsMake(0,60,50,0)
#define kNormalIcon [UIImage imageNamed:@"top_d"]
#define kSelectIcon [UIImage imageNamed:@"top_u"]
//城市保存
#define TTAreaFilePath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTAreaPlist]
@interface TTHomeViewController ()<TTGridviewDelegate,TTAlertViewDelegate>
{
    UIScrollView    *   _scrollView;
    UIImageView     *   _billBoardView; //头部默认
    CycleScrollView *   _cycleView;     //头部展示
    //  TTGridView      *   _girdView;      //app功能块
    
    NSMutableArray  *   _images;        //头部所有图片
    TTAlertView     *   _cityAlert;     //城市选择
    //    NavItemButton   *   _cityBtn;
    UIButton          *   _cityBtn;
}
@end

@implementation TTHomeViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _images = [NSMutableArray array];
    }
    return self;
}
- (void)loadView
{
    CGFloat tabarH = 44;
    CGFloat bottom = 20;
    if (iPhone5) {
        bottom = -(64 + tabarH);
    }
    UIScrollView *scrollView = [[UIScrollView alloc] initWithFrame:kScreenBounds];
    //    scrollView.contentSize = CGSizeMake(ScreenWidth, ScreenHeight-64);
    self.view = scrollView;
    scrollView.showsHorizontalScrollIndicator = NO;
    _scrollView = scrollView;
    
}
- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    
    //开启timer
    [_cycleView.animationTimer setFireDate:[NSDate distantPast]];
    
}
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    
    if ([TTAccountTool sharedTTAccountTool].currentAccount) {
        
        self.title = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
        //主页重新刷新视图数据
        _girdView.portalView.hidden = YES;
        [self upDateHomeView];
        
    }else{
        _girdView.portalView.hidden = NO;
        self.title = @"游客";
        [self upDateHomeView];
    }
    
}
- (void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:YES];
    //关闭
    [_cycleView.animationTimer setFireDate:[NSDate distantFuture]];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    TTLog(@"主页 = TTHomeViewController");
    self.view.backgroundColor = TTGlobalBg;
    TTLog(@"当前城市:%@",[TTAccountTool sharedTTAccountTool].currentCity.areaName);
    [TTSettingTool setInteger:0 forKey:TTClickFoterDockItem];
    //加载地区列表
    [self loadSaveCityList:[TTAccountTool sharedTTAccountTool].currentCity.superArea];
    
    //导航
    [self setNavTheme:[TTAccountTool sharedTTAccountTool].currentCity.areaName];
    //广告栏
    [self addBillboardView];
    
    [self loadBillboardView];
    
    //登录入口view
    [self upDateHomeView];
}
- (void)setNavTheme:(NSString *)cityName
{
    //    self.title = @"主页";
    _cityBtn = [[UIButton alloc] init];
    _cityBtn.backgroundColor = [UIColor clearColor];
    _cityBtn.userInteractionEnabled = NO;
    _cityBtn.frame = CGRectMake(10,0,60,44);
    [_cityBtn setTitle:cityName forState:UIControlStateNormal];
    //    [_cityBtn setImage:kNormalIcon forState:UIControlStateNormal];
    _cityBtn.titleEdgeInsets = UIEdgeInsetsMake(0, 0, 0, -40);
    _cityBtn.titleLabel.font = [UIFont systemFontOfSize:16];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_cityBtn];
    [_cityBtn addTarget:self action:@selector(selectedArea:) forControlEvents:UIControlEventTouchDown];
    
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.imageEdgeInsets = btnEdgeLeft;
    btn.frame = CGRectMake(0, 0, 57, 20);
    btn.userInteractionEnabled = NO;
    [btn setBackgroundImage:[UIImage imageNamed:@"tietie_top_logo"] forState:UIControlStateNormal];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
}

//获取列表
- (void)selectedArea:(NavItemButton *)btn
{
    
    [self performSelector:@selector(showCityList) withObject:nil afterDelay:0.3];
    
}
- (void)showCityList
{
    [_cityBtn setImage:kSelectIcon forState:UIControlStateNormal];
    _cityAlert = [[TTAlertView alloc] init];
    _cityAlert.deletage = self;
    [_cityAlert show:self];
}
//alertViewDelegage
- (void)getCityName:(NSString *)cityName
{
    [self setNavTheme:cityName];
    [_cityAlert dismiss];
    
    if (![cityName isEqualToString:[TTAccountTool sharedTTAccountTool].currentCity.areaName]) {
        //更新地区列表
        [self loadSaveCityList:[TTAccountTool sharedTTAccountTool].currentCity.areaName];
    }
    //选定城市、加载数据
    //    [self loadCityData];
}
//城市选择框显示
- (void)isShowAlertView
{
    //显示改变按钮图片
    [_cityBtn setImage:kNormalIcon forState:UIControlStateNormal];
    
}
#pragma mark - 添加广告栏
- (void)addBillboardView
{
    _billBoardView = [[UIImageView alloc] initWithImage:[UIImage stretchImageWithName:@"banner_dot_normal"]];
    _billBoardView.userInteractionEnabled = YES;
    _billBoardView.frame = CGRectMake(0, 0, self.view.frame.size.width,iPhone5?ScreenHeight*0.45:ScreenHeight*0.35);
    
    [self.view addSubview:_billBoardView];
}
- (void)loadBillboardView
{
    //    NSArray *arr = @[@"http://c.hiphotos.baidu.com/image/w%3D230/sign=1bed9accc1cec3fd8b3ea076e689d4b6/faedab64034f78f055c9fdf37b310a55b2191ceb.jpg",@"http://img8.house365.com/wxhome/news/2013/08/12/137629253552088eb74526b.jpg",@"http://c.hiphotos.baidu.com/image/w%3D230/sign=1bed9accc1cec3fd8b3ea076e689d4b6/faedab64034f78f055c9fdf37b310a55b2191ceb.jpg"
    //                     ];
    NSArray *arr = @[@"http://118.144.88.33:12041/image/banner1.png",
                     @"http://118.144.88.33:12041/image/banner2.png",
                     @"http://118.144.88.33:12041/image/banner3.png"
                     ];
    _cycleView = [[CycleScrollView alloc] initWithFrame:CGRectMake(0,0, ScreenWidth,_billBoardView.frame.size.height) animationDuration:3.0f];
    _cycleView.autoresizingMask =  UIViewAutoresizingFlexibleWidth|UIViewAutoresizingFlexibleBottomMargin;
    __unsafe_unretained TTHomeViewController  *home = self;
    UIImageView *imageView;
    //    for (NSString *url in arr) {
    //        imageView = [[UIImageView alloc]init];
    //        imageView.frame = _cycleView.frame;
    //        [imageView setImageWithURL:[NSURL URLWithString:url] placeholderImage:[UIImage imageNamed:@"load.png"]];
    //
    //        [_images addObject:imageView];
    //    }
    
    for (int i=0; i<arr.count; i++) {
        imageView = [[UIImageView alloc]init];
        imageView.frame = _cycleView.frame;
        [imageView setImageWithURL:[NSURL URLWithString:arr[i]]];
        [_images addObject:imageView];
    }
    
    
    [_cycleView setDataForView:[_images count]];
    //获取第pageIndex个位置的contentView
    _cycleView.fetchContentViewAtIndex = ^UIView *(NSInteger pageIndex){
        return home->_images[pageIndex];
    };
    //返回图片显示的页数
    _cycleView.totalPagesCount = ^NSInteger(void){
        return home->_images.count;
    };
    _cycleView.TapActionBlock = ^(NSInteger index){
        MyLog(@"点击了第%d张",index);
    };
    [_billBoardView addSubview:_cycleView];
}
#pragma mark -贴贴
/*
 *  业务区
 */
- (void)upDateHomeView
{
    if (!_girdView) {
        _girdView = [TTGridView gridView];
        _girdView.delegate = self;
        _girdView.bgImage.backgroundColor = TTGlobalBg;
        _girdView.frame = CGRectMake(10,_billBoardView.frame.size.height+15,ScreenWidth+15, 154);
        _girdView.bgImage.userInteractionEnabled = YES;
        
        _girdView.autoresizingMask = UIViewAutoresizingFlexibleBottomMargin;
        [_scrollView addSubview:_girdView];
        
    }
    if ([TTAccountTool sharedTTAccountTool].currentAccount==nil) {
        [self setNavTheme:[TTAccountTool sharedTTAccountTool].currentCity.areaName];
        _girdView.portalView.hidden = NO;
        _cycleView.autoresizingMask =  UIViewAutoresizingFlexibleWidth|UIViewAutoresizingFlexibleTopMargin;
        //未登录状态
        [_girdView.tieCoupon setImage:[UIImage imageNamed:@"button_t"] forState:UIControlStateNormal];
        [_girdView.tieCoupon setTitle:@"  优惠券" forState:UIControlStateNormal];
        [_girdView.tieDiscount setImage:[UIImage stretchImageWithName:@"button_s"] forState:UIControlStateNormal];
        [_girdView.tieDiscount setTitle:@"折扣优惠" forState:UIControlStateNormal];
        [_girdView.tieScore setImage:[UIImage stretchImageWithName:@"button_c"] forState:UIControlStateNormal];
        [_girdView.tieScore setTitle:@"积分优惠" forState:UIControlStateNormal];
    }else{
        _girdView.portalView.hidden = YES;
        //        for (UIView *view in self.girdView.subviews) {
        //            if ([view isKindOfClass:[UIButton class]]) {
        //                CGRect btnFrame = view.frame;
        //                btnFrame.origin.y-=(iPhone5?55:40);
        //                view.frame = btnFrame;
        //            }
        //        }
        //        [self.view insertSubview:_girdView.portalView aboveSubview:_girdView.bgImage];
        _girdView.portalView.imageView.image = [UIImage imageNamed:@"icon_t"];
        [_girdView.tieCoupon setImage:[UIImage stretchImageWithName:@"icon_t"] forState:UIControlStateNormal];
        [_girdView.tieCoupon setTitle:@"   贴券" forState:UIControlStateNormal];
        [_girdView.tieDiscount setImage:[UIImage stretchImageWithName:@"icon_user"] forState:UIControlStateNormal];
        [_girdView.tieDiscount setTitle:@"   贴友" forState:UIControlStateNormal];
        [_girdView.tieScore setImage:[UIImage stretchImageWithName:@"icon_c"] forState:UIControlStateNormal];
        [_girdView.tieScore setTitle:@"   贴分" forState:UIControlStateNormal];
    }
}
//登录入口
- (void)clickFastLogin:(UIButton *)btn
{
    TTLoginViewController *login = [[TTLoginViewController alloc] init];
    [self.navigationController pushViewController:login animated:YES];
    
}
//贴劵
- (void)clickCoupon:(UIButton *)btn
{
    if ([TTAccountTool sharedTTAccountTool].currentAccount!=nil) {
        
        TTTieCoupponsViewController *couponsVC = [[TTTieCoupponsViewController alloc]initWithNibName:@"TTTieCoupponsViewController" bundle:nil];
        [self.navigationController pushViewController:couponsVC animated:YES];
    }else{
        //        TTCouponViewController *coupon = [[TTCouponViewController alloc] init];
        //        [coupon setNavThemeTitle:btn.titleLabel.text flag:0];
        //        [self.navigationController pushViewController:coupon animated:YES];
        //        [TTSettingTool setInteger:0 forKey:TTSettingTabarBackVc];
        [TTSettingTool setInteger:0 forKey:TTClickFoterDockItem];
        [self goToCoupon];
    }
}
//贴友
- (void)clickDiscount:(UIButton *)btn
{
    if ([TTAccountTool sharedTTAccountTool].currentAccount!=nil) {
        //        TTFriendViewController * ftVC = [[TTFriendViewController alloc]init];
        //        [self.navigationController pushViewController:ftVC animated:YES];
        TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
        if (xmppTool.xmppStream.isConnected && xmppTool.xmppStream.isAuthenticated) {
            TTFriendsViewController *FVc = [[TTFriendsViewController alloc] init];
            [self.navigationController pushViewController:FVc animated:YES];
        } else {
            [SystemDialog alert:@"消息服务器未完成登陆，请稍等！"];
        }
        
    }else{
        //        [self gotoLogin];
        //        TTCouponViewController *coupon = [[TTCouponViewController alloc] init];
        //        [coupon setNavThemeTitle:btn.titleLabel.text flag:2];
        //        [self.navigationController pushViewController:coupon animated:YES];
        //        [TTSettingTool setInteger:2 forKey:TTSettingTabarBackVc];
        [TTSettingTool setInteger:1 forKey:TTClickFoterDockItem];
        [self goToCoupon];
    }
}
//贴分
- (void)clickScore:(UIButton *)btn
{
    if ([TTAccountTool sharedTTAccountTool].currentAccount!=nil) {
        TTTieFenViewController * ftVC = [[TTTieFenViewController alloc]initWithNibName:@"TTTieFenViewController" bundle:nil];
        [self.navigationController pushViewController:ftVC animated:YES];
    }else{
        //        TTCouponViewController *coupon = [[TTCouponViewController alloc] init];
        //        [coupon setNavThemeTitle:btn.titleLabel.text flag:1];
        //        [self.navigationController pushViewController:coupon animated:YES];
        //        [TTSettingTool setInteger:1 forKey:TTSettingTabarBackVc];
        [TTSettingTool setInteger:2 forKey:TTClickFoterDockItem];
        [self goToCoupon];
    }
}

- (void)clickQuickLogin:(UIButton *)btn
{
    [self gotoLogin];
}
//登录
- (void)gotoLogin
{
    TTLoginViewController *login = [[TTLoginViewController alloc] init];
    [self.navigationController pushViewController:login animated:YES];
    //    TTTieCoupponsViewController *couponsVC = [[TTTieCoupponsViewController alloc]initWithNibName:@"TTTieCoupponsViewController" bundle:nil];
    //    [self.navigationController pushViewController:couponsVC animated:YES];
}
- (void)clickExpect:(UIButton *)btn
{
    //    TTLog(@"push 敬请期待");
    //    TTPushMsgListViewController*  ttpush = [[TTPushMsgListViewController alloc] init];
    //    [self.navigationController pushViewController:ttpush animated:NO];
}

- (void)goToCoupon
{
    [TTSettingTool setInteger:1 forKey:TTSettingTabarBackVc];
    [[NSNotificationCenter defaultCenter] postNotificationName:TTSettingTabarBackVc object:nil];
    
}
#pragma mark 存储当前用户选定城市对应的地区列表
- (void)loadSaveCityList:(NSString *)superArea
{
    //    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryArea dict:@{@"superArea":superArea,@"sysPlat":@"5"} succes:^(id responseObject) {
        //        [self showLoading:NO];
        TTLog(@"当前地区列表%@",responseObject);
        NSString *key = [responseObject objectForKey:rspCode];
        NSString *result = [responseObject objectForKey:rspDesc];
        if ([key isEqualToString:rspCode_success]) {
            NSArray *citys = responseObject[@"areaList"];
            [citys writeToFile:TTAreaFilePath atomically:YES];
        }else{
            [SystemDialog alert:result];
            return ;
        }
    } fail:^(NSError *error) {
        //        [self showLoading: NO];
        TTLog(@"获取地区列表失败");
    }];
}
@end
