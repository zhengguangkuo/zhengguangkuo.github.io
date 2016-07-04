//
//  TTCouponViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponViewController.h"
#import "NavItemView.h"
#import "HelpView.h"
#import "Dock.h"
#import "TTSearchViewController.h"
#import "TTCouponCell.h"
#import "TTCouponDetailViewController.h"
#import "TTShareTableView.h"
#import "TieTieTool.h"
#import "TTCoupon.h"
#import "MapsViewController.h"
#import "TTTopButton.h"
#import "TTTopDock.h"
#import "TTCreditsCoupon.h"
#import "TempView.h"
#define kFooterTabarHeight 44
//保存顶部dock上的选定信息
#define AreaRightData       @"AreaRightData"
#define CateRightData       @"CateRightData"

@interface TTCouponViewController ()<UITableViewDataSource,UITableViewDelegate,TTShareTableViewDelegate,MJRefreshBaseViewDelegate,MapsViewControllerDelegate,DockDelegate>
{
    TTTopDock               *   _headDock;
    Dock                    *   _bottomDock;
    UITableView             *   _couponTabView;
    TTShareTableView        *   _shareTableView;
    UIButton                *   _currentBtn;
    NSInteger                   _flag;
    NSMutableArray          *   _allCoupons;
    MJRefreshHeaderView     *   _header;                //上拉
    MJRefreshFooterView     *   _footer;                //下拉
    NSInteger                   _page;
    MapsViewController      *   _mapController;         //地图
    NSInteger                   _couponTotal;           //优惠劵总条数
    NSInteger                   _creditsTotal;          //优惠劵总条数
    NSUserDefaults          *   _userDefaults;
    NSString                *   _tempLeftCatCode;       //分类编码
    NSString                *   _tempRightCatCode;      //分类编码
    NSInteger                   _currentIndex;
    NSArray                 *   _footItems;
    NSString                *   _superCode;
    TempView                *   _temp;
    NSString                *   _areaStr;
    NSString                *   _cateStr;
}
@end

@implementation TTCouponViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        _userDefaults = [NSUserDefaults standardUserDefaults];
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];

    //隐藏
    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
    [_shareTableView hideDropDown:self.view];
    

    if ([[TTSettingTool objectForKey:kflagExitDetal] boolValue]==0) {
        return;//由详情退出不需要加载
    }
    //清除列表数据
    [_allCoupons removeAllObjects];
    
    //加载地区、分类父级数据
    [self loadHeadDockData];
    //顶部dock数据
    NSString *area = [NSString stringWithFormat:@"%@全范围",[TTAccountTool sharedTTAccountTool].currentCity.areaName];
    [_headDock.regionItem setTitle:area forState:UIControlStateNormal];
    [_headDock.categoryItem setTitle:@"全部分类" forState:UIControlStateNormal];

    _bottomDock.didSelectDefault = ^NSInteger(void){
        return [[TTSettingTool objectForKey:TTClickFoterDockItem] integerValue];
    };
    [self didSelectItem:[[TTSettingTool objectForKey:TTClickFoterDockItem] integerValue]];

}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // yes 第一次进入
    [TTSettingTool setBool:1 forKey:kflagExitDetal];
    
    self.view.backgroundColor = white1;
    _superCode = @"";
    _allCoupons = [NSMutableArray array];
    _page = 0;
    _couponTotal = 0;
    _creditsTotal = 0;
    _tempLeftCatCode = @"";
    _tempRightCatCode = @"";
    TTLog(@"优惠劵 = TTCouponViewController");
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
    }
