//
//  TTSetPayPwdViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTSetPayPwdViewController : TTRootViewController<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *firstPayPwdTF;
@property (weak, nonatomic) IBOutlet UITextField *secondPayPwdTF;
@property (weak, nonatomic) IBOutlet UITextField *loginPwdTF;
@property (weak, nonatomic) IBOutlet UITextField *msgAuthenticationCodeTF;
- (IBAction)codeBtnClicked:(id)sender;

@end
