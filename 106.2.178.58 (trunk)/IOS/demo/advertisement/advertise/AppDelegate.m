//
//  AppDelegate.m
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import "AppDelegate.h"
#import "HomeViewController.h"
#import "MenuViewController.h"
#import "IIViewDeckController.h"
#import "UserInfo.h"


#pragma mark -
@interface AppDelegate()
- (void)initHomeWithDeckController;
@property  (nonatomic, strong) IIViewDeckController* DeckController;

@property  (nonatomic, strong) UINavigationController* HomeNavigation;

@property  (nonatomic, strong) UINavigationController* MenuNavigation;

@end


@implementation AppDelegate

@synthesize window = _window;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
	// [BeeUnitTest runTests];
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window makeKeyAndVisible];
    
    [self initHomeWithDeckController];

//    UserInfo*  tempobject = [[UserInfo alloc] init];
//    [tempobject setUserId:@"11111"];
//    [tempobject setUserMobile:@"139000000"];
//    [tempobject setUserName:@"boys"];
//    [tempobject setSessionKey:@"tead"];
//    [tempobject setReturnVal:@"tsdfsdf"];
//    
//    [tempobject saveAccout:@"UserInfo"];
//    
//    [tempobject resetAccout];
//    
//    NSLog(@"tempobejct1 username = %@",tempobject.userName);
//    [tempobject readAcount:@"UserInfo"];
//    
//    NSLog(@"tempobejct2 username = %@",tempobject.userName);
//
    return YES;
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

- (void)initHomeWithDeckController
{
    HomeViewController* homeVC = [[HomeViewController alloc] init];
    self.HomeNavigation = [[UINavigationController alloc] initWithRootViewController:homeVC];
    
    MenuViewController* menuVC = [[MenuViewController alloc] init];
    self.MenuNavigation = [[UINavigationController alloc]initWithRootViewController:menuVC];
    
    self.DeckController = [[IIViewDeckController alloc] initWithCenterViewController:self.HomeNavigation leftViewController:self.MenuNavigation];
    
    self.window.rootViewController = self.DeckController;
}

@end
