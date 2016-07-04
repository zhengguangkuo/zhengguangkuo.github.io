//
//  TTEditUserInfoViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-8-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
typedef void (^editValueBlock)(NSString *value);
@interface TTEditUserInfoViewController : TTRootViewController
@property (weak,nonatomic) UILabel *contentLable;
@property (weak, nonatomic) IBOutlet UITextField *editValueTF;
@property (strong,nonatomic)editValueBlock block;
@property (strong,nonatomic)NSString * mytitle;
@end
