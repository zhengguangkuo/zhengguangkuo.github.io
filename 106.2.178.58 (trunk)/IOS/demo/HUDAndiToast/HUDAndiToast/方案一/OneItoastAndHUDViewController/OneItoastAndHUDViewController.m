//
//  OneItoastAndHUDViewController.m
//  HUDAndiToast
//
//  Created by HWG on 14-1-26.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "OneItoastAndHUDViewController.h"
#import "SystemDialog+Show.h"
#import "MBProgressHUD+Show.h"
@interface OneItoastAndHUDViewController ()

@end

@implementation OneItoastAndHUDViewController

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
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark -iToast
- (IBAction)showHint:(id)sender {
    NSString *itostStr = @"提示框显示提示框显示";
    [SystemDialog alert:itostStr];
}

#pragma mark -load
- (IBAction)showLoad:(id)sender {
    //显示指示器
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.labelText = @"兄弟正在帮你努力加载…………";
    
    //加载成功
    //  [MBProgressHUD showSuccessWithText:@"哈哈,加载成功"];
    
    //加载失败
    //  [MBProgressHUD showErrorWithText:@"亲,加载失败了"];
    
    //隐藏指示器
    //  [MBProgressHUD hideAllHUDsForView:self.view animated:YES];
}
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [MBProgressHUD hideAllHUDsForView:self.view animated:YES];
}
@end
