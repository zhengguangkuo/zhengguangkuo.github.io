//
//  HomeViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "AboutViewController.h"
#import "IIViewDeckController.h"
#import "FileManager.h"


@interface AboutViewController ()


@end


@implementation AboutViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"关于"];
    
    UITextView* textView = [[UITextView alloc] initWithFrame:[UIScreen mainScreen].bounds];
    textView.font = [UIFont systemFontOfSize:15.0f];
    
    [textView  setBackgroundColor:[UIColor whiteColor]];
    [self.view  addSubview:textView];
    [textView setTextColor:[UIColor blackColor]];
    [textView setText:[FileManager readResource:@"about" type:@"txt"]];
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
