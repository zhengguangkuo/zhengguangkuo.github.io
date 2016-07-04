//
//  ViewController.m
//  Image
//
//  Created by HWG on 14-1-13.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import "ViewController.h"
#define kCount 7
@interface ViewController ()

@end

@implementation ViewController

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

- (IBAction)clickFull:(id)sender {
    UIImageView *view = [[UIImageView alloc] init];
    view.frame = [UIScreen mainScreen].applicationFrame;
    view.image = [UIImage fullscreenImageWithName:@"new_feature_2.png"];
    [self.view addSubview:view];
}

- (IBAction)clickstretch:(id)sender {
    UIButton *btn = [[UIButton alloc] init];
    btn.frame = CGRectMake(20, 50, 40, 20);
    [btn setBackgroundImage:[UIImage stretchImageWithName:@"tabbar_profile_selected.png"] forState:UIControlStateNormal];
    [self.view addSubview:btn];

}

- (IBAction)cutImge:(id)sender {
    
//    UIImageView *view = [[UIImageView alloc] initWithFrame:CGRectMake(100, 100, 80, 40)];
//    UIImage  *newImage = [[UIImage alloc] init];
//    [newImage getImageFromImage:@"tabbar_profile_selected.png" rect:view.frame];
//    view.image = newImage;
//    [self.view addSubview:view];
}

- (IBAction)clickScale:(id)sender {
    UIImage *primary = [UIImage imageNamed:@"loading_bg.png"];
    UIImageView *view1 = [[UIImageView alloc] init];
    view1.bounds = CGRectMake(0, 0,280, 150);
    view1.center = CGPointMake(ScreenWidth/2, ScreenHeight*0.3);
    view1.image = primary;
    [self.view addSubview:view1];
    NSLog(@"PRI=>%@",NSStringFromCGSize(primary.size));
    UIImageView *view2 = [[UIImageView alloc] init];
    view2.bounds = CGRectMake(0, 0,280, 150);
    view2.center = CGPointMake(ScreenWidth/2, view1.center.y+170);
//    view2.backgroundColor = [UIColor redColor];
//    [view2.image reSizeImage:primary toSize:CGSizeMake(200, 100)];
//    UIImage *image = [primary reSizeImage:primary toSize:CGSizeMake(100, 100)];
    
//    UIImage *image = [primary imageWithAlpha];
//    view2.image = image;
    
    [self.view addSubview:view2];
}

- (IBAction)cleanBg:(id)sender {
    if (self.view.subviews.count == kCount) return;
    for (UIView *view in self.view.subviews) {

           [view removeFromSuperview];

    }

}

@end
