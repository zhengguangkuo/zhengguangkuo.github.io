//
//  TTOrderViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "OrderInfo.h"
#import "TTRootViewController.h"

@interface TTOrderViewController : TTRootViewController<UITableViewDelegate,UITableViewDataSource>

@property (weak, nonatomic) IBOutlet UILabel *senderName;
@property (weak, nonatomic) IBOutlet UILabel *status;
@property (weak, nonatomic) IBOutlet UILabel *couponsNum;
@property (weak, nonatomic) IBOutlet UILabel *orderNo;
@property (weak, nonatomic) IBOutlet UILabel *addTime;
@property (weak, nonatomic) IBOutlet UILabel *recivedTime;
//@property (nonatomic,strong)OrderInfo *orderInfo;
@property (weak, nonatomic) IBOutlet UITableView *table;
@property (nonatomic,strong) NSString * orderNoStr;
@property (weak, nonatomic) IBOutlet UIButton *recieveBtn;
@property (weak, nonatomic) IBOutlet UIButton *refuseBtn;

- (IBAction)recieveBtnClicked:(id)sender;

- (IBAction)refuseBtnClicked:(id)sender;

@end
