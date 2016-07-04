//
//  TTFouctionInfoViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFouctionInfoViewController.h"

@interface TTFouctionInfoViewController ()

@end

@implementation TTFouctionInfoViewController

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
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        //        for (UIViewController *vc  in self.childViewControllers) {
        ////            vc.edgesForExtendedLayout = UIRectEdgeNone;
        ////            TTLog(@"---%@ = %@",vc,NSStringFromCGRect(vc.view.frame));
        //            CGRect frame = vc.view.frame;
        //            frame.origin.y-=64;
        //            vc.view.frame =frame;
        //        }
    }
#endif

    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"公司介绍";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];

}
-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
