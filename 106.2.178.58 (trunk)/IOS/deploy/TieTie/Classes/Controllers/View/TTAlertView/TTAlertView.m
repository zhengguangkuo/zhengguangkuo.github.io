//
//  TTAlertView.m
//  Miteno
//
//  Created by wg on 14-6-6.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAlertView.h"
#import "AlertHeadTitleView.h"
#import "TTCityModel.h"
#define kAlertCityW 140
#define KalertCityH 44*8+kRemainder
#define kRemainder 10
#define kCellRow 15
#define kSectionHead 40
#define kSectionRowHead 50

@interface TTAlertView ()<UITableViewDataSource,UITableViewDelegate,UIGestureRecognizerDelegate>
{
    UITableView                 *   _cityTab;
    UIButton                    *   _currentBtn;
    NSString                    *   _currentDistance;
    
    NSArray                     *   _hotCitys;
    NSMutableDictionary         *   _allCitys;
    NSMutableArray              *   _headTitle;
    NSMutableArray              *   _citys; //组标题
    
}
@end
@implementation TTAlertView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.bounds = CGRectMake(0, 0, kAlertCityW, 132/*ScreenHeight*0.6*/);
        self.center = CGPointMake(240,120/*ScreenWidth/2, ScreenHeight/2*/);
//        self.layer.cornerRadius = 5;
        self.layer.masksToBounds = YES;
        [self initCoustomViews];
        _allCitys = [NSMutableDictionary dictionary];
        _headTitle = [NSMutableArray array];
        _citys = [NSMutableArray array];
        //数据
        _hotCitys = @[@"北京",@"上海"];
        [self getCityData];
        
    }
    return self;
}
- (void)getCityData
{
//    NSString *path = [[NSBundle mainBundle] pathForResource:@"citydict.plist" ofType:nil];
//    _allCitys = [NSMutableDictionary dictionaryWithContentsOfFile:path];
    _citys = [NSMutableArray arrayWithContentsOfFile:TTCityFilePath];

//    dispatch_async(dispatch_get_main_queue(), ^{
//
//    });
//    static dispatch_once_t onceToken;
//    dispatch_once(&onceToken, ^{
//        for (NSDictionary *dict  in cityData) {
//            TTLog(@"Dict : %@",cityData);
//            //            CitysModel *city = [[CitysModel alloc] initWithDict:dict];
//            [_citys addObject:dict];
//        }
//    });

    //标题包装数组
//    [_citys addObjectsFromArray:[[_allCitys allKeys] sortedArrayUsingSelector:@selector(compare:)]];
//    //添加并包装热门
//    NSString *hot = @"热门城市";
//    [_citys insertObject:hot atIndex:0];
//    [_allCitys setObject:_hotCitys forKey:hot];
    //    TTLog(@"_citys---%@--%d",_citys,_citys.count);
    
}
- (void)show:(UIViewController *)ctrl
{
    [ctrl.view addSubview:self];
    _bgView = [[UIView alloc] init];
    _bgView.bounds = [UIScreen mainScreen].bounds;
    _bgView.center = CGPointMake(self.window.width/2, self.window.height/2);
    //    _bgView.frame = [UIScreen mainScreen].applicationFrame;
    [_bgView setBackgroundColor:kGlobalBg];
    [ctrl.view.window insertSubview:_bgView atIndex:ctrl.view.window.subviews.count];
    [ctrl.view.window insertSubview:self aboveSubview:_bgView];
    //添加手势
    UITapGestureRecognizer *singleRecognizer = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipeFrom:)];
    singleRecognizer.delegate = self;
    singleRecognizer.numberOfTouchesRequired = 1;
    [_bgView addGestureRecognizer:singleRecognizer];
}

- (void)initCoustomViews
{
//    //headtitle
//    AlertHeadTitleView *headTitle = [AlertHeadTitleView alertHeadTitleView];
//    [headTitle.cancelSel addTarget:self action:@selector(dismiss) forControlEvents:UIControlEventTouchUpInside];
//    [self addSubview:headTitle];
    
    //添加tableview
    CGRect frame = CGRectMake(0,0, self.frame.size.width, self.frame.size.height-44);
    _cityTab = [[UITableView alloc] initWithFrame:frame style:UITableViewStylePlain];
    _cityTab.sectionFooterHeight = 0;
    _cityTab.sectionHeaderHeight = 20;
    _cityTab.delegate = self;
    _cityTab.dataSource = self;
    _cityTab.scrollEnabled = NO;
    [self addSubview:_cityTab];
}
//- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
//{
//    
//    return _citys.count;
//}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
//    if (section==0) {
//        return 1;
//    }else{
//        NSString *key = [_citys objectAtIndex:section];
//        NSArray *city = [_allCitys objectForKey:key];
//        return _citys.count;
//    }
    return _citys.count;
}
#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    /*
    if (indexPath.section==0) {
        static  NSString *ID = @"hotCell";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:ID];
            UIScrollView *scrollerView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, 0, self.width, kSectionRowHead)];
            scrollerView.showsHorizontalScrollIndicator = NO;
            [cell.contentView addSubview:scrollerView];
            //添加按钮
            CGFloat btnY = 10;
            CGFloat btnW = 60;
            NSInteger index = 0;
            for (NSString *hotCity in _hotCitys) {
                UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
                [btn setTitle:hotCity forState:UIControlStateNormal];
                [btn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
                [btn setBackgroundImage:[UIImage imageNamed:@"choosear_line_img"] forState:UIControlStateNormal];
                btn.frame = CGRectMake(btnW*(index ++)+10,btnY, btnW-10, 30);
                [scrollerView addSubview:btn];
                [btn addTarget:self action:@selector(getHotCity:) forControlEvents:UIControlEventTouchUpInside];
            }
            scrollerView.contentSize = CGSizeMake(_hotCitys.count*btnW+10, 0);
            
        }
        return cell;
    }
    */
    static  NSString *identifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
    }
