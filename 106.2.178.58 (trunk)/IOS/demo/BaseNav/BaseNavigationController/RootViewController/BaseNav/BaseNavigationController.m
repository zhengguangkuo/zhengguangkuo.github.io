//
//  BaseNavigationController.m
//  BaseNav
//
//  Created by HWG on 14-1-17.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import "BaseNavigationController.h"
@interface BaseNavigationController()
{
    UIButton    *_leftButton;
    UIButton    *_rightButton;
    UIButton    *_searchButton;
}
@end

@implementation BaseNavigationController
- (id)initWithRootViewController:(UIViewController *)rootViewController
{
    self = [super initWithRootViewController:rootViewController];
    if (self) {
        UIImage *image = [UIImage imageNamed:@"nav_image_bg.png"];

        [self.navigationBar setBackgroundImage:image forBarMetrics:UIBarMetricsDefault];
        
   
    }
    return self;
}

@end
