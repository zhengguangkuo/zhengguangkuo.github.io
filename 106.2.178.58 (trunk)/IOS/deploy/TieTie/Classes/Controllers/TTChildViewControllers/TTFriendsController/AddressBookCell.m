//
//  AddressBookCell.m
//  Miteno
//
//  Created by wg on 14-8-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "AddressBookCell.h"

@implementation AddressBookCell
+ (instancetype)AddressBookCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"AddressBookCell";
    AddressBookCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    if (cell == nil) {
        cell = [[NSBundle mainBundle] loadNibNamed:ID owner:nil options:nil][0];
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
