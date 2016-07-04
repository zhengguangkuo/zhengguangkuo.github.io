//
//  TTMarketViewController.m
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMarketViewController.h"
#import "TTCouponListViewController.h"
#import "UINavigationItem+TTItem.h"
@interface TTMarketViewController ()

@end

@implementation TTMarketViewController

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
    self.title = @"市场";
  
    self.view.backgroundColor = TTBgBackGround;
    
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeContactAdd];
    btn.frame = CGRectMake(100, 100, 30, 80);
    [self.view addSubview:btn];
   
    [btn addTarget:self action:@selector(clickNext) forControlEvents:UIControlEventTouchUpInside];
}

- (void)clickNext
{
    TTCouponListViewController *list = creatVc(TTCouponListViewController);
    [self.navigationController pushViewController:list animated:YES];
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
