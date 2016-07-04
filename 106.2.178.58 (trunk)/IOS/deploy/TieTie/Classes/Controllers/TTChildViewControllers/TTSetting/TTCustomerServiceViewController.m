//
//  TTCustomerServiceViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-28.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCustomerServiceViewController.h"

@interface TTCustomerServiceViewController ()
{
    NSArray *arrayData;
}

@end

@implementation TTCustomerServiceViewController

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
    self.title = @"联系客服";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    self.view.backgroundColor = TTGlobalBg;
    // Do any additional setup after loading the view from its nib.
    arrayData = arrayData ? arrayData : @[@[@"客服邮箱:",@"service@miteno.com"],@[@"客服电话(8:00—20:00):",@"400-032-8666"]];
    NSDictionary *infoDic = [[NSBundle mainBundle] infoDictionary];
    NSString *currentVersion = [infoDic objectForKey:@"CFBundleVersion"];
    self.versionLab.text = [NSString stringWithFormat:@"ver:%@",currentVersion];
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

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:kIdentifier];
        cell.textLabel.font = [UIFont systemFontOfSize:16];
    }
    NSInteger row = [indexPath row];
    NSArray *arr = arrayData[row];
    cell.textLabel.text = [arr firstObject];
    cell.detailTextLabel.text = [arr lastObject];
    cell.detailTextLabel.textColor = [UIColor blueColor];
    
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([indexPath row] == 0) {
//        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"service@miteno.com"]];
        [self sendMailInApp];
    } else {
        if ([indexPath row] == 1) {
            
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
    if (buttonIndex == 0) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel://4000328666"]];
    }
    
    //    NSString * str= @"tel:400-032-8666";
    //    UIWebView * callWebview = [[UIWebView alloc] init];
    //    [callWebview loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:str]]];
    //    [self.view addSubview:callWebview];
}

#pragma mark - MFMailComposeViewControllerDelegate
//激活邮件功能
- (void)sendMailInApp
{
    Class mailClass = (NSClassFromString(@"MFMailComposeViewController"));
    if (!mailClass) {
        [SystemDialog alert:@"当前系统版本不支持应用内发送邮件功能，您可以使用mailto方法代替"];
        return;
    }
    if (![mailClass canSendMail]) {
        [SystemDialog alert:@"用户没有设置邮件账户"];
        return;
    }
    [self displayMailPicker];
}

//调出邮件发送窗口
- (void)displayMailPicker
{
    MFMailComposeViewController *mailPicker = [[MFMailComposeViewController alloc] init];
    mailPicker.mailComposeDelegate = self;
    
    //设置主题
    [mailPicker setSubject: @"问题咨询"];
    //添加收件人
    NSArray *toRecipients = [NSArray arrayWithObject: @"service@miteno.com"];
    [mailPicker setToRecipients: toRecipients];
    
    NSString *emailBody = @"感谢您关注贴贴世界，请在正文中输入您的问题。";
    [mailPicker setMessageBody:emailBody isHTML:YES];
    [self presentViewController:mailPicker animated:YES completion:nil];

}

#pragma mark - 实现 MFMailComposeViewControllerDelegate
- (void)mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
    //关闭邮件发送窗口
    [self dismissViewControllerAnimated:YES completion:nil];
    NSString *msg;
    switch (result) {
        case MFMailComposeResultCancelled:
            msg = @"用户取消编辑邮件";
            break;
        case MFMailComposeResultSaved:
            msg = @"用户成功保存邮件";
            break;
        case MFMailComposeResultSent:
            msg = @"用户点击发送，将邮件放到队列中，还没发送";
            break;
        case MFMailComposeResultFailed:
            msg = @"用户试图保存或者发送邮件失败";
            break;
        default:
            msg = @"";
            break;
    }
    [SystemDialog alert:msg];
}

@end
