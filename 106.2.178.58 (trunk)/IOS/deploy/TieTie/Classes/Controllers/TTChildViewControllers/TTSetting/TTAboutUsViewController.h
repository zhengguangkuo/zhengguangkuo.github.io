//
//  TTAboutUsViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTAboutUsViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,UIActionSheetDelegate,UIAlertViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *table;

@end
