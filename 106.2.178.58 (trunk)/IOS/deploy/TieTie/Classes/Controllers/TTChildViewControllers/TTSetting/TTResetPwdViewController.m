//
//  TTResetPwdViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTResetPwdViewController.h"
#import "TTLoginViewController.h"

@interface TTResetPwdViewController ()

- (void)commit;
@end

@implementation TTResetPwdViewController

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
    self.view.backgroundColor = TTGlobalBg;
    self.navigationItem.title = @"重置密码";
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"提交" style:UIBarButtonItemStylePlain target:self action:@selector(commit)];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)commit
{
    NSString *password = self.pwdTF.text;
    NSString * regexP = @"(^[\\w]{6,16}$)";
    if (password.length<1) {
        [SystemDialog alert:@"请输入密码"];
        return;
    }
    if(![password isMatchedByRegex:regexP]){
        [SystemDialog alert:@"密码应为6-16位数字或字母！\n"];
        return;
    }
    if (![password isEqualToString:self.pwdAgainTF.text]) {
        [SystemDialog alert:@"两次输入的密码不一致"];
        return;
    }
    
    [self resetPasswordRequest];
}


- (void)resetPasswordRequest
{
    NSString *mobile = self.userPhone;
    NSString *password = self.pwdTF.text;
    NSString *pwdMd5 = [NSString stringWithFormat:@"%@{%@}",password,mobile];
    NSString *pwdToken = self.pwdToken;
    //{"Mobile":"13426364664","newPassword":"2134445555","pwdToken":"密码令牌","sysPlat":"5"}
    NSDictionary *paramsDic = @{@"Mobile":mobile,@"newPassword":[NSString md5:pwdMd5],@"pwdToken":pwdToken,@"sysPlat":@"5"};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_resetpassword dict:paramsDic succes:^(id responseObj) {
        [self showLoading:NO];
        if ([[responseObj objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"重置密码成功"];
            for (UIViewController *vc in self.navigationController.viewControllers) {
                if ([vc isKindOfClass:[TTLoginViewController class]]) {
                    TTLoginViewController *loginVC = (TTLoginViewController*)vc;
                    loginVC.isGoHome = YES;
                    [self loginOut];
                    [self.navigationController popToViewController:loginVC animated:NO];
                    break;
                }
            }
        } else {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        [SystemDialog alert:@"重置密码失败"];
    }];
}

- (void)loginOut
{
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
    
    [[TTXMPPTool sharedInstance] disconnect];
}

@end
