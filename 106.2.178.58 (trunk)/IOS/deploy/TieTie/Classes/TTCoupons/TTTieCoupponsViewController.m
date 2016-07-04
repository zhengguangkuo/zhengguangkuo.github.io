//
//  TTTieCoupponsViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTieCoupponsViewController.h"
#import "TTCouponTableViewCell.h"
#import "MyCoupons.h"
#import "TTCouponDetailViewController.h"
#import "TTPickFriendViewController.h"
#import "TTCoupon.h"
#import "TTMyGiftsViewController.h"


#define kUnused     0
#define kUsed       1
#define kExpired    2

@interface TTTieCoupponsViewController ()
{
    NSMutableArray *unusedCoupons;
    NSMutableArray *usedCoupons;
    NSMutableArray *expiredCoupons;
    NSInteger pageCount;

    NSMutableArray *currentCoupons;
    MJRefreshHeaderView *headerRefresh;
    MJRefreshFooterView *footerRefresh;
    
    NSInteger sendtag;//送出优惠券在数组中的位置。
}

- (void)showMyGifts;
@end

@implementation TTTieCoupponsViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            //            vc.edgesForExtendedLayout = UIRectEdgeNone;
            //            TTLog(@"---%@ = %@",vc,NSStringFromCGRect(vc.view.frame));
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif 
    self.tableView.backgroundColor = TTGlobalBg;

// Do any additional setup after loading the view from its nib.

    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    [self.navigationItem setTitle:@"我的贴券"];
    self.navigationItem.rightBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"gift-" target:self action:@selector(showMyGifts) direction:ItemDirectionRight];
    
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

- (void)viewWillAppear:(BOOL)animated
{
    [self.sendBtn setHidden:!self.tableView.editing];
}
- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)dealloc
{
    [headerRefresh free];
    [footerRefresh free];
}

- (void)editTableCells
{
    UIButton *btn = (UIButton*)self.navigationItem.rightBarButtonItem.customView;
    [btn setTitle:(self.tableView.editing ? @"编辑":@"取消") forState:UIControlStateNormal];
    [self setEditing:!self.tableView.editing animated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)sendCoupons:(id)sender {
    TTPickFriendViewController *tieFriendsVC = [[TTPickFriendViewController alloc]init];
    [self.navigationController pushViewController:tieFriendsVC animated:YES];
}

- (IBAction)cancelBtnClicked:(id)sender {
    [self.sendBtn setHidden:YES];
    [self.cancelBtn setHidden:YES];
}

- (void)sendCouponsToFriends:(id)sender event:(id)event
{
    UIButton *btn = (UIButton*)sender;
    sendtag = btn.tag;
    MyCoupons *myCoupon = [currentCoupons objectAtIndex:sendtag];
    if (myCoupon.couponNum <= 0) {
        [SystemDialog alert:@"赶紧去领取优惠券吧！"];
    } else {
        TTPickFriendViewController *tieFriendsVC = [[TTPickFriendViewController alloc]initWithNibName:@"TTPickFriendViewController" bundle:nil];
        [self.navigationController pushViewController:tieFriendsVC animated:YES];
        [tieFriendsVC setDataWithMycoupons:myCoupon];
    }
}

- (IBAction)segmentClicked:(id)sender {
    UISegmentedControl *segmented = (UISegmentedControl*)sender;
    BOOL hasCoupons = NO;
    pageCount = 1;
     TTLog(@"selectedSegmentIndex:%d",segmented.selectedSegmentIndex);
    switch (segmented.selectedSegmentIndex) {
        case kUnused:
            if (unusedCoupons && [unusedCoupons count] > 0) {
                currentCoupons = unusedCoupons;
                hasCoupons = YES;
            } else {
                [self loadUnusedCouponsAsync];
            }
            break;
        case kUsed:
            if (usedCoupons && [usedCoupons count] > 0) {
                currentCoupons = usedCoupons;
                hasCoupons = YES;
            } else {
                [self loadUsedCouponsAsync];
            }
            break;
        case kExpired:
            if (expiredCoupons && [usedCoupons count] > 0) {
                currentCoupons = expiredCoupons;
                hasCoupons = YES;
            } else {
                [self loadExpiredCouponsAsync];
            }
            break;
    }
    
    if (hasCoupons) {
        pageCount = [currentCoupons count]/10 + 1;
        [self.tableView reloadData];
    }
}

- (void)showMyGifts
{
    TTMyGiftsViewController *myGiftsVC = [[TTMyGiftsViewController alloc]initWithNibName:@"TTMyGiftsViewController" bundle:nil];
    [self.navigationController pushViewController:myGiftsVC animated:YES];
}

#pragma mark - UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        TTPickFriendViewController *tieFriendsVC = [[TTPickFriendViewController alloc]init];
        [self.navigationController pushViewController:tieFriendsVC animated:YES];
    } else {
        return;
    }
}

