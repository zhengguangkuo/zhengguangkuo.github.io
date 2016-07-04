//
//  OrderInfoWithoutPriceTableViewCell.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OrderInfoWithoutPriceTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *couponImageUrl;
@property (weak, nonatomic) IBOutlet UILabel *couponName;
@property (weak, nonatomic) IBOutlet UILabel *couponContent;
@property (weak, nonatomic) IBOutlet UILabel *couponValidity;

@end
