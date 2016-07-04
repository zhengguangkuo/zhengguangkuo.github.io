//
//  TTFriendsDetailViewController.h
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"
#import "TTFriends.h"
@interface TTFriendsDetailViewController : TTRootViewController
@property (nonatomic, strong) TTFriends *friends;
@property (nonatomic, copy) NSString    *friendMobile;
@end
