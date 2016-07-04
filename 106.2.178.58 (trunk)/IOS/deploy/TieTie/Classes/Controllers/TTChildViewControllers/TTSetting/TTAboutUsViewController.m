//
//  TTAboutUsViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAboutUsViewController.h"
#import "TTFouctionInfoViewController.h"
#import "TTSufuClientProtocolViewController.h"
#import "TTCustomerServiceViewController.h"

#define kCheckUpdate 3
#define kSeviceTel   1

@interface TTAboutUsViewController ()

@end

@implementation TTAboutUsViewController
{
    NSArray         *arrayData;
    NSString        *newVersionUrl;
}

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
    self.view.backgroundColor = TTGlobalBg;
    self.table.backgroundColor = TTGlobalBg;
    self.title = @"关于贴贴";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];

    arrayData = arrayData ? arrayData : @[@[@"意见反馈",@"公司介绍",@"帮助中心",@"检查更新"],
                                          @[@"用户协议",@"联系客服"]];

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
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return [arrayData count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[arrayData objectAtIndex:section] count];
}

//- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    return 44;
//}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:kIdentifier];
    }
    
    NSInteger section = [indexPath section];
    NSInteger row = [indexPath row];
    cell.textLabel.text = [[arrayData objectAtIndex:section] objectAtIndex:row];
    cell.textLabel.font = [UIFont systemFontOfSize:12.0];
//    if ((section == [arrayData count]-1) && (row == [[arrayData objectAtIndex:section] count] - 1)) {
//        cell.detailTextLabel.text = @"400-032-8666";
//        cell.detailTextLabel.font = [UIFont systemFontOfSize:12.0];
//    }
    
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    return cell;
}

#pragma mark - UITableViewDelegate
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 1;
}
//- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
//{
//    return 15;
//}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *dictionaryVCs = @{
                                    @"意见反馈":@"TTSuggestionFeedBackViewController",
                                    @"公司介绍":@"TTFouctionInfoViewController",
                                    @"帮助中心":@"MitenoViewController",
                                    @"检查更新":@"",
                                    @"用户协议":@"TTSufuClientProtocolViewController",
                                    @"联系客服":@"TTCustomerServiceViewController"
                                    };
    
    NSString *key = [[arrayData objectAtIndex:[indexPath section]] objectAtIndex:[indexPath row]];
    NSString *value = [dictionaryVCs objectForKey:key];
    
    if (value && (value.length > 0)) {
        UIViewController *VC = [[NSClassFromString(value) alloc]init];
        [self.navigationController pushViewController:VC animated:YES];
    } else {
        if ([key isEqualToString:[[arrayData objectAtIndex:0] lastObject]]) {
            [self onCheckVersion];
            
        }else if ([key isEqualToString:[[arrayData objectAtIndex:1] lastObject]]){
            UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:nil
                                                               delegate:self
                                                      cancelButtonTitle:@"取消"
                                                 destructiveButtonTitle:@"400-032-8666"
                                                      otherButtonTitles:nil, nil];
            [sheet showInView:self.view];
        }
    }
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

#pragma mark - UIActionSheetDelegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel://4000328666"]];
//    NSString * str= @"tel:400-032-8666";
//    UIWebView * callWebview = [[UIWebView alloc] init];
//    [callWebview loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:str]]];
//    [self.view addSubview:callWebview];
}


//获取当前版本号
- (NSString *)currentVersion
{
    //获取本地版本号
    NSDictionary *infoDict = [[NSBundle mainBundle] infoDictionary];
    
    NSString *nowVersion = [infoDict objectForKey:@"CFBundleShortVersionString"];
    
    return nowVersion;
}

-(void)onCheckVersion
{
    NSDictionary *infoDic = [[NSBundle mainBundle] infoDictionary];
    //CFShow((__bridge CFTypeRef)(infoDic));
    NSString *currentVersion = [infoDic objectForKey:@"CFBundleVersion"];
    NSString *appleId = @"882398375";
    NSString *URL = [NSString stringWithFormat:@"http://itunes.apple.com/lookup?id=%@",appleId];//测试id：/*284417350*/
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    [request setURL:[NSURL URLWithString:URL]];
    [request setHTTPMethod:@"POST"];
    NSHTTPURLResponse *urlResponse = nil;
    NSError *error = nil;
    NSData *recervedData = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponse error:&error];
    
    NSString *results = [[NSString alloc] initWithBytes:[recervedData bytes] length:[recervedData length] encoding:NSUTF8StringEncoding];
    
    NSDictionary *dic = [results objectFromJSONString];
    NSArray *infoArray = [dic objectForKey:@"results"];
    if ([infoArray count]) {
        NSDictionary *releaseInfo = [infoArray objectAtIndex:0];
        NSString *lastVersion = [releaseInfo objectForKey:@"version"];
        
        if (![lastVersion isEqualToString:currentVersion]) {
            newVersionUrl = [releaseInfo objectForKey:@"trackVireUrl"];
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"更新" message:@"有新的版本更新，是否前往更新？" delegate:self cancelButtonTitle:@"关闭" otherButtonTitles:@"更新", nil];
            alert.tag = 10000;
            [alert show];
        }
        else
        {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"更新" message:@"此版本为最新版本" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
            alert.tag = 10001;
            [alert show];
        }
    } else {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提醒" message:@"苹果商店没有此应用，无法更新！" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
        alert.tag = 10002;
        [alert show];
    }
}

#pragma mark - UIAlertViewDelegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if ((alertView.tag == 10000) && (buttonIndex == 1)) {
        TTLog(@"click the button to update");
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:newVersionUrl]];
    }
}



@end
