//
//  TTBaseCardTableViewCell.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBaseCardTableViewCell.h"

@implementation TTBaseCardTableViewCell

- (void)awakeFromNib
{
    // Initialization code
//    [self.baseCardSetting setBackgroundImage:[UIImage imageNamed:@"btn_bg"] forState:UIControlStateNormal];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)setbindCardIconFlag:(BOOL)isUnbind
{
    if (isUnbind) {
        [self.baseCardSetting setHidden:NO];
        [self.baseCardSetting setTitle:@"基卡设置" forState:UIControlStateNormal];
        [self.baseCardSetting setTitleColor:[UIColor darkGrayColor] forState:UIControlStateNormal];
        [self.baseCardIcon setHidden:YES];
    } else {
//        [self.baseCardSetting setTitle:@"编辑" forState:UIControlStateNormal];
//        [self.baseCardSetting setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
        [self.baseCardSetting setHidden:YES];
        [self.baseCardIcon setHidden:NO];
    }
}

@end
