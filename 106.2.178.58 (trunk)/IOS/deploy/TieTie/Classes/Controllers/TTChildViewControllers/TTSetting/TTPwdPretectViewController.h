//
//  TTPwdPretectViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
typedef void(^ChangeArrayData)(void);

@interface TTPwdPretectViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UIButton *firstProblemBtn;
@property (weak, nonatomic) IBOutlet UIButton *secondProblemBtn;
@property (weak, nonatomic) IBOutlet UIButton *thirdProblemBtn;
@property (weak, nonatomic) IBOutlet UITextField *firstAnswerTF;
@property (weak, nonatomic) IBOutlet UITextField *secondAnswerTF;
@property (weak, nonatomic) IBOutlet UITextField *thirdAnswerTF;
@property (nonatomic,copy) ChangeArrayData arrayDataBlock;
@property (weak, nonatomic) IBOutlet UIButton *commitBtn;
@property (nonatomic) BOOL isFromSetPayPwd;


- (IBAction)firstProblemClicked:(id)sender;
- (IBAction)secondProblemClicked:(id)sender;
- (IBAction)thirdProblemClicked:(id)sender;

- (IBAction)commit:(id)sender;


@end
