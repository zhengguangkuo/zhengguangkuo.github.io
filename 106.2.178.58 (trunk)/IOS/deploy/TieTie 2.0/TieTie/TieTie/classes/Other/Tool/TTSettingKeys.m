//
//  TTSettingKeys.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#define     TTDefineConstStr(name) \
NSString *  const name = @#name;

//用户相关
TTDefineConstStr(TTUserPhone)            //账号
TTDefineConstStr(TTPassWord)             //密码
TTDefineConstStr(TTMipauFlag)            //是否有密保
TTDefineConstStr(TTPayPwdFlag)           //是否有支付密码
TTDefineConstStr(TTUserID)               //当前用户ID
TTDefineConstStr(TTUserShowPWD)          //是否显示明暗文
TTDefineConstStr(TTQrCode)               //二维码
TTDefineConstStr(TTLongitude)            //经度
TTDefineConstStr(TTLatitude)             //纬度

//设置相关
TTDefineConstStr(TTClientVersion)        //客户端版本号
TTDefineConstStr(TTSettingTabarBackVc)   //backVC
TTDefineConstStr(TTClickFoterDockItem)   
TTDefineConstStr(TTUpdateHomeData)       //刷新主界面

//网络请求 参数和返回值======start=================================================================
TTDefineConstStr(marked)                 //参数marked
TTDefineConstStr(jsonStr)                //参数jsonStr
TTDefineConstStr(rspCode)                //返回码
TTDefineConstStr(rspDesc)                //返回码描述

NSString *const rspCode_success = @"000";
//网络请求 参数和返回值======end===================================================================

//通知。
TTDefineConstStr(KNOTIFICATIONCENTER_UPDATEUITABBARITEM)
TTDefineConstStr(KNOTIFICATIONCENTER_UDATEMYVCARDTEMP)
TTDefineConstStr(KNOTIFICATIONCENTER_LOGINXMPPSUCCESS)
TTDefineConstStr(KNOTIFICATIONCENTER_UPDATEPHONEINXMPP)
TTDefineConstStr(KNOTIFICATIONCENTER_REGISTERXMPPSUCCESS)
TTDefineConstStr(KNOTIFICATIONCENTER_QUERYROSTERSUCCESS)
TTDefineConstStr(KNOTIFICATIONCENTER_MESSAGEFROMXMPP)
