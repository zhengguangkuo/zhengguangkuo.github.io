//
//  TTCouponDetailViewController.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponDetailViewController.h"

#import "TTDetailHeadView.h"
#import "TTCouponItem.h"
#import "TTCouponArrowItem.h"
#import "TTCouponDisScoreCell.h"
#import "TTCoupon.h"
#import "TTCouponDetail.h"
#import "TTActivity.h"
#import <MessageUI/MFMessageComposeViewController.h>
#import "WXApi.h"
#import "WXApiObject.h"
#import "TTHeadCell.h"
#import "TTCouponMerDetail.h"
#import "TTCouponBaseCell.h"
#import "TTTopCell.h"
#import "TTTopItem.h"
#import "TTCouponMerCell.h"
#import "NSDictionary(JSON).h"
#import "TTTieCoupponsViewController.h"
#import "BusinessDetailsViewController.h"
#import "TTCreditsCoupon.h"
#import "TTArrowItem.h"
#import "TTMoreCell.h"
#import "TTWebCell.h"

#define kSectionH 15
#define kDownBtnH 44
#define kArrowRow 35.5

@interface TTCouponDetailViewController ()<UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,TTActivityDelegate,MFMessageComposeViewControllerDelegate,UIAlertViewDelegate,UIScrollViewDelegate,UIWebViewDelegate>
{
    TTDetailHeadView  *   _headView;
    UITableView       *   _couponView;
    NSMutableArray    *   _allDatas;
    UIScrollView      *   _scrollView;
    
    NSString          *   _longitude;       //经度
    NSString          *   _latitude;        //纬度
    NSMutableArray    *   _employMers;      //使用数据
    NSMutableArray    *   _grantMers;       //发放数组
    UIWebView         *   _moreWeb;         //web
    NSString          *   _moreData;        //点击更多数据
    NSDictionary      *   _tempCredits;     //积分详情
    NSMutableArray    *   _merList;         //商家列表
    NSMutableArray    *   _merDatas;        //商家列表
    NSString          *   _couponIcon;      //优惠劵icon
    UIView            *   _view;
    CGFloat               _tableviewOffset; //tableview的到顶部的距离
    UIButton          *   _topDown;
    //使用商家、发放商家 记录
    CGFloat               _merEmployDis;
    CGFloat               _merGrantDis;
}

@end
@implementation TTCouponDetailViewController
- (void)loadView
{
    _scrollView  = [[UIScrollView alloc]initWithFrame:kScreenBounds];
    _scrollView.contentSize = CGSizeMake(ScreenWidth, ScreenHeight);
    _scrollView.delegate = self;
    _scrollView.scrollEnabled = NO;
    _scrollView.backgroundColor = white1;
    self.view = _scrollView;
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    static dispatch_once_t token;
    dispatch_once(&token, ^{
        [self getUserLocation]; //重新定位一次
        
    });
    
    [self removeDown];
    
    _tableviewOffset = 0;
    _tableviewOffset = 0.0f;
    
    //清空数据
    [_allDatas removeAllObjects];
    [_moreWeb removeFromSuperview];
    _moreWeb = nil;
    
    //如果展开多个商家
    if (_merEmployDis + _merGrantDis > 0) {
        CGRect tabF = _couponView.frame;
        tabF.size.height -= _merEmployDis+_merGrantDis;
        _couponView.frame = tabF;
    }
    
    _moreData = @"";
    _scrollView.contentSize = CGSizeMake(ScreenWidth, _couponView.height);
    _couponView.scrollEnabled = YES;
    
    
    if ([self.object isKindOfClass:[TTCoupon class]]) {
        [TTSettingTool setInteger:0 forKey:TTClickFoterDockItem];
        self.title = @"优惠券详情";
        self.coupon = self.object;
        
        //加载优惠详情
        [self loadCouponDetail];
    }
    if ([self.object isKindOfClass:[TTCreditsCoupon class]]) {
        [TTSettingTool setInteger:2 forKey:TTClickFoterDockItem];
        self.title = @"积分详情";
        self.creditsCoupon = self.object;
        
        //加载积分详情
        [self loadCreditsData];
    }
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    TTLog(@" 详情 = viewDidDisappear ");
    [self removeDown];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    _moreData = @"";
    _allDatas = [NSMutableArray array];
    _merList = [NSMutableArray array];
    _tempCredits = [NSDictionary dictionary];
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
    }
