//
//  TTSetCell.m
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSetCell.h"
@interface TTSetCell()
{
    UIView *_divider;
}
@end
@implementation TTSetCell
+ (instancetype)SetCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTSetCell";
    TTSetCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    if (cell == nil) {
        cell = [[NSBundle mainBundle] loadNibNamed:ID owner:nil options:nil][0];
    }
    return cell;
}
- (void)awakeFromNib
{
    // Initialization code
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