//    NSString   *key = [_citys objectAtIndex:indexPath.section];
//    NSArray  *city = [_allCitys objectForKey:key];
//    cell.textLabel.text = city[indexPath.row];
//    CitysModel *city = _citys[indexPath.row];
    cell.textLabel.text = _citys[indexPath.row][@"areaName"];
    return cell;
}
//- (NSArray *)sectionIndexTitlesForTableView:(UITableView *)tableView
//{
//    return [NSArray arrayWithObjects:@"A",@"B"];
//}
//-(UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
//{
//    
//        NSString *key = [_citys objectAtIndex:section];
//        CGFloat x = 15;
//        CGFloat y = 10;
//        CGFloat w = _cityTab.width;
//        CGFloat h = kSectionHead;
//        UIView *_headerView = [[UIView alloc] initWithFrame:CGRectMake(x, y, w, h)];
//    _headerView.backgroundColor = TTGlobalBg;
//        // 用来显示标题
//        UILabel *_headerLabel = [[UILabel alloc] init];
//        _headerLabel.backgroundColor = [UIColor clearColor];
//        //    _headerLabel.font = [UIFont fontWithName:@"Arial" size:18];
//        _headerLabel.textColor = [UIColor grayColor];
//        if (section==0) {
//            _headerLabel.frame = CGRectMake(x, y, _cityTab.width, h/2);
//            _headerLabel.text = key;
//        }else{
//            _headerLabel.frame = CGRectMake(x, y/2-3, _cityTab.width, 20);
//            _headerLabel.text = key;
//        }
//        [_headerView addSubview:_headerLabel];
//        
//        return _headerView;
//}
//- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
//{
//    return section==0?kSectionHead:kSectionHead-16;
//}
//- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    return indexPath.section==0?kSectionRowHead:44;
//}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
//    if (indexPath.section==0) return;
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    //归档当前城市
    TTCityModel *city = [[TTCityModel alloc] init];
    city.superArea = _citys[indexPath.row][TTsuperArea];
    city.areaCode = _citys [indexPath.row][TTareaCode];
    city.areaLevel = _citys [indexPath.row][TTareaLevel];
    city.areaName = _citys [indexPath.row][TTareaName];
    [[TTAccountTool sharedTTAccountTool] addCity:city];
    
    if ([_deletage respondsToSelector:@selector(getCityName:)]) {
        [_deletage getCityName:cell.textLabel.text];
    }
//    TTLog(@"Sel:%@",cell.textLabel.text);
}

#pragma mark -取消
- (void)clickCancel
{
    [UIView animateWithDuration:0.2 animations:^{
        [_bgView removeFromSuperview];
        [self removeFromSuperview];
    }];
    if ([_deletage respondsToSelector:@selector(isShowAlertView)]) {
        [_deletage isShowAlertView];
    }
}
- (void)selectedRow:(UIButton *)btn
{
    _currentBtn.selected = NO;
    
    btn.selected = YES;
    
    _currentBtn = btn;
    
    _currentDistance = btn.titleLabel.text;
}
- (void)dismiss
{
    [self clickCancel];
}
- (void)handleSwipeFrom:(UITapGestureRecognizer*)recognizer {
    
    [self clickCancel];
    //底下是刪除手势的方法
    [_bgView removeGestureRecognizer:recognizer];
    if ([_deletage respondsToSelector:@selector(isShowAlertView)]) {
        [_deletage isShowAlertView];
    }
}


- (void)getHotCity:(UIButton *)sender
{
    if ([_deletage respondsToSelector:@selector(getCityName:)]) {
        
        [_deletage getCityName:sender.titleLabel.text];
    }
//    TTLog(@"Sel:%@",sender.titleLabel.text);
}

@end
