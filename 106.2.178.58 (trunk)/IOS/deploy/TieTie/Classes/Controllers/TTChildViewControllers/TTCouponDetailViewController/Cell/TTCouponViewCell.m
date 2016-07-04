//
//  TTCouponViewCell.m
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponViewCell.h"

@implementation TTCouponViewCell
+ (instancetype)couponViewCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTCouponViewCell";
    TTCouponViewCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[TTCouponViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:ID];
    }
    
    return cell;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
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
//- (void)setFrame:(CGRect)frame
//{
//    frame.origin.x = kActDetailSpace;
//    frame.size.width -= 2*kActDetailSpace;
//    [super setFrame:frame];
//}
@end
