//
//  TTXMPPTool.m
//  Miteno
//
//  Created by zhengguangkuo on 14-7-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTXMPPTool.h"
#import "XMPPvCardTemp.h"
#import "TTRosterItem.h"
#import "DBManager.h"

//#define  RESOURCE  @"svn.test.localdomain"
#define  RESOURCE  @"m.tie1tie.com"
//#define  SERVICE   @"106.2.178.58"
//#define  SERVICE   @"106.2.168.141"
//#define  SERVICE   @"115.182.16.111"//测试环境
#define  SERVICE   @"118.144.88.33"//生产环境
@implementation TTXMPPTool
{
    NSFetchedResultsController *fetchedGroupResultsController;
}

+ (id)sharedInstance
{
    static TTXMPPTool *xmppTool;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        xmppTool = [[TTXMPPTool alloc] init];
    });
    return xmppTool;
}

//设置xmppstream，xmpproser，xmppvcard
- (void)setupStream
{
    //==========setupStream start====================
    if (self.xmppStream == nil) {
        self.xmppStream = [[XMPPStream alloc] init];
        [self.xmppStream addDelegate:self delegateQueue:dispatch_get_main_queue()];
        // [self.xmppStream supportsDigestMD5Authentication];
        //允许程序在后台时接受消息
        [self.xmppStream setEnableBackgroundingOnSocket:YES];
    }
    
    if (self.xmmppReconnect ==nil) {
        self.xmmppReconnect = [[XMPPReconnect alloc]init];
        [self.xmmppReconnect activate:self.xmppStream];
    }
    
    //xmpp绑定花名册
    if(self.xmppRoster==nil)
    {
        self.xmppRosterStorage = [[XMPPRosterCoreDataStorage alloc]init];
        self.xmppRoster = [[XMPPRoster alloc] initWithRosterStorage:self.xmppRosterStorage];
        [self.xmppRoster activate:self.xmppStream];
        //自动更新好友信息
        [self.xmppRoster setAutoFetchRoster:YES];
        [self.xmppRoster addDelegate:self delegateQueue:dispatch_get_main_queue()];
        self.xmppRoster.autoAcceptKnownPresenceSubscriptionRequests = YES;
    }
    
    
    if(self.xmppvCardAvatarModule==nil)
    {
        self.xmppvCardStorage = [XMPPvCardCoreDataStorage sharedInstance];
        self.xmppvCardTempModule = [[XMPPvCardTempModule alloc] initWithvCardStorage:self.xmppvCardStorage];
        [self.xmppvCardTempModule addDelegate:self delegateQueue:dispatch_get_main_queue()];
        
        self.xmppvCardAvatarModule = [[XMPPvCardAvatarModule alloc]initWithvCardTempModule:self.xmppvCardTempModule];
        [self.xmppvCardAvatarModule addDelegate:self delegateQueue:dispatch_get_main_queue()];
        
        [self.xmppvCardTempModule   activate:self.xmppStream];
        [self.xmppvCardAvatarModule activate:self.xmppStream];
        
    }
    //==========setupStream end====================
}

// 链接服务器
- (void)connect
{
    if (![self.xmppStream isConnected]) {
        NSString *username = self.userInfo.userId;
//        username = USERNAME;
        
        [self setupStream];
        
        TTLog(@"开始连接");
        //XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@@%@",USERNAME,RESOURCE]];
        XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@@%@",username,RESOURCE]];
        //XMPPJID *jid = [XMPPJID jidWithUser:USERNAME domain:SERVICE resource:RESOURCE];
        [self.xmppStream setMyJID:jid];
        [self.xmppStream setHostName:SERVICE];
        [self.xmppStream setHostPort:5222];
        
        NSError *error = nil;
        if (![self.xmppStream connectWithTimeout:10 error:&error]) {
            TTLog(@"Connect Error: %@", [[error userInfo] description]);
        }
    }
}

//断开连接
- (void)disconnect
{
//    XMPPPresence *presence = [XMPPPresence presenceWithType:@"unavailable"];
//    [self.xmppStream sendElement:presence];
//    [self.xmppStream disconnect];
    [self logoutUser];
    fetchedGroupResultsController = nil;
}

