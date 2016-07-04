//
//  TTTieFenViewController.h
//  TTTieFenViewController
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
@interface TTTieFenViewController :TTRootViewController <UITableViewDelegate,UITableViewDataSource>
@property(nonatomic,retain)UITableView * listTableView;
@end
