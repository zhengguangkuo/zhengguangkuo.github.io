//
//  TTFriendViewController.h
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTGiftConpusViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong)   NSString* UserID;

@property (nonatomic, strong)   NSString* FriendName;

@end