#endif
    //    self.view.backgroundColor = TTGlobalBg;
    _flag = 10000;
    
    //设置导航
    [self setLeftItem];
    
    //添加Dock
    [self addHeadDock];
    [self addFooterTabar];
    [self addTableView:_currentIndex];
    
    //添加下拉刷新和上拉加载更多
    _header = [[MJRefreshHeaderView alloc] init];
    _header.delegate = self;
    [_header beginRefreshing];
    _header.scrollView = _couponTabView;
    
    _footer = [[MJRefreshFooterView alloc] init];
    _footer.scrollView = _couponTabView;
    //    [_footer beginRefreshing];
    _footer.delegate = self;
    
}
#pragma mark 设置导航栏
- (void)setLeftItem
{
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.imageEdgeInsets = btnEdgeLeft;
    btn.frame = CGRectMake(0, 0, 57, 20);
    btn.userInteractionEnabled = NO;
    [btn setBackgroundImage:[UIImage imageNamed:@"tietie_top_logo"] forState:UIControlStateNormal];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
}
//changeNavItem
- (void)setNavThemeTitle:(NSString *)title flag:(NSInteger)flag
{
    NavItemView * theMeView = [[NavItemView alloc] themeWithFrame:CGRectMake(5,0,110, 44) title:title flag:flag];
    [theMeView.help addTarget:self action:@selector(clickHelp) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.titleView = theMeView;
}
//帮助
- (void)clickHelp
{
    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
    [_shareTableView hideDropDown:self.view];
    HelpView *help = [HelpView helpView];
    [help show:self];
}

//地图
- (void)pushMapVc
{
    //    MapsViewController *mapViewController = [[MapsViewController alloc]init];
    //    CLLocationCoordinate2D coordinate;
    //    coordinate.longitude = [[TTSettingTool objectForKey:TTLongitude] floatValue];
    //    coordinate.latitude = [[TTSettingTool objectForKey:TTLatitude] floatValue];
    //    [mapViewController addAnnotationViewWithCLLocationCoordinate2D:coordinate andMerchName:@"地图"];
    //    [self.navigationController pushViewController:mapViewController animated:YES];
}

//文字搜素
-(void)pushContentVc
{
    TTLog(@"文字搜索");
    TTSearchViewController *seachVC = [[TTSearchViewController alloc] init];
    [self.navigationController pushViewController:seachVC animated:YES];
}
#pragma mark -顶部dock
- (void)addHeadDock
{
    _headDock = [[TTTopDock alloc] initWithFrame:CGRectMake(0,0, ScreenWidth, kDockHeight)];
    
    
    [self.view addSubview:_headDock];
    
    //监听item点击事件
    __unsafe_unretained TTCouponViewController *coupon =self;
    _headDock.itemClickBlock = ^(TTTopButton *item){
        if (item.tag ==  TabarButtonTypeLeft) {
            [coupon hideShareView];
            //            [coupon->_shareTableView.regionView reloadData];
        }
        if (item.tag ==TabarButtonTypeRight) {
            [coupon hideShareView];
        }
        
        item.selected?[coupon showShareView:item.tag]:[coupon hideShareView];
    };
}
- (void)hideShareView
{
    [_shareTableView hideDropDown:self.view];
}
#pragma mark -底部dock
- (void)addFooterTabar
{
    _bottomDock = [[Dock alloc] init];
    _bottomDock.frame = CGRectMake(0,self.view.frame.size.height-(2*kFooterTabarHeight)-(isIOS7), self.view.frame.size.width,kFooterTabarHeight);
    _bottomDock.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"segmented_bg"]];
    _bottomDock.delegate = self;
    _footItems = @[@"优惠券",@"折扣优惠",@"积分优惠"];
    [_bottomDock addDockBtnWithBgIcon:@"" selectIcon:@"segmented_highlight" title:_footItems[0] titleLayout:ItemTitleLayoutBottom];
    [_bottomDock addDockBtnWithBgIcon:@"" selectIcon:@"segmented_highlight" title:_footItems[1] titleLayout:ItemTitleLayoutBottom];
    [_bottomDock addDockBtnWithBgIcon:@"" selectIcon:@"segmented_highlight" title:_footItems[2] titleLayout:ItemTitleLayoutBottom];
    [self.view addSubview:_bottomDock];
}
//bottomDock delegate
- (void)didSelectItem:(NSInteger)itemIndex
{
    NSString *curCity =  [TTAccountTool sharedTTAccountTool].currentCity.areaName;
    NSString *area = [NSString stringWithFormat:@"%@全范围",curCity];
    //切换导航
    [self setNavThemeTitle:_footItems[itemIndex] flag:itemIndex];
    [self cleanData];
    
    switch (itemIndex) {
        case 0:
        {   //优惠劵
            [_temp  removeFromSuperview];
            //            [self cleanData];
            
            _header.hidden = NO;
            _footer.hidden = NO;
            _headDock.hidden = NO;
            _headDock.userInteractionEnabled = YES;
            _headDock.categoryItem.userInteractionEnabled = YES;
            [_headDock.categoryItem setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
            _headDock.categoryItem.hidden = NO;
            
            //恢复按钮状态
            [_headDock.regionItem setTitle:area forState:UIControlStateNormal];
            [_headDock.categoryItem setTitle:@"全部类型" forState:UIControlStateNormal];
            //            _cateStr = @"";
            //            _areaStr = @"";
            [_userDefaults removeObjectForKey:AreaRightData];
            [_userDefaults removeObjectForKey:CateRightData];
            _couponTabView.tag = itemIndex;
            
            if (_allCoupons.count>0) {
                if (![_allCoupons[0] isKindOfClass:[TTCoupon class]]) {
                    
                    [_allCoupons removeAllObjects];
                    [_couponTabView reloadData];
                }
            }
            _page = 1;
            //[coupon loadCouponData:coupon ->_page+1];
            if (_allCoupons.count<=0) {
//                [TieTieTool clear];
                [self loadCouponData:_page  areaCode:@"" catCode:@""];
            }
        }
            break;
        case 1:{
             [_temp  removeFromSuperview];
            //折扣
            //            [self cleanData];
            _header.hidden = YES;
            _footer.hidden = YES;
            _headDock.userInteractionEnabled = NO;
            _headDock.hidden = YES;
            [_allCoupons removeAllObjects];
            //
            if (_temp == nil) {
                
                _temp = [TempView tempView];
                _temp.center = CGPointMake(ScreenWidth/2, ScreenHeight/2-(_temp.height/2));
            }
            _temp.text.text = @"折扣优惠还在建设中,请耐心等待...";
            _temp.icon.image = [UIImage imageNamed:@"icon_ts"];
            [self.view addSubview:_temp];
            
            _couponTabView.tag = itemIndex;
            [self loadDiscountData];
        }
            break;
        case 2:{
            [_temp  removeFromSuperview];
            //            [self cleanData];
            _header.hidden = NO;
            _footer.hidden = NO;
            _headDock.hidden = NO;
            _headDock.userInteractionEnabled = YES;
            _headDock.categoryItem.userInteractionEnabled = NO;
            [_headDock.regionItem setTitle:area forState:UIControlStateNormal];
            [_headDock.categoryItem setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            [_headDock.categoryItem setTitle:@"暂无" forState:UIControlStateNormal];
            if (_allCoupons.count>0) {
                if (![_allCoupons[0] isKindOfClass:[TTCreditsCoupon class]]) {
                    
                    [_allCoupons removeAllObjects];
                    [_couponTabView reloadData];
                }
            }
            //积分
            _couponTabView.tag = itemIndex;
            _page = 1;
             if (_allCoupons.count<=0) {
            [self loadScoreData:_page catCode:_tempLeftCatCode];
             }
            
//            if (_creditsTotal<=0) {
//                if (_temp == nil) {
//                    _temp = [TempView tempView];
//                    _temp.center = CGPointMake(ScreenWidth/2, ScreenHeight/2-(_temp.height/2));
//                    
//                }
//                _temp.text.text = @"非常抱歉！积分暂时还没有数据...";
//                _temp.icon.image = [UIImage imageNamed:@"bt_c_normal"];
//                [self.view addSubview:_temp];
//            }
        }
            break;
        default:
            break;
    }
}

#pragma mark -TTShareView
- (void)showShareView:(int)index
{
    _shareTableView = [[TTShareTableView alloc] init];
    _shareTableView.delegate = self;
    
    
    NSMutableArray *allData = [NSMutableArray array];
    if (index == 0) {
        //左边
        TTLog(@"左边");
        //leftCell  -  区域
        NSMutableArray *areas = [[NSMutableArray alloc] initWithContentsOfFile:TTAreaPath];
        //        NSMutableArray *areaDatas = [NSMutableArray array];
        if (areas.count>0) {
            for (NSDictionary *dict in areas) {
                Area *area = [[Area alloc] initWithDict:dict];
                [allData addObject:area];
            }
        }
    }else{
 
        NSArray *cats = [[NSArray alloc] initWithContentsOfFile:TTCatPath];
        if (cats.count>0) {
            for (NSDictionary *dict in cats) {
                cateGory *cate = [[cateGory alloc] initWithDict:dict];
                [allData addObject:cate];
            }
        }
    }
    
    _shareTableView.selectAreaCode = _areaStr;
    _shareTableView.selectCateCode = _cateStr;
    [_shareTableView addClildsView:_headDock.frame index:index data:allData];
    [self.view addSubview:_shareTableView];
}

- (void)addTableView:(int)index
{
    if (_couponTabView == nil) {
        _couponTabView = [[UITableView alloc] initWithFrame:CGRectMake(0, _headDock.frame.size.height, ScreenWidth,self.view.frame.size.height-(2*kFooterTabarHeight)-kDockHeight-(isIOS7)) style:UITableViewStylePlain];
        _couponTabView.backgroundColor = white1;
        _couponTabView.separatorStyle = UITableViewCellSeparatorStyleNone;
        _couponTabView.delegate = self;
        _couponTabView.dataSource = self;
    }
    _couponTabView.tag = index;
    
    [self.view addSubview:_couponTabView];
}
#pragma mark -shareViewDelegate
- (void)clickResultText:(NSString *)text codeLevel:(NSString *)codeLevel flagTag:(int)flagTag
{
    TTLog(@"cellText = %@",text);
    [_shareTableView hideDropDown:self.view];
    _currentBtn.selected = NO;
}
#pragma mark -加载优惠劵数据
//加载数据
- (void)loadCouponData:(NSInteger)startPage areaCode:(NSString *)areaCode catCode:(NSString *)catCode
{
    if (![[TTAccountTool sharedTTAccountTool].currentCity.areaName isEqualToString:@"北京"]) {
        [_allCoupons removeAllObjects];
        [_couponTabView reloadData];
        [SystemDialog alert:@"未搜索到数据..."];
        return;
    }
    
    if ( _allCoupons.count>0&&_allCoupons.count >= _couponTotal) {
        [self showLoading:NO];
        [SystemDialog alert:@"刷新完成..."];
        [self performSelector:@selector(stopRefresh) withObject:nil afterDelay:0.01];
        return ;
        
    }
    
    NSString *page = [NSString stringWithFormat:@"%d",startPage];
    NSDictionary *dict = @{@"page":page,
                           @"rows":@"10",
                           @"catCode":catCode,
                           @"areaCode":areaCode,
                           @"sendType":@"",
                           @"payType":@"0",
                           @"sysPlat":@"5"};
    
    [self loadCouponWithDict:dict];
    
    TTLog(@"加载dict = %@",dict);
}
#pragma mark -加载优惠劵数据
- (void)loadCouponWithDict:(NSDictionary *)dict
{
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryCouponAct dict:dict succes:^(id responseObject) {
        NSArray *couList = responseObject[@"couponList"];
        if (couList.count<=0) {
            [self showLoading:NO];
            [_couponTabView reloadData];
            [SystemDialog alert:@"刷新完成..."];
            [self performSelector:@selector(stopRefresh) withObject:nil afterDelay:0.01];
            return ;
        }
        for (NSDictionary *dict in couList) {
            TTCoupon *coupon = [[TTCoupon alloc] initWithDict:dict];
            [_allCoupons addObject:coupon];
        }
        _couponTotal = [[responseObject objectForKey:@"total"] intValue];
        TTLog(@"优惠劵 总共 %@条数据",responseObject[@"total"]);
        [self showLoading:NO];
        
        [_couponTabView reloadData];
        [_header endRefreshing];
        [_footer endRefreshing];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"优惠券列表获取失败");
    }];
}
#pragma mark -加载积分数据
- (void)loadScoreData:(NSInteger)startPage catCode:(NSString *)catCoe
{
    if (![[TTAccountTool sharedTTAccountTool].currentCity.areaName isEqualToString:@"北京"]) {
        [_allCoupons removeAllObjects];
        [_couponTabView reloadData];
        [SystemDialog alert:@"未搜索到数据..."];
        return;
    }
    
    //搜索全部
    if ([catCoe isEqualToString:@""]) {
        
        catCoe = [TTAccountTool sharedTTAccountTool].currentCity.superArea;
    }
    if ( _allCoupons.count>0&&_allCoupons.count >= _creditsTotal) {
        [self showLoading:NO];
        [SystemDialog alert:@"刷新完成..."];
        [self performSelector:@selector(stopRefresh) withObject:nil afterDelay:0.01];
        return ;
        
    }
    NSString *page = [NSString stringWithFormat:@"%d",startPage];
    NSDictionary *dict = @{@"page":page,
                           @"rows":@"10",
                           @"AreaCode":catCoe,
                           @"sysPlat":@"5"};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_creditsList dict:dict succes:^(id responseObject) {
        
        NSArray *couList = responseObject[@"activityList"];
//        if (couList.count<=0) {
//            [self showLoading:NO];
//            [_couponTabView reloadData];
////            [SystemDialog alert:@"刷新完成..."];
//            _footer.hidden = YES;
//            _header.hidden = YES;
//            [self performSelector:@selector(stopRefresh) withObject:nil afterDelay:0.01];
//            return ;
//        }
        for (NSDictionary *dict in couList) {
            TTCreditsCoupon *credits = [[TTCreditsCoupon alloc] initWithDict:dict];
            [_allCoupons addObject:credits];
        }
        _creditsTotal = [[responseObject objectForKey:@"total"] intValue];
        if (_creditsTotal==0) {
            [SystemDialog alert:@"未搜索到数据..."];
        }
        TTLog(@"积分列表 总共 %@条数据",responseObject[@"total"]);
        
        [self showLoading:NO];
        
        [_couponTabView reloadData];
        [_header endRefreshing];
        [_footer endRefreshing];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"积分列表获取失败");
    }];
    
}
#pragma mark -加载折扣数据
- (void)loadDiscountData
{
    [self showLoading:NO];
    //    _headDock.userInteractionEnabled = NO;
    //    [_couponTabView reloadData];
    //    [_couponTabView removeFromSuperview];
    //    if (_viewbg == nil) {
    //
    //        _viewbg = [[UIImageView alloc] init];
    //        _viewbg.frame = CGRectMake(0, 44, 320, 270);//358
    //        _viewbg.image = [UIImage imageNamed:@"bt_s_normal"];
    //    }
    //    [self.view addSubview:_viewbg];
    [_allCoupons removeAllObjects];
    
    
    [_couponTabView reloadData];
}

