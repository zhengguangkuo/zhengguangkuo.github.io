//
//  TTPickCouponViewController.m
//  Miteno
//
//  Created by wg on 14-8-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPickCouponViewController.h"
#import "TTCouponTableViewCell.h"
#import "MyCoupons.h"

@interface TTPickCouponViewController ()
{
    NSMutableArray *currentCoupons;
    NSMutableArray *unusedCoupons;
    
    NSInteger pageCount;
    MJRefreshHeaderView *headerRefresh;
    MJRefreshFooterView *footerRefresh;
}

- (void)sendCouponToFriend:(id)sender;
@end

@implementation TTPickCouponViewController

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
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"选择贴券";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    
    if (self.friendInfo.nickName) {
        self.friendName.text = self.friendInfo.nickName;
    }else{
        self.friendName.text = self.friendInfo.ID;
    }
    
    //添加下拉刷新和上拉加载更多
    headerRefresh = [[MJRefreshHeaderView alloc] init];
    headerRefresh.delegate = self;
    //    [headerRefresh beginRefreshing];
    headerRefresh.scrollView = self.tableView;
    
    footerRefresh = [[MJRefreshFooterView alloc] init];
    footerRefresh.scrollView = self.tableView;
    footerRefresh.delegate = self;
    
    //在每次视图显示之后加载未使用优惠券，从第一页开始。
    pageCount = 1;
    [self loadUnusedCouponsAsync];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)sendCouponToFriend:(id)sender
{
    UIButton *btn = (UIButton*)sender;
    NSInteger row = btn.tag;
    [self sendSingleCoupon:row];
}

- (void)sendSingleCoupon:(NSInteger)row
{
    //{"sendUserId":"13426364669","receiveUserId":"Uid776656","couponId":"Uid776656","sysPlat":"5"}
    NSString *sendUserId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *receiveUserId = self.friendInfo.nickName;
    MyCoupons *coupon = [currentCoupons objectAtIndex:row];
    NSString *couponId = coupon.actId;
    NSDictionary *paramsDic = @{@"sendUserId": sendUserId,
                                @"receiveUserId":receiveUserId,
                                @"actId":couponId,
                                @"Number":@"1",
                                @"sysPlat":@"5"};//@{@"sendUserId":sendUserId,@"receiveUserId":receiveUserId,@"couponId":couponId,@"sysPlat":@"5"};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_sendCoupon dict:paramsDic succes:^(id responseObj) {
        if ([[responseObj objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"赠送成功！"];
            [self updateTableView:row];
        } else {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (void)updateTableView:(NSInteger)row
{
    MyCoupons *coupon = [currentCoupons objectAtIndex:row];
    coupon.couponNum -= 1;
    [self.tableView reloadData];
}

- (void)backgroundViewWithNoData:(BOOL)hasBgView
{
    if (!hasBgView) {
        _tableView.backgroundView = nil;
    } else {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"icon_user_bg"]];
        [imageView setCenter:CGPointMake(160, 200)];
        UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
        label.text = @"您还没有好友，点击右上角添加吧！";
        label.textColor = [UIColor grayColor];
        label.textAlignment = NSTextAlignmentCenter;
        [label setCenter:CGPointMake(160, 240)];
        
        UIView *bgView = [[UIView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame];
        [bgView addSubview:imageView];
        [bgView addSubview:label];
        
        _tableView.backgroundView = bgView;
    }
}

#pragma mark - load data from sever.

- (void)loadUnusedCouponsAsync
{
    NSString *page = [NSString stringWithFormat:@"%i",pageCount];
    NSString *rows = @"10";
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *exFlag = @"1";//0全部1：未使用；2已使用；3：已过期
    NSString *sysPlat = @"5";
    
    NSDictionary *dict = @{@"page":page,
                           @"rows":rows,
                           @"userId":userId,
                           @"exFlag":exFlag,
                           @"sysPlat":sysPlat};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryCoupon dict:dict succes:^(id respondObject) {
        TTLog(@"========loadUnusedCouponsAsync=====%@",[respondObject valueForKey:rspDesc]);
        [self showLoading:NO];
        //{"rspCode":"-100","rspDesc":"网络连接异常"}
        NSArray *myCoupons = [respondObject objectForKey:@"myCouponList"];
        NSString *rspondCode = [respondObject valueForKey:rspCode];
        
        if ([rspondCode isEqualToString:rspCode_success]) {
            
            if (!unusedCoupons) {
                unusedCoupons = [[NSMutableArray alloc]init];
            }
            
            if ([myCoupons count] == 0) {
                [self showLoading:NO];
                [self stopRefresh];
                [SystemDialog alert:@"暂时没有新数据!"];
                currentCoupons = unusedCoupons;
                [self.tableView reloadData];
                return;
            }
            
            //非第一次获取最后一页的订单时，只添加更新的订单。
            if ([unusedCoupons count]%[rows integerValue] > 0) {
                for (NSDictionary *dict in myCoupons) {
                    BOOL isAdd = YES;
                    MyCoupons *myCoupon = [[MyCoupons alloc]initWithDictionary:dict];
                    for (MyCoupons *coupon in unusedCoupons) {
                        if ([coupon.actId isEqualToString:myCoupon.actId]) {
                            isAdd = NO;
                            break;
                        }
                    }
                    
                    if (isAdd) {
                        [unusedCoupons addObject:myCoupon];
                    }
                }
            } else {
                for (NSDictionary *dict in myCoupons){
                    MyCoupons *mycoupon = [[MyCoupons alloc]initWithDictionary:dict];
                    [unusedCoupons addObject:mycoupon];
                }
            }
            
            
            if ([unusedCoupons count] % [rows integerValue] == 0) {
                pageCount += 1;
            }
            currentCoupons = unusedCoupons;
            [self.tableView reloadData];
            [self stopRefresh];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
        
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}


#pragma mark - Table Data Source Mthods
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [currentCoupons count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 88;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kCustomCell = @"CustomCell";
    TTCouponTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kCustomCell];
    if (!customCell) {
        customCell = [[[NSBundle mainBundle]loadNibNamed:@"TTCouponTableViewCell" owner:self.tableView options:nil] lastObject];
    }
    
    NSInteger row = [indexPath row];
    MyCoupons *coupons = [currentCoupons objectAtIndex:row];
    [customCell.icon setImageWithURL:[NSURL URLWithString:coupons.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    customCell.couponName.text = coupons.actName;
    customCell.couponContent.text = coupons.merchName;
    customCell.couponCount.text = [NSString stringWithFormat:@"%d张",coupons.couponNum];
    customCell.couponValidity.text = [NSString stringWithFormat:@"%@",coupons.endDate];
    
    if (coupons.ex_flag == COUPONS_UNUSED) {
        [customCell.sendBtn setHidden:NO];
        customCell.sendBtn.tag = [indexPath row];
        [customCell.sendBtn addTarget:self action:@selector(sendCouponToFriend:) forControlEvents:UIControlEventTouchUpInside];
    } else {
        [customCell.sendBtn setHidden:YES];
    }
    
    
    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,customCell.size.height-1, customCell.frame.size.width,0.5);
    [customCell addSubview:divider];
    return customCell;
}

#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (refreshView == footerRefresh) {
        [self loadUnusedCouponsAsync];
    } else {
        [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
    }
}
- (void)stopRefresh
{
    [headerRefresh endRefreshing];
    [footerRefresh endRefreshing];
}


@end
