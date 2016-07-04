//
//  TTBaseCardInfoViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTBaseCardInfo.h"
#import "TTRootViewController.h"

typedef void (^ChangBackgroundImage)(NSArray*);

@interface TTBaseCardInfoViewController : TTRootViewController
@property (weak, nonatomic) IBOutlet UITextField *cardCodeTF;
@property (weak, nonatomic) IBOutlet UITextField *messageAuthenticationCodesTF;
@property (weak, nonatomic) IBOutlet UIButton *receiveAuthCodeBtn;
- (IBAction)authCodeBtnClicked:(id)sender;
@property (weak, nonatomic) IBOutlet UILabel *prefixNumber;

@property (nonatomic,copy) ChangBackgroundImage block_changeImage;
@property (nonatomic,strong)TTBaseCardInfo *baseCard;
@end
