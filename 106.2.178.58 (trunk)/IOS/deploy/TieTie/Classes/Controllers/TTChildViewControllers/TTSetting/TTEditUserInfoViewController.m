//
//  TTEditUserInfoViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTEditUserInfoViewController.h"
#import "NSString(Additions).h"

@interface TTEditUserInfoViewController ()

- (void)saveValue;
@end

@implementation TTEditUserInfoViewController

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
    if ([self.title isEqualToString:@"手机"]||[self.title isEqualToString:@"宅电"]) {
        self.editValueTF.keyboardType = UIKeyboardTypeNumberPad;
    }
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = TTGlobalBg;
    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(saveValue)];
    self.editValueTF.text = self.contentLable.text;
    NSString *title = self.title;
    self.editValueTF.placeholder = [NSString stringWithFormat:@"请输入您的%@",title];
    
    [self addLeftView:self.editValueTF];
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

- (void)saveValue
{
    NSString *value = self.editValueTF.text;
    if ([self checkValue:value]) {
        [self checkValue:value];
        self.block(value);
        [self.navigationController popViewControllerAnimated:YES];
    }
}

- (BOOL)checkValue:(NSString *)value
{
    if ([self.title isEqualToString:@"手机"]) {
        if (![NSString checkPhone:value]) {
            [SystemDialog alert:@"您输入的手机号格式不正确"];
            return NO;
        }
    }
    
    if ([self.title isEqualToString:@"邮箱"]) {
        if (![NSString checkEmail:value]) {
            [SystemDialog alert:@"您输入的邮箱格式不正确"];
            return NO;
        }
    }
    
    return YES;
}

@end
