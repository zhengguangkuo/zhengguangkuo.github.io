//
//  ImageTableViewCell.m
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import "PickCell.h"
//#import "UIImageView+DispatchLoad.h"
#import "UIView(category).h"
#import "TTFriend.h"
#import "UIColor+Extension.h"

@implementation PickCell

@synthesize  pickButton;
@synthesize  HeadImage;
@synthesize  FriendName;
@synthesize  mFriend;
@synthesize  mRow;
@synthesize delegate;


- (id)initCustom
{
    return [[[NSBundle mainBundle] loadNibNamed:@"PickCell" owner:self options:nil] lastObject];

}

- (void)awakeFromNib
{
    self.mFriend = [[TTPickFriend alloc] init];
    self.selectionStyle = UITableViewCellSelectionStyleNone;
    [self setBackgroundColor:[UIColor colorWithHex:0xf9f9f9]];
    [self.contentView setBackgroundColor:[UIColor clearColor]];
    [self ViewWithBorder:[UIColor clearColor]];
    
    [self.pickButton addTarget:self action:@selector(pickFriend) forControlEvents:UIControlEventTouchUpInside];

}

- (void)ChangeState
{
    if(self.mFriend.bCheckFlag)
    {
       [self.mFriend setBCheckFlag:FALSE];
    }
    else
    {
       [self.mFriend setBCheckFlag:TRUE];
    }
}

- (void)pickFriend
{
    [self ChangeState];
    [self DrawCheckBox];
    if([self.delegate respondsToSelector:@selector(checkSelected:path:)])
    [self.delegate checkSelected:self.mFriend.bCheckFlag path:self.mRow];
}



- (void)SetObject:(TTPickFriend*)object nRowIndex:(int)Value
{
    [self CleanData];
    self.mFriend = object;
    self.mRow = Value;
    [self.FriendName setText:self.mFriend.userName];
    [self DrawCheckBox];
//    [self.HeadImage setImageFromUrl:self.mFriend.headPic];
    self.HeadImage.image = object.HeaderImage;
}


- (void)DrawCheckBox
{
    NSString* ImageName = ((self.mFriend.bCheckFlag==TRUE)?@"u115":@"u112");
    [self.pickButton setBackgroundImage:[UIImage imageNamed:ImageName] forState:UIControlStateNormal];
}



- (void)CleanData
{
    [self.HeadImage setImage:[UIImage imageNamed:@"coupon_normal"]];
    [self.FriendName setText:@"贴友昵称"];
    [self.pickButton setBackgroundImage:[UIImage imageNamed:@"u112.png"] forState:UIControlStateNormal];
}

@end
