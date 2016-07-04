//
//  MTAppDelegate.h
//  MTBarDemo
//
//  Created by wg on 14-6-3.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"
@interface MTAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) BMKMapManager *mapManager;
@end
