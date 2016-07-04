//
//  TTCustomerServiceViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-8-28.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"
#import <MessageUI/MFMailComposeViewController.h>

@interface TTCustomerServiceViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,MFMailComposeViewControllerDelegate>

@property (weak, nonatomic) IBOutlet UILabel *versionLab;
@end
