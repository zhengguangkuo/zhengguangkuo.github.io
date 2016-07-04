//
//  TTSettingLogOutViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-26.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSettingLogOutViewController.h"
#import "TTLoginViewController.h"
#import "TTAboutUsViewController.h"

@interface TTSettingLogOutViewController ()
{
    NSArray *arrayData;
}

@end

@implementation TTSettingLogOutViewController

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
    // Do any additional setup after loading the view from its nib.
    self.title = @"更多";
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.imageEdgeInsets = btnEdgeLeft;
    btn.frame = CGRectMake(0, 0, 57, 20);
    btn.userInteractionEnabled = NO;
    [btn setBackgroundImage:[UIImage imageNamed:@"tietie_top_logo"] forState:UIControlStateNormal];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
    self.tableView.backgroundColor = TTGlobalBg;
    arrayData = arrayData ? arrayData : @[@[@"基卡设置",@"支付密码"],@[@"关于贴贴"]];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)loginBtnClicked:(id)sender {
    TTLoginViewController *loginVC = [[TTLoginViewController alloc]init];
    [self.navigationController pushViewController:loginVC animated:YES];
}


#pragma mark - UITableViewDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return [arrayData count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[arrayData objectAtIndex:section] count];
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *identifier = @"cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    NSInteger section = [indexPath section];
    NSArray *array = [arrayData objectAtIndex:section];
    cell.textLabel.text =   [array objectAtIndex:[indexPath row]];
    if (section == 0) {
        cell.textLabel.textColor = [UIColor grayColor];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    } else {
        cell.textLabel.textColor = [UIColor blackColor];
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
    }
    
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([indexPath section] == 1) {
        TTAboutUsViewController *aboutVC = [[TTAboutUsViewController alloc] init];
        [self.navigationController pushViewController:aboutVC animated:YES];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}
@end