#endif
    //导航
    [self setNavTheme];
    [self addTableView];
    
    //tableview
    
}
- (void)loadCreditsData
{
    //    TTCouponItem *title = [TTCouponItem couponItemWithTitle:@"优惠发放商家"];
    NSDictionary *dict = @{
                           @"activityId":self.creditsCoupon.activityId,
                           @"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_creditsDetail dict:dict succes:^(id responseObject) {
        //        TTLog(@"积分详情 = %@",[responseObject JSONString]);
        _tempCredits = responseObject;
        
        TTCreditsCoupon * credits= [[TTCreditsCoupon alloc] initWithDict:responseObject];
        self.creditsCoupon = credits;
        TTCouponDetail *activtName = [TTCouponDetail couponDetail];
        activtName.actName = self.creditsCoupon.activityName;
        //自定义规则 =3 显示日期
        activtName.sendType = @"3";
        NSString *startDate = [NSString processDateMethod:_creditsCoupon.startDate];
        NSString *endDate = [NSString processDateMethod:_creditsCoupon.endDate];
        activtName.issuedCnt = [NSString stringWithFormat:@"%@-%@",startDate,endDate];
        [_allDatas addObject:@[activtName]];
        
        //商家
        //        TTCouponMerDetail *merCredits = self.creditsCoupon.merCoupon;
        _merList = self.creditsCoupon.merchsList;
        //        TTCouponMerDetail *merCredits = _merList[0];
        NSMutableArray *arr = [NSMutableArray array];
        for (NSDictionary *dict  in _merList) {
            TTCouponMerDetail *merCredits = [[TTCouponMerDetail alloc] initWithDict:dict];
            [arr addObject:merCredits];
        }
        
        TTCouponItem *ruleTitle = [TTCouponItem couponItemWithTitle:@"积分规则"];
        TTTopItem *creditRule = [TTTopItem topItem];
        creditRule.title = self.creditsCoupon.creditRule;
        [_allDatas addObject:@[ruleTitle,creditRule]];
        
        TTCouponItem *cashTitle = [TTCouponItem couponItemWithTitle:@"抵现规则"];
        TTTopItem *cashRule = [TTTopItem topItem];
        cashRule.title = [_tempCredits objectForKey:@"cashRule"];
        [_allDatas addObject:@[cashTitle,cashRule]];
        
        TTCouponItem *title = [TTCouponItem couponItemWithTitle:@"返分列表"];
        if (arr.count>0) {
            [_allDatas addObject:@[title,arr[0]]];
        }
        
        
        TTCouponItem *detailTitle = [TTCouponItem couponItemWithTitle:@"使用须知"];
        TTTopItem *detail = [TTTopItem topItem];
        detail.title = self.creditsCoupon.detail;
        [_allDatas addObject:@[detailTitle,detail]];
        
        [_couponView reloadData];
        
        [self showLoading:NO];
    } fail:^(NSError *err) {
        [self showLoading:NO];
    }];
}
- (void)setNavTheme
{
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
    
}
- (void)backPrevious
{
    [TTSettingTool setBool:0 forKey:kflagExitDetal];
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)addTableView
{
    CGRect frame = CGRectMake(0,0,ScreenWidth,ScreenHeight-64);
    _couponView = [[UITableView alloc] initWithFrame:frame style:UITableViewStyleGrouped];
    _couponView.delegate = self;
    _couponView.dataSource = self;
    _couponView.sectionHeaderHeight = 0;
    _couponView.backgroundColor = white1;
    [_scrollView addSubview:_couponView];
}

