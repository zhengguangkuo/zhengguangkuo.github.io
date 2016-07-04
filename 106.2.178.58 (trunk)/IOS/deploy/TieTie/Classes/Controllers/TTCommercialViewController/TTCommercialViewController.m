//
//  TTCommercialViewController.m
//  Miteno
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//
#import <QuartzCore/QuartzCore.h>
#import "TTCommercialViewController.h"
#import "Dock.h"
#import "TTShareTableView.h"
#import "BusinessDetailsViewController.h"
#import "BusinessCircleCell.h"
#import "TTSearchViewController.h"

#import "TTTopButton.h"
#import "TTTopDock.h"

#import "TTComercialCoupon.h"
#define KcellHeight      89


@interface TTCommercialViewController ()<UITableViewDelegate,UITableViewDataSource,TTShareTableViewDelegate,MJRefreshBaseViewDelegate>
{
    TTTopDock            *  _headDock;              //bar
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
    

    TTShareTableView     *   _shareTableView;
    
    NSString             *   _superCode;
    NSString             *   _tempCatCode;
    NSInteger                _page;
    NSInteger                _nowPage;
    
    NSMutableArray       *   _businessMapArr;
    NSMutableDictionary  *   _MapsDic;
    
    NSMutableArray       *   _allCoupons;
    
}


@end

@implementation TTCommercialViewController
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
    [_tableView reloadData];
    [super viewWillAppear:YES];
    
    static dispatch_once_t token;
    dispatch_once(&token, ^{
        [self getUserLocation]; //重新定位一次
        
    });
    
    TTLog(@"viewWillAppear");
    //    _currentBtn.selected = NO;
  
    [self loadHeadDockData];

    if ([[TTAccountTool sharedTTAccountTool].currentCity.areaName isEqualToString:@"北京"]) {
        [_headDock.regionItem setTitle:@"北京" forState:UIControlStateNormal];
        [_headDock.categoryItem setTitle:@"全部类型" forState:UIControlStateNormal];
    }else{
        [_headDock.regionItem setTitle:@"上海" forState:UIControlStateNormal];
        [_headDock.categoryItem setTitle:@"全部类型" forState:UIControlStateNormal];
    }

    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
    [_shareTableView hideDropDown:self.view];
    
    if (![[TTAccountTool sharedTTAccountTool].currentCity.areaName isEqualToString:@"北京"]) {
        _page = 1;
        _superCode = @"";
        _tempCatCode = @"";
        [self removeAllArrObject];
        [_tableView reloadData];
        _header.hidden = YES;
        _footer.hidden = YES;
        [self setBusinessTableViewFrame];
    }else{
        _header.hidden = NO;
        _footer.hidden = NO;
    }
    if (!_businessMapArr.count) {
        [self loadCommercialFromServerAsync:_page areaCode:@"" catCode:@""];

    }
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    TTLog(@"商圈 = TTCommercialViewController");
    [self loadAllMerData];
    self.view.backgroundColor = TTGlobalBg;
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
    }
#endif
    _page = 1;
    _superCode = @"";
    _tempCatCode = @"";
    _businessesArr = [NSMutableArray array];    
    _businessMapArr = [NSMutableArray array];
    _MapsDic = [NSMutableDictionary dictionary];
    //设置导航
    [self setNavTheme];
    [self setLeftItem];
    //添加dock
    [self addDock];
    
    
    //添加下拉刷新和上拉加载更多
    _header = [[MJRefreshHeaderView alloc] init];
    _header.delegate = self;
   [_header beginRefreshing];
    _header.scrollView = _tableView;
    
    _footer = [[MJRefreshFooterView alloc] init];
    _footer.scrollView = _tableView;
    _footer.delegate = self;
    
    
    //添加tableView
    [self addTableViews];


    //假数据
  //  _dic = @{@"merchId":@"Uid0988887",@"merchName":@"天福号",@"merchTag":@"日本料理",@"Address":@"地址1",@"Distance":@"1.0",@"ticketState":@"1",@"discountState":@"0",@"integralState":@"1",@"Image":@"http://www.menwww.com/uploadfile/2014/0629/20140629102843127.jpg"};
    

}

