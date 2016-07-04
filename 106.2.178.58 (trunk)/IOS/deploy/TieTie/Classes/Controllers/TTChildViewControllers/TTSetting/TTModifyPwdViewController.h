//
//  TTModifyPwdViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTModifyPwdViewController : TTRootViewController
@property (weak, nonatomic) IBOutlet UITextField *oldPwdTF;
@property (weak, nonatomic) IBOutlet UITextField *theNewPwdTF;
@property (weak, nonatomic) IBOutlet UITextField *confirmNewPwdTF;

@end
