//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPickFriendViewController.h"
#import "UIImage(addition).h"
#import "UIView(category).h"
#import "TTFriend.h"
#import "PickCell.h"
#import "UIColor+Extension.h"
#import "TextStepperField.h"
#import "TTXMPPTool.h"
#import "TTRosterItem.h"
#import "TTTieCoupponsViewController.h"
#import "TTChatViewController.h"


@interface TTPickFriendViewController () <checkdelegate,NSFetchedResultsControllerDelegate>
{
    MyCoupons *myCoupon;
    NSFetchedResultsController *_fetchedResultsController;
}
@property  (nonatomic, strong)  NSMutableArray* PickList;

@property  int  SelectedIndex;

- (void)handleNotification:(id)sender;
- (void)ParseJsonData: (NSArray*)JsonData;

@end


@implementation TTPickFriendViewController

@synthesize SelectedIndex = _SelectedIndex;

@synthesize PickList = _PickList;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"选择贴友";
        self.PickList = [[NSMutableArray alloc] init];
        self.SelectedIndex = -1;
        [self view];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
    // Do any additional setup after loading the view.
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    UIBarButtonItem *rightBarItem = [[UIBarButtonItem alloc]initWithTitle:@"赠送" style:UIBarButtonItemStylePlain target:self action:@selector(SendConpus)];
    self.navigationItem.rightBarButtonItem = rightBarItem;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleNotification:) name:KNOTIFICATIONCENTER_QUERYROSTERSUCCESS object:nil];
    /****测试xmpp获取好友列表****/
    //查找好友列表的信息
    [self setupFetchedController];
    
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
    [self  setSelectedIndex:-1];
}

