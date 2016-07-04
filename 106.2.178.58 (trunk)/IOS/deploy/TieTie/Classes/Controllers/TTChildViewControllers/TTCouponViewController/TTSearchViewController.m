//
//  TTSearchViewController.m
//  Miteno
//
//  Created by wg on 14-6-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSearchViewController.h"
#import "NavItemView.h"
@interface TTSearchViewController ()
{
    NSMutableArray * _businessNameArr;

    UITableView * _historyBusinessTV;

    UIButton * _releaseHistoryBTN;
}
@end

@implementation TTSearchViewController

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

    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];

    self.title = @"搜索";
    self.view.backgroundColor = TTGlobalBg;
    
    _historyBusinessTV = [[UITableView alloc]init];
    _historyBusinessTV.delegate = self;
    _historyBusinessTV.dataSource = self;
    
    _releaseHistoryBTN = [[UIButton alloc]init];
    [_releaseHistoryBTN addTarget:self action:@selector(releaseAllHistory) forControlEvents:UIControlEventTouchUpInside];
    [_releaseHistoryBTN setTitle:@"清空历史记录" forState:UIControlStateNormal];
    [_releaseHistoryBTN.titleLabel setFont:[UIFont systemFontOfSize:14]];
    [_releaseHistoryBTN setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
    [_releaseHistoryBTN setTitleColor:[UIColor lightTextColor] forState:UIControlStateHighlighted];
    
    [self.view addSubview:_historyBusinessTV];
    [self.view addSubview:_releaseHistoryBTN];

//    _businessNameArr = [NSMutableArray arrayWithObjects:@"元气寿司",@"米斯特披萨",@"黑松白鹿",@"汉堡王", nil];
    _businessNameArr = [NSMutableArray arrayWithArray:[[NSUserDefaults standardUserDefaults] objectForKey:@"historyList"]];

    [self setFrame];
}
-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}
-(void)setFrame
{
    if (_businessNameArr) {
        int count = _businessNameArr.count;
        if (_businessNameArr.count>4) {
            count = 4;
        }
        [_historyBusinessTV setFrame:CGRectMake(0, 44, 320, 40*count)];
        [_releaseHistoryBTN setFrame:CGRectMake(106.5,44+_historyBusinessTV.frame.size.height+8 , 107, 30)];
    }
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 40;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    self.searchTF.text = [_businessNameArr objectAtIndex:indexPath.row];
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _businessNameArr.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        //cell = [[[NSBundle mainBundle]loadNibNamed:@"BusinessDetailsFirstViewTableViewCell" owner:self options:nil]lastObject];
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    cell.textLabel.text = [_businessNameArr objectAtIndex:indexPath.row];
    [cell.textLabel setFont:[UIFont systemFontOfSize:15]];
    return cell;

}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/
-(void)releaseAllHistory
{
    [_businessNameArr removeAllObjects];
    [self setFrame];
    [_historyBusinessTV reloadData];
}

- (IBAction)searchGO:(id)sender {
    TTLog(@"开始搜索！");

    if (![_searchTF.text isEqualToString:@""]) {
//
//        for (NSString * str in _businessNameArr) {
//            if (![_searchTF.text isEqualToString:str]) {
                [_businessNameArr addObject:_searchTF.text];
//            }
//        }
    }
    

    [[NSUserDefaults standardUserDefaults] setValue:_businessNameArr forKey:@"historyList"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    [self setFrame];
    [_historyBusinessTV reloadData];
}
@end
