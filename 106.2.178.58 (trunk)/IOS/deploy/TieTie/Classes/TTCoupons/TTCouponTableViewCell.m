//
//  TTCouponTableViewCell.m
//  TieTie
//
//  Created by zhengguangkuo on 14-6-5.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//
#import "TTCouponTableViewCell.h"

@implementation TTCouponTableViewCell

- (void)awakeFromNib
{
    // Initialization code
    [self.sendBtn setBackgroundImage:[UIImage imageNamed:@"icon_give_normal.png"] forState:UIControlStateNormal];
    [self.sendBtn setBackgroundImage:[UIImage imageNamed:@"icon_give_selected.png"] forState:UIControlStateSelected];
}

//- (void)showStepper
//{
//    self.textStepper.Step = 1;
//    self.textStepper.Minimum = 1;
//    self.textStepper.IsEditableTextField = NO;
//    self.textStepper.hidden = NO;
//}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

//- (void)showDate:(BOOL)validity
//{
//    if (validity) {
////        [self.couponValidity setHidden:NO];
////        [self.couponDate setHidden:NO];
//    }
//    
//}

//- (void)setUnusedCell
//{
//    self.couponsCountName.text = @"数量";
//    [self.sendBtn setHidden:NO];
//}
//
//- (void)setUsedCell
//{
//    self.couponsCountName.text = @"使用数量";
//    [self.sendBtn setHidden:YES];
//}
//
//- (void)setOutDateCell
//{
//    self.couponsCountName.text = @"领取数量";
//    [self.sendBtn setHidden:YES];
//}

@end
