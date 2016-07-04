//
//  Config.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#ifndef __CONFIG_DEF__
#define __CONFIG_DEF__


#define    kServiceBaseUrl   @"http://192.168.16.27:8092/miteno-mobile/"

// IPhone5 screen Adjust for UI
// When Size of Height less than 1136 return
// NO or Return Act Size
#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)


#define  kScreenFrame   [UIScreen mainScreen].applicationFrame

#define  kScreenBounds  [UIScreen mainScreen].bounds

#define  kScreenWidth   [UIScreen mainScreen].bounds.size.width

#define  kScreenHeight  [UIScreen mainScreen].bounds.size.height

#define  kConnectFailure   @"网络状况不佳"



//#define  WEB_SERVICE_ENV_VAR   @"http://192.168.16.115:8090/miteno-mobile/"

//#define  WEB_SERVICE_ENV_VAR   @"http://www.nfclive.com.cn/miteno-mobile/"

//#define  WEB_SERVICE_ENV_VAR   @"http://192.168.16.97:8080/miteno-mobile/"

//#define    WEB_SERVICE_ENV_VAR   @"http://192.168.16.27:8092/miteno-mobile/"

#define WEB_SERVICE_ENV_VAR      @"http://app.modepay.cn/miteno-mobile/"


//------------------ path  of  method----------------------------//

#define    Key_Secrity_Login     @"mpayFront/j_spring_security_check"


#define    key_Secrity_Regist    @"mpayFront/reg"


#define    Key_Secrity_Check_Validate   @"mpayFront/checkValidateCode"


#define    Key_Basic_Card_Setting    @"mpayFront/getAllBaseCardOnUser"


#define    Key_Basic_Card_Binding    @"mpayFront/bindBaseCard"


//------------------ path  of  method----------------------------//










































#endif
















