//
//  TTTieFenViewController.m
//  TTTieFenViewController
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "TTTieFenViewController.h"
#import "TTTieFenCell.h"
#import "TTTieFenAlertView.h"
#import "NavItemView.h"
#import "TTTieFenDetailViewController.h"
@interface TTTieFenViewController ()<MJRefreshBaseViewDelegate>
{
//    NSMutableArray * _fenArr;
    NSMutableArray * _fenName;
    
    NSMutableArray * _fenListArr;
    
    MJRefreshHeaderView  *  _header;            //上拉
    MJRefreshFooterView  *  _footer;            //下拉
    NSInteger _page;

}
@end

@implementation TTTieFenViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {

    }
    return self;
}
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    if (!_fenListArr.count) {
        [self loadFenListFromServerAsync:_page];
    }
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    _page = 1;
    _fenListArr = [NSMutableArray array];


    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
//    UILabel *TitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 50, 44)];
//    TitleLabel.font = [UIFont boldSystemFontOfSize:18];
//    TitleLabel.text = @"我的贴分";
//    TitleLabel.textColor = [UIColor whiteColor];
//    TitleLabel.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
//    self.navigationItem.titleView = TitleLabel;
    self.title = @"我的贴分";
    self.view.backgroundColor = TTGlobalBg;
    [self addSubViews];
    

}
-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}

-(void)addSubViews
{

    
    self.listTableView = [[UITableView alloc]init];

    //添加下拉刷新和上拉加载更多
    _header = [[MJRefreshHeaderView alloc] init];
    _header.delegate = self;
    [_header beginRefreshing];
    _header.scrollView = self.listTableView;
    
    _footer = [[MJRefreshFooterView alloc] init];
    _footer.scrollView = self.listTableView;
    _footer.delegate = self;

    
    self.listTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.listTableView.separatorColor = [UIColor blackColor];
    self.listTableView.delegate = self;
    self.listTableView.dataSource = self;
    _header.beginRefreshingBlock = ^(MJRefreshBaseView *view){};
    _header.scrollView = self.listTableView;
    _footer.beginRefreshingBlock = ^(MJRefreshBaseView *view){};
    _footer.scrollView =  self.listTableView;

    [self.view addSubview:self.listTableView];
    

}

//加载积分列表
- (void)loadFenListFromServerAsync:(NSInteger)startPage
{
    NSString * page = [NSString stringWithFormat:@"%d",startPage];
    [self showLoading:YES];
    NSDictionary * dict = @{@"page":page,@"rows":@"10",@"Mobile":[TTAccountTool sharedTTAccountTool].currentAccount.userPhone,@"orgName":@"",@"sysPlat":@"5"};

    [TieTieTool tietieWithParameterMarked:TTAction_searchCredits dict:dict succes:^(id responseObject) {
//        NSArray * arr = [NSArray arrayWithObject:responseObject[@"myCreditsList"]];
        TTLog(@"%@***************积分列表",responseObject);
        NSArray * textArr = [NSArray arrayWithArray:responseObject[@"myCreditsList"]];
        if (!textArr.count) {
            
            if (!_fenListArr.count) {
                [self fenListDetailNull];
            }
            [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
            [SystemDialog alert:@"更新完成"];
            [self showLoading:NO];
  
              return ;
        }

        if (![[TTAccountTool sharedTTAccountTool].currentCity.areaName isEqualToString:@"北京"]) {
            _page = 1;
            [_fenListArr removeAllObjects];
            [self.listTableView reloadData];
            [self showLoading:NO];
            [self fenListDetailNull];
            return;
        }
        
        for (NSDictionary * dic in textArr) {
            [_fenListArr addObject:dic];
            }
        
        [self.listTableView setFrame:CGRectMake(0, 0, ScreenWidth, _fenListArr.count*89)];
        if (self.listTableView.frame.size.height>ScreenHeight) {
            [self.listTableView setFrame:CGRectMake(0, 0, ScreenWidth, ScreenHeight)];
            [self.listTableView setContentSize:CGSizeMake(ScreenWidth, _fenListArr.count*89)];
        }

        [self.listTableView reloadData];
        [self stopRefresh];
        [self showLoading:NO];

    } fail:^(NSError *error) {
        if (!_fenListArr.count) {
            [self fenListDetailNull];
        }

        [self showLoading:NO];
        TTLog(@"%@--**********************error积分列表",error);
    }];
    
}
-(void)fenListDetailNull
{
    UIImageView * nullImageView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 40, 40)];
    [nullImageView setCenter:CGPointMake(ScreenWidth*0.5, ScreenHeight*0.5-80)];
    nullImageView.image = [UIImage imageNamed:@"icon_money.png"];
    [self.view addSubview:nullImageView];
    UILabel * nullLabel = [[UILabel alloc]initWithFrame:CGRectMake(10, nullImageView.frame.origin.y+80, 310, 20)];
    nullLabel.text = @"您还没有积分，在商家消费，都可以获";
    [self.view addSubview:nullLabel];
    UILabel * nullLabel2 = [[UILabel alloc]initWithFrame:CGRectMake(10, nullImageView.frame.origin.y+110, 310, 20)];
    nullLabel2.text = @"得积分！";
    [self.view addSubview:nullLabel2];
    
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    TTLog(@"%d...........",_fenListArr.count);
    return _fenListArr.count;
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    TTTieFenCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"TTTieFenCell" owner:self options:nil]lastObject];
    }
    [cell setCellWithDic:[_fenListArr objectAtIndex:indexPath.row]];
    
    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,cell.frame.size.height-1, cell.frame.size.width,0.5);
    [cell.contentView addSubview:divider];

    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 89;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    TTTieFenDetailViewController * TieFenDetailVC = [[TTTieFenDetailViewController alloc]init];
    TieFenDetailVC.headDic = [_fenListArr objectAtIndex:indexPath.row];
    [self.navigationController pushViewController:TieFenDetailVC animated:YES];
    
    

    
}

#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (_header == refreshView) { // 下拉
//        [self loadFenListFromServerAsync:_page==0?1:_page];
//        _page+=1;
        [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
    } else { // 上拉
        _page+=1;
        [self loadFenListFromServerAsync:_page];
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


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
