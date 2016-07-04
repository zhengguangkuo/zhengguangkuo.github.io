//
//  HomeViewController.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import "HomeViewController.h"
#import "AdvertiseView.h"

@interface HomeViewController ()

@property (nonatomic, strong)  NSMutableArray*  ImageUrlArray;

@end

@implementation HomeViewController

@synthesize ImageUrlArray = _ImageUrlArray;

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self SetNaviationTitleName:@"HomePagegfhgfhgfhgfhgfhfgh"];
//    self.ImageUrlArray = [[NSMutableArray alloc] init];
//    [self.ImageUrlArray addObject:@"http://s1.lashouimg.com/zt/201110/27/131972570125915500.jpg"];
//    [self.ImageUrlArray addObject:@"http://s1.lashouimg.com/zt/201110/25/131954190510086200.jpg"];
//    [self.ImageUrlArray addObject:@"http://s1.lashouimg.com/zt/201110/27/131972321636938600.jpg"];
//    [self.ImageUrlArray addObject:@"http://s1.lashouimg.com/zt/201110/25/131953930325377400.jpg"];
//    [self.ImageUrlArray addObject:@"http://s1.lashouimg.com/zt/201110/25/131953847613105000.jpg"];
//    AdvertiseView*  topbgview = [[AdvertiseView alloc]  initWithFrame:CGRectMake(0, 0, 320, 220) defaultImage:@"icon"];
//    [self.view addSubview:topbgview];
//    [topbgview LoadData:self.ImageUrlArray];
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
