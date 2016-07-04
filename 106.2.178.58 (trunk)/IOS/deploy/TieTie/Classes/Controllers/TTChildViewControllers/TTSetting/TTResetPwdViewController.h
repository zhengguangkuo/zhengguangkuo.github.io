//
//  TTResetPwdViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-8-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"

@interface TTResetPwdViewController : TTRootViewController
@property (weak, nonatomic) IBOutlet UITextField *pwdAgainTF;
@property (weak, nonatomic) IBOutlet UITextField *pwdTF;
@property (strong,nonatomic)NSString *userPhone;
@property (strong,nonatomic) NSString *pwdToken;

@end
