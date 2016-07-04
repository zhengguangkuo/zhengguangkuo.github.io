//
//  TTFindPayPwdViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFindPayPwdViewController.h"
#import "TTPwdPretectViewController.h"

@interface TTFindPayPwdViewController ()

@end

@implementation TTFindPayPwdViewController

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
    [self.navigationItem setTitle:@"找回支付密码"];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)nextBtnClicked:(id)sender {
    TTPwdPretectViewController *pretectVC = [[TTPwdPretectViewController alloc] initWithNibName:@"TTPwdPretectViewController" bundle:nil];
    pretectVC.isFromSetPayPwd = NO;
    [self.navigationController pushViewController:pretectVC animated:YES];
}

- (void)requestAuthCode
{
    //1用户注册，2修改手机号，3绑定基卡，4创建支付密码5优惠券定向派发6密保令牌7找回密码
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramsDic = @{@"Mobile":mobile,@"Type":@"7",@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_send dict:paramsDic succes:^(id respondObject) {
        [self showLoading:NO];
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"短信验证码已发送！"];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}
@end
