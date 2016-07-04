//
//  TTFriendViewController.h
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
#import "MyCoupons.h"
#import "TextStepperField.h"

@interface TTPickFriendViewController : TTRootViewController<UITableViewDataSource,UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *table;
@property (weak, nonatomic) IBOutlet UIImageView *couponImageView;
@property (weak, nonatomic) IBOutlet UILabel *couponNameLab;

@property (weak, nonatomic) IBOutlet UILabel *couponCountLab;
@property (weak, nonatomic) IBOutlet UILabel *couponValidity;
@property (weak, nonatomic) IBOutlet UIView *couponView;
@property (weak, nonatomic) IBOutlet TextStepperField *textStepper;


- (void)setDataWithMycoupons:(MyCoupons*)coupons;
@end
