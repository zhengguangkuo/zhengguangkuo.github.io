//
//  OrderInfoTableViewCell.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@interface OrderInfoTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *cuponsImage;
@property (weak, nonatomic) IBOutlet UILabel *cuponsName;
@property (weak, nonatomic) IBOutlet UILabel *cuponsCentent;
@property (weak, nonatomic) IBOutlet UILabel *cuponsMoney;
@property (weak, nonatomic) IBOutlet UILabel *cuponsValidity;
@property (weak, nonatomic) IBOutlet UIButton *cuponsDateCount;
@property (weak, nonatomic) IBOutlet UILabel *cuponsMoneyDelete;

@end
