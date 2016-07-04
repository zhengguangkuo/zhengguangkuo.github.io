//
//  TTBaseCardInfoViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBaseCardInfoViewController.h"
#import "NSObject+Property.h"
#import "TTUserBindCardsTableViewController.h"

@interface TTBaseCardInfoViewController ()<UITextFieldDelegate>

- (void)saveInfo;

@end

@implementation TTBaseCardInfoViewController

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
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    self.navigationItem.title = @"基卡设置";
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(saveInfo)];
    
    [self addLeftView:self.cardCodeTF];
    self.prefixNumber.text = self.baseCard.cardBin;
    [self addLeftView:self.messageAuthenticationCodesTF];
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

    if (textField.tag==0) {
        NSInteger inputLength = [self.baseCard.cardLength integerValue] - self.baseCard.cardBin.length;
        if (tobeStr.length > inputLength) {
            return NO;
        }
    }else{
        if (tobeStr.length>6) {
            return NO;
        }
    }
    return YES;
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)saveInfo
{
    NSString *cardNo = [NSString stringWithFormat:@"%@%@",self.prefixNumber.text,self.cardCodeTF.text];
    BOOL isValid = [self checkCardNo:cardNo] && [self checkAuthCode:self.messageAuthenticationCodesTF.text];
    
    if (isValid) {
        NSArray *array = @[cardNo,self.messageAuthenticationCodesTF.text];
        [self bindBaseCardRequest:array];
    } else {
        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:nil message:@"卡号或验证码不正确！" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:nil, nil];
        [alert show];
    }
        
}

- (BOOL)checkCardNo:(NSString *)cardNO
{
    BOOL ret = NO;
    
    if (cardNO && [self.baseCard.cardLength integerValue] == cardNO.length) {
        
        ret = [cardNO hasPrefix:self.baseCard.cardBin];
        
    }
    
    return ret;
}

- (BOOL)checkAuthCode:(NSString *)authcode
{
    BOOL ret = NO;
    
    if (authcode) {
        ret = authcode.length == 6;
    }
    
    return ret;
}

- (void)bindBaseCardRequest:(NSArray*)array
{
    TTBaseCardInfo *info = self.baseCard;
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *cardTypeNo = info.cardTypeNo;
    NSString *cardNo = [array firstObject];
    NSString *validateCode = [array lastObject];
    
    NSDictionary *dict = @{@"mobile":mobile,
                           @"cardTypeNo":cardTypeNo,
                           @"cardNo":cardNo,
                           @"validateCode":validateCode,
                           @"sysPlat":@"5"};
    
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_bindBaseCard dict:dict succes:^(id respondObject) {
        [self showLoading:NO];
        TTLog(@"bindBaseCard: %@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"基卡绑定成功"];
            NSArray *controllerVC = self.navigationController.viewControllers;
            for (UIViewController *VC in controllerVC) {
                if ([VC isKindOfClass:[TTUserBindCardsTableViewController class]]) {
                    TTUserBindCardsTableViewController *userBindCardsVC = (TTUserBindCardsTableViewController*)VC;
                    [userBindCardsVC loadUserBindCardsFromSever];
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

- (IBAction)authCodeBtnClicked:(id)sender {
    //1用户注册，2修改手机号，3绑定基卡，4创建支付密码5优惠券定向派发6密保令牌7找回密码
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramsDic = @{@"Mobile":mobile,@"Type":@"3",@"sysPlat":@"5"};

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
@end
