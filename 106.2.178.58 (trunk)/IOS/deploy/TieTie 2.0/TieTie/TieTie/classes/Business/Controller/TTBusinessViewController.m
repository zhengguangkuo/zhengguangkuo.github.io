//
//  TTBusinessViewController.m
//  TieTie
//
//  Created by APPLE on 14-9-25.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//
#import <QuartzCore/QuartzCore.h>
#import "TTBusinessViewController.h"
#import "MJRefresh.h"
#import "BusinessCircleCell.h"
#import "BusinessDetailsViewController.h"
#import "TTComercialCoupon.h"

#define KcellHeight      89

@interface TTBusinessViewController ()<UITableViewDelegate,UITableViewDataSource,MJRefreshBaseViewDelegate>
{
    UITableView          *  _tableView;         //列表
    MJRefreshHeaderView  *  _header;            //上拉
    MJRefreshFooterView  *  _footer;            //下拉
    
    /*
     优化
     */
    NSInteger                _flag;
    UIButton             *   _currentBtn;
    //    UILabel     *   _addressLabel;
    
    NSDictionary         *   _dic;
    
    //存取商家的数组
    NSMutableArray       *   _businessesArr;
    
    
    
    NSString             *   _superCode;
    NSString             *   _tempCatCode;
    NSInteger                _page;
    NSInteger                _nowPage;
    
    NSMutableArray       *   _businessMapArr;
    NSMutableDictionary  *   _MapsDic;
    
    NSMutableArray       *   _allCoupons;
    
    NSInteger                     _total;//总条数
}


@end

@implementation TTBusinessViewController
#pragma mark -生命周期
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _flag = 10000;
        
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
}

-(void)loadView
{
    _tableView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame style:UITableViewStylePlain];
    
    
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;;
    _tableView.delegate = self;
    _tableView.dataSource = self;
    _tableView.backgroundColor = TTBgBackGround;
    self.view = _tableView;
    
#ifdef __IPHONE_7_0
    if (iOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        self.extendedLayoutIncludesOpaqueBars = NO;
        self.modalPresentationCapturesStatusBarAppearance=NO;
        self.navigationController.navigationBar.translucent = NO;
    }
#endif

}

- (void)viewDidLoad
{
    [super viewDidLoad];

    _page = 1;
    _superCode = @"";
    _tempCatCode = @"";
    _businessesArr = [NSMutableArray array];
    _businessMapArr = [NSMutableArray array];
    _MapsDic = [NSMutableDictionary dictionary];

    self.title = @"商圈";
    
    
    //添加下拉刷新和上拉加载更多
    _header = [[MJRefreshHeaderView alloc] init];
    _header.delegate = self;
    [_header beginRefreshing];
    _header.scrollView = _tableView;
    
    _footer = [[MJRefreshFooterView alloc] init];
    _footer.scrollView = _tableView;
    _footer.delegate = self;
    
    
    
}

//加载商家
- (void)loadCommercialFromServerAsync:(NSInteger)startPage areaCode:(NSString*)areaCode catCode:(NSString *)catCode
{
    if (_total!=0&&_businessesArr.count>=_total) {
        [self performSelector:@selector(stopFooterRefresh) withObject:nil afterDelay:0.3];
        return;
    }

    NSString * page = [NSString stringWithFormat:@"%d",startPage];
    NSDictionary * dict = @{@"latitude" :@"",
                            @"longitude":@"",
                            @"radius"   :@"",
                            @"page"     :page,
                            @"rows"     :@"10",
                            @"CatCode"  :catCode,
                            @"AreaCode" :areaCode,
                            @"sysPlat"  :@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryMerch dict:dict succes:^(id responseObject) {
        NSArray * shoplist = responseObject[@"shopsList"];
        TTLog(@"%@&&&&&&&&&&&&&&&&&&&&&&&&&&&商圈",[responseObject JSONString]);
        
        _total = [[responseObject objectForKey:@"total"] integerValue];
        for (NSDictionary *dict in shoplist) {
            TTComercialCoupon * comercialCoupon = [[TTComercialCoupon alloc]initWithDic:dict];
            [_businessesArr addObject:comercialCoupon];
        }

        [_tableView reloadData];
        if (_businessesArr.count>=_total) {
            [self performSelector:@selector(stopFooterRefresh) withObject:nil afterDelay:0.3];
            [self showLoading:NO];
            return;
        }
        [_header endRefreshing];
        [_footer endRefreshing];
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@--**********************error商圈",error);
    }];
    
}

-(void)removeAllArrObject
{
    [_businessesArr removeAllObjects];
    [_businessMapArr removeAllObjects];
    [_MapsDic removeAllObjects];
    
}

#pragma mark -tableViewdelegate And datasource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _businessesArr.count;
}
#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    BusinessCircleCell *cell = [BusinessCircleCell businessCircleCellWithTableView:tableView];
    
    UIView *divider = [[UIView alloc] init];
    if (!_businessesArr.count) {
        return cell;
    }
    
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,cell.frame.size.height-1, cell.frame.size.width,0.5);
    [cell.contentView addSubview:divider];
    [cell setBusinessInformationWithCoupon:[_businessesArr objectAtIndex:indexPath.row]];
    
    
    return cell;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return KcellHeight;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    BusinessDetailsViewController * BDVC = [[BusinessDetailsViewController alloc]init];
    TTComercialCoupon * coupon = [_businessesArr objectAtIndex:indexPath.row];
    BDVC.MerchId = coupon.merchId;
    [self.navigationController pushViewController:BDVC animated:YES];
    
}


#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (_header == refreshView) { // 下拉
        if (_businessesArr.count>0) {
            [_businessesArr removeAllObjects];
            _footer.hidden = NO;
        }
        _page = 1;
        [self loadCommercialFromServerAsync:_page areaCode:@"11" catCode:@""];
    }
    else {

        // 上拉
        _page+=1;
        [self loadCommercialFromServerAsync:_page areaCode:@"11" catCode:@""];
    }
    
}

- (void)stopFooterRefresh
{
    [_footer endRefreshing];
    _footer.hidden = YES;
}


- (void)dealloc
{
    [_header free];
    [_footer free];
    
}



@end