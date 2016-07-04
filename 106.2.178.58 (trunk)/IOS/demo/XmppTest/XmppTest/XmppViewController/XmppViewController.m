//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "XmppViewController.h"
#import "XMPPvCardTemp.h"
#import "XMPPvCardTempModule.h"

//#define  USERNAME  @"mikeBell"

//#define  PASSWORD  @"distRong"


//#define  USERNAME  @"LeonFire"

//#define  PASSWORD  @"AdaHit"


//#define  USERNAME  @"cowgirl"

//#define  PASSWORD  @"fantasy"


#define   USERNAME   @"GreenTown"

#define   PASSWORD   @"HireWall"


#define  RESOURCE  @"svn.test.localdomain"

#define  SERVICE   @"106.2.178.58"


@interface XmppViewController()<XMPPStreamDelegate>

@property  (nonatomic, strong)  NSMutableArray* FriendList;

@end


@implementation XmppViewController

@synthesize xmppStream;

@synthesize xmppRoster;

@synthesize xmppRosterStorage;

@synthesize xmppvCardStorage;

@synthesize xmppvCardTempModule;

@synthesize xmppvCardAvatarModule;

@synthesize FriendList;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if(self)
    {
       self.title = @"消息服务器";
       self.FriendList = [[NSMutableArray alloc] initWithCapacity:10];
    }
    return self;
}


- (void)viewDidLoad
{
    [super viewDidLoad];
}


- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self  connect];
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






//完成登录操作
- (void)xmppStreamDidConnect:(XMPPStream *)sender
{
/*------------------------帐户登录-----------------------*/
    NSError *error = nil;
    //NSString* md5str = [PASSWORD md5Encrypt];
    //NSLog(@"md5str= %@",md5str);
    if (![self.xmppStream authenticateWithPassword:PASSWORD error:&error])
    {
        NSLog(@"Authenticate Error: %@", [[error userInfo] description]);
    }
    else
    {
        NSLog(@"连接成功");
    
    }
/*------------------------帐户登录------------------------*/
    
/*------------------------帐户注册------------------------*/
//    if (![self.xmppStream registerWithPassword:PASSWORD error:&error])
//    {
//          NSLog(@"regist Error: %@", [[error userInfo] description]);
//    }
//    else
//    {
//          NSLog(@"注册成功");
//          [self ShowAlertMsg:@"注册成功" content:@"注册成功"];
//    }
/*------------------------帐户注册------------------------*/
}

- (void)AddUser:(XMPPJID*)jid
{
     [self.xmppRoster addUser:jid withNickname:@"test09"];
}



- (void)FetchCard:(XMPPJID*)Jid
{
//    NSString* JidStr = [Jid  user];
//    NSLog(@"JidStr = %@",JidStr);
//    XMPPIQ* iq = [XMPPIQ iqWithType:@"get"];
//    [iq addAttributeWithName:@"to" stringValue:JidStr];
//    NSXMLElement *vElement = [NSXMLElement elementWithName:@"vCard" xmlns:@"vcard-temp"];
//    [iq addChild:vElement];
//    [xmppStream sendElement:iq];
//    [self.xmppvCardTempModule fetchvCardTempForJID:Jid ignoreStorage:YES];
//    [self NewXmlCard];
      [self.xmppvCardTempModule fetchvCardTempForJID:Jid];
}

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
    [xmppvCardTempModule updateMyvCardTemp:newvCardTemp];
}


- (void)updateCard:(NSString*)nickName
{
    XMPPvCardTemp* myvCardTemp = [xmppvCardTempModule myvCardTemp];
    
    if(myvCardTemp)
    {
        [myvCardTemp setNickname:nickName];
        [xmppvCardTempModule updateMyvCardTemp:myvCardTemp];
    }
}



- (BOOL)xmppStream:(XMPPStream *)sender didReceiveIQ:(XMPPIQ *)iq
{
    NSLog(@"获取名片信息");
    //获取当前数据的头像和性别
    if ([iq elementForName:@"vCard"]){
        
        NSLog(@"获取名片夹");
        
        NSXMLElement *vCard = [iq elementForName:@"vCard"];
        
        NSString* nickName = [[vCard elementForName:@"NICKNAME"] stringValue];
        NSLog(@"nickName = %@",nickName);
        
        NSString* phoneNum = [[vCard elementForName:@"PHONE"] stringValue];
        NSLog(@"phoneNum = %@",phoneNum);
    }
    return YES;
    
    return NO;
}