//注册用户
- (BOOL)registeUser
{
    NSString *password = self.userInfo.password;
//    password = PASSWORD;
    
    NSError *error = nil;
    /*------------------------帐户注册------------------------*/
    return [self.xmppStream registerWithPassword:password error:&error];
    /*------------------------帐户注册------------------------*/
}

//登录
- (BOOL)loginUser
{
    /*------------------------帐户登录-----------------------*/
    NSError *error = nil;
    //NSString* md5str = [PASSWORD md5Encrypt];
    //NSLog(@"md5str= %@",md5str);
    NSString *password = self.userInfo.password;
//    password = PASSWORD;
    
//    if (![self.xmppStream authenticateWithPassword:password error:&error])
//    {
//        NSLog(@"Authenticate Error: %@", [[error userInfo] description]);
//    }
//    else
//    {
//        NSLog(@"连接成功");
//    }
    return [self.xmppStream authenticateWithPassword:password error:&error];
    /*------------------------帐户登录------------------------*/
}

- (void)logoutUser
{
    [self.xmppStream disconnectAfterSending];
}


//设置或者更新电子名片
- (void)updateCard:(NSString *)nickName
{
    XMPPvCardTemp* myvCardTemp = [self.xmppvCardTempModule myvCardTemp];
    
    if(!myvCardTemp)
    {
        //新建电子名片。
        myvCardTemp = [XMPPvCardTemp vCardTemp];
        //设置昵称。
        [myvCardTemp setNickname:nickName];
        //设置头像
        UIImage *image = [UIImage imageNamed:@"coupon_normal"];
        myvCardTemp.photo = UIImagePNGRepresentation(image);
    }
    
    if (!myvCardTemp.jid) {
        myvCardTemp.jid = self.xmppStream.myJID;
    }
    
    [self.xmppvCardTempModule updateMyvCardTemp:myvCardTemp];
}

//可扩展方式设置或更新电子名片
- (void)NewXmlCard
{
    //  NSXMLElement *vJidXML = [NSXMLElement
    //                             elementWithName:@"IQ FROM" stringValue:@"juliet@capulet.com"];
    
    NSXMLElement *vCardXML = [NSXMLElement elementWithName:@"vCard" xmlns:@"vcard-temp"];
    
    NSXMLElement *vNickXML = [NSXMLElement elementWithName:@"NICKNAME" stringValue:@"abc"];
    
    NSXMLElement *vPhoneXML = [NSXMLElement elementWithName:@"PHONE" stringValue:@"13910303432"];
    
    NSXMLElement *vEmail = [NSXMLElement elementWithName:@"EMAIL" stringValue:@"solider12@163.com"];
    
    [vCardXML addChild:vNickXML];
    
    [vCardXML addChild:vEmail];
    
    [vCardXML addChild:vPhoneXML];
    
    XMPPvCardTemp* newvCardTemp = [XMPPvCardTemp vCardTempFromElement:vCardXML];
    [self.xmppvCardTempModule updateMyvCardTemp:newvCardTemp];
}

- (void)updateCardPhoto:(UIImage*)image
{
    if (!image) {
        return;
    }
    
    XMPPvCardTemp* myvCardTemp = [self.xmppvCardTempModule myvCardTemp];
    
    if(!myvCardTemp)
    {
        //新建电子名片。
        myvCardTemp = [XMPPvCardTemp vCardTemp];
    }
    
    //设置头像
    myvCardTemp.photo = UIImagePNGRepresentation(image);
    
    if (!myvCardTemp.jid) {
        myvCardTemp.jid = self.xmppStream.myJID;
    }
    
    [self.xmppvCardTempModule updateMyvCardTemp:myvCardTemp];

}

//修改头像
- (void)updatecardPhotoWithXml:(UIImage*)image
{
    NSXMLElement *vCardXML = [NSXMLElement elementWithName:@"vCard" xmlns:
                              @"vcard-temp"];
    NSXMLElement *photoXML = [NSXMLElement elementWithName:@"PHOTO"];
    NSXMLElement *typeXML = [NSXMLElement elementWithName:@"TYPE"
                                              stringValue:@"image/jpeg"];
//    UIImage *image=[UIImage imageNamed:@"load.png"];
    NSData *dataFromImage =UIImagePNGRepresentation(image);
    //UIImageJPEGRepresentation(image, 0.7f);
    NSXMLElement *binvalXML = [NSXMLElement elementWithName:@"BINVAL"
                                                stringValue:[dataFromImage base64EncodedStringWithOptions:NSDataBase64Encoding64CharacterLineLength]];
    [photoXML addChild:typeXML];
    [photoXML addChild:binvalXML];
    [vCardXML addChild:photoXML];
    
    XMPPvCardTemp *myvCardTemp = [self.xmppvCardTempModule myvCardTemp];
    if (myvCardTemp) {
        [myvCardTemp setPhoto:dataFromImage];
        [self.xmppvCardTempModule updateMyvCardTemp:myvCardTemp];
    } else {
        XMPPvCardTemp *newvCardTemp = [XMPPvCardTemp vCardTempFromElement
                                       :vCardXML];
        [self.xmppvCardTempModule updateMyvCardTemp:newvCardTemp];
    }
}

