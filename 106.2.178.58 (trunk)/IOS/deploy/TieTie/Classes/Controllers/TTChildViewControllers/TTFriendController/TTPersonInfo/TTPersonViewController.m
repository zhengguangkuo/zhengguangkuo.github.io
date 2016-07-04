//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPersonViewController.h"
#import "TTNickNameViewController.h"
#import "UIImage(addition).h"
#import "UIView(category).h"
#import "UIButton(addtion).h"
#import "TTFriend.h"


@interface TTPersonViewController ()

- (void)ParseJsonData: (NSString*)JsonData;

@end


@implementation TTPersonViewController

@synthesize mFriend;
@synthesize HeadImage;
@synthesize UserName;
@synthesize NickName;
@synthesize PhoneNo;
@synthesize AddFriend;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"详细资料";
        self.mFriend = [[TTFriend alloc] init];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor colorWithRed:0.9 green:0.9 blue:0.9 alpha:1]];
    [self NavigationViewBackBtn];
    [self.AddFriend ViewBorder:[UIColor clearColor] Radius:10.0f];
    [self.AddFriend addTarget:self action:@selector(RequestData) forControlEvents:UIControlEventTouchUpInside];
    [self NavigationRightButtons];
    
}


- (void)NavigationRightButtons
{
    UIButton* edit = [UIButton ButtonWithParms:[UIColor clearColor] title:@"" bgnormal:[UIImage imageNamed:@"uz25.png"] imgHighlight:nil target:self action:@selector(UpdateNickName)];
    [edit setFrame:CGRectMake(260, 7, 20, 20)];
    NSArray*  temArray = [NSArray arrayWithObjects:edit, nil];
    [self SetNaviationRightButtons:temArray];
}


- (void)UpdateNickName
{
    TTNickNameViewController* NickVCtr =
    [[TTNickNameViewController alloc] init];
    [self.navigationController pushViewController:NickVCtr animated:NO];
}

- (void)ClearnData
{
    [self.UserName setText:@"暂无"];
    [self.NickName setText:@"暂无"];
    [self.PhoneNo setText:@"暂无"];
    [self.HeadImage setImage:[UIImage imageNamed:@"load.png"]];
}


- (void)RefreshData
{
    [self ClearnData];
    if(!IsEmptyString(self.mFriend.userName))
    [self.UserName setText:self.mFriend.userName];
    
    if(!IsEmptyString(self.mFriend.nickName))
    [self.NickName setText:self.mFriend.nickName];
    
    if(!IsEmptyString(self.mFriend.mobile))
    [self.PhoneNo setText:self.mFriend.mobile];
    
    if(!IsEmptyString(self.mFriend.headPic))
    [self.HeadImage setImageFromUrl:self.mFriend.headPic];
}


- (void)RequestData
{
    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_TT_Add_Friend];
    
    NSDictionary* dic = [NSDictionary dictionaryWithObjectsAndKeys:
                         @"Ada",@"KeyWord",
                         nil];
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:dic
                                                       withHud:YES];
    [tempservice setDelegate:self];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"data = %@",data);
         [self ParseJsonData:data];
         [self.view makeToast:@"添加成功!"];
     }
     ];
    
    [tempservice startOperation];
}








- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
    [self  RefreshData];
}


- (void)ParseJsonData: (NSString*)JsonData
{
    



}

@end
