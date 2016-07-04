//
//  TTFriendsDetailViewController.m
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFriendsDetailViewController.h"
#define TTFriendUserID  @"TTFriendUserID"
#import "TTFriendsViewController.h"
@interface TTFriendsDetailViewController ()
@property (weak, nonatomic) IBOutlet UILabel *userName;
@property (weak, nonatomic) IBOutlet UILabel *nickName;
@property (weak, nonatomic) IBOutlet UITextField *userTel;
- (IBAction)andFriend:(id)sender;

@end

@implementation TTFriendsDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
       
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
    
    self.title = @"详细资料";
    self.view.backgroundColor = TTGlobalBg;
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    
    TTLog(@"详细资料  %@",self.friends.tel);
    if (self.friends.userName.length > 0) {
        _userName.text = self.friends.userName;
        _nickName.text = self.friends.userName;
    }else{
        _userName.text = @"未编辑";
        _nickName.text = @"未编辑";
    }
    if (self.friends.tel.length > 0) {
        _userTel.text = self.friends.tel;
    }
    if (self.friendMobile.length>0) {
        _userTel.text = self.friendMobile;
    }

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark -添加好友
- (IBAction)andFriend:(id)sender {
    [self showLoading:YES];
    // 查询用户好友ID
    [TieTieTool tietieWithParameterMarked:TTAction_findUser dict:@{@"Mobile":self.userTel.text,@"sysPlat":@"5"} succes:^(id responseObject) {
        [self showLoading:NO];
        NSString *ID = responseObject[@"userId"];
        if (ID.length>0) {
            [TTSettingTool setObject:ID forKey:TTFriendUserID];
           
            [self gotoAndFriend];
        }else{
             [SystemDialog alert:@"该号码不是贴贴用户!"];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"findUser : error");
    }];
}
//该用户能获取到UIID 继续添加好友
- (void)gotoAndFriend
{
    NSString *friendID = [TTSettingTool objectForKey:TTFriendUserID];
        NSDictionary *dict = @{@"nowUserId":[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId,
                               @"friendUserId":friendID,
                               @"nowPhone":[TTAccountTool sharedTTAccountTool].currentAccount.userPhone,
                               @"friendPhone":_userTel.text};

    self.loginText = @"正在添加...";
    [self showLoading:YES];
        [TieTieTool tietieWithParameterMarked:TTAction_judgeUserAndRoster dict:dict succes:^(id responseObject) {
            NSString *key = [responseObject objectForKey:rspCode];
            NSString *value = [responseObject objectForKey:rspDesc];
            
            if ([key isEqualToString:rspCode_success]) {
                //添加好友成功
                for (UIViewController *controller in self.navigationController.viewControllers) {
                    if ([controller isKindOfClass:[TTFriendsViewController class]]) {
                        TTFriendsViewController *friendsVC = (TTFriendsViewController*)controller;
                        [friendsVC setupFetchedController];
                        
                        [self.navigationController popToViewController:friendsVC animated:YES];
                        [SystemDialog alert:value];

                    }
                }
            }else{
                [SystemDialog alert:value];
            }
            [self showLoading:NO];
        } fail:^(NSError *error) {
             [self showLoading:NO];
            TTLog(@"findUser : error");
        }];
}
@end
