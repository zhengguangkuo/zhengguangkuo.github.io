//
//  Config.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#ifndef __CONFIG_DEF__
#define __CONFIG_DEF__


#define    kServiceBaseUrl   @"http://192.168.16.27:8092/miteno-mobile/"

// IPhone5 screen Adjust for UI
// When Size of Height less than 1136 return
// NO or Return Act Size
#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)


#define  kScreenBounds  [UIScreen mainScreen].bounds

#define  kScreenWidth   [UIScreen mainScreen].bounds.size.width

#define  kScreenHeight  [UIScreen mainScreen].bounds.size.height

#define  kConnectFailure   @"网络状况不佳"




#endif


