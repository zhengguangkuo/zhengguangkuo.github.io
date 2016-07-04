//
//  AppDelegate.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import "AppDelegate.h"
#import "NewFeatureViewController.h"
#import "TTBaseNavViewController.h"
#import "TTMainViewController.h"
#import "AFNetworking.h"
#import "PushMsg.h"
#import "MsgDB.h"
#import "DBManager.h"
#import "TTXMPPTool.h"

#define kAppKey             @"356162524"
#define kAppSecret          @"64f4bd6de6e79ab1f7b7683f25edc795"
#define kAppRedirectURL     @"http://www.modepay.cn"

@interface AppDelegate()

@end

@implementation AppDelegate

////本地
//- (void)loadNotification
//{
//    UILocalNotification *notification = [[UILocalNotification alloc] init];
//    notification.fireDate = [NSDate dateWithTimeIntervalSinceNow:4];
//    notification.alertBody = @"本地通知";
//    notification.soundName = UILocalNotificationDefaultSoundName;
//    notification.applicationIconBadgeNumber = 5;
//    
//    [[UIApplication sharedApplication]scheduleLocalNotification:notification];
//}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [[AFNetworkReachabilityManager sharedManager] startMonitoring];
    
    // 检测网络连接的单例,网络变化时的回调方法
    [[AFNetworkReachabilityManager sharedManager] setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
//        AFNetworkReachabilityStatusUnknown          = -1,
//        AFNetworkReachabilityStatusNotReachable     = 0,
//        AFNetworkReachabilityStatusReachableViaWWAN = 1,
//        AFNetworkReachabilityStatusReachableViaWiFi = 2,
        if (status == AFNetworkReachabilityStatusNotReachable) {
            [SystemDialog alert:@"网络连接失败"];
            self.networkType = nil;
        }
        if (status == AFNetworkReachabilityStatusReachableViaWiFi) {
            [SystemDialog alert:@"已经连接Wifi"];
            self.networkType = @"1";
        }
        if (status == AFNetworkReachabilityStatusReachableViaWWAN) {
            [SystemDialog alert:@"已开启3G网络"];
            self.networkType = @"2";
        }
    }];
    

//    if (launchOptions && [launchOptions objectForKey:UIApplicationLaunchOptionsRemoteNotificationKey]) {
//
//        application.applicationIconBadgeNumber -= 1;
//        
//        UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"launchoptions" message:@"test" delegate:self cancelButtonTitle:@"cancel" otherButtonTitles:nil, nil];
//        [alert show];
//    }
    
//    [self loadNotification];
    
    
    [TTAccountTool sharedTTAccountTool].currentAccount = nil;
    
    if ([TTAccountTool sharedTTAccountTool].currentAccount == nil) {
        [TTAccountTool sharedTTAccountTool].currentCity = nil;
        [TTAccountTool sharedTTAccountTool].currentAccount.mipauFlag = @"1";
        [TTAccountTool sharedTTAccountTool].currentAccount.payPwdFlag = @"1";
        TTCityModel *city = [[TTCityModel alloc] init];
        city.superArea = @"11";
        city.areaName = @"北京";
        city.areaLevel = @"2";
        city.areaCode = @"1100";
        [[TTAccountTool sharedTTAccountTool] addCity:city];
    }
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    self.window.backgroundColor = [UIColor whiteColor];
    [self.window makeKeyAndVisible];
    
    //======push message start by zgk====
    //let the divce know we want to receive push notifications
//    [[UIApplication sharedApplication] registerForRemoteNotificationTypes:(UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound | UIRemoteNotificationTypeAlert)];
//    DBManager *db = [DBManager sharedDBManager];
//    [db createDataBase:msgDBName];
//    NSDictionary *dic = @{@"time": @"text",@"message":@"text"};
//    [db createTable:msgTableName sqldic:dic];
//    //======push message end by zgk ======
//    
//    [UIApplication sharedApplication].applicationIconBadgeNumber = 0;

    [self startMapManager];
//
//     判断是否第一次使用这个版本
//
    NSString *key = (NSString *)kCFBundleVersionKey;
    
    //先去沙盒中取出上次使用的版本号
    NSString *lastVersionCode = [TTSettingTool objectForKey:TTClientVersion];

    //加载程序中info.plist文件(获得当前软件的版本号)
    NSString *currentVersionCode = [NSBundle mainBundle].infoDictionary[key];
    if ([lastVersionCode isEqualToString:currentVersionCode]) {
        // 非第一次使用软件
        [self initMainWithDeckController:YES];

    } else {
        // 第一次使用软件
        // 保存当前软件版本号
        [TTSettingTool setObject:currentVersionCode forKey:TTClientVersion];
        
        [TTSettingTool setBool:TRUE forKey:TTUserShowPWD];
        //引导页控制器
        NewFeatureViewController *new = [[NewFeatureViewController alloc] init];
        new.startBlock = ^(BOOL shared){
            //开始体验   进入主菜单
            [self initMainWithDeckController:shared];
        };
        
        self.window.rootViewController = new;
    }
    
    return YES;
}


//- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
//{
//    TTLog(@"My token is: %@", deviceToken);
//    
//    NSString *pushToken = [[[deviceToken description]
//                             stringByReplacingOccurrencesOfString:@"<" withString:@""]
//                            stringByReplacingOccurrencesOfString:@">" withString:@""];
//    
//    [self sendDeviceTokenToPushServer:pushToken];
//    
//}

//- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
//{
//    TTLog(@"Failed to get token, error: %@", error);
//}
//
//- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
//{
//    if(application.applicationState == UIApplicationStateActive){
//        UIAlertView *alert =
//        [[UIAlertView alloc] initWithTitle:@"温馨提示"
//                                   message:@"UIApplicationStateActive"
//                                  delegate:nil
//                         cancelButtonTitle:@"确定"
//                         otherButtonTitles:nil];
//        [alert show];
//    } else if (application.applicationState == UIApplicationStateInactive){
//        UIAlertView *alert =
//        [[UIAlertView alloc] initWithTitle:@"温馨提示"
//                                   message:@"UIApplicationStateInactive"
//                                  delegate:nil
//                         cancelButtonTitle:@"确定"
//                         otherButtonTitles:nil];
//        [alert show];
//    } else {
//        UIAlertView *alert =
//        [[UIAlertView alloc] initWithTitle:@"温馨提示"
//                                   message:@"UIApplicationStateBackground"
//                                  delegate:nil
//                         cancelButtonTitle:@"确定"
//                         otherButtonTitles:nil];
//        [alert show];
//    }
//    
//    TTLog(@"Receive remote notification : %@",userInfo);
//    [[UIApplication sharedApplication] setApplicationIconBadgeNumber:2];
//    NSDictionary *dic = [userInfo objectForKey:@"aps"];
//    NSInteger badgeNum = [[dic objectForKey:@"badge"] integerValue];
//    [UIApplication sharedApplication].applicationIconBadgeNumber = badgeNum;
//    
//    //update UITableBarItem
//    [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_UPDATEUITABBARITEM object:nil userInfo:dic];
//}

- (void)initMainWithDeckController:(BOOL)shared
{

    TTMainViewController *tabarVc = [[TTMainViewController alloc] init];
    self.window.rootViewController = tabarVc;



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
    [self stopMapManager];
    
    

}
#pragma mark -启动地图
- (void)startMapManager
{
    mapManager = [[BMKMapManager alloc]init];
    BOOL ret = [mapManager start:@"cye1OnpKo0qMGDUqmZCEojUP" generalDelegate:nil];
    if (!ret) {
        TTLog(@"Map Manager start failed!");
    }
}

- (void)stopMapManager
{
    if (mapManager) {
        if ([mapManager stop]) {
            TTLog(@"Map Manager stop!");
        }
    }
}

//#pragma mark -推送
//- (void)sendDeviceTokenToPushServer: (NSString *)deviceTokenString
//{
//    
//    // Establish the request
//    TTLog(@"sendProviderDeviceToken = %@", deviceTokenString);
//    
//    NSString *UUIDString = [[UIDevice currentDevice].identifierForVendor UUIDString];
//    NSString *body = [NSString stringWithFormat:@"action=savetoken&clientid=%@&token=%@", UUIDString, deviceTokenString];
//    
//    NSString *baseurl = [NSString stringWithFormat:@"%@?",@"http://106.2.178.58/push_chat_service.php"];  //服务器地址
//    NSURL *url = [NSURL URLWithString:baseurl];
//    
//    NSMutableURLRequest *urlRequest = [NSMutableURLRequest requestWithURL:url];
//    [urlRequest setHTTPMethod: @"POST"];
//    [urlRequest setHTTPBody:[body dataUsingEncoding:NSUTF8StringEncoding]];
//    [urlRequest setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
//    
//    AFHTTPRequestOperation *operation = [[AFHTTPRequestOperation alloc]initWithRequest:urlRequest];
//    [operation setCompletionBlockWithSuccess:^(AFHTTPRequestOperation *operation, id responseObject) {
//        TTLog(@"send deviceToken successed!");
//    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
//        TTLog(@"send deviceToken failed!");
//    }];
//    [operation start];
//}
//- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application
//{
//    // 清除图片缓存
//    [TieTieTool clear];
//}
#pragma mark -微信分享相关
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    if ([url.scheme isEqualToString:TTWXAppID]) {
        
        return [WXApi handleOpenURL:url delegate:self];
    }else{
        
        return YES;
    }
}

@end
