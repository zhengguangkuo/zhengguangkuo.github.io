//
//  HomeViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "AboutViewController.h"
#import "IIViewDeckController.h"


@interface AboutViewController ()


@end


@implementation AboutViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"关于"];
    //[self NavigationViewBackBtn];
}


-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
}



- (void)backToPrevious
{
    [self.viewDeckController toggleLeftViewAnimated:YES];
    
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
