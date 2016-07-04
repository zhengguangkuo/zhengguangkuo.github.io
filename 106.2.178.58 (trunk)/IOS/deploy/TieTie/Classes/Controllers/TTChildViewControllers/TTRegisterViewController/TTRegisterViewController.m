//
//  TTRegisterViewController.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRegisterViewController.h"
#import "UIKeyboardViewController.h"
#import "TTLoginViewController.h"
#import "NavItemView.h"
#import "TTXMPPTool.h"
#import "TTSufuClientProtocolViewController.h"
#define TTAgreeService  @"TTAgreeService"
@interface TTRegisterViewController ()</*UIKeyboardViewControllerDelegate,*/UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UIButton *agreeGray;
@property (weak, nonatomic) IBOutlet UIButton *agreeBlue;

- (void)registerContentServer;
@end

@implementation TTRegisterViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
}
- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self.userPhone becomeFirstResponder];
}
- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
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

    self.title = @"用户注册";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"完成" style:UIBarButtonItemStylePlain target:self action:@selector(registerContentServer)];
    
    self.view.backgroundColor = TTGlobalBg;
    //同意条款
    self.serviceBox.selected = YES;
    [TTSettingTool setInteger:self.serviceBox.selected forKey:TTAgreeService];
    
}
- (void)backPrevious
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString * tobeStr = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    switch (textField.tag) {
        case 0:
            if (tobeStr.length>11) {
                return NO;
            }
            break;
        case 10:
            if (tobeStr.length>12) {
                return NO;
            }
            break;
        case 100:
            if (tobeStr.length>12) {
                return NO;
            }
            break;
        default:
            if (tobeStr.length>6) {
                return NO;
            }
            break;
    }
    return YES;
}


- (BOOL)judgePhoneText
{
    //判断手机号
    NSString * regexPhone = @"^((13[0-9])|(147)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    if (_userPhone.text==nil||[_userPhone.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入手机号！\n"];

        return NO;
    }
    
    if (![_userPhone.text isMatchedByRegex:regexPhone]) {
        [SystemDialog alert:@"手机号格式不正确！\n"];

        return NO;
    }
    return YES;
}

- (void)registerContentServer
{
    //判断手机号
    if ([self judgePhoneText] == NO) {
        return;
    };
    
    //判断密码是否匹配
    NSString *regex = @"(^[\\w]{6,16}$)";
    if(_userPwd.text==nil||[_userPwd.text isEqualToString:@""]){
        [SystemDialog alert:@"请输入密码"];
        return;
    }
    if (![_userPwd.text isMatchedByRegex:regex]) {
        [SystemDialog alert:@"密码应为6-16位数字或字母！"];
        return;
    }
    //判断确认密码
    if (_verifyPassword.text==nil||[_verifyPassword.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入确认密码！"];
        return;
    }
    if (![_verifyPassword.text isMatchedByRegex:regex]) {
        [SystemDialog alert:@"确认密码应为6-16位数字或字母！"];
        return;
    }
    if (![_userPwd.text isEqualToString:_verifyPassword.text]) {
        [SystemDialog alert:@"两次密码输入不一致，请重新输入！"];
        return;
    }
    
    //判断验证码
    if (_code.text==nil||[_code.text isEqualToString:@""]) {
        [SystemDialog alert:@"请获取验证码"];
        return;
    }
    
    if ([[TTSettingTool objectForKey:TTAgreeService] integerValue]==0) {
        [SystemDialog alert:@"同意协议条款,请勾选!"];
        return;
    }

     NSString *pwd = [NSString stringWithFormat:@"%@{%@}",_verifyPassword.text,_userPhone.text];
    NSDictionary *dict = @{
                           @"Mobile":_userPhone.text,
                           @"Password":[NSString md5:pwd],
                           @"validCode":_code.text,
                           @"sysPlat":@"5"
                           };
    TTLog(@"注册信息:%@",dict);
    self.loginText = @"正在注册...";
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_registerUser dict:dict succes:^(id responseObject) {
        NSString *key = [responseObject objectForKey:@"rspCode"];
        NSString *result = [responseObject objectForKey:@"rspDesc"];
        TTLog(@"注册%@",responseObject);
        if ([key isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"注册成功"];
            
            __weak TTRegisterViewController *registerVC = self;
            [registerVC.navigationController popViewControllerAnimated:YES];
        }else{
            [SystemDialog alert:result];
        }

        [self showLoading:NO];
    } fail:^(NSError *error) {
        TTLog(@"Error: %@", error);
        [self showLoading:NO];
    }];

}

//登录
- (void)gotoLogin
{
    TTLoginViewController *login = [[TTLoginViewController alloc] init];
    [self.navigationController pushViewController:login animated:YES];
}
//获取验证码
- (IBAction)getCode:(id)sender {
    [self.view endEditing:YES];

    if ([self judgePhoneText] == NO) {
        return;
    };
    [self showLoading:YES];
    //(1用户注册2修改手机号3绑定基卡，4创建支付密码5优惠券定向派发6密保令牌7找回密码)
    [TieTieTool tietieWithParameterMarked:TTAction_send dict:@{@"Mobile": _userPhone.text,@"Type":@"1",@"sysPlat":@"5"} succes:^(id responseObject) {
        
        TTLog(@"短信验证码 = %@",responseObject);
        if ([[responseObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"短信验证码已发送！"];
        }else {
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];

        }
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"error = %@",error);
    }];
    
    [TieTieTool startTimer:sender];
}


//同意条款
- (IBAction)clickServiceBox:(id)sender {
    UIButton *btn = (UIButton *)sender;
    btn.selected = !btn.isSelected;
    
    if ([[TTSettingTool objectForKey:TTAgreeService] integerValue] == btn.selected) {
        btn.selected = !btn.isSelected;
    }
    [TTSettingTool setInteger:btn.selected forKey:TTAgreeService];

    self.serviceBox.selected = [[TTSettingTool objectForKey:TTAgreeService] integerValue];
    
}

- (IBAction)blueBtnClicked:(id)sender {
    TTSufuClientProtocolViewController *sufuVC = [[TTSufuClientProtocolViewController alloc] init];
    [self.navigationController pushViewController:sufuVC animated:YES];
    
}

@end
