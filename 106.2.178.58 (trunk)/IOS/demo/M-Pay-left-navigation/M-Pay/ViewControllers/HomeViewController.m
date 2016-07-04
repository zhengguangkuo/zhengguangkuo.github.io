//
//  HomeViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "HomeViewController.h"
#import "AdvertiseView.h"
#import "UIImage(addition).h"

@interface HomeViewController ()

@property (nonatomic, strong)  NSMutableArray*  ImageUrlArray;

@end

@implementation HomeViewController

@synthesize ImageUrlArray = _ImageUrlArray;

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"梅泰诺一卡通"];
    [self NavigationHiddenBack];

    UIButton* cardBagBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    CGFloat PosY = [self GetNavigationBarPosY] + 5;
    [cardBagBtn setFrame:CGRectMake(10, PosY, 300, (470 - 44 - PosY)/2)];
    NSLog(@"y = %f",[self GetNavigationBarPosY]);
    [cardBagBtn setBackgroundImage:[UIImage imageNamed:@"bag"] forState:UIControlStateNormal];
    
    [cardBagBtn addTarget:self action:@selector(CardBagClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:cardBagBtn];
    
    UIButton*  couponsBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [couponsBtn setFrame:CGRectMake(10,cardBagBtn.frame.origin.y + cardBagBtn.frame.size.height + 5, cardBagBtn.frame.size.width,cardBagBtn.frame.size.height)];
   
    [couponsBtn setBackgroundImage:[UIImage imageNamed:@"sale"] forState:UIControlStateNormal];
    
    [couponsBtn addTarget:self action:@selector(CouponsClick:) forControlEvents:UIControlEventTouchUpInside];
    
    [self.view addSubview:couponsBtn];
    
    
    UIButton*  loginButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [loginButton setFrame:CGRectMake(140, couponsBtn.frame.origin.y + couponsBtn.frame.size.height + 3, 100, 40)];
    CGPoint center = loginButton.center;
    [loginButton setCenter:CGPointMake(kScreenWidth/2,center.y)];
    
    
    [loginButton setBackgroundImage:[UIImage UIImageScretchImage:@"main_bt_img_bg"] forState:UIControlStateNormal];
    
    [loginButton setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];

    [loginButton setTitleColor:[UIColor grayColor] forState:UIControlEventTouchDown];
    
    
    [loginButton setTitle:@"登录" forState:UIControlStateNormal];
    
    [loginButton.titleLabel setFont:[UIFont boldSystemFontOfSize:16.0f]];
    
    [loginButton setBackgroundImage:[UIImage UIImageScretchImage:@"main_bt_img_bg_down"] forState:UIControlEventTouchDown];
    
    [loginButton addTarget:self action:@selector(LoginClick:) forControlEvents:UIControlEventTouchUpInside];
   
    [self.view addSubview:loginButton];
}



- (void)CardBagClick:(id)sender
{



}


- (void)CouponsClick:(id)sender
{
    
    
    
}


- (void)LoginClick:(id)sender
{
    NSLog(@"herere");


}


-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
}



-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:YES];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
