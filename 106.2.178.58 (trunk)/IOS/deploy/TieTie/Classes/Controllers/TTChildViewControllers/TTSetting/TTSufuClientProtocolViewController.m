//
//  TTSufuClientProtocolViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSufuClientProtocolViewController.h"

@interface TTSufuClientProtocolViewController ()
{
     UIWebView   *_webView;
}
@end

@implementation TTSufuClientProtocolViewController
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

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = [UIColor whiteColor];
    self.title = @"用户协议";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    


    
//    [WebView loadHTMLString:html baseURL:nil];
//    [self.view addSubview:WebView];
}
- (void)viewWillAppear:(BOOL)animated{
    [super viewWillDisappear:animated];
    
//    UIWebView * WebView = [[UIWebView alloc]initWithFrame:CGRectMake(15,0,ScreenWidth,ScreenHeight)];
//    WebView.backgroundColor = [UIColor whiteColor];
    
    NSString *filePath = [[NSBundle mainBundle]pathForResource:@"aboutUs(1)" ofType:@"html"];
    NSString *htmlString = [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:nil];
    [(UIWebView *)self.view loadHTMLString:htmlString baseURL:[NSURL URLWithString:filePath]];
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
