//
//  TTRegisterViewController.h
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
@interface TTRegisterViewController : TTRootViewController
@property (weak, nonatomic) IBOutlet UITextField *userPhone;
@property (weak, nonatomic) IBOutlet UITextField *userPwd;
@property (weak, nonatomic) IBOutlet UITextField *verifyPassword;
@property (weak, nonatomic) IBOutlet UITextField *code;
@property (weak, nonatomic) IBOutlet UIButton *codeBtn;
- (IBAction)getCode:(id)sender;
@property (weak, nonatomic) IBOutlet UIButton *checkBox;
@property (weak, nonatomic) IBOutlet UIButton *end;
@property (weak, nonatomic) IBOutlet UIButton *serviceBox;
- (IBAction)clickServiceBox:(id)sender;


- (IBAction)blueBtnClicked:(id)sender;
@end