#pragma mark -加载优惠劵详情
- (void)loadCouponDetail
{
    NSString *latitude = @"";
    NSString *longitude = @"";
    if ([[TTSettingTool objectForKey:TTLatitude] floatValue]>0) {
        
        latitude = [NSString stringWithFormat:@"%@",[TTSettingTool objectForKey:TTLatitude]];
        longitude = [NSString stringWithFormat:@"%@",[TTSettingTool objectForKey:TTLongitude]];
    }

    //    __unsafe_unretained TTCouponDetailViewController *detail = self;
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_findCouponAct dict:@{@"actId": self.coupon.actId,@"sysPlat":@"5"} succes:^(id responseObject) {
        //TTLog(@"优惠劵详情%@",[responseObject JSONString]);
        TTCouponDetail *detail = [[TTCouponDetail alloc] initWithDict:responseObject];
        [_allDatas addObject:@[detail]];
        _moreData = detail.actDetail;   //更多最后一行
        _couponIcon = detail.IconPic;
        //        TTLog(@"优惠劵详情%@",detail.Rule);
        if (_allDatas.count > 0) {
            //加载发放、使用商家
            //-----------------------------------------------------------------------------------------------------
            //使用商家
            //            [self loadGrantMer];
            NSDictionary *dict = @{@"latitude":latitude,
                                   @"longitude":longitude,
                                   @"actId":self.coupon.actId,
                                   @"radius":@"",
                                   @"type":@"0",
                                   @"sysPlat":@"5"};
            [TieTieTool tietieWithParameterMarked:TTAction_queryCouponMerch dict:dict succes:^(id responseObject) {
                //                TTLog(@"使用商家 = %@",[responseObject JSONString]);
                NSArray *employMers = responseObject[@"couponMerchList"];
                NSMutableArray  *employ = [NSMutableArray array];
                for (NSDictionary *dict in employMers) {
                    TTCouponMerDetail *merDetail = [[TTCouponMerDetail alloc] initWithDict:dict];
                    [employ addObject:merDetail];
                }
                _employMers = employ;
                TTCouponItem *title = [TTCouponItem couponItemWithTitle:@"优惠使用商家"];
                if (employ.count>1) {
                    
                    TTCouponArrowItem *branch = [TTCouponArrowItem couponItemWithTitle:[NSString  stringWithFormat:@"查看全部%d家分店",employ.count]];
                    //                    branch.operation = ^(NSInteger section){
                    //                       [_allDatas addObject:@[title,employ[2]]];
                    //                        [_allDatas insertObject:employ[2] atIndex:row];
                    //                        [_couponView reloadData];
                    //                    };
                    if (employ.count>0) {
                        
                        [_allDatas addObject:@[title,employ[0],branch]];
                    }
                }else{
                    if (employ.count > 0) {
                        
                        [_allDatas addObject:@[title,employ[0]]];
                    }
                    
                }
                //-----------------------------------------------------------------------------------------------------
                if ([detail.sendType isEqualToString:@"2"]) {
                    //发放
                    //                [self loadEmployMer];
                    //发放商家
                    
                    TTCouponItem *title = [TTCouponItem couponItemWithTitle:@"优惠发放商家"];
                    NSDictionary *dict = @{@"latitude":latitude,
                                           @"longitude":longitude,
                                           @"actId":self.coupon.actId,
                                           @"radius":@"",
                                           @"type":@"1",
                                           @"sysPlat":@"5"};
                    //                    [self showLoading:YES];
                    [TieTieTool tietieWithParameterMarked:TTAction_queryCouponMerch dict:dict succes:^(id responseObject) {
                        //                        TTLog(@"发放商家 = %@",[responseObject JSONString]);
                        NSArray *mers = responseObject[@"couponMerchList"];
                        NSMutableArray *allMer = [NSMutableArray array];
                        for (NSDictionary *dict in mers) {
                            //                            TTLog(@"%@",dict);
                            TTCouponMerDetail *merDetail = [[TTCouponMerDetail alloc] initWithDict:dict];
                            [allMer addObject:merDetail];
                        }
                        _grantMers = allMer;
                        if (allMer.count>1) {
                            TTCouponArrowItem *branch = [TTCouponArrowItem couponItemWithTitle:[NSString  stringWithFormat:@"查看全部%d家分店",allMer.count]];
                            [_allDatas addObject:@[title,allMer[0],branch]];
                            
                        }else{
                            if (allMer.count>0) {
                                
                                [_allDatas addObject:@[title,allMer[0]]];
                            }
                        }
                        
                        [self loadMore:detail.Rule];
                        [_couponView reloadData];
                        [self showLoading:NO];
                    } fail:^(NSError *err) {
                        [self showLoading:NO];
                    }];
                }else{
                    
                    [self loadMore:detail.Rule];
                }
                
                
                //-----------------------------------------------------------------------------------------------------
                [_couponView reloadData];
//                [self showLoading:NO];
            } fail:^(NSError *error) {
//                [self showLoading:NO];
            }];
        }
        
        [self showLoading:NO];
        [_couponView reloadData];
    } fail:^(NSError *error) {
        [self showLoading:NO];
    }];
}

