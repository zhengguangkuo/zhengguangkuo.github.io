//
//  TTMyGiftsViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTMyGiftsViewController : TTRootViewController<UITableViewDelegate,UITableViewDataSource,MJRefreshBaseViewDelegate,UIGestureRecognizerDelegate>
@property (weak, nonatomic) IBOutlet UITableView *table;
@property (weak, nonatomic) IBOutlet UISegmentedControl *segment;

- (IBAction)segmentedClicked:(id)sender;
- (void)updateTableAfterHandleOrder:(NSString*)orderNo status:(NSString*)status;
@end