//验证成功
- (void)xmppStreamDidAuthenticate:(XMPPStream *)sender
{
    XMPPPresence *presence = [XMPPPresence presenceWithType:@"available"];
    [self.xmppStream sendElement:presence];
    [self ShowAlertMsg:@"登录成功" content:@"验证通过,恭喜您"];

    //[self NewXmlCard];
    //[self updateCard:@"kitty"];
    //[self AddUser:<#(XMPPJID *)#>];
    [self FetchCard:self.xmppStream.myJID];
 }


//验证失败
- (void)xmppStream:(XMPPStream *)sender didNotAuthenticate:(NSXMLElement *)error
{
    NSLog(@"验证失败");
   [self ShowAlertMsg:@"登录失败" content:@"验证错误"];
}


//注册成功
- (void)xmppStreamDidRegister:(XMPPStream *)sender
{
   [self ShowAlertMsg:@"注册成功" content:@"注册成功"];
}

//注册失败
- (void)xmppStream:(XMPPStream *)sender didNotRegister:(NSXMLElement *)error
{
    NSLog(@"%@",[[error elementForName:@"error"] stringValue]);
    [self ShowAlertMsg:@"注册失败" content:@"用户已注册"];
}


- (void)ShowAlertMsg:(NSString*)title content:(NSString*)content
{
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:title
              message:content
             delegate:nil
    cancelButtonTitle:@"Ok"
    otherButtonTitles:nil];
    [alertView show];
}


//断开连接
- (void)disconnect {
    XMPPPresence *presence = [XMPPPresence presenceWithType:@"unavailable"];
    [self.xmppStream sendElement:presence];
    
    [self.xmppStream disconnect];
}




