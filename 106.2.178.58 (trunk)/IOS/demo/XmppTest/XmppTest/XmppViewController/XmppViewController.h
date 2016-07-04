//
//  TTFriendViewController.h
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "XMPPFramework.h"
#import "NSString+File.h"

@interface XmppViewController : UIViewController<XMPPStreamDelegate,XMPPRosterDelegate,XMPPvCardTempModuleDelegate>
{
     XMPPStream*  xmppStream;
     XMPPRoster*  xmppRoster;                      //花名册
     XMPPRosterCoreDataStorage* xmppRosterStorage; //花名册工具类
    
     XMPPvCardCoreDataStorage *xmppvCardStorage;
	 XMPPvCardTempModule *xmppvCardTempModule;
	 XMPPvCardAvatarModule *xmppvCardAvatarModule;
}
@property  (nonatomic, strong) XMPPStream*  xmppStream;
@property  (nonatomic, strong) XMPPRoster*  xmppRoster;
@property  (nonatomic, strong) XMPPRosterCoreDataStorage*      xmppRosterStorage;
@property  (nonatomic, strong) XMPPvCardCoreDataStorage* xmppvCardStorage;
@property  (nonatomic, strong) XMPPvCardTempModule*  xmppvCardTempModule;
@property  (nonatomic, strong) XMPPvCardAvatarModule*  xmppvCardAvatarModule;


- (void)AddUser:(XMPPJID*)jid;

- (void)FetchCard:(XMPPJID*)jid;

- (void)NewXmlCard;

- (void)updateCard:(NSString*)nickName;

- (void)ShowAlertMsg:(NSString*)title content:(NSString*)content;

@end
