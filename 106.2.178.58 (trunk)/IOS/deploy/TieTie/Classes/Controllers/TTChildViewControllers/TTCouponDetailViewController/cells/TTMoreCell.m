//
//  TTMoreCell.m
//  Miteno
//
//  Created by wg on 14-8-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMoreCell.h"
#import "TTArrowItem.h"
@implementation TTMoreCell
+ (instancetype)moreCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTMoreCell";
    TTMoreCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[NSBundle mainBundle]loadNibNamed:ID owner:nil options:nil][0];
    }
    return cell;
}
- (void)setArrowItem:(TTArrowItem *)arrowItem
{
    _arrowItem = arrowItem;
    _moreTitle.text= _arrowItem.arrowTitle;
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
