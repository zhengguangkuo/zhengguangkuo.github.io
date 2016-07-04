//
//  TTModifyPayPwdViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTModifyPayPwdViewController.h"
#import "AppDelegate.h"
#import "TTDESEncrypt.h"

@interface TTModifyPayPwdViewController ()<UITextFieldDelegate>

- (void)commitToSever;
@end

@implementation TTModifyPayPwdViewController

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
    [self.navigationItem setTitle:@"修改支付密码"];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"提交" style:UIBarButtonItemStylePlain target:self action:@selector(commitToSever)];
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    [self addLeftView:self.oldPayPwd];
    [self addLeftView:self.theNewPayPwd];
    [self addLeftView:self.confirmNewPwd];
    [self addLeftView:self.loginPwd];
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

- (void)addLeftView:(UITextField*)textField
{
    UILabel *leftLab = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 10, 44)];
    textField.leftView = leftLab;
    textField.leftViewMode = UITextFieldViewModeAlways;
}

- (void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void)commitToSever
{
    
    if ([self checkText]) {
        [self editPayPassword];
    }
}

- (void)editPayPassword
{
    //{"Mobile":"13426364664","Password":"23423sdf","payPassword":"666555","newPayPwd":"555999","sysPlat":"5"}
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *md5Password = [NSString stringWithFormat:@"%@{%@}",self.loginPwd.text,userId];
    NSString *oldPayPwd = self.oldPayPwd.text;
    NSString *theNewPayPwd = self.theNewPayPwd.text;

    NSDictionary *paramsDic = @{@"Mobile": userId,
                                @"Password":[NSString md5:md5Password],
                                @"payPassword":[TTDESEncrypt desencrypt:oldPayPwd],
                                @"newPayPwd":[TTDESEncrypt desencrypt:theNewPayPwd],
                                @"sysPlat":@"5"};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_editPayPwd dict:paramsDic succes:^(id respondObject) {
        [self showLoading:NO];
        TTLog(@"editPayPassword:%@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"支付密码修改成功"];
            [self.navigationController popViewControllerAnimated:YES];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (BOOL)checkText
{
    NSString *payPwd = self.oldPayPwd.text;
    if (!(payPwd && payPwd.length == 6)) {
        [SystemDialog alert:@"支付密码必须是6位有效数字！"];
        return NO;
    }
    
    payPwd = self.theNewPayPwd.text;
    if (!(payPwd && payPwd.length == 6)) {
        [SystemDialog alert:@"支付密码必须是6位有效数字！"];
        return NO;
    }
    
    payPwd = self.loginPwd.text;
    NSString *loginPwd = [TTAccountTool sharedTTAccountTool].currentAccount.passWord;
    if (![payPwd isEqualToString:loginPwd]) {
        [SystemDialog alert:@"登录密码不正确！"];
        return NO;
    }
    
    return YES;
}

- (BOOL)checkPayPassword:(NSString*)pwd
{
    return NO;
}

#pragma mark - UITextFieldDelegate
- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}
@end
