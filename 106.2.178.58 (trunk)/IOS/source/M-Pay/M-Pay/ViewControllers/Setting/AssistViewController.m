//
//  HomeViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "AssistViewController.h"
#import "IIViewDeckController.h"


@interface AssistViewController ()


@end

@implementation AssistViewController



- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"辅助功能"];
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
