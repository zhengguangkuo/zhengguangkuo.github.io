
//
//  TTFriendsViewController.m
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFriendsViewController.h"
#import "TTFriendsCell.h"
#import "TTAndFriendsViewController.h"
#import "TTCommentViewController.h"
#import "PickCell.h"
#import "UIColor+Extension.h"
#import "TextStepperField.h"
#import "TTXMPPTool.h"
#import "TTFriends.h"
#import "TTRosterItem.h"
#import "TTPickCouponViewController.h"
#import "TTChatViewController.h"
@interface TTFriendsViewController ()
{
    NSMutableArray  *   _friendsListData;
}

- (void)handleNotification:(id)sender;

@end

@implementation TTFriendsViewController

- (void)loadView
{
    UITableView *tableView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame style:UITableViewStylePlain];
    tableView.delegate = self;
    tableView.dataSource = self;
    tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    // 设置背景
    tableView.backgroundView = nil;
    tableView.backgroundColor = TTGlobalBg;
    self.view = tableView;
    _tableView = tableView;
}

- (void)backgroundViewWithNoData:(BOOL)hasBgView
{
    if (!hasBgView) {
        _tableView.backgroundView = nil;
    } else {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"icon_user_bg"]];
        [imageView setCenter:CGPointMake(160, 200)];
        UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
        label.text = @"您还没有好友，点击右上角添加吧！";
        label.textColor = [UIColor grayColor];
        label.textAlignment = NSTextAlignmentCenter;
        [label setCenter:CGPointMake(160, 240)];
        
        UIView *bgView = [[UIView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame];
        [bgView addSubview:imageView];
        [bgView addSubview:label];
        
        _tableView.backgroundView = bgView;
    }
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    if (_friendsListData.count > 0) {
        [self backgroundViewWithNoData:NO];
    } else {
        [self backgroundViewWithNoData:YES];
    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.view.backgroundColor = TTGlobalBg;
   
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    self.title = @"我的贴友";
    self.navigationItem.rightBarButtonItem =  [UIBarButtonItem barButtonItemWithIcon:@"top_add_" target:self action:@selector(addFriend) direction:ItemDirectionRight];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleNotification:) name:KNOTIFICATIONCENTER_QUERYROSTERSUCCESS object:nil];
    //获取好友列表
    [self setupFetchedController];
}

- (void)handleNotification:(id)sender
{
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
            if (!_friendsListData) {
                _friendsListData = [NSMutableArray array];
            } else {
                [_friendsListData removeAllObjects];
            }
            NSArray *userList = [responseObj objectForKey:@"userList"];
            
            for (NSDictionary *dic in userList) {
                TTFriends *friend = [[TTFriends alloc] init];
                friend.nickName = [dic objectForKey:@"name"];//item.name;
                friend.ID = [dic objectForKey:@"userID"];//[item.jid user];
                //        friend.HeaderImage = [xmppTool loadUserImage:object];
                friend.HeaderImage = [UIImage imageNamed:@"icon_user"];
                [_friendsListData addObject:friend];
            }
            
            if (_friendsListData.count > 0) {
                [self backgroundViewWithNoData:NO];
            } else {
                [self backgroundViewWithNoData:YES];
            }
            [self.tableView reloadData];
            
        } else /*if ([responseCode isEqualToString:@"002"])*/ {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }

    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];

}

#pragma mark - 实例化NSFetchedResultsController
- (void)setupFetchedController
{
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    
    if (xmppTool.xmppStream && xmppTool.xmppStream.isConnected && xmppTool.xmppStream.isAuthenticated) {
        [self showLoading:YES];
        [xmppTool friendList];
    } else {
        [SystemDialog alert:@"消息服务器未完成登录"];
    }
}

#pragma mark - NSFetchedResultsControllerDelegate
- (void)controllerDidChangeContent:(NSFetchedResultsController *)controller
{
    [self.tableView reloadData];
}
#pragma mark -tableViewdelegate And datasource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _friendsListData.count;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTFriends *friend = _friendsListData[indexPath.row];
    TTChatViewController *chatVC = [[TTChatViewController alloc] init];
    chatVC.friendData = friend;
    [self.navigationController pushViewController:chatVC animated:YES];
}

#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTFriendsCell *friendCell = [TTFriendsCell FriendCellWithTableView:tableView];
    UIImageView *img = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"whightbg2"]];
    friendCell.backgroundView = img;

    TTFriends *friend = _friendsListData[indexPath.row];
    if (friend.nickName) {
        friendCell.nickName.text = friend.nickName;
    }else{
        friendCell.nickName.text = friend.ID;
    }
    friendCell.headImage.image = friend.HeaderImage;
    UIView *view = [friendCell.removeBtn superview];
    view.tag = indexPath.row;
    friendCell.selectionStyle = UITableViewCellSelectionStyleNone;
    [friendCell.removeBtn addTarget:self action:@selector(removeFriend:) forControlEvents:UIControlEventTouchUpInside];
    [friendCell.commentBtn addTarget:self action:@selector(commentFriend:) forControlEvents:UIControlEventTouchUpInside];
    [friendCell.giveBtn addTarget:self action:@selector(giveCoupon:) forControlEvents:UIControlEventTouchUpInside];
    
    return friendCell;
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 86;
}

#pragma mark -cellSubview点击事件
//删除好友
- (void)removeFriend:(UIButton *)sender
{
    UIView *view = [sender superview];
//     NSIndexPath *indexpath = [self.tableView indexPathForCell:cell];
    TTFriends *friend = [_friendsListData objectAtIndex:view.tag];
    TTLog(@"删除%d",view.tag);

    //{"nowUserId":"UID11111111","friendUserId":"UID4445555"}
    NSString *userId = [TTAccountTool sharedTTAccountTool].currentAccount.nowUserId;
    NSString *friedId = friend.ID;
    NSDictionary *paramsDic = @{@"nowUserId":userId,@"friendUserId":friedId};
    __weak TTFriendsViewController *friendsVC = self;
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_deleteRoster dict:paramsDic succes:^(id responseObj) {
        [self showLoading:NO];
        if ([[responseObj objectForKey:rspCode]isEqualToString:rspCode_success]) {
            [_friendsListData removeObject:friend];
            if (_friendsListData.count > 0) {
                [self backgroundViewWithNoData:NO];
            } else {
                [self backgroundViewWithNoData:YES];
            }
            [friendsVC.tableView reloadData];
        } else {
            [SystemDialog alert:[responseObj objectForKey: rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}
//备注好友
- (void)commentFriend:(UIButton *)sender
{
    TTLog(@"备注:%d",[(UIView *)[sender superview] tag]);
    TTCommentViewController *comment = [[TTCommentViewController alloc] init];
  
    [self.navigationController pushViewController:comment animated:YES];
}
//赠劵
- (void)giveCoupon:(UIButton *)sender
{
    TTLog(@"赠券:%d",[(UIView *)[sender superview] tag]);
    NSInteger row = [(UIView *)[sender superview] tag];
    TTFriends *info = [_friendsListData objectAtIndex:row];
    TTPickCouponViewController *pick = [[TTPickCouponViewController alloc] init];
    pick.friendInfo = info;
    [self.navigationController pushViewController:pick animated:YES];
}
//添加好友
- (void)addFriend
{
    TTAndFriendsViewController *andF = [[TTAndFriendsViewController alloc] init];
    [self.navigationController pushViewController:andF animated:YES];
}
@end
