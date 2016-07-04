//
//  ViewController.m
//  BaseNavigationController
//
//  Created by HWG on 14-1-17.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import "ViewController.h"

@implementation ViewController

- (void)viewDidLoad
{
    self.title = @"主页";
    
    self.view.backgroundColor = [UIColor grayColor];
    
    //导航右边一个按钮
//    [self initNavigationBarLeftItem:@"backButton.png" RightItem:@"search_off.png"];
    
    //导航右边2个按钮
//    [self initNavigationBarLeftItem:@"backButton.png" RightItem:@"search_off.png:nav_turn_via_1.png"];
    
    //导航右边3个按钮
    [self initNavigationBarLeftItem:@"backButton.png" RightItem:@"search_off.png:nav_turn_via_1.png:fs.png"];
    
    [self.leftButton addTarget:self action:@selector(clickLeft) forControlEvents:UIControlEventTouchUpInside];
    
    [self.searchButton addTarget:self action:@selector(clickSearch) forControlEvents:UIControlEventTouchUpInside];
    
    [self.midButton addTarget:self action:@selector(clickMid) forControlEvents:UIControlEventTouchUpInside];
    
    [self.rightButton addTarget:self action:@selector(clickRight) forControlEvents:UIControlEventTouchUpInside];
}
- (void)clickLeft
{
    NSLog(@"LeftButton");
}
- (void)clickSearch
{
    NSLog(@"searchButton");
}
-(void)clickMid
{
    NSLog(@"midButton");
}
- (void)clickRight
{
    NSLog(@"Right");
}
@end
