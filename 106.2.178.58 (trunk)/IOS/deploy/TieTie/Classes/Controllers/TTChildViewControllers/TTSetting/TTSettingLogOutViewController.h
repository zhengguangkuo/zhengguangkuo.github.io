//
//  TTSettingLogOutViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-8-26.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"

@interface TTSettingLogOutViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UIImageView *image;
- (IBAction)loginBtnClicked:(id)sender;
@property (weak, nonatomic) IBOutlet UITableView *tableView;

@end