#pragma mark -代理方法名(上拉、下拉刷新)
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (_header == refreshView) { // 下拉
        [self performSelector:@selector(stopRefresh) withObject:nil afterDelay:0.01];
        //        [SystemDialog alert:@"刷新完成..."];
        //        [self loadCouponData:_page==0?1:_page];
        //         [_allCoupons removeAllObjects];
        //        _page = 1;
    } else { // 上拉
        _page+=1;
        switch (_couponTabView.tag) {
            case 0:{
                //优惠劵
                [self loadCouponData:_page areaCode:@"" catCode:@"" ];
                TTLog(@"优惠劵");
            }break;
            case 1:{
                //折扣优惠
                TTLog(@"折扣优惠");
            }break;
            case 2:{
                
                //积分优惠
                TTLog(@"积分优惠");
                [self loadScoreData:_page catCode:_tempLeftCatCode];
            }break;
            default:
                break;
        }
        
    }
}
- (void)stopRefresh
{
    [_header endRefreshing];
    [_footer endRefreshing];
}
- (void)dealloc
{
    [_header free];
    [_footer free];
    
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
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,cell.size.height, cell.frame.size.width,0.5);
    [cell addSubview:divider];
    if (_allCoupons.count<=0)   return cell;
    id obj = _allCoupons[indexPath.row];
    if ([obj isKindOfClass:[TTCoupon class]]) {
        TTCoupon *coupon = obj;
        cell.coupon = coupon;
    }
    if ([obj isKindOfClass:[TTCreditsCoupon class]]) {
        TTCreditsCoupon *credits = obj;
        cell.creditsCoupon = credits;
    }
    return cell;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 90;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    TTLog(@"行:%d",indexPath.row);
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    TTCouponDetailViewController *couponDetail = [[TTCouponDetailViewController alloc] init];
    
    id obj = _allCoupons[indexPath.row];

    couponDetail.object = obj;
    [self.navigationController pushViewController:couponDetail animated:YES];
}
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
    [_shareTableView hideDropDown:self.view];
    _currentBtn.selected = NO;
    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
}

