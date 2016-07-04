//
//  TTEmailFindViewController.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTEmailFindViewController.h"
#import "TTCouponViewCell.h"
#import "UIKeyboardViewController.h"
@interface TTEmailFindViewController ()<UIKeyboardViewControllerDelegate>
{
    NSArray  * _quesNumber;
    NSArray  * _allQues;
    UIKeyboardViewController                *   _keyBoardController; //键盘keyBoardTool

}

@property (weak, nonatomic) IBOutlet UITextField *problemOne;
@property (weak, nonatomic) IBOutlet UITextField *answer;

@property (weak, nonatomic) IBOutlet UIScrollView *scrollView;
- (IBAction)submit:(id)sender;

@end

@implementation TTEmailFindViewController
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    _keyBoardController=[[UIKeyboardViewController alloc] initWithControllerDelegate:self];
	[_keyBoardController addToolbarToKeyboard];
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"找回密码";

    self.view.backgroundColor = TTGlobalBg;

}

- (IBAction)submit:(id)sender {
    [self.view endEditing:YES];
    [SystemDialog alert:@"请输入正确答案"];
}
@end
