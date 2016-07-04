
//  TTSettingKeys.h
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//


/*
 *   贴贴状态保存
 */

#define     TTDeclareConstStr(name) \
extern      NSString *const name;


//app store
#define     TTLogConnectError       @"网络连接超时,请检查网络连接！"
/*
 * * 贴贴接口参数
 */
//
#define  TTBaseURL                  @"http://118.144.88.33:12041/callin!tietie.action"      //生产环境
//#define     TTBaseURL             @"http://192.168.16.75:8080/tietie_interface/callin!tietie.action"//开发环境

//#define     TTBaseURL             @"http://115.182.16.113:8080/callin!tietie.action"  //测试环境
//
#define     TTURL(...)              [TTBaseURL stringByAppendingFormat:__VA_ARGS__]
#define     TTCouponURL             @""

#define     TTQuizFindPwd           @""


//网络请求事件====start===============================================================
#define     TTAction_queryOpenCity          @"queryOpenCity"            //获取城市列表
#define     TTAction_queryArea              @"queryArea"                //获取城市区域
#define     TTAction_loginUser              @"loginUser"                //登录接口
#define     TTAction_registerUser           @"registerUser"             //注册接口
#define     TTAction_send                   @"send"                     //短信验证

#define     TTAction_findUser               @"findUser"                 //个人信息/查询
#define     TTAction_editpassword           @"editPassword"             //个人信息/修改密码
#define     TTAction_resetpassword          @"resetPassword"            //个人信息/重置密码
#define     TTAction_findPwd                @"findPwd"                  //个人信息/找回密码（登录）
#define     TTAction_editMobile             @"editMobile"               //个人信息/修改手机号

#define     TTAction_queryBaseCardType      @"queryBaseCardType"        //我的基卡/基卡类型列表
#define     TTAction_unbindBaseCard         @"unbindBaseCard"           //我的基卡/取消
#define     TTAction_bindBaseCard           @"bindBaseCard"             //我的基卡/设置
#define     TTAction_queryUserBindBaseCard  @"queryUserBindBaseCard"    //我的基卡/用户已绑定基卡列表
#define     TTAction_addPayPwd              @"addPayPwd"                //支付密码/新增
#define     TTAction_queryMipauQuestion     @"queryMipauQuestion"       //支付密码/获取密保问题
#define     TTAction_editPayPwd             @"editPayPwd"               //支付密码/修改
#define     TTAction_resetPayPwd            @"resetPayPwd"              //支付密码/重置
#define     TTAction_addMipau               @"addMipau"                 //密保/设置
#define     TTAction_queryMipau             @"queryMipau"               //密保/获取密保
#define     TTAction_verifyMipau            @"verifyMipau"              //密保/验证密保
#define     TTAction_regAndbindBaseCard     @"regAndbindBaseCard"       //绑定基卡（包含注册）
#define     TTAction_feedback               @"addFeedBack"                 //意见反馈

#define     TTAction_Merch_queryCategory    @"Merch_queryCategory"      //商户分类接口（搜索类型）
#define     TTAction_queryMerch             @"queryMerch"               //商户/列表接口
#define     TTAction_searchMerch            @"searchMerch"              //商户/搜索列表
#define     TTAction_findMerch              @"findMerch"                //商户/详情
#define     TTAction_queryCoupon            @"queryCoupon"              //我的贴券/列表（未使用，已使用，已过期）

#define     TTAction_CouponAct_queryCategory  @"CouponAct_queryCategory"  //优惠劵地区分类接口（搜索类型）
#define     TTAction_queryCouponAct         @"queryCouponAct"           //优惠券/列表
#define     TTAction_queryArea              @"queryArea"                //优惠劵地区接口（搜索类型）
#define     TTAction_receiveCoupon          @"receiveCoupon"            //优惠劵领取
#define     TTAction_findCouponAct          @"findCouponAct"            //优惠劵详情顶部数据信息
#define     TTAction_queryCouponMerch       @"queryCouponMerch"         //优惠劵详情下面的数据信息
#define     TTAction_backCoupon             @"backCoupon"               //优惠券/退券
#define     TTAction_sendCoupon             @"sendCoupon"               //优惠券/赠券（活动赠送）
#define     TTAction_sendSingleCoupon       @"sendSingleCoupon"         //优惠券/赠券（单张赠送）
#define     TTAction_handleOrder            @"handleOrder"              //优惠券/赠券（订单处理）
#define     TTAction_queryCouponOrder       @"queryCouponOrder"         //优惠券订单查询
#define     TTAction_queryCouponOrderView   @"queryCouponOrderView"     //优惠券订单详情