#pragma mark -加载更多按钮
- (void)loadMore:(NSString *)rule
{
    TTCouponItem *couponRule = [TTCouponItem couponItemWithTitle:@"优惠使用须知"];
    TTTopItem *item = [TTTopItem topItem];
    item.title = rule;
    [_allDatas addObject:@[couponRule,item]];
    
    TTArrowItem *moreBtn = [[TTArrowItem alloc] init];
    moreBtn.arrowTitle = @"点击加载更多";
    [_allDatas addObject:@[moreBtn]];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return _allDatas.count;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    NSArray *arr = _allDatas[section];
    return arr.count;
}

#pragma mark
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTCouponBaseCell *baseCell = [TTCouponBaseCell couponBaseCellWithTableView:tableView];
    id obj = _allDatas[indexPath.section][indexPath.row];

    if ([obj isKindOfClass:[TTCouponDetail class]]) {
        TTTopCell *cell = [TTTopCell topCellWithTableView:tableView];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.coupondetail = obj;
        return cell;
    }else if([obj isKindOfClass:[TTCouponMerDetail class]]){
        TTCouponMerCell *merCell = [TTCouponMerCell couponMerCellWithTableView:tableView];
        merCell.couponMer = obj;
        return merCell;
    }else if([obj isKindOfClass:[TTCouponArrowItem class]]){
        TTCouponDisScoreCell *cell = [TTCouponDisScoreCell couponDisScoreCellWithTableView:tableView];
        cell.item = obj;
        return cell;
    }else if([obj isKindOfClass:[TTTopItem class]]){
        TTTopItem *item = obj;
        
        TTWebCell *webCell = [TTWebCell webCellWithTableView:tableView];
        [webCell setContentText:item.title];
        webCell.textLabel.font = [UIFont systemFontOfSize:12];
        webCell.selectionStyle = UITableViewCellSelectionStyleNone;
        return webCell;
    }else if([obj isKindOfClass:[TTArrowItem class]]){
        TTArrowItem *arrowItem = obj;
        TTMoreCell *more = [TTMoreCell moreCellWithTableView:tableView];
        more.moreTitle.text = arrowItem.arrowTitle;
        
        return more;
    }else{
        TTCouponItem *item = obj;
        baseCell.textLabel.text = item.title;
        baseCell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return baseCell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    id obj = _allDatas[indexPath.section][indexPath.row];
    
    CGFloat tabContenH = 0;
    if ([obj isKindOfClass:[TTCouponArrowItem class]]) {
        
        
        TTCouponItem *item = _allDatas[indexPath.section][0];
        [_allDatas removeObject:_allDatas[indexPath.section]];
        if ([item.title isEqualToString:@"优惠使用商家"]) {
            TTCouponItem *merMeg = [TTCouponItem couponItemWithTitle:@"优惠使用商家"];
            [_employMers insertObject:merMeg atIndex:0];
            
            [_allDatas insertObject:_employMers atIndex:indexPath.section];
            
            CGFloat moveDown =53*(_employMers.count-2)-kArrowRow;
            CGRect webF = _moreWeb.frame;
            webF.origin.y+= moveDown;
            _moreWeb.frame = webF;
            
            
            CGRect tabF = _couponView.frame;
            tabF.size.height += moveDown;
            _couponView.frame = tabF;
            
            
            tabContenH = moveDown;
            //控制器返回 减去的值
            _merEmployDis = moveDown;
            
            
        }
        if ([item.title isEqualToString:@"优惠发放商家"]) {
            TTCouponItem *merMeg = [TTCouponItem couponItemWithTitle:@"优惠发放商家"];
            [_grantMers insertObject:merMeg atIndex:0];
            [_allDatas insertObject:_grantMers atIndex:indexPath.section];
            
            CGFloat moveDown =53*(_grantMers.count-2)-kArrowRow;
            CGRect webF = _moreWeb.frame;
            webF.origin.y+= moveDown;
            _moreWeb.frame = webF;
            
            CGRect tabF = _couponView.frame;
            tabF.size.height += moveDown;
            _couponView.frame = tabF;
            
            tabContenH = moveDown;
            
            _merGrantDis = moveDown;
        }
        
        if ([item.title isEqualToString:@"返分商家列表"]) {
            TTCouponItem *merMeg = [TTCouponItem couponItemWithTitle:@"返分商家列表"];
            [_merList insertObject:merMeg atIndex:0];
            [_allDatas insertObject:_merList atIndex:indexPath.section];
            
            CGFloat moveDown =53*(_merList.count-2)-kArrowRow;
            
            CGRect webF = _moreWeb.frame;
            webF.origin.y+= moveDown;
            _moreWeb.frame = webF;
            
            CGRect tabF = _couponView.frame;
            tabF.size.height += moveDown;
            _couponView.frame = tabF;
            
            tabContenH = moveDown;
            
            _merGrantDis = moveDown;
            
        }
        
        if (!_moreWeb) {
            
            _couponView.frame = CGRectMake(0, 0, ScreenWidth, self.view.frame.size.height);
            _couponView.contentSize = CGSizeMake(ScreenWidth,self.view.frame.size.height);
        }
        
        [_couponView reloadData];
        
        
    }
    if ([obj isKindOfClass:[TTArrowItem class]]) {
        [_allDatas removeObject:[_allDatas lastObject]];
        TTCouponItem *merMeg = [TTCouponItem couponItemWithTitle:@"活动详情"];
        
        //        TTTopItem *item = [TTTopItem topItem];
        //        item.title =  _moreData;
        [_allDatas addObject:@[merMeg /*,item*/]];
        [_couponView reloadData];
        dispatch_async(dispatch_get_main_queue(), ^{
            if (!_moreWeb) {
                
                //获取tab实际高度
                CGRect tabF = _couponView.frame;
                tabF.size.height += _tableviewOffset;
                _couponView.frame = tabF;
                
                
                _moreWeb = [[UIWebView alloc] init];
                _moreWeb.delegate = self;
                _moreWeb.backgroundColor = [UIColor whiteColor];
                _moreWeb.frame = CGRectMake(0,_couponView.frame.size.height-37.5,ScreenWidth,44);
            }
            [_moreWeb loadHTMLString:_moreData baseURL:nil];
            [_moreWeb setScalesPageToFit:NO];
            _moreWeb.scrollView.bounces=NO;
            [(UIScrollView *)[[_moreWeb subviews] objectAtIndex:0]    setBounces:YES];
            //            [self.view insertSubview:_moreWeb aboveSubview:_couponView];
            [self.view addSubview:_moreWeb];
            
            [self showLoading:YES];
            
        });
        
    }
    if ([obj isKindOfClass:[TTCouponMerDetail class]]) {
        TTCouponMerDetail *mer = _allDatas[indexPath.section][indexPath.row];
        
        BOOL hasVC = NO;
        for (UIViewController *VC in self.navigationController.viewControllers) {
            if ([VC isKindOfClass:[BusinessDetailsViewController class]]) {
                hasVC = YES;
                BusinessDetailsViewController *merDetail = (BusinessDetailsViewController*)VC;
                merDetail.merchId = mer.merchId;
                [self.navigationController popToViewController:merDetail animated:NO];
                break;
            }
        }
        if (!hasVC) {
            BusinessDetailsViewController *merDetail = [[BusinessDetailsViewController alloc] init];
            merDetail.merchId = mer.merchId;
            [self.navigationController pushViewController:merDetail animated:NO];
        }
        
    }
    
}

