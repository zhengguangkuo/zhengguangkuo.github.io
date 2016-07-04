//
//  TTPickCouponViewController.h
//  Miteno
//
//  Created by wg on 14-8-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"
#import "TTFriends.h"

@interface TTPickCouponViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate,MJRefreshBaseViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UIImageView *headerImage;
@property (weak, nonatomic) IBOutlet UILabel *friendName;

@property (strong,nonatomic)TTFriends *friendInfo;

@end
