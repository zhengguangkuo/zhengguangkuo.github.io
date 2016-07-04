//
//  TTSafeSetViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSafeSetViewController.h"
#import "TTModifyPwdViewController.h"
#import "TTModifyPhoneNumViewController.h"
#import "TTPwdPretectViewController.h"
#import "TTPayPwdTableViewController.h"

@interface TTSafeSetViewController ()
{
    NSArray *arrayData;
}
@end

@implementation TTSafeSetViewController

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
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
    // Do any additional setup after loading the view from its nib.
    //0有 1没有
    BOOL project = [[TTAccountTool sharedTTAccountTool].currentAccount.mipauFlag integerValue] == 0;
    [self reloadArrayData:project];
    
    self.view.backgroundColor = TTGlobalBg;
    self.navigationItem.title = @"安全设置";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];

}
-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [arrayData count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44.0;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *identifiy = @"cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifiy];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifiy];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        UIView *divider = [[UIView alloc] init];
        divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
        divider.frame = CGRectMake(0,cell.size.height-1, cell.frame.size.width,0.5);
        [cell addSubview:divider];
    }
    cell.textLabel.text = [arrayData objectAtIndex:[indexPath row]];
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *dicVCs = @{@"登录密码":@"TTModifyPwdViewController",
                             @"更改手机号":@"TTModifyPhoneNumViewController",
                             @"设置密保":@"TTPwdPretectViewController",
                             @"支付密码":@"TTPayPwdTableViewController"
                            };
    
    NSString *className = [dicVCs objectForKey:[arrayData objectAtIndex:[indexPath row]]];
    UIViewController *vc = [[NSClassFromString(className) alloc]init];
    
    __weak TTSafeSetViewController *safeVC = self;
    if ([vc isKindOfClass:[TTPwdPretectViewController class]]) {
        TTPwdPretectViewController * ttVC = (TTPwdPretectViewController*)vc;
        ttVC.arrayDataBlock = ^(){
            [safeVC reloadArrayData:YES];
            [tableView reloadData];
        };
    }
    [self.navigationController pushViewController:vc animated:YES];
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

#pragma  mark - custom methods
- (void)reloadArrayData:(BOOL)hasPwdProject
{
    if (hasPwdProject) {
        arrayData = @[@"登录密码",@"支付密码",@"更改手机号"];
    } else {
        arrayData = @[@"设置密保",@"登录密码",@"支付密码",@"更改手机号"];
    }
}

@end
