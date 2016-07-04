//
//  AppDelegate.m
//  checkbox
//
//  Created by guorong on 14-1-21.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import "AppDelegate.h"
#import "UICheckbox.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window makeKeyAndVisible];
    
   
    
    CheckViewGroup*  tempgroup = [[CheckViewGroup alloc] initWithTag:0 type:UICheckGroupBox];

    UICheckbox* ya = [[UICheckbox alloc] initWithFrame:CGRectMake(100, 200, 300, 30) text:@"test1" nTag:0];
    ya.delegate = self;
    [self.window addSubview:ya];
    [tempgroup AddCheckView:ya];
    
    UICheckbox* ya2 = [[UICheckbox alloc] initWithFrame:CGRectMake(200, 200, 300, 30) text:@"test2" nTag:1];
    ya2.delegate = self;
    [self.window addSubview:ya2];
    [tempgroup  AddCheckView:ya2];
    
    
    
    
    
    
    CheckViewGroup*  tempgroup2 = [[CheckViewGroup alloc] initWithTag:1 type:UIMutipleGroupBox];
    
    UICheckbox* ya3 = [[UICheckbox alloc] initWithFrame:CGRectMake(100, 260, 300, 30) text:@"test4" nTag:0];
    ya.delegate = self;
    [self.window addSubview:ya3];
    [tempgroup2 AddCheckView:ya3];
    
    UICheckbox* ya4 = [[UICheckbox alloc] initWithFrame:CGRectMake(200, 260, 300, 30) text:@"test5" nTag:1];
    ya2.delegate = self;
    [self.window addSubview:ya4];
    [tempgroup2  AddCheckView:ya4];

    
    

    return YES;
}

- (void)checkSelected:(NSString*)text
              checked:(BOOL)bflag
                 path:(NSIndexPath*)path
{
    NSLog(@"someting!");
}


- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
