//
//  TTFindPayPwdViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
@interface TTFindPayPwdViewController : TTRootViewController
@property (weak, nonatomic) IBOutlet UITextField *msgAuthcodeTF;
@property (weak, nonatomic) IBOutlet UIButton *getAuthCodeBtn;
- (IBAction)nextBtnClicked:(id)sender;

@end
