//
//  TTQuestionViewController.m
//  Miteno
//
//  Created by wg on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTQuestionViewController.h"
#import "TTAnswerViewController.h"
@interface TTQuestionViewController ()<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *userPhone;

- (IBAction)clickNext:(id)sender;

@end

@implementation TTQuestionViewController

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
    
    self.title = @"密保找回";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
    
}
- (void)backPrevious
{
    [self.navigationController popViewControllerAnimated:YES];
}



- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString * tobeStr = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    if (tobeStr.length>11) {
        return NO;
    }
    
    return YES;
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (BOOL)judgePhoneText
{
    //判断手机号
    NSString * regexPhone = @"^((13[0-9])|(147)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    if (self.userPhone.text==nil||[self.userPhone.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入手机号！\n"];
        return NO;
    }
    
    if (![self.userPhone.text isMatchedByRegex:regexPhone]) {
        [SystemDialog alert:@"手机号格式不正确！\n"];
        return NO;
    }
    return YES;
}
- (IBAction)clickNext:(id)sender {
    
    [self.view endEditing:YES];
    
    if ([self judgePhoneText] == NO) {
        return;
    };
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryMipau dict:@{@"Mobile": self.userPhone.text,@"sysPlat":@"5"} succes:^(id responseObject) {
        [self showLoading:NO];
        NSString *key = [responseObject objectForKey:rspCode];
        if ([key isEqualToString:rspCode_success]) {
            if(responseObject[@"myQuestionList"]){
                TTAnswerViewController *answer = [[TTAnswerViewController alloc] init];
                answer.questionsArr = responseObject[@"myQuestionList"];
                answer.userPhone = self.userPhone.text;
                [self.navigationController pushViewController:answer animated:YES];
            }else{
                [self.navigationController popViewControllerAnimated:YES];
                [SystemDialog alert:@"您未设置密保,请使用其他方式找回!"];
            }
        }else{
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        [SystemDialog alert:TTLogConnectError];
    }];
}
@end