#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (refreshView == footerRefresh) {
        switch (self.segmentControl.selectedSegmentIndex) {
            case kUnused:
                [self loadUnusedCouponsAsync];
                break;
            case kUsed:
                [self loadUsedCouponsAsync];
                break;
            case kExpired:
                [self loadExpiredCouponsAsync];
                break;
            default:
                break;
        }
    } else {
        [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
    }
}
- (void)stopRefresh
{
    [headerRefresh endRefreshing];
    [footerRefresh endRefreshing];
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
        [customCell.sendBtn addTarget:self action:@selector(sendCouponsToFriends:event:) forControlEvents:UIControlEventTouchUpInside];
    } else {
        [customCell.sendBtn setHidden:YES];
    }
    

    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,customCell.size.height-1, customCell.frame.size.width,0.5);
    [customCell addSubview:divider];
    return customCell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return YES;
}

- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath
{
    //可多选的编辑状态。
//    return UITableViewCellEditingStyleDelete | UITableViewCellEditingStyleInsert;
    //滑动删除的编辑状态。
    return UITableViewCellEditingStyleDelete;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [currentCoupons removeObjectAtIndex:[indexPath row]];
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationLeft];
    }
}

- (void)setEditing:(BOOL)editing animated:(BOOL)animated
{
    [self.tableView setEditing:editing animated:animated];
    [self.sendBtn setHidden:!editing];
}

#pragma mark - Table Delegate Methods
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (!tableView.editing) {
        TTCouponDetailViewController *couponDetail = [[TTCouponDetailViewController alloc] init];
        MyCoupons *mycoupons = [currentCoupons objectAtIndex:[indexPath row]];
//        couponDetail.coupon = [self instanceWithMycoupons:mycoupons];
        id object = [self instanceWithMycoupons:mycoupons];
        couponDetail.object = object;
        [self.navigationController pushViewController:couponDetail animated:YES];
        
        [tableView deselectRowAtIndexPath:indexPath animated:NO];
    }
}


