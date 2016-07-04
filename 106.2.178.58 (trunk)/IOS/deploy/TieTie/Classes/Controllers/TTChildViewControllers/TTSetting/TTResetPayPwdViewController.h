//
//  TTResetPayPwdViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTResetPayPwdViewController : UIViewController
@property (weak, nonatomic) IBOutlet UITextField *inputPwdFT;
@property (weak, nonatomic) IBOutlet UITextField *reinputPwdFT;
@property (weak, nonatomic) IBOutlet UITextField *loginPwdFT;

- (IBAction)commit:(id)sender;
@end