//添加好友
- (void)addFriend:(NSString *)name
{
    XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@@%@",name,RESOURCE]];
    [self.xmppRoster addUser:jid withNickname:name];
}

//添加好友
- (void)addUser:(XMPPJID *)jid
{
    [self.xmppRoster addUser:jid withNickname:@"greentown"];
}

//删除好友
- (void)removeFriend:(NSString *)name
{
    XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@@%@",name,RESOURCE]];
    [self.xmppRoster removeUser:jid];
}

//查询花名册，获取好友的jid
- (void)friendList
{
    [self queryRoster];
}

- (void)queryRoster {
    NSXMLElement *query = [NSXMLElement elementWithName:@"query" xmlns:@"jabber:iq:roster"];
    NSXMLElement *iq = [NSXMLElement elementWithName:@"iq"];
    XMPPJID *myJID = self.xmppStream.myJID;
    [iq addAttributeWithName:@"from" stringValue:myJID.description];
    [iq addAttributeWithName:@"to" stringValue:myJID.domain];
    [iq addAttributeWithName:@"id" stringValue:[self.xmppStream generateUUID]];
    [iq addAttributeWithName:@"type" stringValue:@"get"];
    [iq addChild:query];
    [self.xmppStream sendElement:iq];
}

/*
 * founction:
 *
 * parameter:
 *
 * result:XMPPUserCoreDataStorageObject
 */
- (NSArray*)getFriendInfos
{
//    NSArray *groups = [[self fetchedGroupResultsController] fetchedObjects];
//
//    NSString *groupName = [NSString stringWithFormat:@"%@%@",self.xmppStream.myJID.user,@"friends"];
//    if (groups && [groups count] > 0) {
//        for (int i = 0,count = [groups count]; i < count; i++) {
//            
//            XMPPGroupCoreDataStorageObject *group = [groups objectAtIndex:i];
//            if ([group.name isEqualToString:groupName]) {
//                NSArray *friendsArr = [group.users allObjects];
//                return friendsArr;
//            }
//        }
//    }
//    
//    return nil;
    
//    NSManagedObjectContext *context = [self.xmppRosterStorage mainThreadManagedObjectContext];
//
//    NSEntityDescription *entity = [NSEntityDescription entityForName:@"XMPPGroupCoreDataStorageObject"
//                                      inManagedObjectContext:context];
//    
//    NSString *sectionKey = @"name";
//    NSSortDescriptor *sortDesc = [[NSSortDescriptor alloc] initWithKey:sectionKey ascending:YES];
//    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"name != %@", @"name"];
//    
//    NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
//    [fetchRequest setEntity:entity];
//    [fetchRequest setSortDescriptors:@[sortDesc]];
//    [fetchRequest setPredicate:predicate];
//    
//    NSError *error = nil;
//    NSArray *matches = [context executeFetchRequest:fetchRequest error:&error];
//
//    XMPPGroupCoreDataStorageObject *group = [matches objectAtIndex:1];
////    XMPPUserCoreDataStorageObject *user = [[group.users allObjects] objectAtIndex:0];
//    return [group.users allObjects];
//
//    
    // 1. 如果要针对CoreData做数据访问，无论怎么包装，都离不开NSManagedObjectContext
    // 实例化NSManagedObjectContext
    NSManagedObjectContext *context = [self.xmppRosterStorage mainThreadManagedObjectContext];
    
    // 2. 实例化NSFetchRequest
    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"XMPPUserCoreDataStorageObject"];
    
    // 3. 实例化一个排序
    NSSortDescriptor *sort1 = [NSSortDescriptor sortDescriptorWithKey:@"displayName" ascending:YES];
    NSSortDescriptor *sort2 = [NSSortDescriptor sortDescriptorWithKey:@"jidStr" ascending:YES];
    
    [request setSortDescriptors:@[sort1, sort2]];
