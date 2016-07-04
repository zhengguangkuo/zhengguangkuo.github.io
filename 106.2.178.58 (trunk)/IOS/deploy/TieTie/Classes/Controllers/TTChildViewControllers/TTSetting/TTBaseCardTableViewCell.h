//
//  TTBaseCardTableViewCell.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTBaseCardTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *cardFaceImageView;
@property (weak, nonatomic) IBOutlet UILabel *cardName;
@property (weak, nonatomic) IBOutlet UILabel *cardType;
@property (weak, nonatomic) IBOutlet UILabel *cardCode;
@property (weak, nonatomic) IBOutlet UIButton *baseCardSetting;
@property (weak, nonatomic) IBOutlet UIImageView *baseCardIcon;
@property (weak, nonatomic) IBOutlet UISwitch *bindAbleSwitch;


- (void)setbindCardIconFlag:(BOOL)isUnbind;


@end
