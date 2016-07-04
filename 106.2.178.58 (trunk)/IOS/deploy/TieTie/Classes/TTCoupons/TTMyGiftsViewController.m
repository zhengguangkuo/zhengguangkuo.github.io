//
//  TTMyGiftsViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMyGiftsViewController.h"
#import "MyCoupons.h"
#import "TTGiftsCouponTableViewCell.h"
#import "TTOrderViewController.h"
#import "OrderInfo.h"
#import "TTCouponDetailViewController.h"
#import "TTCoupon.h"

@interface TTMyGiftsViewController ()
{
    NSMutableArray *arrayDataReceived;
    NSMutableArray *arrayDataSend;
    NSMutableArray *arrayData;
    
    NSInteger pageCount;
    MJRefreshHeaderView *headerRefresh;
    MJRefreshFooterView *footerRefresh;
}
- (void)handleOrder:(id)sender;
- (void)handleSingleTap:(id)sender;
@end

@implementation TTMyGiftsViewController

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
    self.table.backgroundColor = TTGlobalBg;
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    self.navigationItem.title = @"我的礼物";
    // Do any additional setup after loading the view from its nib.
    pageCount = 1;
    [self showLoading:YES];
    [self loadCouponsRecieveFromFriends];
    //添加下拉刷新和上拉加载更多
    headerRefresh = [[MJRefreshHeaderView alloc] init];
    headerRefresh.delegate = self;
    headerRefresh.scrollView = self.table;
    
    footerRefresh = [[MJRefreshFooterView alloc] init];
    footerRefresh.scrollView = self.table;
    footerRefresh.delegate = self;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)dealloc
{
    [headerRefresh free];
    [footerRefresh free];
}