#define     TTAction_searchCredits          @"searchCredits"            //我的积分列表
#define     TTAction_searchCreditsDetail    @"searchCreditsDetail"      //我的积分详情列表
#define     TTAction_creditsList            @"queryCreditActList"       //积分活动列表
#define     TTAction_creditsDetail          @"findCreditAct"            //积分活动详情

#define     TTAction_judgeUsersAction       @"judgeUsersAction"         //查询消息服务用户是否存在
#define     TTAction_addUserAction          @"addUserAction"            //消息服务器/添加用户
#define     TTAction_judgeUserAndRoster     @"judgeUserAndRoster"       //消息服务器/添加好友
#define     TTAction_deleteRoster           @"deleteRoster"             //消息服务器/删除好友接口
#define     TTAction_updateNames            @"updateNames"              //消息服务器/更新用户手机号


//网络请求事件====end============================================================================

//网络请求 参数和返回值======start=================================================================
TTDeclareConstStr(marked)               // 参数 key @"marked"
TTDeclareConstStr(jsonStr)              // 参数 key @"jsonStr"
TTDeclareConstStr(rspCode)              // 返回值 key @"rspCode"
TTDeclareConstStr(rspDesc)              // 返回值 key @"rspDesc"
TTDeclareConstStr(rspCode_success)          // 返回值 成功（rspcode） value @"000"

//网络请求 参数和返回值======end===================================================================




//用户相关
#define     TTFileName              @"TTAccounts.data"
#define     TTCurrentFileName       @"TTCurrentAccount.data"    //当前账号
#define     TTCitysPlist            @"TTCitys.plist"            //城市列表
#define     TTAreaPlist             @"TTAreas.plist"            //用户当前城市列表
#define     TTCurrentCityName       @"TTCurrentCity.data"       //当前所在城市

#define     TTUserInfoPlist             @"UserInfo.plist"       //个人名片

#define TTAreaPath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTAreaList]

#define     TTAreaList              @"TTAreaList.plist"              //所有地区父级列表

#define TTCatPath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTCateGoryList]

#define     TTCateGoryList          @"TTCateGory.plist"              //所有种类父级列表

#define kUndoneLogin @"kUndoneLogin"
TTDeclareConstStr(TTUserPhone)          //用户账号
TTDeclareConstStr(TTPassWord)           //用户密码
TTDeclareConstStr(TTMipauFlag)          //是否有密保
TTDeclareConstStr(TTPayPwdFlag)         //是否有支付密码
TTDeclareConstStr(TTUserID)             //当前用户ID
TTDeclareConstStr(TTQrCode)             //二维码
TTDeclareConstStr(TTUserShowPWD)        //是否显示明暗文密码(已废弃)

//设置相关
TTDeclareConstStr(TTClientVersion)      //客户端版本号
TTDeclareConstStr(TTSettingTabarBackVc) //优惠劵视图
TTDeclareConstStr(TTClickFoterDockItem) //优惠劵底部点击
TTDeclareConstStr(TTUpdateHomeData)     //刷新主界面

TTDeclareConstStr(TTLongitude)          //当前经度
TTDeclareConstStr(TTLatitude)           //当前纬度

//通知
TTDeclareConstStr(KNOTIFICATIONCENTER_UPDATEUITABBARITEM)
TTDeclareConstStr(KNOTIFICATIONCENTER_UDATEMYVCARDTEMP)
TTDeclareConstStr(KNOTIFICATIONCENTER_UPDATEPHONEINXMPP)
TTDeclareConstStr(KNOTIFICATIONCENTER_LOGINXMPPSUCCESS)
TTDeclareConstStr(KNOTIFICATIONCENTER_REGISTERXMPPSUCCESS)
TTDeclareConstStr(KNOTIFICATIONCENTER_QUERYROSTERSUCCESS)
TTDeclareConstStr(KNOTIFICATIONCENTER_MESSAGEFROMXMPP)
TTDeclareConstStr(KNOTIFICATIONCENTER_CHATMESSAGEFROMXMPP)

//分享相关
#define TTWXAppID              @"wxd003fc99a95118c7"  //微信
