//
//  AppDelegate.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"
#import "WXApi.h"
#import "WXApiObject.h"

@interface AppDelegate : UIResponder<UIApplicationDelegate,WXApiDelegate>
{
    BMKMapManager *mapManager;
}
@property (nonatomic, retain) NSString  *networkType;    //用户当前使用网络类型
@property (strong, nonatomic) UIWindow * window;

@end
