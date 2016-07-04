//
//  TTFriendViewController.h
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
#import "TTFriend.h"

@interface TTPersonViewController : TTRootViewController

@property  (nonatomic, strong)  TTFriend* mFriend;
@property (weak, nonatomic) IBOutlet UIImageView *HeadImage;
@property (weak, nonatomic) IBOutlet UILabel *UserName;
@property (weak, nonatomic) IBOutlet UILabel *NickName;
@property (weak, nonatomic) IBOutlet UILabel *PhoneNo;
@property (weak, nonatomic) IBOutlet UIButton *AddFriend;


- (void)RefreshData;

@end
