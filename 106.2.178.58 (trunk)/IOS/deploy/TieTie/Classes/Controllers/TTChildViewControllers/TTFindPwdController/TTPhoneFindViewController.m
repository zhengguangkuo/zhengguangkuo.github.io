//
//  TTPhoneFindViewController.m
//  Miteno
//
//  Created by wg on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPhoneFindViewController.h"
#import "TTLoginViewController.h"
@interface TTPhoneFindViewController ()<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *phoneNumber;
@property (weak, nonatomic) IBOutlet UITextField *SMSCode;
- (IBAction)clickGetCode:(id)sender;

@end

@implementation TTPhoneFindViewController

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

    [self setNavTheme];
    
}
- (void)setNavTheme
{
    self.title = @"手机找回";
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"完成" style:UIBarButtonItemStylePlain target:self action:@selector(complete)];
    
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
            if (tobeStr.length>6) {
                return NO;
            }
            break;
    }
    return YES;
}


- (BOOL)judgePhoneText
{
//    BOOL isPhoneText = YES;
    //判断手机号
    NSString * regexPhone = @"^((13[0-9])|(147)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    if (self.phoneNumber.text==nil||[self.phoneNumber.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入手机号！\n"];
//        isPhoneText = NO;
        return NO;
    }
    
    if (![self.phoneNumber.text isMatchedByRegex:regexPhone]) {
        [SystemDialog alert:@"手机号格式不正确！\n"];
//        isPhoneText = NO;
        return NO;
    }
    return YES;//isPhoneText;
}
//发送短信
- (IBAction)clickGetCode:(id)sender {
    
    [self.view endEditing:YES];
    
    if ([self judgePhoneText] == NO) {
        return;
    };
    [self showLoading:YES];
    //(7找回密码)
    [TieTieTool tietieWithParameterMarked:TTAction_send dict:@{@"Mobile": self.phoneNumber.text,@"Type":@"7",@"sysPlat":@"5"} succes:^(id responseObject) {
        [self showLoading:NO];
        TTLog(@"短信验证码 = %@",responseObject);
        if ([[responseObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"短信验证码已发送！"];
        }else{
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"error = %@",error);
    }];
    
    [TieTieTool startTimer:sender];
}

//完成
- (void)complete
{
    //判断手机号
    if ([self judgePhoneText] == NO) {
        return;
    };
    
    if (self.SMSCode.text == nil||[self.SMSCode.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入有效的验证码!"];
        return;
    }
    
    NSDictionary *paramsDic = @{@"Mobile": self.phoneNumber.text,@"vaildateCode":self.SMSCode.text,@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_findPwd dict:paramsDic succes:^(id responseObject) {
        [self showLoading:NO];
        NSString *key = [responseObject objectForKey:rspCode];
        if ([key isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"新密码已发送到手机"];
            for (UIViewController *vc in [self.navigationController viewControllers]) {
                if ([vc isKindOfClass:[TTLoginViewController class]]) {
                    [self.navigationController popToViewController:vc animated:YES];
                }
            }
        }else{
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        [SystemDialog alert:TTLogConnectError];
    }];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