//    NSPredicate *predicate = [NSPredicate predicateWithFormat:];
//    [request setPredicate:predicate];
    
    //========= 可以获取数据列表 start===========
    NSError *err;
    //array的元素类型是：XMPPUserCoreDataStorageObject
    NSArray *array = [context executeFetchRequest:request error:&err];
    //    friendArray = [NSArray arrayWithArray:array];
    NSInteger count = [array count];
    TTLog(@"=============好友数量：%d",count);
    return array;
}

- (NSFetchedResultsController *)fetchedGroupResultsController
{
    if (![self.xmppStream isConnected]) {
        return nil;
    }
    if (fetchedGroupResultsController == nil)
    {
        NSManagedObjectContext *moc = [self.xmppRosterStorage mainThreadManagedObjectContext];
        
        NSEntityDescription *entity = [NSEntityDescription entityForName:@"XMPPGroupCoreDataStorageObject"
                                                  inManagedObjectContext:moc];
        
        NSSortDescriptor *sd1 = [[NSSortDescriptor alloc] initWithKey:@"name" ascending:YES];
        //NSSortDescriptor *sd2 = [[NSSortDescriptor alloc] initWithKey:@"displayName" ascending:YES];
        
        NSArray *sortDescriptors = [NSArray arrayWithObjects:sd1, nil];
        
        NSFetchRequest *fetchRequest = [[NSFetchRequest alloc] init];
        [fetchRequest setEntity:entity];
        [fetchRequest setSortDescriptors:sortDescriptors];
        [fetchRequest setFetchBatchSize:10];
        
        fetchedGroupResultsController = [[NSFetchedResultsController alloc] initWithFetchRequest:fetchRequest
                                                                            managedObjectContext:moc
                                                                              sectionNameKeyPath:@"name"
                                                                                       cacheName:nil];
        [fetchedGroupResultsController setDelegate:self];
        
        
        NSError *error = nil;
        if (![fetchedGroupResultsController performFetch:&error])
        {
            TTLog(@"Error performing fetch: %@", error);
        }
        
    }
    
    return fetchedGroupResultsController;
}

- (void)queryMyvCardTemp
{
     NSXMLElement *query = [NSXMLElement elementWithName:@"vCard" xmlns:@"vcard-temp"];
    NSXMLElement *iq = [NSXMLElement elementWithName:@"iq"];
    XMPPJID *myJID = self.xmppStream.myJID;
    [iq addAttributeWithName:@"from" stringValue:myJID.description];
    [iq addAttributeWithName:@"id" stringValue:/*[self.xmppStream generateUUID]*/@"v1"];
    [iq addAttributeWithName:@"type" stringValue:@"get"];
    [iq addChild:query];
    [self.xmppStream sendElement:iq];
//    <iq from='stpeter@jabber.org/roundabout'
//    id='v1'
//    type='get'>
//    <vCard xmlns='vcard-temp'/>
//    </iq>
}

//获取好友名片信息
- (void)fetchCard:(XMPPJID *)jid
{
    [self.xmppvCardTempModule fetchvCardTempForJID:jid];
}

#pragma mark - XMPPStreamDelegate
//完成链接服务器
- (void)xmppStreamDidConnect:(XMPPStream *)sender
{
    //2.链接服务器之后登录用户。
    TTLog(@"xmppStreamDidConnect");
    NSString *userPhone = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    if ([self.userInfo.name isEqualToString:userPhone]) {
        [self loginUser];
    } else {
        [self updatePhoneNumInXmppServer:userPhone];
    }
    
}

- (void)sendMessage:(NSString *)text toUser:(NSString *)userJid
{
    NSXMLElement *body = [NSXMLElement elementWithName:@"body"];
    [body setStringValue:text];
    NSXMLElement *message = [NSXMLElement elementWithName:@"message"];
    [message addAttributeWithName:@"type" stringValue:@"chat"];
    [message addAttributeWithName:@"to" stringValue:userJid];
    [message addChild:body];
    [self.xmppStream sendElement:message];
}