#pragma mark 设置导航栏
- (void)setLeftItem
{
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
        btn.frame = CGRectMake(0, 0, 57, 20);
    btn.userInteractionEnabled = NO;
    [btn setBackgroundImage:[UIImage imageNamed:@"tietie_top_logo"] forState:UIControlStateNormal];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
}

- (void)setNavTheme
{
    self.navigationItem.rightBarButtonItem = [UIBarButtonItem barButtonItemWithleftIcon:nil rightIcon:@"top_map_" target:self actionLeft:nil actionRitht:@selector(clickSearchMap)];

//    UILabel *TitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 50, 44)];
//    TitleLabel.font = [UIFont boldSystemFontOfSize:18];
//    TitleLabel.text = @"商圈";
//    TitleLabel.textColor = [UIColor whiteColor];
//    TitleLabel.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
//    self.navigationItem.titleView = TitleLabel;
      self.title = @"商圈";
}
//加载商家
- (void)loadCommercialFromServerAsync:(NSInteger)startPage areaCode:areaCode catCode:(NSString *)catCode
{
//    CLLocationCoordinate2D coordinate;
//    coordinate.longitude = [[TTSettingTool objectForKey:TTLongitude] floatValue];
//    coordinate.latitude = [[TTSettingTool objectForKey:TTLatitude] floatValue];
//    BMKMapPoint point = BMKMapPointForCoordinate(coordinate);
//    NSLog(@"%f,%f",point.x,point.y);
    NSString *latitude = [NSString stringWithFormat:@"%@",[TTSettingTool objectForKey:TTLatitude]];
    NSString *longitude = [NSString stringWithFormat:@"%@",[TTSettingTool objectForKey:TTLongitude]];
    if ([latitude isEqualToString:@"(null)"]) {
        latitude = @"";
    }if ([longitude isEqualToString:@"(null)"]) {
        longitude = @"";
    }
    
    if ([areaCode isEqualToString:@"areaCode"]||[areaCode isEqualToString:@""]) {

       areaCode = [TTAccountTool sharedTTAccountTool].currentCity.superArea;
    }
    if ([catCode isEqualToString:@"catCode"]) {
        catCode = @"";
    }
    if (_superCode!=areaCode||_tempCatCode!=catCode||_nowPage==startPage) {
        [self removeAllArrObject];
    }
    
    NSString * page = [NSString stringWithFormat:@"%d",startPage];
    NSDictionary * dict = @{@"latitude" :latitude,
                            @"longitude":longitude,
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

        if (!shoplist.count) {
            [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
            [SystemDialog alert:@"刷新完成..."];
            [self showLoading:NO];
            [self setBusinessTableViewFrame];
            [_tableView reloadData];
            return ;
        }
 
        for (NSDictionary *dict in shoplist) {
            TTComercialCoupon * comercialCoupon = [[TTComercialCoupon alloc]initWithDic:dict];
            [_businessesArr addObject:comercialCoupon];
            
            //商家地图数据
             NSString * str = [NSString stringWithFormat:@"%@,%@",comercialCoupon.longitude,comercialCoupon.latitude];
            NSArray * arr = [NSArray arrayWithObjects:str,comercialCoupon.merchName,nil];
            NSDictionary * dic = @{comercialCoupon.merchName:comercialCoupon.merchId};
            [_MapsDic addEntriesFromDictionary:dic];
            [_businessMapArr addObject:arr];
        }
        _tempCatCode = catCode;
        _superCode = areaCode;
        _nowPage = startPage;
        
        [self setBusinessTableViewFrame];
        [_tableView reloadData];
        [self stopRefresh];
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@--**********************error商圈",error);
    }];

}
-(void)setBusinessTableViewFrame
{
//    [_tableView setFrame:CGRectMake(0, 44, ScreenWidth, 89*_businessesArr.count/*ScreenHeight-150*/)];
//    if (89*_businessesArr.count>ScreenHeight-150) {
//        [_tableView setFrame:CGRectMake(0, 44, ScreenWidth, ScreenHeight-150)];
//    }
    if (_businessesArr.count) {
        [_tableView setFrame:CGRectMake(0, 44, ScreenWidth, ScreenHeight-150)];
    }
    else{
        [_tableView setFrame:CGRectMake(0, 0, 0, 0)];
    }
}


