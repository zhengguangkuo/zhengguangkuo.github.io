//
//  TTAnswerViewController.m
//  Miteno
//
//  Created by wg on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAnswerViewController.h"
#import "TTResetPwdViewController.h"

@interface TTAnswerViewController ()
@property (weak, nonatomic) IBOutlet UILabel *question;
@property (weak, nonatomic) IBOutlet UITextField *answer;
- (IBAction)clickCommit:(id)sender;

@end

@implementation TTAnswerViewController

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
    self.title = @"密保验证";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    self.question.enabled = NO;
    self.question.userInteractionEnabled = NO;
    
    for (NSDictionary *dict in self.questionsArr) {
          self.question.text = [NSString stringWithFormat:@"%@",dict[@"Question"]];
      
    }
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)clickCommit:(id)sender {
    [self.view endEditing:YES];
    if (self.answer.text == nil || [self.answer.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入您设置的答案"];
        return;
    }
    [self showLoading:YES];
    [TieTieTool tietieWitheEncodParameterMarked:TTAction_verifyMipau dict:@{@"Mobile":self.userPhone,@"Answer":self.answer.text,@"sysPlat":@"5"} succes:^(id responseObject) {
        [self showLoading:NO];
 
        NSStringEncoding enc = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
        NSString *datas = [[NSString alloc] initWithData:responseObject encoding:enc];
        NSDictionary *d = [datas objectFromJSONString];
        if ([[d objectForKey:rspCode] isEqualToString:rspCode_success]) {
           [SystemDialog alert:@"密保验证成功！"];
            NSString *pwdToken = [d objectForKey:@"passwordToken"];
            TTResetPwdViewController *resetPwdVC = [[TTResetPwdViewController alloc]init];
            resetPwdVC.userPhone = self.userPhone;
            resetPwdVC.pwdToken = pwdToken;
            [self.navigationController pushViewController:resetPwdVC animated:YES];
        }else{
            [SystemDialog alert:[d objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"addMipou: error");
    }];
}
@end
