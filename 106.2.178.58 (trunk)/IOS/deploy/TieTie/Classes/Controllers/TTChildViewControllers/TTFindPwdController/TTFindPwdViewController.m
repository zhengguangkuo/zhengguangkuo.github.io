//
//  TTFindPwdViewController.m
//  Miteno
//
//  Created by wg on 14-7-30.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFindPwdViewController.h"
#import "SettingCell.h"
#import "TTPhoneFindViewController.h"
#import "TTQuestionViewController.h"
#define kSpceCellLine   15

@interface TTFindPwdViewController ()
{
    NSArray *   _showData;
}
@end

@implementation TTFindPwdViewController

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

    
    [self setNavTheme];
    
    _showData = @[@"手机找回",@"密保找回"];
    
    self.view.backgroundColor = TTGlobalBg;
    self.tableView.contentInset = UIEdgeInsetsMake(kSpceCellLine, 0, 0, 0);
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,0,ScreenWidth, 1.0);
    [self.view addSubview:divider];
}

- (void)setNavTheme
{
    self.title = @"找回密码";
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
    
}
#pragma mark -tableViewdelegate And datasource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 2;
}
#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    SettingCell *cell = [SettingCell settingCellWithTableView:tableView];
    UIImageView *img = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"whightbg2"]];
    cell.backgroundView = img;
    cell.indexPath = indexPath;
    cell.textLabel.text = _showData[indexPath.row];
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.row==0) {
        //手机找回
        TTPhoneFindViewController  *phoneF = [[TTPhoneFindViewController alloc] init];
        [self.navigationController pushViewController:phoneF animated:YES];
    }else{
        //问题找回
        TTQuestionViewController *question = [[TTQuestionViewController alloc] init];
        [self.navigationController pushViewController:question animated:YES];
    }
}
- (void)backPrevious
{
    [self.navigationController popViewControllerAnimated:YES];
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

@end
