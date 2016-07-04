//
//  HomeViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "AccountViewController.h"
#import "IIViewDeckController.h"


@interface AccountViewController ()


@end

@implementation AccountViewController



- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"我的帐户"];
}

- (void)backToPrevious
{
    [self.viewDeckController toggleLeftViewAnimated:YES];
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
