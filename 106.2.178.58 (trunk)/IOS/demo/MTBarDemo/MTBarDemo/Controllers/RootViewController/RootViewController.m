//
//  RootViewController.m
//  WG_lottery(彩票)
//
//  Created by wg on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "RootViewController.h"

@interface RootViewController ()

@end

@implementation RootViewController

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
	
//    UIAlertView *alertView = [[UIAlertView alloc]initWithTitle:@"ios7及以下适配\导航栏适配\自定义Tabar" message:nil delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"MTBarDemo功能介绍:" message:@"1).ios7及以下适配\n2).导航栏适配\n3).自定义Tabar\n4).上传头像" delegate:self cancelButtonTitle:@"确定" otherButtonTitles: nil];
    [alertView show];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
