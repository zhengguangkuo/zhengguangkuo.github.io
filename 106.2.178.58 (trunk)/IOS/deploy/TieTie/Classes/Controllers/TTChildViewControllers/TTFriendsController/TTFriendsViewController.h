//
//  TTFriendsViewController.h
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"

@interface TTFriendsViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate>
@property (nonatomic, strong)UITableView    *tableView;
- (void)setupFetchedController;
@end
