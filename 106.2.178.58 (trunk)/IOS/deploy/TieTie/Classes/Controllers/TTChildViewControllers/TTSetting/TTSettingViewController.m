//
//  TTSettingViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSettingViewController.h"
#import "TTUserBindCardsTableViewController.h"
#import "TTAboutUsViewController.h"
#import "MitenoViewController.h"
#import "TTSuggestionFeedBackViewController.h"
#import "TTModifyInfoViewController.h"
#import "TTPayPwdTableViewController.h"
#import "TTMainViewController.h"
#import "TTSettingViewHeaderImageTableViewCell.h"
#import "TTPwdPretectViewController.h"
#import "TTXMPPTool.h"
#import "AppDelegate.h"
#import "TTMyTwoDimensionCodeViewController.h"
#import "TTCardReadingViewController.h"
#define  CreateVC(XXX) [[XXX##ViewController alloc]init]
@class TTSafeSetViewController;
@class TTMyTwoDimensionCodeViewController;
@interface TTSettingViewController ()
{
    NSArray         *arrayData;
}
- (void)exit;
- (void)updateMyvCardTempAfternotification;
@end

@implementation TTSettingViewController

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
    
    TTLog(@"设置 = TTSettingViewController");
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

    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.imageEdgeInsets = btnEdgeLeft;
    btn.frame = CGRectMake(0, 0, 57, 20);
    btn.userInteractionEnabled = NO;
    [btn setBackgroundImage:[UIImage imageNamed:@"tietie_top_logo"] forState:UIControlStateNormal];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
    self.title = @"更多";
    self.table.backgroundColor = TTGlobalBg;
    
    arrayData = arrayData ? arrayData : [NSArray arrayWithObjects:@[@"个人信息"], @[@"二维码名片"],@[@"基卡设置",/*@"卡片读取",*/@"安全设置"],@[@"关于贴贴"],@[@"退出账号"], nil];
}

- (NSMutableDictionary *)loadInfoFromPlist
{   
    NSArray *paths=NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *plistPath = [[paths objectAtIndex:0] stringByAppendingPathComponent:@"UserInfo.plist"];
    NSLog(@"%@",plistPath);
    NSMutableDictionary *data = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath];
    NSMutableDictionary *info = [data objectForKey:[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId];
    TTLog(@"您当前的用户id：%@",[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId);
    return info;
}

- (void)setContentForViews
{
    NSMutableDictionary *data = [self loadInfoFromPlist];
    if (data) {
        UIImage *image = [UIImage imageWithData:[data objectForKey:@"image"]];
        if (image) {
            self.imageView.image =image;
        }
        if ([data[@"nickName"] length] > 0) {
           self.name.text = [NSString stringWithFormat:@"%@(%@)",data[@"name"],data[@"nickName"]];
        } else {
            self.name.text = data[@"name"];
        }
    } else {
        self.name.text = @"您尚未设置个人信息！";
        self.imageView.image = [UIImage imageNamed:@"Icon-72.png"];
    }
    self.tietieNo.text = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
}

- (void)viewWillAppear:(BOOL)animated
{
    [self setContentForViews];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)updateMyvCardTempAfternotification
{
    NSIndexPath *indextPath = [NSIndexPath indexPathForRow:0 inSection:0];
    [self.table reloadRowsAtIndexPaths:[NSArray arrayWithObject:indextPath] withRowAnimation:UITableViewRowAnimationAutomatic];
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

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:kIdentifier];
        cell.textLabel.font = [UIFont systemFontOfSize:16];
    }
    NSInteger section = [indexPath section];
    NSInteger row = [indexPath row];
    cell.textLabel.text = [[arrayData objectAtIndex:section] objectAtIndex:row];
    
    if (section == [arrayData count] - 1 && row == [[arrayData objectAtIndex:section] count] - 1) {
        cell.textLabel.textColor = [UIColor redColor];
        cell.textLabel.textAlignment = NSTextAlignmentCenter;
        cell.accessoryType = UITableViewCellAccessoryNone;
    } else {
        cell.textLabel.textColor = [UIColor blackColor];
        cell.textLabel.textAlignment = NSTextAlignmentLeft;
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *dictionaryVCs = @{@"基卡设置":@"TTUserBindCardsTableViewController",
                                    @"密保设置":@"TTPwdPretectViewController",
                                    @"二维码名片":@"TTMyTwoDimensionCodeViewController",
                                    @"支付密码":@"TTPayPwdTableViewController",
                                    @"个人信息":@"TTModifyInfoViewController",
                                    @"关于贴贴":@"TTAboutUsViewController",
                                    @"安全设置":@"TTSafeSetViewController",
//                                    @"卡片读取":@"TTCardReadingViewController",
                                    @"退出账号":@""};
    
    NSString *key = [[arrayData objectAtIndex:[indexPath section]]objectAtIndex:[indexPath row]];
    NSString *value = [dictionaryVCs objectForKey:key];
    
    if (value && value.length >0) {
        UIViewController *VC = [[NSClassFromString(value) alloc]init];
        __weak TTSettingViewController *settingVC = self;
        if ([VC isKindOfClass:[TTModifyInfoViewController class]]) {
           TTModifyInfoViewController *ttVc = (TTModifyInfoViewController*)VC;
            ttVc.block_setImage = ^(UIImage *image){
                [settingVC setContentForViews];
            };
        }
//        if ([VC isKindOfClass:[TTMyTwoDimensionCodeViewController class]]) {
//            TTMyTwoDimensionCodeViewController *ttVc = (TTMyTwoDimensionCodeViewController*)VC;
//        }
        [self.navigationController pushViewController:VC animated:YES];
    } else {
        [self exit];
    }
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

- (float)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 1;
}

- (float)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 14;
}

#pragma mark - UIAlertViewDelegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 1) {
        if ([TTAccountTool sharedTTAccountTool].currentAccount!=nil) {
            [TTAccountTool sharedTTAccountTool].currentAccount = nil;
            [TTAccountTool sharedTTAccountTool].currentCity = nil;
            [TTSettingTool setInteger:0 forKey:TTQrCode];
            
                TTCityModel *city = [[TTCityModel alloc] init];
                city.superArea = @"11";
                city.areaName = @"北京";
                city.areaLevel = @"2";
                city.areaCode = @"1100";
                [[TTAccountTool sharedTTAccountTool] addCity:city];
        }
        for (UIViewController *vc in self.navigationController.childViewControllers) {
            TTLog(@"= %@",vc);
        }
        [[NSNotificationCenter defaultCenter] postNotificationName:TTUpdateHomeData object:self userInfo:nil];
        
        [[TTXMPPTool sharedInstance] disconnect];
    }
}

#pragma mark - UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    
}


//退出账号
- (void)exit
{
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"提示" message:@"您确认现在退出账号？" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    [alert show];
//
//    UIActionSheet *actionSheet = [[UIActionSheet alloc]initWithTitle:@"您确认现在退出账号？" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:@"确认" otherButtonTitles:nil, nil];
//    [actionSheet showFromRect:CGRectMake(0, 0, 100, 100) inView:self.view animated:YES];
}

@end