//注册成功
- (void)xmppStreamDidRegister:(XMPPStream *)sender
{
//    [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_REGISTERXMPPSUCCESS object:nil];
    [SystemDialog alert:@"注册成功"];
    //set vCardTemp
    [self updateCard:nil];
}

//注册失败
- (void)xmppStream:(XMPPStream *)sender didNotRegister:(NSXMLElement *)error
{
    NSString *errorStr =[[error elementForName:@"error"] stringValue];
    TTLog(@"%@",errorStr);
    [SystemDialog alert:@"注册失败"];
}


//登录验证成功
- (void)xmppStreamDidAuthenticate:(XMPPStream *)sender
{
    [SystemDialog alert:@"消息服务器登陆成功"];
//    [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_LOGINXMPPSUCCESS object:nil];
    //登录成功后给好友发送上下线通知。
    XMPPPresence *presence = [XMPPPresence presenceWithType:@"available"];
    [self.xmppStream sendElement:presence];
    [self queryMyvCardTemp];
//    [self queryRoster];
}

- (void)updatePhoneNumInXmppServer:(NSString*)phoneNum
{
    TTUserInfoForXmpp *info = [TTUserInfoForXmpp sharedInstance];
    //{"userID":"UID11111111","name":"12365896547"}
    NSDictionary *paramsDic = @{@"userID":info.userId,@"name":phoneNum};
    [TieTieTool tietieWithParameterMarked:TTAction_updateNames dict:paramsDic succes:^(id responseObj) {
        if ([[responseObj objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [self loginUser];
            TTLog(@"手机号修改成功");
        } else {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        TTLog(@"%@",error);
    }];
}


//登录验证失败
- (void)xmppStream:(XMPPStream *)sender didNotAuthenticate:(NSXMLElement *)error
{
    [SystemDialog alert:@"消息服务器登录失败"];
}

//获取好友的xmppjid
- (BOOL)xmppStream:(XMPPStream *)sender didReceiveIQ:(XMPPIQ *)iq
{
    TTLog(@"========didReceiveIQ========");

    if ([@"result" isEqualToString:iq.type]) {
        NSXMLElement *query = iq.childElement;
        if ([@"query" isEqualToString:query.name]) {
            NSArray *items = [query children];
            NSMutableArray *rosterItems = [[NSMutableArray alloc]init];
            NSString *groupName = [NSString stringWithFormat:@"%@%@",[self.xmppStream.myJID user],@"friends"];
            
            for (NSXMLElement *item in items) {
                if ([[self.xmppStream.myJID user] isEqualToString:[item attributeStringValueForName:@"name"]]) {
                    continue;
                }
                TTRosterItem *rosterItem = [[TTRosterItem alloc]init];
                NSString *jidString = [item attributeStringValueForName:@"jid"];
                rosterItem.jidStr = jidString;
                rosterItem.jid = [XMPPJID jidWithString:jidString];
                rosterItem.name = [item attributeStringValueForName:@"name"];
                rosterItem.subscription = [item attributeStringValueForName:@"subscription"];
                
                NSXMLElement *group = [[item children] objectAtIndex:0];
                rosterItem.group = [group stringValue];
                
                if ([rosterItem.group isEqualToString:groupName]) {
                    [rosterItems addObject:rosterItem];
                }
            }
            [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_QUERYROSTERSUCCESS object:rosterItems];
        }
    }

    
//    if ([iq elementForName:@"vCard"]){
//        NSLog(@"获取名片夹");
//        
//        NSXMLElement *vCard = [iq elementForName:@"vCard"];
//        NSLog(@"%@",vCard);
//        
//        XMPPvCardTemp *myvCardTemp = [self.xmppvCardTempModule myvCardTemp];
//        if (!myvCardTemp) {
//            myvCardTemp = [[XMPPvCardTemp alloc]init];
//            NSString* nickName = [[vCard elementForName:@"NICKNAME"] stringValue];
//            NSLog(@"nickName = %@",nickName);
////            myvCardTemp.nickname = nickName;
//            
//            NSString* phoneNum = [[vCard elementForName:@"PHONE"] stringValue];
//            NSLog(@"phoneNum = %@",phoneNum);
//           
//            
//            NSString *binvalString = [[(XMPPElement *)[[iq elementForName:@"vCard"] elementForName:@"PHOTO"] elementForName:@"BINVAL"] stringValue];
//            if (binvalString) {
//                NSData *imageData = [[NSData alloc]initWithBase64EncodedString:binvalString options:NSDataBase64DecodingIgnoreUnknownCharacters];
//                UIImage *image = [UIImage imageWithData:imageData];
//                NSLog(@"获取到头像图片！");
//                [self updateCardPhoto:image];
//            }
//        }
//    }
    return YES;
}

//好友请求的消息处理方法
- (void)xmppRoster:(XMPPRoster *)sender didReceivePresenceSubscriptionRequest:(XMPPPresence *)presence
{
    TTLog(@"=======didReceivePresenceSubscriptionRequest====");
    //取得好友状态
    NSString *presenceType = [NSString stringWithFormat:@"%@", [presence type]]; //online/offline
    //请求的用户
    NSString *presenceFromUser =[NSString stringWithFormat:@"%@", [[presence from] user]];
    TTLog(@"presenceType:%@",presenceType);
    
    TTLog(@"presence2:%@  sender2:%@",presence,sender);
    
    XMPPJID *jid = [XMPPJID jidWithString:presenceFromUser];
    [self.xmppRoster acceptPresenceSubscriptionRequestFrom:jid andAddToRoster:YES];
    
}


//available 上线
//away 离开
//do not disturb 忙碌
//unavailable 下线
//好友上下线通知处理
- (void)xmppStream:(XMPPStream *)sender didReceivePresence:(XMPPPresence *)presence
{
    TTLog(@"==========didReceivePresence=====");
    //    DDLogVerbose(@"%@: %@ ^^^ %@", THIS_FILE, THIS_METHOD, [presence fromStr]);
    //取得好友状态
    NSString *presenceType = [NSString stringWithFormat:@"%@", [presence type]]; //online/offline
    //当前用户
    //    NSString *userId = [NSString stringWithFormat:@"%@", [[sender myJID] user]];
    //在线用户
    NSString *presenceFromUser =[NSString stringWithFormat:@"%@", [[presence from] user]];
    TTLog(@"presenceType:%@",presenceType);
    TTLog(@"用户:%@",presenceFromUser);
    if (![presenceFromUser isEqualToString:[[sender myJID] user]]) {
        if ([presenceType isEqualToString:@"available"]) {
            //[SystemDialog alert:@"didReceivePresence:available"];
        } else if ([presenceType isEqualToString:@"unavailable"]) {
            //[SystemDialog alert:@"didReceivePresence:unavailable"];
        }
    }
}

//发送消息
- (void)xmppStream:(XMPPStream *)sender didSendMessage:(XMPPMessage *)message
{
    TTLog(@"didSendMessage:(XMPPMessage *)message");
    DBManager *dbManager = [DBManager sharedDBManager];
    NSString *tableName = [NSString stringWithFormat:@"chat_%@",[message.to user]];
    double time = (double)[[NSDate date] timeIntervalSince1970];
    NSString *createTime = [NSString stringWithFormat:@"%f",time];
    NSString *messageBody = [message body];
    NSString *messageType = @"1";
    NSInteger recieved = 0;
    NSInteger status = 1;
    
    NSDictionary *msgDict = @{
                              @"CreateTime": createTime,
                              @"Message":messageBody,
                              @"MessageType":messageType,
                              @"Recieved":[NSNumber numberWithInteger:recieved],
                              @"Status":[NSNumber numberWithInteger:status]
                              };
//    [dbManager insertTable:tableName object:msgDict];
    [dbManager insertChatTable:tableName valuesDict:msgDict];
}

//接受消息
- (void)xmppStream:(XMPPStream *)sender didReceiveMessage:(XMPPMessage *)message
{
    TTLog(@"==didReceiveMessage===用户接受消息=======");
    if ([message.type isEqualToString:@"chat"]) {
        
        double time = (double)[[NSDate date] timeIntervalSince1970];
        NSString *createTime = [NSString stringWithFormat:@"%f",time];
        NSString *msgBody = message.body;
//        NSString *msgType = message.type;

        NSDictionary *msgDict = @{
                                  @"CreateTime":createTime,
                                  @"Message":msgBody,
                                  @"MessageType":@"1",
                                  @"Recieved":[NSNumber numberWithInteger:1],
                                  @"Status":[NSNumber numberWithInteger:1]
                                  };
        DBManager *dbMananger = [DBManager sharedDBManager];
        NSString *tableName = [NSString stringWithFormat:@"chat_%@",[message.from user]];
        [dbMananger insertChatTable:tableName valuesDict:msgDict];
        
        [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_CHATMESSAGEFROMXMPP object:msgDict];
    }
    
//    NSString *body = [message body];
//    if (body) {
//        DBManager *db = [DBManager sharedDBManager];
//        double time = (double)[[NSDate date] timeIntervalSince1970];
//        NSString *timeStr = [NSString stringWithFormat:@"%f",time];
//        NSDictionary *dic = @{@"time":timeStr,@"message":body};
//        [db insertTable:msgTableName object:dic];
//        
//        [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_UPDATEUITABBARITEM object:nil];
//    }

    
//    //程序运行在前台，消息正常显示
//    if ([[UIApplication sharedApplication] applicationState] == UIApplicationStateActive){
//        NSLog(@"当前程序在前台接受message：%@",message);
//    } else if ( [[UIApplication sharedApplication] applicationState] == UIApplicationStateBackground){
//        //如果程序在后台运行，收到消息以通知类型来显示
//        UILocalNotification *localNotification = [[UILocalNotification alloc] init];
//        localNotification.alertAction = @"Ok";
//        localNotification.alertBody = [NSString stringWithFormat:@"From xmpp :didReceiveMessage"];//通知主体
//        localNotification.soundName = @"crunch.wav";//通知声音
//        localNotification.applicationIconBadgeNumber = 1;//标记数
//        [[UIApplication sharedApplication] presentLocalNotificationNow:localNotification];//发送通知
//    } else {
//    
//    }

}



- (void)xmppRoster:(XMPPRoster *)sender didReceiveRosterItem:(DDXMLElement *)item
{
    TTLog(@"didReceiveRosterItem:roster item:%@",item);
}

- (void)xmppvCardTempModule:(XMPPvCardTempModule *)vCardTempModule
        didReceivevCardTemp:(XMPPvCardTemp *)vCardTemp
                     forJID:(XMPPJID *)jid
{
    XMPPvCardTemp *myvCardTemp = [self.xmppvCardTempModule myvCardTemp];
    if (myvCardTemp) {
       [[NSNotificationCenter defaultCenter] postNotificationName:KNOTIFICATIONCENTER_UDATEMYVCARDTEMP object:nil];
    }
    TTLog(@"didReceivevCardTemp: 获取列表开始");
    TTLog(@"jid====%@",jid);
}


- (void)xmppvCardTempModuleDidUpdateMyvCard:(XMPPvCardTempModule *)vCardTempModule//更新名片
{
    TTLog(@"xmppvCardTempModuleDidUpdateMyvCard:%s--%@",__func__,vCardTempModule);
    [SystemDialog alert:@"电子名片更新成功！"];
}

//获取完好友列表
- (void)xmppRosterDidEndPopulating:(XMPPRoster *)sender
{
    TTLog(@"xmppRosterDidEndPopulating:获取完毕好友列表");
}
/*
 *  返回头像
 */
- (UIImage *)loadUserImage:(XMPPUserCoreDataStorageObject *)user
{
    // 1. 判断user中是否包含头像，如果有，直接返回
    if (user.photo) {
        TTLog(@"===========XMPPUserCoreDataStorageObject 包含头像");
        return user.photo;
    }
    
    // 2. 如果没有头像，从用户的头像模块中提取用户头像
    NSData *photoData = [self.xmppvCardAvatarModule photoDataForJID:user.jid];
    TTLog(@"===========XMPPUserCoreDataStorageObject 不包含头像＝＝＝＝如果没有头像，从用户的头像模块中提取用户头像");
    if (photoData) {
        return [UIImage imageWithData:photoData];
    }
    
    //    NSString *binvalString = [[(XMPPElement *)[[iq elementForName:@"vCard"] elementForName:@"PHOTO"] elementForName:@"BINVAL"] stringValue];
    //    if (binvalString) {
    //        NSData *imageData = [[NSData alloc]initWithBase64EncodedString:binvalString options:NSDataBase64DecodingIgnoreUnknownCharacters];
    //        UIImage *image = [UIImage imageWithData:imageData];
    //        [self.imageView setImage:image];
    //        NSLog(@"iamge：%@",image);
    //    }
    
    return [UIImage imageNamed:@"coupon_normal"];
}
@end
