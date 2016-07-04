//
//  TTTieFenDetailTableViewCell.m
//  Miteno
//
//  Created by APPLE on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTieFenDetailTableViewCell.h"

@implementation TTTieFenDetailTableViewCell

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setTieFenDetailWithDic:(NSDictionary*)dic
{
    _BusinessName.text = dic[@"merchName"];
    _tranType.text = dic[@"tranType"];
    _cPoint.text = dic[@"cPoint"];
    _tranTime.text = dic[@"tranTime"];
}
@end
