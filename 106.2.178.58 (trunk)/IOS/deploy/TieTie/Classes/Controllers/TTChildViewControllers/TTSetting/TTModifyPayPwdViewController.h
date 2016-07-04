//
//  TTModifyPayPwdViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTModifyPayPwdViewController :TTRootViewController<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *oldPayPwd;
@property (weak, nonatomic) IBOutlet UITextField *theNewPayPwd;
@property (weak, nonatomic) IBOutlet UITextField *confirmNewPwd;
@property (weak, nonatomic) IBOutlet UITextField *loginPwd;

@end
