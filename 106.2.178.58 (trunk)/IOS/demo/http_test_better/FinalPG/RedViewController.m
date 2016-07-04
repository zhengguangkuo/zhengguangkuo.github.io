//
//  ViewController.m
//  ttyhthfhfh
//
//  Created by guorong on 13-9-23.
//  Copyright (c) 2013年 guorong. All rights reserved.
//

#import "RedViewController.h"
#import "UIImageView+DispatchLoad.h"

#define  URL1 @"http://118.144.88.37:80/group1/M00/00/02/oYYBAFMVfpyAE-peAABlCAa55so472.png"

#define  URL2 @"http://118.144.88.37:80/group1/M00/00/01/ooYBAFLLlAqAD0RyAAAhl7-FPI4589.png"


#define  URL3 @"http://118.144.88.37:80/group1/M00/00/02/oYYBAFMVe8SAAvXfAABxshTbp9M292.png"




@interface RedViewController ()

@end

@implementation RedViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self  SetTitleText:@"SocketView"];
    [self.view setBackgroundColor:[UIColor greenColor]];
    
    UIImageView*  tempImage1 = [[UIImageView alloc ] initWithImage:[UIImage imageNamed:@"head"]];
    [tempImage1 setTag:0];
    [self.view addSubview:tempImage1];
    [tempImage1 setFrame:CGRectMake(80, 60, 200, 100)];
    
    
    UIImageView*  tempImage2 = [[UIImageView alloc ] initWithImage:[UIImage imageNamed:@"head"]];
    [tempImage2 setTag:1];
    [self.view addSubview:tempImage2];
    [tempImage2 setFrame:CGRectMake(80, 180, 200, 100)];
    
    
    UIImageView*  tempImage3 = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"head"]];
    [tempImage3 setTag:3];
    [self.view addSubview:tempImage3];
    [tempImage3 setFrame:CGRectMake(80, 290, 200, 100)];
    
    NSLog(@"mist herere");
    
    
    
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_group_t group = dispatch_group_create();
    dispatch_group_async(group, queue, ^{
        [tempImage1 setImageFromUrl:URL1];
    });
    dispatch_group_async(group, queue, ^{
        [tempImage2 setImageFromUrl:URL2];
    });
    dispatch_group_async(group, queue, ^{
        [tempImage3 setImageFromUrl:URL3];
    });
    dispatch_group_notify(group, dispatch_get_main_queue(), ^{
        NSLog(@"消失吧大白菜的菊花");
    });  
    dispatch_release(group);  

	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
