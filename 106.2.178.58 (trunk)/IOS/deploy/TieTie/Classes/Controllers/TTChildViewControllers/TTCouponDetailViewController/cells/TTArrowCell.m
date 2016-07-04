//
//  TTArrowCell.m
//  Miteno
//
//  Created by wg on 14-8-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTArrowCell.h"

@implementation TTArrowCell
+ (instancetype)arrowCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTArrowCell";
    TTArrowCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[NSBundle mainBundle]loadNibNamed:ID owner:nil options:nil][0];
        cell.backgroundColor = white1;
    }
    return cell;
}
- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
