//
//  TTTopCell.m
//  Miteno
//
//  Created by wg on 14-8-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTopCell.h"
#define kActDetailSpace 5
@implementation TTTopCell
+ (instancetype)topCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTTopCell";
    TTTopCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[NSBundle mainBundle]loadNibNamed:ID owner:nil options:nil][0];
        cell.backgroundColor = [UIColor clearColor];
        UIImageView *img = [[UIImageView alloc] initWithImage:[UIImage stretchImageWithName:@"graybg_b2_border_radius"]];
        cell.backgroundView = img;
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return cell;
}
- (void)setCoupondetail:(TTCouponDetail *)coupondetail
{
    _coupondetail = coupondetail;
    _actName.text = coupondetail.actName;
    //是领用还是派发 领用:0  派发:2  积分 3 显示日期
    if ([coupondetail.sendType isEqualToString:@"0"]) {
        _actTypeText.textColor = [UIColor grayColor];
        self.actTypeText.text = [NSString stringWithFormat:@"已经领用%@张",coupondetail.issuedCnt];
    }else if([coupondetail.sendType isEqualToString:@"3"]){
        self.actTypeText.text = coupondetail.issuedCnt;
        self.actTypeText.font = [UIFont systemFontOfSize:9];
    }else{

        [self.actTypeText setHidden:YES];
    }
}
- (void)layoutSubviews
{
    [super layoutSubviews];
    CGRect frame = _actName.frame;
    frame.size.width+=100;
    _actName.frame = frame;
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
- (void)setFrame:(CGRect)frame
{
    frame.origin.x = kActDetailSpace;
    frame.size.width -= 2*kActDetailSpace;
    [super setFrame:frame];
}
@end
