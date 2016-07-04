//
//  TTRosterItem.h
//  Miteno
//
//  Created by zhengguangkuo on 14-8-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "XMPPJID.h"

@interface TTRosterItem : NSObject

@property (nonatomic,copy)XMPPJID *jid;
@property (nonatomic,copy)NSString *jidStr;
@property (nonatomic,copy)NSString *name;
@property (nonatomic,copy)NSString *subscription;
@property (nonatomic,copy)NSString *group;

@end
