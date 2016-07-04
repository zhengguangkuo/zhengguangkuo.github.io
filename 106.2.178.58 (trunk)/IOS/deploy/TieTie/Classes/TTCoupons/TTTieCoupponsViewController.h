//
//  TTTieCoupponsViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
@interface TTTieCoupponsViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,MJRefreshBaseViewDelegate>
@property (weak, nonatomic) IBOutlet UISegmentedControl *segmentControl;
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UIButton *sendBtn;
@property (weak, nonatomic) IBOutlet UIButton *cancelBtn;

- (IBAction)sendCoupons:(id)sender;
- (IBAction)cancelBtnClicked:(id)sender;

- (IBAction)segmentClicked:(id)sender;
- (void)updateTableAfterSend:(NSInteger)count;
- (void)updateUnusedCoupons:(NSString *)actId;
@end