#pragma mark - webDelegate
- (void)webViewDidFinishLoad:(UIWebView *)webView
{
    _couponView.scrollEnabled = NO;
    _scrollView.scrollEnabled = YES;
    
    CGFloat webViewHeight=[webView.scrollView contentSize].height;
    CGRect newFrame = webView.frame;
    newFrame.size.height = webViewHeight;
    webView.frame = newFrame;
    _scrollView.contentSize = CGSizeMake(ScreenWidth,_couponView.height+newFrame.size.height);
    [self showLoading:NO];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.section==0) {
        return 44;
    }
    id obj = _allDatas[indexPath.section][indexPath.row];
    if ([obj isKindOfClass:[TTCouponMerDetail class]]) {
        return 53;
    }else if([obj isKindOfClass:[TTCouponArrowItem class]]){
        return kArrowRow;
    }else if([obj isKindOfClass:[TTTopItem class]]){
        TTTopItem *item = obj;
        TTWebCell *cell = (TTWebCell *)[self tableView:_couponView cellForRowAtIndexPath:indexPath];
        [cell setContentText:item.title];
        return cell.frame.size.height;
    }else{
        
        return 35;
    }
}
#pragma mark -头、尾部
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    _view = [[UIView alloc] init];
    
    _headView = [TTDetailHeadView detailHeadView];
    _headView.headDownBg.backgroundColor = [UIColor clearColor];
    if (section == 0) {
        NSString *icon = @"";
        if ([self.object isKindOfClass:[TTCoupon class]]) {
            
            //            pic = self.coupon.picPath;
            icon = self.coupon.picPath;
            //            type = self.coupon.sendType;
            //顶部大图
            UIImageView *imageView = [[UIImageView alloc] init];
            [imageView setImageWithURL:[NSURL URLWithString:icon] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
            UIImage *image = [UIImage OriginImage:imageView.image scaleToSize:CGSizeMake(_headView.headImageView.frame.size.width, _headView.headImageView.frame.size.height)];
            _headView.headImageView.image = image;
//            [_headView.headImageView setImageWithURL:[NSURL URLWithString:icon] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];

            
            [_headView.headIcon setImageWithURL:[NSURL URLWithString:_couponIcon] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
            //领
            if ([self.coupon.sendType isEqualToString:@"0"]) {
                
                [_headView.headClaim setBackgroundImage:[UIImage imageNamed:@"icon_g"] forState:UIControlStateNormal];
                //                [_headView.headMerDetail addTarget:self action:@selector(merDetail) forControlEvents:UIControlEventTouchUpInside];
            }else{
                //返
                [_headView.headClaim setBackgroundImage:[UIImage imageNamed:@"icon_b"] forState:UIControlStateNormal];
                [_headView.headMerDetail removeFromSuperview];
            }
        }
        if ([self.object isKindOfClass:[TTCreditsCoupon class]]) {
            //顶部小图
            _headView.headIcon.hidden = YES;
            //            pic = self.creditsCoupon.picPath;
            icon = self.creditsCoupon.saasLogo;
            //顶部大图
//            [_headView.headImageView setImageWithURL:[NSURL URLWithString:icon] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
            //顶部大图
            UIImageView *imageView = [[UIImageView alloc] init];
            [imageView setImageWithURL:[NSURL URLWithString:icon] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
            
            UIImage *image = [UIImage OriginImage:imageView.image scaleToSize:CGSizeMake(_headView.headImageView.frame.size.width, _headView.headImageView.frame.size.height)];
            _headView.headImageView.image = image;
        }
        
        //分享
        [_headView.headShare addTarget:self action:@selector(share) forControlEvents:UIControlEventTouchUpInside];
        
        [_view addSubview:_headView];
        
    }
    if (section==1&&[self.coupon.sendType isEqualToString:@"0"]&&[self.object isKindOfClass:[TTCoupon class]]) {
        UIButton *downbtn = [self windowAndBtn];
        downbtn.frame = CGRectMake(5, 0, self.view.size.width-10, kDownBtnH);
        [_view addSubview:downbtn];
    }
    
    return _view;
}

//领取优惠劵
- (void)downLoadCoupon
{
    if ([TTAccountTool sharedTTAccountTool].currentAccount == nil) {
        [SystemDialog alert:@"您还未登陆!"];
        return;
    }
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"确定要领取该优惠么？"
                                                    message:nil
                                                   delegate:self
                                          cancelButtonTitle:@"返回"
                                          otherButtonTitles:@"领取", nil];
    alert.tag = 1000;
    
    [alert show];
}
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    if (section == 0) {
        return 165;
    }
    else if (section==1&&[self.coupon.sendType isEqualToString:@"0"]&&[self.object isKindOfClass:[TTCoupon class]]) {
        return kDownBtnH+kSectionH;
    }else{
        return 0;
    }
    
}
//表视图被上下拉时 (手指拖动时会不停的调该方法)scrollerViewDelegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
    scrollView.contentInset = UIEdgeInsetsZero;
    
    CGPoint contentOffsetPoint = scrollView.contentOffset;
    
    
    if ([self.coupon.sendType isEqualToString:@"0"]&&[self.object isKindOfClass:[TTCoupon class]]) {
        
        if ([scrollView isKindOfClass:[UITableView class]]) {
            //
            //               TTLog(@"\n     UITableView 偏移 = %f",contentOffsetPoint.y);
            if (scrollView.contentOffset.y>0) {
                
                _tableviewOffset =  contentOffsetPoint.y;
            }
            
        }
        //tablview 与scrollview不可同时滚动不好控制
        if (contentOffsetPoint.y>=225) {
            [self windowAndDown];
            
        }else{
            
            [self removeDown];
        }
        
    }
    
}

