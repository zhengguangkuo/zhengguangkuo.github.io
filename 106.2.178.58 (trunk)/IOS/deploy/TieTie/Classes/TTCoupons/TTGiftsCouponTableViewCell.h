//
//  TTGiftsCouponTableViewCell.h
//  Miteno
//
//  Created by zhengguangkuo on 14-7-31.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTGiftsCouponTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *couponImage;
@property (weak, nonatomic) IBOutlet UILabel *couponActName;
@property (weak, nonatomic) IBOutlet UILabel *couponContent;
@property (weak, nonatomic) IBOutlet UILabel *couponSender;
@property (weak, nonatomic) IBOutlet UIButton *receiveBtn;
@property (weak, nonatomic) IBOutlet UILabel *couponCount;

@end
