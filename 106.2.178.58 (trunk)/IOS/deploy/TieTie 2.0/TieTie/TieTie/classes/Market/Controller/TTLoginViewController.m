
//
//  TTLoginViewController.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTLoginViewController.h"
@interface TTLoginViewController ()</*UIKeyboardViewControllerDelegate,*/UITextFieldDelegate>
{
   
}
@property (weak, nonatomic) IBOutlet UITextField *userPhone;
@property (weak, nonatomic) IBOutlet UITextField *passWord;
@property (weak, nonatomic) IBOutlet UISwitch *rememberPWD;
@property (weak, nonatomic) IBOutlet UIButton *resterBtn;
@property (weak, nonatomic) IBOutlet UIButton *findPwdBtn;
@property (weak, nonatomic) IBOutlet UIImageView *pwdBg;

- (IBAction)login:(id)sender;
//- (IBAction)registerGo:(id)sender;
- (IBAction)findPWD:(id)sender;
- (IBAction)statuPWD:(id)sender;

- (void)goHome;
@end

@implementation TTLoginViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.isGoHome = NO;
    }
    return self;
}
- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
//    _keyBoardController=[[UIKeyboardViewContr1oller alloc] initWithControllerDelegate:self];
//	[_keyBoardController addToolbarToKeyboard];
}
- (void)viewDidAppear:(BOOL)animated
{
    [self.userPhone becomeFirstResponder];
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    
#ifdef __IPHONE_7_0
    if (iOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
//    UILabel *TitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 50, 44)];
//    TitleLabel.font = [UIFont boldSystemFontOfSize:18];
//    TitleLabel.text = @"用户登录";
//    TitleLabel.textColor = [UIColor whiteColor];
//    TitleLabel.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
//    self.navigationItem.titleView = TitleLabel;
    
    self.title = @"用户登录";
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"icon_nav_quxiao_normal"] style:UIBarButtonItemStylePlain target:self action:@selector(backItem)];
}
- (void)viewDidLayoutSubviews
{
    [super viewDidLayoutSubviews];
    self.view.backgroundColor = TTBgBackGround;

//    self.rememberPWD.on = [[TTSettingTool objectForKey:TTUserShowPWD] boolValue
//    ];
//    self.passWord.secureTextEntry = self.rememberPWD.on;
    
//    if (!IOS7) {
//        //ios7的宽度
//        CGFloat ios7SwitchW = 51;
//        
//        //改变swich位置
//        CGRect frame = self.rememberPWD.frame;
//        CGFloat  switchW = frame.size.width - ios7SwitchW;
//        frame.origin.x-= switchW;
//        self.rememberPWD.frame = frame;
//        
//        //pwd输入框改变位置
//        CGRect pwdFrame = self.passWord.frame;
//        pwdFrame.size.width -= switchW;
//        self.passWord.frame = pwdFrame;
//    }
}
//注册
-(void)gotoRegister
{

}
- (void)backPrevious
{


}

- (IBAction)backItem{
     [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)goHome
{

    [[NSNotificationCenter defaultCenter] postNotificationName:TTUpdateHomeData object:self userInfo:nil];
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
        default:
            if (tobeStr.length>12) {
                return NO;
            }
            break;
    }
    return YES;
}


- (IBAction)login:(id)sender
{
    [self loginUserInContentServer:_userPhone.text pwd:_passWord.text completion:^{
//        if ([[TTSettingTool objectForKey:kUndoneLogin] integerValue]>=0) {
//            [[NSNotificationCenter defaultCenter] postNotificationName:TTSettingTabarBackVc object:nil];
//            [self.navigationController popViewControllerAnimated:NO];
//        }else{
        [self.navigationController popToRootViewControllerAnimated:NO];
            [[NSNotificationCenter defaultCenter] postNotificationName:TTUpdateHomeData object:nil];
//        }
    }];
    //====== add by zgk=== start===
}

- (void)loginXmppServer
{
    
}
- (IBAction)findPWD:(id)sender
{
    TTLog(@"忘记密码");

}

- (IBAction)statuPWD:(id)sender {
    
    self.rememberPWD = (UISwitch *)sender;
    self.passWord.secureTextEntry = !self.passWord.isSecureTextEntry;
    [TTSettingTool setBool:self.rememberPWD.on forKey:TTUserShowPWD];
}

- (void)loginUserInContentServer:(NSString *)userName pwd:(NSString *)passWord completion:(completionBlock)block
{
    TTLog(@"登录");
    [self.view endEditing:YES];
    NSString * regex = @"^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    if (userName.length<1) {
//        [SystemDialog alert:@"请输入手机号！\n"];
        self.warningText.text = @"请输入手机号";
        return;
    }
    if (![userName isMatchedByRegex:regex]) {
        
//        [SystemDialog alert:@"手机号格式不正确！\n"];
        self.warningText.text = @"手机号格式不正确！";
        return;
    }
    NSString * regexP = @"(^[\\w]{6,16}$)";
    if (passWord.length<1) {
//        [SystemDialog alert:@"请输入密码"];
        self.warningText.text = @"请输入密码！";
        return;
    }
    if(![passWord isMatchedByRegex:regexP]){
//        [SystemDialog alert:@"密码应为6-16位数字或字母！\n"];
        self.warningText.text = @"密码应为6-16位数字或字母！";
        return;
    }
    self.warningText.hidden = YES;
    //加密(待定)
    NSString *pwdMd5 = [NSString stringWithFormat:@"%@{%@}",passWord,userName];
    NSDictionary *dict = @{
                           @"Mobile":userName,
                           @"Password":[NSString md5:pwdMd5],
                           @"sysPlat":@"5"
                           };
    //    [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.loginText = @"正在登录...";
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_loginUser dict:dict succes:^(id responseObject) {
        TTLog(@"登录：%@",responseObject);
        NSString *key = [responseObject objectForKey:rspCode];
        if ([key isEqualToString:rspCode_success]) {
            //登录成功 归档

            [self dismissViewControllerAnimated:YES completion:nil];
            block();
        }else{

        }
        //        [MBProgressHUD hideHUDForView:self.view animated:YES];
        [self showLoading:NO];
    } fail:^(NSError *error) {
        TTLog(@"Error: %@", error);
        //        [MBProgressHUD hideHUDForView:self.view animated:YES];
        [self showLoading:NO];
    }];

}








@end
