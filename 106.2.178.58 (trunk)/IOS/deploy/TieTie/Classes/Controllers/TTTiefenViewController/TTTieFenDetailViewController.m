//
//  TTTieFenDetailViewController.m
//  Miteno
//
//  Created by APPLE on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTieFenDetailViewController.h"
#import "TTTieFenDetailTableViewCell.h"
@interface TTTieFenDetailViewController ()<UITableViewDataSource,UITableViewDelegate,MJRefreshBaseViewDelegate>
{
    NSMutableArray * _fenListDetailArr;
    UITableView * _TieFenDetailTableView;

    MJRefreshHeaderView  *  _header;            //上拉
    MJRefreshFooterView  *  _footer;            //下拉
    NSInteger _page;

}
@end

@implementation TTTieFenDetailViewController

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
    _page = 0;
    _fenListDetailArr = [NSMutableArray array];

    // Do any additional setup after loading the view from its nib.
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
//    UILabel *TitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 50, 44)];
//    TitleLabel.font = [UIFont boldSystemFontOfSize:18];
//    TitleLabel.text = @"交易记录";
//    TitleLabel.textColor = [UIColor whiteColor];
//    TitleLabel.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
//    self.navigationItem.titleView = TitleLabel;

    self.title = @"交易记录";
    [self addSubViews];
    
    [self setHeadTieFenDetail];
    [self loadFenDetailListFromServerAsync:_page];
}
-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}

-(void)addSubViews
{
    _TieFenDetailTableView = [[UITableView alloc]init];
    //添加下拉刷新和上拉加载更多
    _header = [[MJRefreshHeaderView alloc] init];
    _header.delegate = self;
    [_header beginRefreshing];
    _header.scrollView = _TieFenDetailTableView;
    
    _footer = [[MJRefreshFooterView alloc] init];
    _footer.scrollView = _TieFenDetailTableView;
    _footer.delegate = self;
    
    
    _TieFenDetailTableView.separatorStyle = UITableViewCellSeparatorStyleSingleLine;
    _TieFenDetailTableView.separatorColor = [UIColor blackColor];
    _TieFenDetailTableView.delegate = self;
    _TieFenDetailTableView.dataSource = self;
    _header.beginRefreshingBlock = ^(MJRefreshBaseView *view){};
    _header.scrollView = _TieFenDetailTableView;
    _footer.beginRefreshingBlock = ^(MJRefreshBaseView *view){};
    _footer.scrollView =  _TieFenDetailTableView;
    [self.view addSubview:_TieFenDetailTableView];

}
-(void)setHeadTieFenDetail
{

    _headName.text = _headDic[@"orgName"];
    _headTiefen.text = _headDic[@"pointBalance"];
    [_headImage setImageWithURL:[NSURL URLWithString:_headDic[@"picPath"]] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];

}

- (void)loadFenDetailListFromServerAsync:(NSInteger)startPage
{
    NSString * page = [NSString stringWithFormat:@"%d",startPage];
    
    [self showLoading:YES];
    
    NSDictionary * dict =@{@"page":page,
                           @"rows":@"10",
                           @"Mobile":@"13691334056",
                           @"orgId":@"10000131",
                           @"tranTypeFlg":@"0",
                           @"tranTimeFlg":@"1",
                           @"sysPlat":@"5"};
    [TieTieTool tietieWithParameterMarked:TTAction_searchCreditsDetail dict:dict succes:^(id responseObject) {
      
        TTLog(@"%@***************积分详情列表",responseObject);
        NSArray * arr = [NSArray arrayWithObject:responseObject[@"myCreditsDetailList"]];
        NSArray * textArr = [NSArray arrayWithArray:[arr lastObject]];
        if (!textArr.count)
        {
            [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
            [SystemDialog alert:@"更新完成"];
            [self showLoading:NO];
            
            return ;
        }
        
        for (NSDictionary * dic in [arr lastObject])
        {
        
            [_fenListDetailArr addObject:dic];
        
                }

        [self showLoading:NO];
        
        [_TieFenDetailTableView setFrame:CGRectMake(0, 76, 320, _fenListDetailArr.count*71)];
        if (_fenListDetailArr.count>=6) {
           [_TieFenDetailTableView setFrame:CGRectMake(0, 76, 320, 6*71)];

        }
        [_TieFenDetailTableView reloadData];
        [_header endRefreshing];
        [_footer endRefreshing];

                
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@--**********************error积分详情列表",error);
    }];
    
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _fenListDetailArr.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    TTTieFenDetailTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"TTTieFenDetailTableViewCell" owner:self options:nil]lastObject];
    }
    [cell setTieFenDetailWithDic:[_fenListDetailArr objectAtIndex:indexPath.row]];
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 71;
}

#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (_header == refreshView) { // 下拉
//        [self loadFenDetailListFromServerAsync:_page==0?1:_page];
//        _page+=1;
        [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
    } else { // 上拉
        _page+=1;
        [self loadFenDetailListFromServerAsync:_page];
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
}

@end
