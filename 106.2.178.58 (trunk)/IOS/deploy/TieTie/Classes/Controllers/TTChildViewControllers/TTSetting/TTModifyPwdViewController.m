//
//  TTModifyPwdViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTModifyPwdViewController.h"
#import "TTLoginViewController.h"
#import "TTSettingViewController.h"
@interface TTModifyPwdViewController ()<UITextFieldDelegate>
-(void)saveInfo;
@end

@implementation TTModifyPwdViewController

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
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"登录密码";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];

    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(saveInfo)];
}
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString * tobeStr = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
        if (tobeStr.length>12) {
            return NO;
        }
    return YES;
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
-(void)saveInfo
{
    NSString *mobileValue = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *password = self.oldPwdTF.text;
    NSString *pwdMd5 = [NSString stringWithFormat:@"%@{%@}",password,mobileValue];
    NSString *newPassword = self.theNewPwdTF.text;
    NSString *newPwdMd5 = [NSString stringWithFormat:@"%@{%@}",newPassword,mobileValue];
    NSDictionary* praramDic = [NSDictionary dictionaryWithObjectsAndKeys:
                         mobileValue,    @"Mobile",
                         [NSString md5:pwdMd5],       @"Password",
                         [NSString md5:newPwdMd5],    @"newPassword",
                         @"5",           @"sysPlat",
                         nil];

//    NSString *marked = @"editPassword";
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_editpassword dict:praramDic succes:^(id respondObject) {
        [self showLoading:NO];
        TTLog(@"%@",[respondObject valueForKey:rspDesc]);
        if ([[respondObject valueForKey:@"rspCode"] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"登录密码修改成功"];
            /*
             *  重新登陆操作 清除数据
             */
//            [TTSettingTool removeObjectForKey:kUndoneLogin];
            
            TTLoginViewController *loginVC = [[TTLoginViewController alloc]initWithNibName:@"TTLoginViewController" bundle:nil];
            loginVC.isGoHome = YES;
            [self loginOut];
            [self.navigationController pushViewController:loginVC animated:NO];
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

@end
