//
//  AppDelegate.m
//  FinalPG
//
//  Created by guorong on 13-9-25.
//  Copyright (c) 2013年 guorong. All rights reserved.
//

#import "AppDelegate.h"



@interface AppDelegate()
-(void)initwithTabbar:(Ivan_UITabBar*)cust_Tabbar;
@end



@implementation AppDelegate


-(void)initwithTabbar:(Ivan_UITabBar*)cust_Tabbar
{
    
    NSArray*  ImageNameArray = [NSArray arrayWithObjects:  @"Favorite.png", @"Favorite1.png",
                                                           @"My Reservation1.png", @"My-Reservation.png",
                                                           @"Profile.png",  @"Profile1.png",
                                                           @"Search.png",   @"Search1.png", nil];
    
    NSArray*  TitleNameArray = [NSArray arrayWithObjects: @"TableView", @"SocketView", @"Opengles", @"Sprite", nil];
    
    int i;
    
    int nCount = [cust_Tabbar.viewControllers count];
    
   
    for(i = 0; i < nCount; i++)
    {
        UIViewController* TempController = [cust_Tabbar.viewControllers objectAtIndex:i];
        
        NSString*  defaultImage = [ImageNameArray objectAtIndex:(i*2 + 0)];
        
        NSString*  SelectedtImage = [ImageNameArray objectAtIndex:(i*2 + 1)];

        
        
        UITabBarItem *BarItem = TempController.tabBarItem;
       
        [BarItem setTitle:[TitleNameArray objectAtIndex:i]];
        
        [BarItem setImage:[UIImage imageNamed:defaultImage]];
        
        BarItem.tag = i;
        
        
        [BarItem setFinishedSelectedImage:[UIImage imageNamed:SelectedtImage] withFinishedUnselectedImage:[UIImage imageNamed:defaultImage]];
    }
        

}


+(AppDelegate*)getAppDelegate
{
    AppDelegate *app = (AppDelegate*)[[UIApplication sharedApplication] delegate];
    
    return app;
}





- (void)dealloc
{
    [_window release];
    [_tabbar release];
    [super dealloc];
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    // Override point for customization after application launch.
    [[NSBundle mainBundle]  loadNibNamed:@"tabbar" owner:self options:nil];
   
    [self initwithTabbar:self.tabbar];
    
    [self.window addSubview:self.tabbar.view];
    
    [self.tabbar SetCurrentSelected:0];
   
    self.window.backgroundColor = [UIColor whiteColor];
    
    [self.window makeKeyAndVisible];
    
    
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

@end