#pragma mark - 地区、商品分类
- (void)loadAllMerData
{
    //地区
    [TieTieTool tietieWithParameterMarked:TTAction_queryArea dict:@{@"superArea":[TTAccountTool sharedTTAccountTool].currentCity.areaCode,@"sysPlat":@"5"} succes:^(id responseObject) {
        NSArray *areas = responseObject[@"areaList"];
        [areas writeToFile:TTAreaPath atomically:YES];
        
     } fail:^(NSError *error) {
        TTLog(@"CouponAct_queryCategory : error");
        
    }];
    //分类
    [TieTieTool tietieWithParameterMarked:TTAction_Merch_queryCategory dict:@{@"superCat":@"",@"sysPlat":@"5"}  succes:^(id responseObject) {
        NSArray *cates = responseObject[@"catList"];
        [cates writeToFile:TTCatPath atomically:YES];
 
    } fail:^(NSError *error) {
        TTLog(@"CouponAct_queryCategory : error");
        
    }];
}


#pragma mark 添加dock
- (void)addDock
{
    _headDock = [[TTTopDock alloc] initWithFrame:CGRectMake(0,0, ScreenWidth, kDockHeight)];
    [_headDock.categoryItem setTitle:@"全部" forState:UIControlStateNormal];
    
    [self.view addSubview:_headDock];
    //监听item点击事件
    __unsafe_unretained TTCommercialViewController *coupon =self;
    _headDock.itemClickBlock = ^(TTTopButton *item){
        
        if (item.tag ==  TabarButtonTypeLeft) {
            [coupon hideShareView];
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

//shareviewDelegate
- (void)clickResultText:(NSString *)text codeLevel:(NSString *)codeLevel flagTag:(int)flagTag
{
    TTLog(@"cellText = %@",text);
    [_shareTableView hideDropDown:self.view];
    _currentBtn.selected = NO;

}

#pragma mark -TTShareView
-(void)showShareView:(int)index
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
        //        [_shareTableView addClildsView:_headDock.frame index:index data:areaDatas];
    }else{
        //右边
        TTLog(@"右边");
        NSArray *cats = [[NSArray alloc] initWithContentsOfFile:TTCatPath];
//        NSMutableArray *cateDatas = [NSMutableArray array];
        if (cats.count>0) {
            for (NSDictionary *dict in cats) {
                cateGory *cate = [[cateGory alloc] initWithDict:dict];
                [allData addObject:cate];
            }
        }
        //        [_shareTableView addClildsView:_headDock.frame index:index data:cateDatas];
    }
    [_shareTableView addClildsView:_headDock.frame index:index data:allData];
    
    //    [_shareTableView addClildsView:_headDock.frame index:index];
    [self.view addSubview:_shareTableView];
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
    TTLog(@"leftCode =  %@ :%@", superName,superCode);
}

//shareViewDelegate  -最终获取的一级内容(右边边点击)
NSString *areaCode = @"";
NSString *catCode = @"";
- (void)didChangeContent:(id)childcontent indexPath:(NSIndexPath *)indexPath
{
    //清空数组
    [_allCoupons removeAllObjects];
    
    //    NSString *childName = @"";
    if ([childcontent isKindOfClass:[Area class]]) {
        
        Area *area = (Area *)childcontent;
        //        childName = area.areaName;
        areaCode = area.areaCode;
        
        [_headDock.regionItem setTitle:area.areaName forState:UIControlStateNormal];
    }else{
        cateGory *cate = (cateGory *)childcontent;
        //        childName = cate.catName;
        catCode = cate.catCode;
        [_headDock.categoryItem setTitle:cate.catName forState:UIControlStateNormal];
    }
    TTLog(@"右边编码 =  :%@",catCode);
    
    //隐藏二级列表
    [_shareTableView hideDropDown:self.view];
    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
    
    //搜索
    _page = 1;
    
    
        //优惠劵
        [self loadCommercialFromServerAsync:_page areaCode:areaCode catCode:catCode];
    
}
-(void)removeAllArrObject
{
    [_businessesArr removeAllObjects];
    [_businessMapArr removeAllObjects];
    [_MapsDic removeAllObjects];

}

#pragma mark 添加tableview
- (void)addTableViews
{
    //UIImageView *imv = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"leather_bg.png"]];
    
    _tableView = [[UITableView alloc] init];     //   _tableView.backgroundView = imv;
    _tableView.separatorColor = [UIColor blackColor];
    _tableView.delegate = self;
    _tableView.separatorStyle=UITableViewCellSeparatorStyleNone;
        _tableView.dataSource = self;
        _header.beginRefreshingBlock = ^(MJRefreshBaseView *view){};
        _header.scrollView = _tableView;
        _footer.beginRefreshingBlock = ^(MJRefreshBaseView *view){};
        _footer.scrollView =  _tableView;
        [self.view addSubview:_tableView];
    

//    //添加当前位置栏
//    _addressLabel = [[UILabel alloc]initWithFrame:CGRectMake(20, ScreenHeight-134, ScreenWidth-60, 20)];
//    _addressLabel.text = @"新街口外大街";
//    [_addressLabel setFont:[UIFont systemFontOfSize:16]];
//    [self.view addSubview:_addressLabel];
//    //添加刷新按钮
//    UIButton * button = [[UIButton alloc]initWithFrame:CGRectMake(ScreenWidth-30, ScreenHeight-134, 20, 20)];
//    [button setBackgroundImage:[UIImage imageNamed:@"u32_normal.png"] forState:UIControlStateNormal];
//    [button addTarget:self action:@selector(updateAddress) forControlEvents:UIControlEventTouchUpInside];
//    [self.view addSubview:button];
}
//-(void)updateAddress
//{
//    NSLog(@"刷新了我的位置");
//    _addressLabel.text = @"天安门城楼";
//}

#pragma mark -导航栏按钮点击事件
- (void)backRootView{
   
    [[NSNotificationCenter defaultCenter] postNotificationName:TTSettingTabarBackVc object:nil];
}

//删除所有添加的view
- (void)removeAllViews
{
    [_shareTableView removeFromSuperview];
}
#pragma mark -mapSearch
- (void)clickSearchMap
{
    NSLog(@"用户当前位置的地图");
    MapsViewController *mapViewController = [[MapsViewController alloc]init];
    mapViewController.MapsDic = [NSDictionary dictionaryWithDictionary:_MapsDic];
    CLLocationCoordinate2D coordinate;
    coordinate.longitude = [[TTSettingTool objectForKey:TTLongitude] floatValue];
    coordinate.latitude = [[TTSettingTool objectForKey:TTLatitude] floatValue];
//    [mapViewController addAnnotationViewWithCLLocationCoordinate2D:coordinate andMerchName:@"地图"];
    [mapViewController addAnnotationViews:_businessMapArr center:coordinate];
    [self.navigationController pushViewController:mapViewController animated:YES];

}

#pragma mark -ContentSearch
- (void)clickSearchContent
{

    TTLog(@"文字搜索");
    TTSearchViewController *seachVC = [[TTSearchViewController alloc] init];
    [self.navigationController pushViewController:seachVC animated:YES];

}
#pragma mark -tableViewdelegate And datasource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    //return 5;
    return _businessesArr.count;
}
#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    
    BusinessCircleCell *cell = [BusinessCircleCell businessCircleCellWithTableView:tableView];

    UIView *divider = [[UIView alloc] init];
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
    [self removeAllViews];

    BusinessDetailsViewController * BDVC = [[BusinessDetailsViewController alloc]init];
    TTComercialCoupon * coupon = [_businessesArr objectAtIndex:indexPath.row];
    BDVC.MerchId = coupon.merchId;
    
    [self.navigationController pushViewController:BDVC animated:YES];
    
//    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
    [_shareTableView hideDropDown:self.view];
    _currentBtn.selected = NO;
    _headDock.regionItem.selected = NO;
    _headDock.categoryItem.selected = NO;
}

#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (_header == refreshView) { // 下拉
//        [self loadCommercialFromServerAsync:_page==0?1:_page catCode:_tempCatCode];
//        _page+=1;
        [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
    }
    else {
        // 上拉
        _page+=1;
        [self loadCommercialFromServerAsync:_page areaCode:_superCode catCode:_tempCatCode];
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



@end
