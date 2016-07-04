
//
//  TTLoginViewController.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTLoginViewController.h"
#import "UIKeyboardViewController.h"
#import "TTRegisterViewController.h"
#import "TTEmailFindViewController.h"
#import "TTHomeViewController.h"
#import "TTAccount.h"
#import "NavItemView.h"
#import "Base64.h"
#import "TTXMPPTool.h"
#import "TTUserInfoForXmpp.h"
#import "ttFindPwdViewController.h"
#import "TTSettingViewController.h"
#import "NSString+File.h"
@interface TTLoginViewController ()</*UIKeyboardViewControllerDelegate,*/UITextFieldDelegate>
{
    UIKeyboardViewController                *   _keyBoardController; //键盘keyBoardTool
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
    if (IOS7) {
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
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithTitle:@"注册" style:UIBarButtonItemStylePlain target:self action:@selector(gotoRegister)];
    
//    //register notification
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(loginUserInContentServer) name:KNOTIFICATIONCENTER_LOGINXMPPSUCCESS object:nil];
}
- (void)viewDidLayoutSubviews
{
    [super viewDidLayoutSubviews];
    self.view.backgroundColor = TTGlobalBg;

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
    TTRegisterViewController *ttRester = [[TTRegisterViewController alloc] init];
    [self.navigationController pushViewController:ttRester animated:YES];
}
- (void)backPrevious
{
    if (self.isGoHome) {
        [self.navigationController popToRootViewControllerAnimated:NO];
        [self goHome];
    } else {
        [[NSNotificationCenter defaultCenter] postNotificationName:TTSettingTabarBackVc object:nil];
        [self.navigationController popViewControllerAnimated:NO];
    }

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
    
//    [self.navigationController popToRootViewControllerAnimated:NO];
//    [[NSNotificationCenter defaultCenter] postNotificationName:TTUpdateHomeData object:nil];
    //登录xmpp服务器
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    xmppTool.userInfo = [TTUserInfoForXmpp sharedInstance];
//    xmppTool.registerOrLoginFlag = NO;
    [xmppTool connect];
    
    //====== add by zgk ======end ====
}
- (IBAction)findPWD:(id)sender
{
    TTLog(@"忘记密码");
    TTFindPwdViewController *findPwd = [[TTFindPwdViewController alloc] init];
    [self.navigationController pushViewController:findPwd animated:YES];
//    TTEmailFindViewController *emailFind = [[TTEmailFindViewController alloc] init];
//    [self.navigationController pushViewController:emailFind animated:YES];
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
        [SystemDialog alert:@"请输入手机号！\n"];
        return;
    }
    if (![userName isMatchedByRegex:regex]) {
        
        [SystemDialog alert:@"手机号格式不正确！\n"];
        return;
    }
    NSString * regexP = @"(^[\\w]{6,16}$)";
    if (passWord.length<1) {
        [SystemDialog alert:@"请输入密码"];
        return;
    }
    if(![passWord isMatchedByRegex:regexP]){
        [SystemDialog alert:@"密码应为6-16位数字或字母！\n"];
        return;
    }
    
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
            TTAccount *account = [[TTAccount alloc] init];
            account.userPhone = userName;
            account.passWord = passWord;
            account.mipauFlag = responseObject[@"mipauFlag"];
            account.payPwdFlag = responseObject[@"payPwdFlag"];
            account.nowUserId = responseObject[@"userId"];
            [[TTAccountTool sharedTTAccountTool] addAccount:account];
            [self findUser];
            
            block();
        }else{
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];
        }
        //        [MBProgressHUD hideHUDForView:self.view animated:YES];
        [self showLoading:NO];
    } fail:^(NSError *error) {
        TTLog(@"Error: %@", error);
        //        [MBProgressHUD hideHUDForView:self.view animated:YES];
        [self showLoading:NO];
    }];

}

- (void)checkUserInXmppServer:(TTUserInfoForXmpp *)userInfo
{
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.nowUserId;
    NSDictionary *paramDic = @{@"userID":userId};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_judgeUsersAction dict:paramDic succes:^(id responseObj) {
        [self showLoading:NO];
        //{"rspCode":"000","rspDesc":"反馈失败"}
        TTLog(@"judgeUsersAction:%@",[responseObj objectForKey:rspDesc]);
        NSString *responseCode = [responseObj objectForKey:rspCode];
        if ([responseCode isEqualToString:rspCode_success]) {
            NSArray *userList = [responseObj objectForKey:@"userList"];
            if (userList && userList.count > 0) {
                TTUserInfoForXmpp *xmppInfo = [TTUserInfoForXmpp sharedInstance];
                xmppInfo.userId = userId;//[responseObj objectForKey:@"userId"];
                xmppInfo.name = [[userList firstObject] objectForKey:@"name"];
                xmppInfo.password = @"000000";
                
                [self loginXmppServer];
            } else {
                [self registerUserInXmppServer];
            }
        } else /*if ([responseCode isEqualToString:@"002"])*/ {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (void)registerUserInXmppServer
{
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.nowUserId;
    NSString *name = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramDic = @{@"userID":userId,
                               @"name":name
                               /*@"Password":userInfo.password*/};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_addUserAction dict:paramDic succes:^(id responseObj) {
        [self showLoading:NO];
        TTLog(@"addUserAction%@",[responseObj objectForKey:rspDesc]);
        if ([[responseObj objectForKey:rspCode]isEqualToString:rspCode_success]) {
            TTUserInfoForXmpp *xmppInfo = [TTUserInfoForXmpp sharedInstance];
            xmppInfo.userId = userId;//[responseObj objectForKey:@"userId"];
            xmppInfo.name = name;
            xmppInfo.password = @"000000";
            
            [self loginXmppServer];
        } else {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}


- (void)findUser
{
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramDic = @{@"Mobile":mobile,@"sysPlat":@"5"};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_findUser dict:paramDic succes:^(id responseObj) {
        [self showLoading:NO];
        TTLog(@"findUser:%@",[responseObj objectForKey:rspDesc]);
        if ([[responseObj objectForKey:rspCode]isEqualToString:rspCode_success]) {
            //＝＝＝＝＝登录消息服务器＝＝＝＝
            [self checkUserInXmppServer:nil];
            //＝＝＝＝＝登录消息服务器＝＝＝＝
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}


@end
