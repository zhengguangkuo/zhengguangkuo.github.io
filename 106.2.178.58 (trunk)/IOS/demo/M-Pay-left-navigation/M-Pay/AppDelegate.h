//
//  AppDelegate.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder<UIApplicationDelegate>
@property (strong, nonatomic) UIWindow * window;

@property  (nonatomic, strong) UINavigationController* HomeNavigation;

@property  (nonatomic, strong) UINavigationController* MenuNavigation;

+(AppDelegate*)getApp;

@end