#pragma mark - 加载地区、类别数据
- (void)loadHeadDockData
{
    /*
     *  加载地区、类别数据
     **/
    
    //如果已经有且是北京地区就不再加载
    NSString *areCode = [TTAccountTool sharedTTAccountTool].currentCity.areaCode;
    if (![NSString isFileExist:TTAreaList]&&[NSString isFileExist:TTCateGoryList]&&[areCode isEqualToString:@"1100"]) {
        return;
    }
    
    //地区列表
    [TieTieTool tietieWithParameterMarked:TTAction_queryArea dict:@{@"superArea":areCode,@"sysPlat":@"5"} succes:^(id responseObject) {
        NSMutableArray *areas = responseObject[@"areaList"];
        [areas writeToFile:TTAreaPath atomically:YES];
        
    } fail:^(NSError *error) {
        TTLog(@"CouponAct_queryCategory : error");
        
    }];
    //分类列表
    [TieTieTool tietieWithParameterMarked:TTAction_CouponAct_queryCategory dict:@{@"superCat":@"",@"sysPlat":@"5"} succes:^(id responseObject) {
        NSArray *cates = responseObject[@"catList"];
        [cates writeToFile:TTCatPath atomically:YES];
    } fail:^(NSError *error) {
        TTLog(@"CouponAct_queryCategory : error");
        
    }];
}
//shareViewDelegate  -根据父类编码 获取 一级内容(左边点击)
- (void)didChangeContentSuper:(id)supContent indexPath:(NSIndexPath *)indexPath
{
    NSString *superName = @"";
    NSString *superCode = @"";
    if ([supContent isKindOfClass:[Area class]]) {
        
        Area *area = (Area *)supContent;
        superName = area.areaName;
        superCode = area.areaCode;
        
    }else{
        cateGory *cate = (cateGory *)supContent;
        superName = cate.catName;
        superCode = cate.catCode;
    }
    TTLog(@"selectLeftCode =  %@ :%@", superName,superCode);
}
//shareViewDelegate  -最终获取的一级内容(右边边点击)
- (void)didChangeContent:(id)childcontent indexPath:(NSIndexPath *)indexPath
{
    //清空数组
    [_allCoupons removeAllObjects];
    
    NSString  *tempAreaItem = [TTSettingTool objectForKey:AreaRightData];
    NSString  *tempCateItem = [TTSettingTool objectForKey:CateRightData];
    
    NSString *areaCode = @"";
    
    
    NSString *catCode = @"";
    
    TTLog(@"%@  ======",[_headDock.regionItem titleForState:UIControlStateNormal]);
    
    //    NSString *childName = @"";
    if ([childcontent isKindOfClass:[Area class]]) {
        
        Area *area = (Area *)childcontent;
        //        childName = area.areaName;
        areaCode = area.areaCode;
        
        if (tempCateItem.length>0) {
            
            catCode =tempCateItem;
        }
        [_headDock.regionItem setTitle:area.areaName forState:UIControlStateNormal];
        TTLog(@"---存储 %@",area.areaName);
        [TTSettingTool setObject:area.areaCode forKey:AreaRightData];
        _areaStr = area.superArea;
    }else{
        cateGory *cate = (cateGory *)childcontent;
        //        childName = cate.catName;
        catCode = cate.catCode;
        
        if (tempAreaItem.length>0) {
            
            areaCode =tempAreaItem;
        }
        [_headDock.categoryItem setTitle:cate.catName forState:UIControlStateNormal];
        [TTSettingTool setObject:cate.catCode forKey:CateRightData];
        _cateStr = cate.superCat;
        TTLog(@"---存储 %@",cate.catName);
    }
    TTLog(@"右边编码 =  :%@",catCode);
    
    //隐藏二级列表
    [_shareTableView hideDropDown:self.view];
    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
    
    //搜索
    _page = 1;
    
    if ([areaCode isEqualToString:@"areaCode"]) {
        areaCode = [TTAccountTool sharedTTAccountTool].currentCity.areaCode;
    }
    if ([catCode isEqualToString:@"catCode"]) {
        catCode = @"";
    }
    if (_couponTabView.tag ==0) {
        
        //优惠劵
        [self loadCouponData:_page areaCode:areaCode catCode:catCode ];
    }else{
        //积分
        [self loadScoreData:_page catCode:areaCode];
    }
 
}
- (void)cleanData
{
    //清理数据
    [_userDefaults removeObjectForKey:kLeftRegionRow];
    [_userDefaults removeObjectForKey:kRightRegionRow];
    [_userDefaults removeObjectForKey:kLeftCateRow];
    [_userDefaults removeObjectForKey:kRightCateRow];
    [_userDefaults removeObjectForKey:rightCat];
    [_userDefaults removeObjectForKey:rightArea];
    
    [TTSettingTool setInteger:0 forKey:kLeftRegionRow];
    [TTSettingTool setInteger:0 forKey:kRightRegionRow];
    [TTSettingTool setInteger:0 forKey:kLeftCateRow];
    [TTSettingTool setInteger:0 forKey:kRightCateRow];

}

@end
