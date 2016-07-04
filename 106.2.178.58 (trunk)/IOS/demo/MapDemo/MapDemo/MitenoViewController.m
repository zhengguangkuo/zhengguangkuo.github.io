//
//  MitenoViewController.m
//  MapDemo
//
//  Created by zhengguangkuo on 14-5-26.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import "MitenoViewController.h"

@interface MitenoViewController ()

@end

@implementation MitenoViewController
{
    MapsViewController *mapvc;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)showMap:(id)sender {
    mapvc = [[MapsViewController alloc]init];
    mapvc.delegate = self;
    
    [self presentViewController:mapvc animated:NO completion:nil];
    
    /* 
     * add annotation view with coordinate , title.
     *
     *
     * [mapvc addAnnotationView:@"116.380569,39.96056" andMerchName:@"北京金元餐厅"];
     *
     */
    
    /*
     *
     *CLLocationCoordinate2D coor;
     *coor.latitude = 39.96056;
     *coor.longitude = 116.380569;
     *[mapvc addAnnotationViewWithCLLocationCoordinate2D:coor andMerchName:@"北京金元餐厅"];
     *
     */
    
    NSMutableArray *tmpArray = [[NSMutableArray alloc]init];
    NSArray *array01 = [[NSArray alloc]initWithObjects:@"116.380569,39.96056",@"北京金元餐厅", nil];
    NSArray *array02 = [[NSArray alloc]initWithObjects:@"116.381569,39.96256",@"Test", nil];
    NSArray *array03 = [[NSArray alloc]initWithObjects:@"116.382569,39.96356",@"TestOther", nil];
    [tmpArray addObject:array01];
    [tmpArray addObject:array02];
    [tmpArray addObject:array03];
    CLLocationCoordinate2D coor;
    coor.latitude = 39.96456;
    coor.longitude = 116.386569;
    [mapvc addAnnotationViews:tmpArray center:coor];
}

- (void)clickPaopaoView:(BMKAnnotationView *)view
{
    UIAlertView *alert = [[UIAlertView alloc]initWithTitle:nil
                                                   message:@"click the paopao view"
                                                  delegate:self
                                         cancelButtonTitle:@"cancel"
                                         otherButtonTitles:nil, nil];
    [alert show];
    
}


@end
