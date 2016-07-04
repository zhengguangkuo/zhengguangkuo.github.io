//
//  AppDelegate.h
//  FinalPG
//
//  Created by guorong on 13-9-25.
//  Copyright (c) 2013年 guorong. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Ivan_UITabBar.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (retain, nonatomic) IBOutlet Ivan_UITabBar *tabbar;
+(AppDelegate*)getAppDelegate;
@end
