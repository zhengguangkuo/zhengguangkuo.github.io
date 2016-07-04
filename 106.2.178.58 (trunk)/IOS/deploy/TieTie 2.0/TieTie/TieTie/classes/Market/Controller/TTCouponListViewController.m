//
//  TTCouponListViewController.m
//  TieTie
//
//  Created by wg on 14-9-23.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponListViewController.h"
#import "TTCouponCell.h"
#import "TTCoupon.h"
#import "MJRefresh.h"
#import "TTLoginViewController.h"
#import "TTBaseNavViewController.h"
@interface TTCouponListViewController ()<UITableViewDelegate,UITableViewDataSource,MJRefreshBaseViewDelegate>
{
    NSMutableArray            *   _allCoupons;
    UITableView               *   _tabView;
    MJRefreshHeaderView       *   _headRefresh;
    MJRefreshFooterView       *   _footerRefresh;
    NSInteger                     _total;//总条数
    NSInteger                     _page;
    
}
@end

@implementation TTCouponListViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void)loadView
{
    _tabView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame style:UITableViewStylePlain];
    
#ifdef __IPHONE_7_0
    if (iOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        
    }
#endif

    _tabView.separatorStyle = UITableViewCellSeparatorStyleNone;;
    _tabView.delegate = self;
    _tabView.dataSource = self;
    _tabView.backgroundColor = TTBgBackGround;
    self.view = _tabView;
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"列表";
    _allCoupons = [NSMutableArray array];
    self.view.backgroundColor = TTBgBackGround;
//    _total = 0;
    _headRefresh = [[MJRefreshHeaderView alloc] init];
    _headRefresh.delegate = self;
    [_headRefresh beginRefreshing];
    _headRefresh.scrollView = _tabView;
    
    _footerRefresh = [[MJRefreshFooterView alloc] init];
    _footerRefresh.scrollView = _tabView;
    //    [_footer beginRefreshing];
    _footerRefresh.delegate = self;
    
}
#pragma mark -代理方法名(上拉、下拉刷新)
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (_headRefresh == refreshView) { // 下拉
        if (_allCoupons.count>0) {
            [_allCoupons removeAllObjects];
            _footerRefresh.hidden = NO;
        }
        _page = 1;
        [self loadCouponData:_page areaCode:@"" catCode:@""];
        
    } else {// 上拉
                _page+=1;
        
        if (_allCoupons.count>=_total) {
            [self performSelector:@selector(stopFooterRefresh) withObject:nil afterDelay:0.3];
            return;
        }
        
            //优惠劵
                [self loadCouponData:_page areaCode:@"" catCode:@"" ];
//                TTLog(@"优惠劵");
    }

}
- (void)stopFooterRefresh
{
//    [_headRefresh   endRefreshing];
    [_footerRefresh endRefreshing];
    _footerRefresh.hidden = YES;
}
- (void)dealloc
{
    [_headRefresh   free];
    [_footerRefresh free];
    
}
#pragma mark -加载优惠劵数据
//加载数据
- (void)loadCouponData:(NSInteger)startPage areaCode:(NSString *)areaCode catCode:(NSString *)catCode
{
    if (_total!=0&&_allCoupons.count>=_total) {
        [self performSelector:@selector(stopFooterRefresh) withObject:nil afterDelay:0.3];
        return;
    }
    NSString *page = [NSString stringWithFormat:@"%d",startPage];
    NSDictionary *dict = @{@"page":page,
                           @"rows":@"10",
                           @"catCode":@"",
                           @"areaCode":@"",
                           @"sendType":@"",
                           @"payType":@"0",
                           @"sysPlat":@"5"};
    startPage == 1?[self showLoading:YES]:@"";
    [self loadCouponWithDict:dict];
    
    TTLog(@"加载dict = %@",dict);
}
#pragma mark -加载优惠劵数据
- (void)loadCouponWithDict:(NSDictionary *)dict
{
   
    [TieTieTool tietieWithParameterMarked:TTAction_queryCouponAct dict:dict succes:^(id responseObject) {
        NSArray *couList = responseObject[@"couponList"];

        for (NSDictionary *dict in couList) {
            TTCoupon *coupon = [[TTCoupon alloc] initWithDict:dict];
            [_allCoupons addObject:coupon];
        }
       NSInteger total = [[responseObject objectForKey:@"total"] intValue];
        _total = total;
        TTLog(@"优惠劵 总共 %@条数据",responseObject[@"total"]);
        
        [_tabView reloadData];
        [_headRefresh endRefreshing];
        [_footerRefresh endRefreshing];
        [self showLoading:NO];
    } fail:^(NSError *error) {
        TTLog(@"优惠券列表获取失败");
        [self showLoading:NO];
    }];
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _allCoupons.count;
}
#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTCouponCell *cell = [TTCouponCell couponCellWithTableView:tableView];
    UIView *divider = [[UIView alloc] init];
    if (!_allCoupons.count)return cell;
    
//    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.backgroundColor = white3;
    divider.frame = CGRectMake(0,cell.frame.size.height, cell.frame.size.width,0.5);
    [cell addSubview:divider];
    id obj = _allCoupons[indexPath.row];
    if ([obj isKindOfClass:[TTCoupon class]]) {
        TTCoupon *coupon = obj;
        cell.coupon = coupon;
    }
    return cell;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 90;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTLoginViewController *login = [[TTLoginViewController alloc] init];
    TTBaseNavViewController *nav = [[TTBaseNavViewController alloc] initWithRootViewController:login];
    [self.navigationController presentViewController:nav animated:YES completion:^{
        
    }];
}
@end