#pragma mark - UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [arrayData count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 88.0;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *ID = @"TTGiftsCouponTableViewCell";
    TTGiftsCouponTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle] loadNibNamed:ID owner:nil options:nil] lastObject];
        cell.accessoryType = UITableViewCellAccessoryNone;
    }
    NSInteger row = [indexPath row];
    OrderInfo *info = [arrayData objectAtIndex:row];
    cell.couponActName.text = info.act_name;
    cell.couponContent.text = info.start_date;
    if (self.segment.selectedSegmentIndex == 0) {
        cell.couponSender.text = info.from_mobile;
    } else {
        cell.couponSender.text = info.to_mobile;
    }
    
    cell.couponCount.text = [NSString stringWithFormat:@"%@张",info.num];
    [cell.couponImage setImageWithURL:[NSURL URLWithString:info.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];

    //状态：0待处理，1已接收，2已拒绝，3已过期
    switch ([info.status integerValue]) {
        case 0:
            if (self.segment.selectedSegmentIndex == 0) {
                [cell.receiveBtn setTitle:@"接受" forState:UIControlStateNormal];
                [cell.receiveBtn setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
                cell.receiveBtn.tag = [indexPath row];
                [cell.receiveBtn addTarget:self action:@selector(handleOrder:) forControlEvents:UIControlEventTouchUpInside];
            } else {
                [cell.receiveBtn setTitle:@"待处理" forState:UIControlStateNormal];
            }
            break;
        case 1:
            [cell.receiveBtn setTitle:@"已接收" forState:UIControlStateNormal];
            [cell.receiveBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            break;
        case 2:
            [cell.receiveBtn setTitle:@"已拒绝" forState:UIControlStateNormal];
            [cell.receiveBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            break;
        case 3:
            [cell.receiveBtn setTitle:@"已过期" forState:UIControlStateNormal];
            [cell.receiveBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            break;
        default:
            break;
    }
    
    UITapGestureRecognizer *singleTap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSingleTap:)];
    singleTap.numberOfTapsRequired = 1;
    [cell.couponImage addGestureRecognizer:singleTap];
    
    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,cell.size.height-1, cell.frame.size.width,0.5);
    [cell addSubview:divider];
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTOrderViewController *orderVC = [[TTOrderViewController alloc]initWithNibName:@"TTOrderViewController" bundle:nil];
    [self.navigationController pushViewController:orderVC animated:YES];
    OrderInfo *info = [arrayData objectAtIndex:[indexPath row]];
    orderVC.orderNoStr = info.order_no;
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}


#pragma mark - custom methods

- (void)handleSingleTap:(id)sender
{
    [self loadCouponData];
}

- (void)loadCouponData
{
    OrderInfo *info = [arrayData firstObject];
    NSDictionary * dict = @{@"actId":info.act_id,@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:@"findCouponAct" dict:dict succes:^(id responseObject) {
        [self showLoading:NO];
        TTLog(@"优惠券详情");
        NSDictionary * CouponDic = responseObject;
        TTCoupon *coupon = [[TTCoupon alloc] initWithDict:CouponDic];
        
        TTCouponDetailViewController * CDVC = [[TTCouponDetailViewController alloc]init];
        CDVC.coupon = coupon;
        [self.navigationController pushViewController:CDVC animated:YES];
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@--优惠券详情error",error);
    }];
    
}

- (void)loadCouponsRecieveFromFriends
{
    //{"page":"1","rows":"10","mobile":"","sign":"0","sysPlat":"5"}
    NSString *page = [NSString stringWithFormat:@"%d",pageCount];
    NSString *rows = @"10";
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *sign = @"1";//0 送出用户id 1 接收用户id
    NSDictionary *bodyDicts = @{@"page":page,@"rows":rows,@"mobile":mobile,@"sign":sign,@"sysPlat":@"5"};
    [TieTieTool tietieWithParameterMarked:TTAction_queryCouponOrder dict:bodyDicts succes:^(id respondObject) {
        TTLog(@"queryCouponOrder:%@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            
            if (!arrayDataReceived) {
                arrayDataReceived = [[NSMutableArray alloc]init];
            }
            
            NSArray *recieveDataArray = [respondObject objectForKey:@"myCouponOrderList"];
            NSString *totalCount = [respondObject objectForKey:@"total"];
            if ([arrayDataReceived count] == [totalCount integerValue]) {
                [SystemDialog alert:@"没有新数据！"];
                arrayData = arrayDataReceived;
                [self.table reloadData];
                [self stopRefresh];
                [self showLoading:NO];
                return;
            }
            
            //非第一次获取最后一页的订单时，只添加更新的订单。
            if ([arrayDataReceived count]%[rows integerValue] > 0) {
                for (NSDictionary *dict in recieveDataArray) {
                    BOOL isAdd = YES;
                    OrderInfo *info = [[OrderInfo alloc]initWithDictionary:dict];
                    for (OrderInfo *orderInfo in arrayDataReceived) {
                        if ([orderInfo.order_no isEqualToString:info.order_no]) {
                            isAdd = NO;
                            break;
                        }
                    }
                    
                    if (isAdd) {
                        [arrayDataReceived addObject:info];
                    }
                }
            } else {
                for (NSDictionary *dict in recieveDataArray){
                    OrderInfo *info = [[OrderInfo alloc]initWithDictionary:dict];
                    [arrayDataReceived addObject:info];
                }
            }

            //如果取出来的订单为一整页的数据，下次该取下一页的数据。
            if ([arrayDataReceived count] % [rows integerValue] == 0) {
                pageCount += 1;
            }
            arrayData = arrayDataReceived;
            [self.table reloadData];
            [self showLoading:NO];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (void)loadCouponsSendToFriends
{
    //{"page":"1","rows":"10","mobile":"","sign":"0","sysPlat":"5"}
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *sign = @"0";//0 送出用户id 1 接收用户id
    NSString *page = [NSString stringWithFormat:@"%d",pageCount];
    NSString *rows = @"10";
    NSDictionary *bodyDicts = @{@"page":page,@"rows":rows,@"mobile":mobile,@"sign":sign,@"sysPlat":@"5"};
    [TieTieTool tietieWithParameterMarked:TTAction_queryCouponOrder dict:bodyDicts succes:^(id respondObject) {
        TTLog(@"queryCouponOrder:%@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            if (!arrayDataSend) {
                arrayDataSend = [[NSMutableArray alloc]init];
            }
            
            NSArray *sendDataArray = [respondObject objectForKey:@"myCouponOrderList"];
            NSString *totalCount = [respondObject objectForKey:@"total"];
            if ([totalCount integerValue] == [arrayDataSend count]) {
                [SystemDialog alert:@"没有新数据！"];
                [self showLoading:NO];
                [self stopRefresh];
                arrayData = arrayDataSend;
                [self.table reloadData];
                return;
            }
            
            //非第一次获取最后一页的订单时，只添加更新的订单。
            if ([arrayDataSend count]%[rows integerValue] > 0) {
                for (NSDictionary *dict in sendDataArray) {
                    BOOL isAdd = YES;
                    OrderInfo *info = [[OrderInfo alloc]initWithDictionary:dict];
                    for (OrderInfo *orderInfo in arrayDataSend) {
                        if ([orderInfo.order_no isEqualToString:info.order_no]) {
                            isAdd = NO;
                            break;
                        }
                    }
                    
                    if (isAdd) {
                        [arrayDataSend addObject:info];
                    }
                }
            } else {
                for (NSDictionary *dict in sendDataArray){
                    OrderInfo *info = [[OrderInfo alloc]initWithDictionary:dict];
                    [arrayDataSend addObject:info];
                }
            }
            
            //如果取出来的订单为一整页的数据，下次该取下一页的数据。
            if ([arrayDataSend count] % [rows integerValue] == 0) {
                pageCount += 1;
            }
            arrayData = arrayDataSend;
            [self.table reloadData];
            [self stopRefresh];
        }

        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (void)handleOrder:(id)sender
{
    UIButton *btn = (UIButton*)sender;
    OrderInfo *info = [arrayData objectAtIndex:btn.tag];
    BOOL receive = YES;
    //{"orderId":" Uid776656","handleState":"00","sysPlat":"5"}
    NSString *orderId = info.order_no;
    NSString *handleState = receive ? @"00":@"01";//00代表用户已接受01代表用户已拒绝
    NSDictionary *paramDic = @{@"orderId":orderId,@"handleState":handleState,@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_handleOrder dict:paramDic succes:^(id responseObj) {
        [self showLoading:NO];
        TTLog(@"handleOrder:%@",[responseObj objectForKey:rspDesc]);
        if ([[responseObj objectForKey:rspCode]isEqualToString:rspCode_success]) {
            info.status = @"1";
            [self.table reloadData];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

#pragma UI Clicked Methods
- (IBAction)segmentedClicked:(id)sender {
    UISegmentedControl * segmented = (UISegmentedControl*)sender;
    BOOL hasCoupons = NO;
    pageCount = 1;
    switch (segmented.selectedSegmentIndex) {
        case 0:
            if (arrayDataReceived && [arrayDataReceived count] > 0) {
                arrayData = arrayDataReceived;
                hasCoupons = YES;
            } else {
                [self showLoading:YES];
                [self loadCouponsRecieveFromFriends];
            }
            break;
        case 1:
            if (arrayDataSend && [arrayDataSend count] > 0) {
                arrayData = arrayDataSend;
                hasCoupons = YES;
            } else {
                [self showLoading:YES];
                [self loadCouponsSendToFriends];
            }
            break;
    }
    
    if (hasCoupons) {
        pageCount = [arrayData count]/10 + 1;
        [self.table reloadData];
    }
}

- (void)updateTableAfterHandleOrder:(NSString *)orderNo status:(NSString *)status
{
    for (OrderInfo *info in arrayDataReceived) {
        if ([orderNo isEqualToString:info.order_no]) {
            //状态：0待处理，1已接收，2已拒绝，3已过期
            info.status = status;
            [self.table reloadData];
            break;
        }
    }
}

#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (refreshView == footerRefresh) {
        if (self.segment.selectedSegmentIndex == 0) {
            [self loadCouponsRecieveFromFriends];
        } else {
            [self loadCouponsSendToFriends];
        }
    }
    [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
}
- (void)stopRefresh
{
    [headerRefresh endRefreshing];
    [footerRefresh endRefreshing];
}
@end