- (TTCoupon *)instanceWithMycoupons:(MyCoupons *)mycoupons
{
    TTCoupon *coupon = [[TTCoupon alloc]init];
    coupon.picPath = mycoupons.picPath;
    coupon.merName = mycoupons.merchName;
    coupon.merActive = mycoupons.actName;
    coupon.actId = mycoupons.actId ? mycoupons.actId : @"";
    coupon.sendType = [NSString stringWithFormat:@"%d",mycoupons.sendType];
    coupon.merDetial = @"";
    coupon.merActiveDate = [NSString stringWithFormat:@"%@ - %@",mycoupons.startDate,mycoupons.endDate];
    coupon.merRebate = @"领";
    
    return coupon;
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
        }
        
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (void)loadUsedCouponsAsync
{
    NSString *page = [NSString stringWithFormat:@"%i",pageCount];
    NSString *rows = @"10";
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *exFlag = @"2";//0全部1：未使用；2已使用；3：已过期
    NSString *sysPlat = @"5";
    
    NSDictionary *dict = @{@"page":page,
                           @"rows":rows,
                           @"userId":userId,
                           @"exFlag":exFlag,
                           @"sysPlat":sysPlat};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryCoupon dict:dict succes:^(id respondObject) {
        TTLog(@"========loadUsedCouponsAsync=====%@",[respondObject valueForKey:rspDesc]);
        //{"rspCode":"-100","rspDesc":"网络连接异常"}
        NSArray *myCoupons = [respondObject objectForKey:@"myCouponList"];
        NSString *rspondCode = [respondObject valueForKey:rspCode];
        
        if ([rspondCode isEqualToString:rspCode_success]) {

            if (!usedCoupons) {
                usedCoupons = [[NSMutableArray alloc]init];
            }
            
            if ([myCoupons count] == 0) {
                [self showLoading:NO];
                [self stopRefresh];
                [SystemDialog alert:@"暂时没有新数据!"];
                currentCoupons = usedCoupons;
                [self.tableView reloadData];
                return;
            }
            
            //非第一次获取最后一页的订单时，只添加更新的订单。
            if ([usedCoupons count]%[rows integerValue] > 0) {
                for (NSDictionary *dict in myCoupons) {
                    BOOL isAdd = YES;
                    MyCoupons *myCoupon = [[MyCoupons alloc]initWithDictionary:dict];
                    for (MyCoupons *coupon in usedCoupons) {
                        if ([coupon.actId isEqualToString:myCoupon.actId]) {
                            isAdd = NO;
                            break;
                        }
                    }
                    
                    if (isAdd) {
                        [usedCoupons addObject:myCoupon];
                    }
                }
            } else {
                for (NSDictionary *dict in myCoupons){
                    MyCoupons *mycoupon = [[MyCoupons alloc]initWithDictionary:dict];
                    [usedCoupons addObject:mycoupon];
                }
            }
            
            if ([usedCoupons count] % [rows integerValue] == 0) {
                pageCount += 1;
            }
            
            currentCoupons = usedCoupons;

            [self.tableView reloadData];
            [self stopRefresh];
        }

        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
    
}

- (void)loadExpiredCouponsAsync
{
    NSString *page = [NSString stringWithFormat:@"%i",pageCount];
    NSString *rows = @"10";
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *exFlag = @"3";//0全部1：未使用；2已使用；3：已过期
    NSString *sysPlat = @"5";
    
    NSDictionary *dict = @{@"page":page,
                           @"rows":rows,
                           @"userId":userId,
                           @"exFlag":exFlag,
                           @"sysPlat":sysPlat};

    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryCoupon dict:dict succes:^(id respondObject) {
        TTLog(@"===========loadexpiredCouponsAsync==== %@",[respondObject valueForKey:rspDesc]);
        //{"rspCode":"-100","rspDesc":"网络连接异常"}
        NSArray *myCoupons = [respondObject objectForKey:@"myCouponList"];
        NSString *rspondCode = [respondObject valueForKey:rspCode];
        
        if ([rspondCode isEqualToString:rspCode_success]) {
            
            if (!expiredCoupons) {
                expiredCoupons = [[NSMutableArray alloc]init];
            }
            
            if ([myCoupons count] == 0) {
                [self showLoading:NO];
                [self stopRefresh];
                [SystemDialog alert:@"暂时没有新数据!"];
                currentCoupons = expiredCoupons;
                [self.tableView reloadData];
                return;
            }
            
            //非第一次获取最后一页的订单时，只添加更新的订单。
            if ([expiredCoupons count]%[rows integerValue] > 0) {
                for (NSDictionary *dict in myCoupons) {
                    BOOL isAdd = YES;
                    MyCoupons *myCoupon = [[MyCoupons alloc]initWithDictionary:dict];
                    for (MyCoupons *coupon in expiredCoupons) {
                        if ([coupon.actId isEqualToString:myCoupon.actId]) {
                            isAdd = NO;
                            break;
                        }
                    }
                    
                    if (isAdd) {
                        [expiredCoupons addObject:myCoupon];
                    }
                }
            } else {
                for (NSDictionary *dict in myCoupons){
                    MyCoupons *mycoupon = [[MyCoupons alloc]initWithDictionary:dict];
                    [expiredCoupons addObject:mycoupon];
                }
            }

            if ([expiredCoupons count] % [rows integerValue] == 0) {
                pageCount += 1;
            }
            
            currentCoupons = expiredCoupons;

            [self.tableView reloadData];
            [self stopRefresh];
        }
        
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading: NO];
        TTLog(@"%@",error);
    }];
}

- (void)updateTableAfterSend:(NSInteger)count
{
    MyCoupons *coupon = [unusedCoupons objectAtIndex:sendtag];
    coupon.couponNum -= count;
    [self.tableView reloadData];
}

- (void)updateUnusedCoupons:(NSString *)actId
{
    BOOL isNewCoupon = YES;
    for (MyCoupons *coupon in unusedCoupons) {
        if ([actId isEqualToString:coupon.actId]) {
            coupon.couponNum += 1;
            isNewCoupon = NO;
            [self.tableView reloadData];
            break;
        }
    }
    if (isNewCoupon) {
        [SystemDialog alert:@"您领取了新的优惠券，请刷新我的贴券查看"];
    }
}

@end
