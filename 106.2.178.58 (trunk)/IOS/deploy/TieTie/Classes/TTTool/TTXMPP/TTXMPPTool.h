//
//  TTXMPPTool.h
//  Miteno
//
//  Created by zhengguangkuo on 14-7-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "XMPPFramework.h"
#import "XMPPvCardTemp.h"
#import "TTUserInfoForXmpp.h"

@interface TTXMPPTool : NSObject<XMPPStreamDelegate,NSFetchedResultsControllerDelegate>

@property (nonatomic, strong) TTUserInfoForXmpp *userInfo;

@property  (nonatomic, strong) XMPPStream*  xmppStream;

@property  (nonatomic, strong) XMPPRoster*  xmppRoster;
@property  (nonatomic, strong) XMPPRosterCoreDataStorage*      xmppRosterStorage;

@property  (nonatomic, strong) XMPPvCardCoreDataStorage* xmppvCardStorage;
@property  (nonatomic, strong) XMPPvCardTempModule*  xmppvCardTempModule;
@property  (nonatomic, strong) XMPPvCardAvatarModule*  xmppvCardAvatarModule;

@property  (nonatomic, strong) XMPPReconnect *xmmppReconnect;

@property (nonatomic) BOOL registerOrLoginFlag;


+ (id)sharedInstance;

//链接服务器
- (void)connect;

//断开服务器
- (void)disconnect;

//设置xmppstream
- (void)setupStream;

//注册用户
- (BOOL)registeUser;

//登录用户
- (BOOL)loginUser;

//用户退出
- (void)logoutUser;

//更新名片（更改已有名片实体类的属性值）
- (void)updateCard:(NSString*)nickName;

//创建新的名片
- (void)NewXmlCard;

//通过更新名片实体类的photo属性更新头像
- (void)updateCardPhoto:(UIImage*)image;

//通过创建名片xml文件的头像元素，更新头像
- (void)updatecardPhotoWithXml:(UIImage*)image;

//添加好友
- (void)addFriend:(NSString *)name;

//删除好友
- (void)removeFriend:(NSString *)name;

//获取好友列表
- (void)friendList;
//返回一个用户头像（add）
- (UIImage *)loadUserImage:(XMPPUserCoreDataStorageObject *)user;

- (NSArray*)getFriendInfos;

//- (void)friendInfo;

//
- (void)addUser:(XMPPJID*)jid;

//
- (void)fetchCard:(XMPPJID*)jid;

- (void)sendMessage:(NSString *)text toUser:(NSString *)userJid;
@end
