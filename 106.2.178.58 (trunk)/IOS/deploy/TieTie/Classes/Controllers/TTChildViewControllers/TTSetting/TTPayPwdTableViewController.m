//
//  TTPayPwdTableViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPayPwdTableViewController.h"
#import "TTSetPayPwdViewController.h"
#import "TTModifyPayPwdViewController.h"
#import "TTFindPayPwdViewController.h"

@interface TTPayPwdTableViewController ()
{
    NSArray *arrayData;
}
- (void)setArrayData:(BOOL)hasPWD;
@end

@implementation TTPayPwdTableViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.tableView.backgroundColor = TTGlobalBg;
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.navigationItem.title = @"支付密码";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
}

- (void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    //0:have no pay pwd, 1:have pay pwd
    BOOL hasPayPwd = [[TTAccountTool sharedTTAccountTool].currentAccount.payPwdFlag isEqualToString:@"0"];
    [self setArrayData:hasPayPwd];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)setArrayData:(BOOL)hasPWD
{
    arrayData = (hasPWD ? @[@"修改支付密码"] : @[@"设置支付密码"]);
    [self.tableView reloadData];
}

#pragma mark - Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [arrayData count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:kIdentifier];
    }
    // Configure the cell...
    
    cell.textLabel.text = [arrayData objectAtIndex:[indexPath row]];
    [cell.textLabel setFont:[UIFont systemFontOfSize:14]];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    
    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,cell.size.height-1, cell.frame.size.width,0.5);
    [cell addSubview:divider];
    return cell;
}

#pragma mark - UITableDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([[TTAccountTool sharedTTAccountTool].currentAccount.payPwdFlag isEqualToString:@"1"]) {
        TTSetPayPwdViewController *setPwdVC = [[TTSetPayPwdViewController alloc]initWithNibName:@"TTSetPayPwdViewController" bundle:nil];
        [self.navigationController pushViewController:setPwdVC animated:YES];
    } else {
        if ([indexPath row]==0) {
            TTModifyPayPwdViewController *modifyVC = [[TTModifyPayPwdViewController alloc]initWithNibName:@"TTModifyPayPwdViewController" bundle:nil];
            [self.navigationController pushViewController:modifyVC animated:YES];
        } else {
            TTFindPayPwdViewController *findVC = [[TTFindPayPwdViewController alloc]initWithNibName:@"TTFindPayPwdViewController" bundle:nil];
            [self.navigationController pushViewController:findVC animated:YES];
        }
    }
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

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
