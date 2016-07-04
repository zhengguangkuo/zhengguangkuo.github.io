//
//  TTModifyPhoneNumViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTModifyPhoneNumViewController.h"
#import "NSString(Additions).h"
#import "TTLoginViewController.h"
#import "TTUserInfoForXmpp.h"

@interface TTModifyPhoneNumViewController ()<UITextFieldDelegate>

-(void)commit;
@end

@implementation TTModifyPhoneNumViewController

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
            //            vc.edgesForExtendedLayout = UIRectEdgeNone;
            //            TTLog(@"---%@ = %@",vc,NSStringFromCGRect(vc.view.frame));
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"手机号码";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(commit)];
    
    [self addLeftView:self.msgAuthCode];
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
            if (tobeStr.length>11) {
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

-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)authCodeBtnClicked:(id)sender {
    //1用户注册，2修改手机号，3绑定基卡，4创建支付密码5优惠券定向派发6密保令牌7找回密码
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramsDic = @{@"Mobile":mobile,@"Type":@"2",@"sysPlat":@"5"};
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

- (void)commit
{
    if(![NSString checkPhone:self.oldPhoneNum.text])
    {
        [self.view makeToast:@"您输入的旧手机号不正确!"];
        return;
    }
    
    if(![NSString checkPhone:self.theNewPhoneNum.text])
    {
        [self.view makeToast:@"您输入的新手机号不正确!"];
        return;
    }
    
    [self savePhoneNumInfo];
}

- (void)savePhoneNumInfo
{
    NSString *mobileNum = self.oldPhoneNum.text;//[TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *newMobileNum = self.theNewPhoneNum.text;
    NSString *pwd = [NSString stringWithFormat:@"%@{%@}",self.loginPwd.text,mobileNum];
    NSString *newPwd = [NSString stringWithFormat:@"%@{%@}",self.loginPwd.text,newMobileNum];
    NSString *validCode = self.msgAuthCode.text;
    NSDictionary*  dic = [[NSDictionary alloc] initWithObjectsAndKeys:
                          mobileNum,       @"Mobile",
                          newMobileNum,    @"newMobile",
                          [NSString md5:pwd],             @"Password",
                          [NSString md5:newPwd],          @"newPassword",
                          validCode,       @"ValidateCode",
                          @"5",            @"sysPlat",nil];
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_editMobile dict:dic succes:^(id respondObject) {
        [self showLoading:NO];
        TTLog(@"%@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"修改手机号成功"];
            [self updatePhoneNumInXmppServer:self.theNewPhoneNum.text];
            TTLoginViewController *loginVC = [[TTLoginViewController alloc]initWithNibName:@"TTLoginViewController" bundle:nil];
            loginVC.isGoHome = YES;
            [self loginOut];
            [self.navigationController pushViewController:loginVC animated:YES];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
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

- (void)updatePhoneNumInXmppServer:(NSString*)phoneNum
{
    //{"userID":"UID11111111","name":"12365896547"}
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.nowUserId;
    NSDictionary *paramsDic = @{@"userID":userId,@"name":phoneNum};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_updateNames dict:paramsDic succes:^(id responseObj) {
        if ([[responseObj objectForKey:rspCode] isEqualToString:rspCode_success]) {
            TTLog(@"消息服务器中的手机号修改成功");
        } else {
//            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
            TTLog(@"%@",[responseObj objectForKey:rspDesc]);
        }
        [self showLoading:NO];
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
