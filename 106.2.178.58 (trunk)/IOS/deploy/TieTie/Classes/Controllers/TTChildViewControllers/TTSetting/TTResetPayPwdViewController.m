//
//  TTResetPayPwdViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTResetPayPwdViewController.h"
#import "TTPayPwdTableViewController.h"

@interface TTResetPayPwdViewController ()

@end

@implementation TTResetPayPwdViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = TTGlobalBg;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)commit:(id)sender {
    NSArray *controllerVC = self.navigationController.viewControllers;
    for (UIViewController *VC in controllerVC) {
        if ([VC isKindOfClass:[TTPayPwdTableViewController class]]) {
            [self.navigationController popToViewController:VC animated:YES];
            break;
        }
    }
}
@end
