//
//  Config.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#ifndef __CONFIG_DEF__
#define __CONFIG_DEF__

    /*
     *
     */
//------------- * * * * * * 贴 * * 贴 * * 参 * * 数 * * * * * * * ---------

//日志相关
#ifdef DEBUG
#define TTLog(...) NSLog(__VA_ARGS__)
#else
#define TTLog(...)
#endif

//设备相关
#define iOS7 ([[[UIDevice currentDevice] systemVersion] floatValue] >= 7.0)

#define isIOS7 iOS7?64:44

// 全局背景色
#define TTGlobalBg [UIColor colorWithPatternImage:[UIImage imageNamed:@"bg01"]]

#define TTNavTitleFont [UIFont systemFontOfSize:22]

 


//------------- * * * * * * * * * * * * * * * * * * * * * * * *  ---------

//设备信息
//判断是否是IOS7系统
#define IOS7 ([[[UIDevice currentDevice] systemVersion] floatValue]>= 7.0 ? YES : NO)

#define isRetina ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 960), [[UIScreen mainScreen] currentMode].size) : NO)

#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)

#define kAppFrame [UIScreen mainScreen].applicationFrame

#define  kScreenBounds    [UIScreen mainScreen].bounds

#define ScreenHeight [[UIScreen mainScreen] bounds].size.height

#define ScreenWidth  [[UIScreen mainScreen] bounds].size.width
#define kPrinciple 64

//#define Selected_City_Code   @"selected_city_code"

//获取当前城市编码
#define kCityCode   @"cityCode"
//设置颜色
#define RGBA(r,g,b,a) [UIColor colorWithRed:r/255.0 green:g/255.0 blue:b/255.0 alpha:a]
#define Black1      RGBA(73,73,73,1)
#define Black2      RGBA(153,153,153,1)
#define white1      [UIColor whiteColor]
#define white2      RGBA(242,242,242,1)
#define white3      RGBA(200,200,200,1)
#define Background  RGBA(206,206,206,1)
#define Orange      RGBA(235,138,66,1)
#define Blue        RGBA(76,147,241,1)

// 半透明
#define kGlobalBg [UIColor colorWithRed:0.5 green:0.5 blue:0.5 alpha:0.5]
#define kGrayTabBg  [UIColor colorWithRed:0.5 green:0.5 blue:0.5 alpha:0];

#define  ChNil(XXX)  [(NSNull*)XXX isKindOfClass:[NSNull class]]?YES:NO

#define  IOS7Y (IOS7?(CGFloat)kPrinciple:(CGFloat)0)

#define  ZeroY  (IOS7?(CGFloat)0:(CGFloat)0)

#define  TableExtraHeight (IOS7?(SIMULATOR==0?0:64):(CGFloat)0)

#define  MaxDevicesHeight  566  //iOS5以上 大屏幕高度

#define  kAdjustH (SIMULATOR==0?(64:0))

#define  kAdjustRate  ((CGFloat)(ScreenHeight/MaxDevicesHeight))


#endif







