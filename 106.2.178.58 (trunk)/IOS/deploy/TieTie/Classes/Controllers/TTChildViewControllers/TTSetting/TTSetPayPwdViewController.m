//
//  TTSetPayPwdViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSetPayPwdViewController.h"
#import "TTPwdPretectViewController.h"
#import "AppDelegate.h"
#import "TTSettingViewController.h"
#import "TTDESEncrypt.h"
@interface TTSetPayPwdViewController ()<UITextFieldDelegate>

@end

@implementation TTSetPayPwdViewController

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
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"设置支付密码";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithTitle:@"下一步" style:UIBarButtonItemStylePlain target:self action:@selector(nextBTNClicked)];
    
    [self addLeftView:self.firstPayPwdTF];
    [self addLeftView:self.secondPayPwdTF];
    [self addLeftView:self.loginPwdTF];
    [self addLeftView:self.msgAuthenticationCodeTF];
}

- (void)addLeftView:(UITextField*)textField
{
    UILabel *leftLab = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 10, 44)];
    textField.leftView = leftLab;
    textField.leftViewMode = UITextFieldViewModeAlways;
}

-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString * tobeStr = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    switch (textField.tag) {
        case 0:
            if (tobeStr.length>6) {
                return NO;
            }
            break;
        case 10:
            if (tobeStr.length>6) {
                return NO;
            }
            break;
        case 100:
            if (tobeStr.length>6) {
                return NO;
            }
            break;
        default:
            if (tobeStr.length>12) {
                return NO;
            }
            break;
    }
    return YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)codeBtnClicked:(id)sender {
    //1用户注册，2修改手机号，3绑定基卡，4创建支付密码5优惠券定向派发6密保令牌7找回密码
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramsDic = @{@"Mobile":mobile,@"Type":@"4",@"sysPlat":@"5"};
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
    
    [TieTieTool startTimer:sender];
}
-(void)nextBTNClicked
{
    [self payPwdAddRequest];
}

- (void)payPwdAddRequest
{
    //{"UserId":"13426364664","Password":"23423sdf","payPassword":"666555","validateCode":"45678","sysPlat":"5"}
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *loginPwd = [NSString stringWithFormat:@"%@{%@}",self.loginPwdTF.text,userId];
    NSString *payPwd = [TTDESEncrypt desencrypt:self.firstPayPwdTF.text];
    NSString *validate = self.msgAuthenticationCodeTF.text;
    NSDictionary *dic = @{@"UserId": userId,
                          @"Password":[NSString md5:loginPwd],
                          @"payPassword":payPwd,
                          @"validateCode":validate,
                          @"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_addPayPwd dict:dic succes:^(id respondObject) {
        [self showLoading:NO];
        NSString *respondCode = [respondObject valueForKey:rspCode];
//        TTLog(@"创建支付密码：%@",respondDescription);
        if ([respondCode isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"支付密码设置成功"];
            NSArray *VCViews = self.navigationController.viewControllers;
            for (UIViewController *VC in VCViews) {
                if ([VC isKindOfClass:[TTSettingViewController class]]) {
                    TTSettingViewController *ttvc = (TTSettingViewController*)VC;
                    //0:有 1：无
                    [TTAccountTool sharedTTAccountTool].currentAccount.payPwdFlag = @"0";
                    [ttvc.table reloadData];
                    [self.navigationController popToViewController:VC animated:YES];
                    break;
                }
            }
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

#pragma mark - UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

@end
