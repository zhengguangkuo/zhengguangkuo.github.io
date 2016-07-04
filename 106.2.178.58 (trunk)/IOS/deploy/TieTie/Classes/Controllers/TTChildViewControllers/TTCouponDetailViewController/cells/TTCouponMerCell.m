//
//  TTCouponMerCell.m
//  Miteno
//
//  Created by wg on 14-8-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponMerCell.h"

@implementation TTCouponMerCell
+ (instancetype)couponMerCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTCouponMerCell";
    TTCouponMerCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[NSBundle mainBundle]loadNibNamed:ID owner:nil options:nil][0];
//        cell.backgroundColor = [UIColor clearColor];
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

- (void)setCouponMer:(TTCouponMerDetail *)couponMer
{
    _couponMer  =   couponMer;
    
    _merName.text = couponMer.merchName;
    _address.text = couponMer.Address;
    if ([couponMer.Distance isEqualToString:@""]||couponMer.Distance == nil) {
        _distance.hidden = YES;
    }else{
        NSString *dis = couponMer.Distance;
//        if (dis.length>4) {
//           dis = [couponMer.Distance substringWithRange:NSMakeRange(0, 4)];
//        }
        _distance.text = [NSString stringWithFormat:@"%@km",dis];
//        [NSString stringWithFormat:@"%.1fkm",[dis floatValue]/1000];
    }
}
@end
