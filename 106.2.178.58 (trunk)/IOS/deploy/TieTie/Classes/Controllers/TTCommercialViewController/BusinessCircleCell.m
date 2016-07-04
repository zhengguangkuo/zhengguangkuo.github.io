//
//  BusinessCircleCell.m
//  BusinessCircleViewController
//
//  Created by APPLE on 14-6-4.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "BusinessCircleCell.h"
@interface BusinessCircleCell()
{
    UIView *_divider;
}
@end
@implementation BusinessCircleCell
+ (instancetype)businessCircleCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"BusinessCircleCell";
    BusinessCircleCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"BusinessCircleCell" owner:self options:nil] lastObject];

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
- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(void)setBusinessInformationWithCoupon:(TTComercialCoupon*)coupon
{
    self.BusinessImageView.layer.cornerRadius = 5;
    self.BusinessImageView.layer.masksToBounds = YES;
    [self.BusinessImageView setImageWithURL:[NSURL URLWithString:coupon.image] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    
    self.BusinessName.text = coupon.merchName;
    self.activityName.text = coupon.merchTag;
    self.adressName.text = coupon.Address;
    self.range.text = [NSString stringWithFormat:@"%.2fkm",[coupon.Distance floatValue]];

    _quanImageView = [[UIImageView alloc]initWithFrame:CGRectMake(230, 6, 18, 20)];
    _zheImageView = [[UIImageView alloc]initWithFrame:CGRectMake(260, 6, 18, 20)];
    _fenImageView = [[UIImageView alloc]initWithFrame:CGRectMake(290, 6, 18, 20)];
    [_quanImageView setImage:[UIImage imageNamed:@"icon_quan.png"]];
    [_zheImageView setImage:[UIImage imageNamed:@"icon_zhe.png"]];
    [_fenImageView setImage:[UIImage imageNamed:@"icon_fen.png"]];
    [self addSubview:_quanImageView];
    [self addSubview:_zheImageView];
    [self addSubview:_fenImageView];
    
    if (![coupon.integralState integerValue]) {
        _quanImageView.frame = _zheImageView.frame;
        _zheImageView.frame = _fenImageView.frame;
        [_fenImageView removeFromSuperview];
    }
    if (![coupon.discountState integerValue]) {
        _quanImageView.frame = _zheImageView.frame;
        [_zheImageView removeFromSuperview];
    }
    if (![coupon.ticketState integerValue]) {
        [_quanImageView removeFromSuperview];
    }
}
@end