- (void)setDataWithMycoupons:(MyCoupons *)coupons
{
    [self.couponImageView setImageWithURL:[NSURL URLWithString:coupons.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    self.couponNameLab.text = coupons.actName;
    self.couponCountLab.text = [NSString stringWithFormat:@"剩余%d张",coupons.couponNum];
    self.couponValidity.text = coupons.endDate;
    self.textStepper.Current = 1;
    self.textStepper.Maximum = coupons.couponNum;
    self.textStepper.Minimum = 1;
    self.textStepper.IsEditableTextField = NO;
    myCoupon = coupons;
}

- (void)RequestPickList
{
    [self.PickList removeAllObjects];
    
}

- (void)friendList
{
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    [xmppTool connect];
    [xmppTool friendList];
}


- (void)ParseJsonData: (NSArray*)friendArray
{
    for(XMPPUserCoreDataStorageObject *object in friendArray)
  {
      TTPickFriend* ttf = [[TTPickFriend alloc] init];
      ttf.HeaderImage = [[TTXMPPTool sharedInstance] loadUserImage:object];//[self loadUserImage:object];
      ttf.userID = [object.jid user];
        ttf.userName = object.nickname;
      TTLog(@"====jid:%@=====nickname:%@===",object.jidStr,object.nickname);
      [self.PickList addObject:ttf];
  }
}

- (void)SendConpus
{
    if (self.SelectedIndex == -1) {
        [SystemDialog alert:@"请选择好友！"];
        return;
    }
//{"sendUserId":"13426364669","receiveUserId":"Uid776656","actId":"Uid776656","Number":"1","sysPlat":"5"}
    
    TTFriend *friend = [self.PickList objectAtIndex:self.SelectedIndex];

    NSString *sendUserId = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *receiveUserId = friend.userName;
    NSString *actId = myCoupon.actId;
    
    NSString *number = [NSString stringWithFormat:@"%i",(int)self.textStepper.Current];
    NSString *sysPlat = @"5";//MPAY平台分配系统标识参数
    
    NSDictionary *dicParams = @{@"sendUserId": sendUserId,
                          @"receiveUserId":receiveUserId,
                          @"actId":actId,
                          @"Number":number,
                          @"sysPlat":sysPlat};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_sendCoupon dict:dicParams succes:^(id respondObject) {
        [self showLoading:NO];
        TTLog(@"sendCoupon:%@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"赠券成功"];
            for (UIViewController *vc in self.navigationController.viewControllers) {
                if ([vc isKindOfClass:[TTTieCoupponsViewController class]]) {
                    TTTieCoupponsViewController *tieVC = (TTTieCoupponsViewController*)vc;
                    [tieVC updateTableAfterSend:self.textStepper.Current];
                    [self.navigationController popToViewController:tieVC animated:YES];
                    break;
                }
            }
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}


#pragma mark - UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.PickList count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static  NSString *identifier = @"PickList";
    int nRow = [indexPath row];
    
    PickCell *cell = (PickCell*)[tableView dequeueReusableCellWithIdentifier:identifier];
    if(cell==nil)
    {
        cell = [[PickCell alloc] initCustom];
        [cell setDelegate:self];
        [cell setEditing:NO];
    }
    
    //===============
    if(nRow>=0&&nRow<[self.PickList count])
    {
        TTPickFriend* tempFriend = self.PickList[nRow];
        [cell SetObject:tempFriend nRowIndex:nRow];
    }
    return cell;
}

#pragma mark -UITableDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
//    TTChatViewController *chatVC = [[TTChatViewController alloc] init];
//    [self.navigationController pushViewController:chatVC animated:YES];
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 88;
}

- (void)checkSelected:(BOOL)bflag
                 path:(int)row
{
    TTPickFriend* object = self.PickList[row];
    //[object setBCheckFlag:bflag];
    TTLog(@"object.bflag = %d",object.bCheckFlag);
    if(self.SelectedIndex>=0&&self.SelectedIndex<[self.PickList count])
    {
        if(self.SelectedIndex!=row)
        {
            PickCell* selectedCell = (PickCell*)[self.table cellForRowAtIndexPath:[NSIndexPath  indexPathForRow:_SelectedIndex inSection:0]];
            [selectedCell ChangeState];
            [selectedCell DrawCheckBox];
        }
    }
    
    if(self.SelectedIndex!=row)
       self.SelectedIndex = row;
    else
    {
        if(bflag)
          self.SelectedIndex = row;
        else
          self.SelectedIndex = -1;
    }
    TTLog(@"=====SelectedIndex========%d",self.SelectedIndex);
}

- (void)handleNotification:(id)sender
{
    [self showLoading:NO];
    NSNotification *notification = (NSNotification*)sender;
    NSMutableArray *rosterItems = notification.object;
    
    NSMutableString *userIds = [[NSMutableString alloc]init];
    
    for (int i = 0,count = [rosterItems count]; i < count; i++) {
        TTRosterItem *item = [rosterItems objectAtIndex:i];
        NSString *userId = [item.jid user];
        if (i == 0) {
            [userIds appendString:userId];
        } else {
            NSString *str = [NSString stringWithFormat:@"#%@",userId];
            [userIds appendString:str];
        }
    }
    
    [self judgeUserAction:userIds];
}

- (void)judgeUserAction:(NSString*)userIds
{
    NSDictionary *paramDic = @{@"userID":userIds};
    
    [TieTieTool tietieWithParameterMarked:TTAction_judgeUsersAction dict:paramDic succes:^(id responseObj) {
        [self showLoading:NO];
        //{"rspCode":"000","rspDesc":"反馈失败"}
        TTLog(@"judgeUsersAction:%@",[responseObj objectForKey:rspDesc]);
        NSString *responseCode = [responseObj objectForKey:rspCode];
        if ([responseCode isEqualToString:rspCode_success]) {
            if (!self.PickList) {
                self.PickList = [NSMutableArray array];
            } else {
                [self.PickList removeAllObjects];
            }
            NSArray *userList = [responseObj objectForKey:@"userList"];
            
            for (NSDictionary *dic in userList) {
                
                TTPickFriend* ttf = [[TTPickFriend alloc] init];
                ttf.HeaderImage = [UIImage imageNamed:@"icon_user"];
                ttf.userID = [dic objectForKey:@"userID"];
                ttf.userName = [dic objectForKey:@"name"];
                [self.PickList addObject:ttf];
            }
            
            if (self.PickList.count > 0) {
                [self backgroundViewWithNoData:NO];
            } else {
                [self backgroundViewWithNoData:YES];
            }
            [self.table reloadData];
            
        } else /*if ([responseCode isEqualToString:@"002"])*/ {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
    
}

- (void)backgroundViewWithNoData:(BOOL)hasBgView
{
    if (!hasBgView) {
        self.table.backgroundView = nil;
    } else {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"icon_user_bg"]];
        [imageView setCenter:CGPointMake(160, 200)];
        UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
        label.text = @"您还没有好友，赶快去添加吧！";
        label.textColor = [UIColor grayColor];
        label.textAlignment = NSTextAlignmentCenter;
        [label setCenter:CGPointMake(160, 240)];
        
        UIView *bgView = [[UIView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame];
        [bgView addSubview:imageView];
        [bgView addSubview:label];
        
        self.table.backgroundView = bgView;
    }
}


#pragma mark - 实例化NSFetchedResultsController
- (void)setupFetchedController
{
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    if ([xmppTool.xmppStream isConnected]) {
        [self showLoading:YES];
        [xmppTool friendList];
    } else {
        [SystemDialog alert:@"消息服务器未完成登录！"];
    }
    
//    // 1. 如果要针对CoreData做数据访问，无论怎么包装，都离不开NSManagedObjectContext
//    // 实例化NSManagedObjectContext
//    NSManagedObjectContext *context = [xmppTool.xmppRosterStorage mainThreadManagedObjectContext];
//    
//    // 2. 实例化NSFetchRequest
//    NSFetchRequest *request = [NSFetchRequest fetchRequestWithEntityName:@"XMPPUserCoreDataStorageObject"];
//    
//    // 3. 实例化一个排序
//    NSSortDescriptor *sort1 = [NSSortDescriptor sortDescriptorWithKey:@"displayName" ascending:YES];
//    NSSortDescriptor *sort2 = [NSSortDescriptor sortDescriptorWithKey:@"jidStr" ascending:YES];
//    
//    [request setSortDescriptors:@[sort1, sort2]];
//    
//    //========= 可以获取数据列表 start===========
//    NSError *err;
//    //array的元素类型是：XMPPUserCoreDataStorageObject
//    NSArray *array = [context executeFetchRequest:request error:&err];
////    friendArray = [NSArray arrayWithArray:array];
//    NSInteger count = [array count];
//    NSLog(@"=============好友数量：%d",count);
//    NSArray *array = [[TTXMPPTool sharedInstance] getFriendInfos];
//    [self ParseJsonData:array];
    
//    XMPPUserCoreDataStorageObject *friendObj = [friendArray firstObject];
//    NSString *friendName = friendObj.displayName;
//    NSString *nickName = friendObj.nickname;
//    NSLog(@"========= %@=========%@=====",friendName,nickName);
//    UIImage *image = [self loadUserImage:friendObj];
//    if (image) {
//        self.couponImageView.image = image;
//    }
    
    //===========可以获取数据列表 end=============================
    
//    // 4. 实例化_fetchedResultsController
//    _fetchedResultsController = [[NSFetchedResultsController alloc]initWithFetchRequest:request managedObjectContext:context sectionNameKeyPath:@"sectionNum" cacheName:nil];
//    
//    // 5. 设置FetchedResultsController的代理
//    [_fetchedResultsController setDelegate:self];
//    
//    // 6. 查询数据
//    NSError *error = nil;
//    if (![_fetchedResultsController performFetch:&error]) {
//        NSLog(@"%@", error.localizedDescription);
//    }
}

//- (UIImage *)loadUserImage:(XMPPUserCoreDataStorageObject *)user
//{
//    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
//    // 1. 判断user中是否包含头像，如果有，直接返回
//    if (user.photo) {
//        NSLog(@"===========XMPPUserCoreDataStorageObject 包含头像");
//        return user.photo;
//    }
//    
//    // 2. 如果没有头像，从用户的头像模块中提取用户头像
//    NSData *photoData = [xmppTool.xmppvCardAvatarModule photoDataForJID:user.jid];
//    NSLog(@"===========XMPPUserCoreDataStorageObject 不包含头像＝＝＝＝如果没有头像，从用户的头像模块中提取用户头像");
//    if (photoData) {
//        return [UIImage imageWithData:photoData];
//    }
//    
////    NSString *binvalString = [[(XMPPElement *)[[iq elementForName:@"vCard"] elementForName:@"PHOTO"] elementForName:@"BINVAL"] stringValue];
////    if (binvalString) {
////        NSData *imageData = [[NSData alloc]initWithBase64EncodedString:binvalString options:NSDataBase64DecodingIgnoreUnknownCharacters];
////        UIImage *image = [UIImage imageWithData:imageData];
////        [self.imageView setImage:image];
////        NSLog(@"iamge：%@",image);
////    }
//    
//    return [UIImage imageNamed:@"load.png"];
//}

#pragma mark - NSFetchedResultsControllerDelegate
- (void)controllerDidChangeContent:(NSFetchedResultsController *)controller
{
    [self setupFetchedController];
    [self.table reloadData];
}
@end
