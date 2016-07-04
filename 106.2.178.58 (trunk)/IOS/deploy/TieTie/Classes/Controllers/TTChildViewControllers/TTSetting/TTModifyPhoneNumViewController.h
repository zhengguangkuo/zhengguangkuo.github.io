//
//  TTModifyPhoneNumViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTModifyPhoneNumViewController : TTRootViewController<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *oldPhoneNum;
@property (weak, nonatomic) IBOutlet UITextField *theNewPhoneNum;
@property (weak, nonatomic) IBOutlet UITextField *msgAuthCode;
@property (weak, nonatomic) IBOutlet UITextField *loginPwd;

- (IBAction)authCodeBtnClicked:(id)sender;

@end
