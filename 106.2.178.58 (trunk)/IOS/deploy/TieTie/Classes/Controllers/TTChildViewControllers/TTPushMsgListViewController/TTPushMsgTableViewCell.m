//
//  TTPushMsgTableViewCell.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-30.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPushMsgTableViewCell.h"
#import "UIColor+Extension.h"

@implementation TTPushMsgTableViewCell

- (void)awakeFromNib
{
//    // Initialization code
//    [self.showDetailBtn setTintColor:[UIColor colorWithHex:0x767786]];
//    [self.showDetailBtn setBackgroundColor:[UIColor colorWithHex:0xf9f9f9]];

    [self.textLab setTextColor:[UIColor colorWithHex:0x383a4e]];
    
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];
    
    // Configure the view for the selected state
}
@end