//手指离开屏幕时调用
//- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
//{
//    
//    
//    
//}

//下载优惠劵按钮
- (UIButton *)windowAndBtn
{
    UIButton *downBtn  = [UIButton buttonWithType:UIButtonTypeCustom];
    [downBtn setBackgroundImage:[UIImage imageNamed:@"detail_down_normal"] forState:UIControlStateNormal];
    [downBtn setBackgroundImage:[UIImage imageNamed:@"detail_down_selected"] forState:UIControlStateSelected];
    [downBtn addTarget:self action:@selector(downLoadCoupon) forControlEvents:UIControlEventTouchUpInside];
    return downBtn;
}
//添加顶部下载按钮
- (void)windowAndDown
{
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    
    UIButton *btn = [self windowAndBtn];
    btn.frame = CGRectMake(5, 65, self.view.size.width-10, kDownBtnH);
    btn.tag = 1000;
    [UIView animateWithDuration:0.3 animations:^{
        [window addSubview:btn];
    }];
}

/*
 * 分享 *
 */
#pragma mark - TTActivityDelegate 分享
- (void)share
{
    NSArray *shareTitles = @[/*@"微信",*/@"短信"];
    NSArray *shareIcons = @[/*@"weixin",*/@"sms"];
    
    TTActivity *actity = [[TTActivity alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" ShareButtonTitles:shareTitles withShareButtonImagesName:shareIcons];
    actity.delegate = self;
    [actity showInView:self.view];
}

- (void)didClickOnImageIndex:(NSInteger)imageIndex
{
    switch (imageIndex) {
        case 0:
            //            [self shareWX];
            //            break;
            //        case 1:
            [self shareSMS];
            break;
        default:
            break;
    }
}
//短信分享
- (void)shareSMS
{
    NSString *text = @"我正在参梅泰诺的\"贴贴活动\",各种优惠券等你来拿.详情请猛戳这里:http://www.miteno.com";
    MFMessageComposeViewController *messageCtrl = [[MFMessageComposeViewController alloc] init];
    if (MFMessageComposeViewController.canSendText) {
        [messageCtrl.navigationBar setTitleTextAttributes:@{UITextAttributeTextColor: [UIColor blackColor],
                                                            UITextAttributeFont :TTNavTitleFont,
                                                            }];
        messageCtrl.recipients = [NSArray arrayWithObjects:@"13556563434", nil];
        messageCtrl.messageComposeDelegate = self;
        messageCtrl.body = text;
        
        [self presentViewController:messageCtrl animated:YES completion:nil];
    }else{
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提示信息"
                                                        message:@"该设备不支持短信功能"
                                                       delegate:self
                                              cancelButtonTitle:nil
                                              otherButtonTitles:@"确定", nil];
        [alert show];
    }
    
}
#pragma mark - MFMessageComposeViewController delegate
- (void)messageComposeViewController:(MFMessageComposeViewController *)controller didFinishWithResult:(MessageComposeResult)result {
    
    if (result == MessageComposeResultCancelled) {
        [controller dismissViewControllerAnimated:YES completion:^{}];
    }
    else if(result == MessageComposeResultSent) {
        [SystemDialog alert:@"短信分享已发送"];
    }
    else if(result == MessageComposeResultFailed) {
        [SystemDialog alert:@"信息发送失败"];
    }
}
//微信分享
- (void)shareWX
{
    //启动
    [WXApi registerApp:TTWXAppID];
    
    if ([WXApi isWXAppSupportApi] && [WXApi isWXAppInstalled]) {
        
        SendMessageToWXReq *rep = [[SendMessageToWXReq alloc] init];
        rep.bText = YES;
        rep.text = @"开始分享";
        [WXApi sendReq:rep];
    }else{
        UIAlertView *alView = [[UIAlertView alloc]initWithTitle:@"" message:@"你的iPhone上还没有安装微信,无法使用此功能，使用微信可以方便的把你参加的活动分享给好友。" delegate:nil cancelButtonTitle:@"取消" otherButtonTitles:@"免费下载微信", nil];
        alView.delegate = self;
        alView.tag = 999;
        [alView show];
    }
    
}
#pragma mark -alertDelegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch (alertView.tag) {
        case 999:
        {
            if (buttonIndex == 1) {
                [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"http://itunes.apple.com/cn/app/id414478124"]];
            }
        }
            break;
        case 1000:
            if (buttonIndex == 1) {
                //领取优惠劵
                [self claimCoupon];
            }
        default:
            break;
    }
    
}
//确认领取优惠劵
- (void)claimCoupon
{
    [TieTieTool tietieWithParameterMarked:TTAction_receiveCoupon dict:@{@"userId":[TTAccountTool sharedTTAccountTool].currentAccount.userPhone,@"actId":self.coupon.actId,@"Number":@"1",@"sysPlat":@"5"} succes:^(id responseObject) {
        if ([[responseObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            
            for (UIViewController *vc in self.navigationController.viewControllers) {
                if ([vc isKindOfClass:[TTTieCoupponsViewController class]]) {
                    TTTieCoupponsViewController *tieVC = (TTTieCoupponsViewController*)vc;
                    [tieVC updateUnusedCoupons:self.coupon.actId];
                    break;
                }
            }
            
            [SystemDialog alert:@"领取优惠券成功！"];
            
        }else{
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];
            
            
        }
    } fail:^(NSError *error) {
        TTLog(@"downCoupono : error");
    }];
    
}
//取消分享
- (void)didClickOnCancelButton
{
    TTLog(@"didClickOnCancelButton");
}
- (void)dealloc
{
    [self removeDown];
}

@end
