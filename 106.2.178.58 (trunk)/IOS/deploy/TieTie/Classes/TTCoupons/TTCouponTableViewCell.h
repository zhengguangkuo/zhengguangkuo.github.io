//
//  TTCouponTableViewCell.h
//  TieTie
//
//  Created by zhengguangkuo on 14-6-5.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TextStepperField.h"

@interface TTCouponTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *icon;
@property (weak, nonatomic) IBOutlet UILabel *couponName;
@property (weak, nonatomic) IBOutlet UILabel *couponContent;
@property (weak, nonatomic) IBOutlet UILabel *couponCount;
@property (weak, nonatomic) IBOutlet UILabel *couponValidity;
@property (weak, nonatomic) IBOutlet UILabel *couponValidityShow;
@property (weak, nonatomic) IBOutlet UIButton *sendBtn;

//@property (weak, nonatomic) IBOutlet UILabel *couponsCountName;
//@property (weak, nonatomic) IBOutlet TextStepperField *textStepper;

//- (void)showDate:(BOOL)validity;
//- (void)setUnusedCell;
//- (void)setUsedCell;
//- (void)setOutDateCell;
//- (void)showStepper;
@end
