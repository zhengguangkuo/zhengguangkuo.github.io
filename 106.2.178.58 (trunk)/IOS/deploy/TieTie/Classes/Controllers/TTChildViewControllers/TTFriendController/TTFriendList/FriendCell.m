//
//  ImageTableViewCell.m
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import "FriendCell.h"
#import "UIImageView+DispatchLoad.h"
#import "UIView(category).h"
#import "TTFriend.h"
#import "TTGiftConpusViewController.h"
#import "TTNickNameViewController.h"

@implementation FriendCell


@synthesize  headpic;
@synthesize  giftButton;
@synthesize  editButton;
@synthesize  friendNick;
@synthesize  mFriend;

- (id)initCustom
{
    self = [super init];
    if(self)
    {
        self = [[[NSBundle mainBundle] loadNibNamed:@"FriendCell" owner:self options:nil] lastObject];
        self.mFriend = [[TTFriend alloc] init];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setBackgroundColor:[UIColor clearColor]];
        [self.contentView setBackgroundColor:[UIColor clearColor]];
        [self ViewWithBorder:[UIColor clearColor]];
        [self.giftButton ViewBorder:[UIColor orangeColor] Radius:6.0f];
        
        [self.giftButton addTarget:self action:@selector(gift) forControlEvents:UIControlEventTouchUpInside];
        
        [self.editButton addTarget:self action:@selector(edit) forControlEvents:UIControlEventTouchUpInside];
    }
    return self;
}

- (void)gift
{
    NSLog(@"gift");
    TTGiftConpusViewController* GiftVCtr = [[TTGiftConpusViewController alloc] init];
    UIViewController* VCtr = [self viewController];
    [VCtr.navigationController pushViewController:GiftVCtr animated:NO];
    [GiftVCtr setUserID:self.mFriend.userID];
    [GiftVCtr setFriendName:self.mFriend.userName];
}


- (void)edit
{
    NSLog(@"edit");
    TTNickNameViewController* NickVCtr =
    [[TTNickNameViewController alloc] init];
    UIViewController* VCtr = [self viewController];
    [VCtr.navigationController pushViewController:NickVCtr animated:NO];
}

-(void)SetObject:(TTFriend*)object
{
    [self CleanData];
    self.mFriend = object;
    [self.friendNick setText:self.mFriend.nickName];
    NSLog(@"set friend object");
    [self.headpic setImageFromUrl:self.mFriend.headPic];
}


-(void)CleanData
{
    [self.headpic setImage:[UIImage imageNamed:@"load.png"]];
    [self.friendNick setText:@"贴友昵称"];
}

@end
