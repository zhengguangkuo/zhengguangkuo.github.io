//
//  TTFriendsMgr.h
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
@class TTFriends;
@interface TTFriendsMgr : NSObject
singleton_for_interface(TTFriendsMgr)
- (void)addFriend:(TTFriends *)friend;
- (NSArray *)queryFriends;
- (NSArray *)queryFriendsWithCondition:(NSString *)condition;
@end