//开始连接
- (void)connect {
    NSLog(@"开始连接");
    if (self.xmppStream == nil) {
        self.xmppStream = [[XMPPStream alloc] init];
        [self.xmppStream addDelegate:self delegateQueue:dispatch_get_main_queue()];
       // [self.xmppStream supportsDigestMD5Authentication];
    }
    
    if(self.xmppRoster==nil)
    {
        //xmpp绑定花名册
        self.xmppRosterStorage = [[XMPPRosterCoreDataStorage alloc]init];
        self.xmppRoster = [[XMPPRoster alloc] initWithRosterStorage:self.xmppRosterStorage];
        [self.xmppRoster activate:self.xmppStream];
        [self.xmppRoster addDelegate:self delegateQueue:dispatch_get_main_queue()];
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
    
    [self.xmppStream disconnect];
    
    if (![self.xmppStream isConnected]) {
        //XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@@%@",USERNAME,RESOURCE]];
        XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@@%@",USERNAME,RESOURCE]];
        //XMPPJID *jid = [XMPPJID jidWithUser:USERNAME domain:SERVICE resource:RESOURCE];
        [self.xmppStream setMyJID:jid];
        [self.xmppStream setHostName:SERVICE];
        [self.xmppStream setHostPort:5222];
        
        NSError *error = nil;
        if (![self.xmppStream connectWithTimeout:10 error:&error]) {
            NSLog(@"Connect Error: %@", [[error userInfo] description]);
        }
    }
}


//- (void)xmppRoster:(XMPPRoster *)sender didReceivePresenceSubscriptionRequest:(XMPPPresence *)presence
//{
//    NSLog(@"22222");
//    //取得好友状态
//    NSString *presenceType = [NSString stringWithFormat:@"%@", [presence type]]; //online/offline
//    //请求的用户
//    NSString *presenceFromUser =[NSString stringWithFormat:@"%@", [[presence from] user]];
//    NSLog(@"presenceType:%@",presenceType);
//    
//    NSLog(@"presence2:%@  sender2:%@",presence,sender);
//    
//    XMPPJID *jid = [XMPPJID jidWithString:presenceFromUser];
//    [xmppRoster acceptPresenceSubscriptionRequestFrom:jid andAddToRoster:YES];
//    
//}


//available 上线
//away 离开
//do not disturb 忙碌
//unavailable 下线
- (void)xmppStream:(XMPPStream *)sender didReceivePresence:(XMPPPresence *)presence
{
    //    DDLogVerbose(@"%@: %@ ^^^ %@", THIS_FILE, THIS_METHOD, [presence fromStr]);
    NSLog(@"11111");
    //取得好友状态
    NSString *presenceType = [NSString stringWithFormat:@"%@", [presence type]]; //online/offline
    //当前用户
    //    NSString *userId = [NSString stringWithFormat:@"%@", [[sender myJID] user]];
    //在线用户
    NSString *presenceFromUser =[NSString stringWithFormat:@"%@", [[presence from] user]];
    NSLog(@"presenceType:%@",presenceType);
    NSLog(@"用户:%@",presenceFromUser);
    //这里再次加好友
    if ([presenceType isEqualToString:@"subscribed"]) {
        XMPPJID *jid = [XMPPJID jidWithString:[NSString stringWithFormat:@"%@",[presence from]]];
        [xmppRoster acceptPresenceSubscriptionRequestFrom:jid andAddToRoster:YES];
    }
}


- (void)xmppRoster:(XMPPRoster *)sender didReceiveRosterItem:(DDXMLElement *)item
{
    NSLog(@"home");
}


- (void)xmppvCardTempModule:(XMPPvCardTempModule *)vCardTempModule
        didReceivevCardTemp:(XMPPvCardTemp *)vCardTemp
                     forJID:(XMPPJID *)jid
{
    NSLog(@"dsfsf 获取列表开始");
    NSLog(@"vCardTemp jid = %@",vCardTemp.jid);
    
//    //    NSLog(@"%s",__func__);
//    NSXMLElement *xmlData=(NSXMLElement *)vCardTemp;
//    NSString *titleString = @"这个用户很懒，没有签名！";
//    NSString *familyString = @"无名用户";
//    NSString *photoString = @"";
//    for(id myItem in [xmlData children])
//    {
//        NSLog(@"myItem name=%@",[myItem name]);
//        if([myItem stringValue].length <= 100)
//        {
//            //            NSLog(@"valuelalala:%@",[myItem stringValue]);
//        }
//        else
//        {
//            NSLog(@"too long");
//        }
//        if([[myItem name] isEqualToString:@"TITLE"])
//        {
//            titleString = [myItem stringValue];
//        }
//        else if([[myItem name] isEqualToString:@"N"])
//        {
//            familyString = [myItem stringValue];
//        }
//        else if([[myItem name] isEqualToString:@"PHOTO"])
//        {
//            photoString = [[myItem stringValue] substringFromIndex:5];
//        }
//    }
//    //    头像image 写入本地
//    NSString *PHOTOImagePath=[self writeDateBase_64Image:photoString WithFileName:jid.user Size:CGSizeMake(88, 88)];
//    NSMutableDictionary *dicObject=[[NSMutableDictionary alloc]initWithObjectsAndKeys:titleString,@"TITLE",familyString,@"FAMILY",PHOTOImagePath,@"PHOTO", nil];
//    [self.rosterInfoDic setObject:dicObject forKey:jid.user];//
//    //family photopath title 写入本地
//    NSLog(@"forJID:(XMPPJID *)jid=%@   \n self.lastFetchedvCard=%@   \n ",jid.user,self.lastFetchedvCard.user);
//    if ([self.lastFetchedvCard.user isEqualToString:jid.user])//(在最后一次名片请求的时候,写入本地,)
//    {
//        //        [DataPlist writeDP:self.rosterInfoDic WithName:xmppStream.myJID.user AndTheType:@"plist"];
//        [DataPlist writePlistToDocumentWithDic:self.rosterInfoDic WithName:xmppStream.myJID.user AndTheType:@"plist"];
//    }
//    NSNotification *notificationObject =[NSNotification notificationWithName:@"upDateListData"object:nil];
//    [[NSNotificationCenter defaultCenter] postNotification:notificationObject];
    
    NSLog(@"vCardTempModule = %@",vCardTempModule);
    NSLog(@"vCardTemp = %@",vCardTemp);
    NSLog(@"jid = %@",jid);
}

- (void)xmppvCardTempModuleDidUpdateMyvCard:(XMPPvCardTempModule *)vCardTempModule//更新名片
{
    NSLog(@"世界你好%s--%@",__func__,vCardTempModule);
}

//获取完好友列表
- (void)xmppRosterDidEndPopulating:(XMPPRoster *)sender
{
    NSLog(@"获取完毕好友列表");
}
@end
