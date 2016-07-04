//
//  TTTieFenCell.m
//  TTTieFenViewController
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "TTTieFenCell.h"

@implementation TTTieFenCell

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];
    // Configure the view for the selected state
}
-(void)setCellWithDic:(NSDictionary*)dic
{
    _Namelb.text = dic[@"orgName"];
    _Fenlb.text = dic[@"pointBalance"];
    [_TubiaoImageView setImageWithURL:[NSURL URLWithString:dic[@"picPath"]] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
}
@end
