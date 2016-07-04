//
//  TTMerchantDetailViewController.m
//  Miteno
//
//  Created by wg on 14-7-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMerchantDetailViewController.h"
#import "TTCoupon.h"
@interface TTMerchantDetailViewController ()
{
    UIWebView   *_webView;
}
@end

@implementation TTMerchantDetailViewController
- (void)loadView{
    _webView = [[UIWebView alloc] initWithFrame:kAppFrame];
    _webView.backgroundColor = [UIColor clearColor];
    self.view = _webView;
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
- (void)viewWillAppear:(BOOL)animated{
    
    self.title = [NSString stringWithFormat:@"(%@)商家详情",self.coupon.merName];
    NSString *filePath = [[NSBundle mainBundle]pathForResource:@"help" ofType:@"html"];
    NSString *htmlString = [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil];
    [(UIWebView *)self.view loadHTMLString:htmlString baseURL:[NSURL URLWithString:filePath]];
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"商家详情";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];
}
- (void)backPrevious
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
