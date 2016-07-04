//
//  TTCouponDetailCell.m
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponDetailCell.h"
#import "TTCouponMerDetail.h"
#import "TTCouponViewFrame.h"
#import "CustomItem.h"
#define kCellFrameH    44
#define kPhoneWidth 50
#define kDistanceH  20
#define kTopDis 10
#define kBtnFont [UIFont systemFontOfSize:10]
@implementation TTCouponDetailCell
+ (instancetype)couponDetailCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTCouponDetailCell";
    TTCouponDetailCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[TTCouponDetailCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:ID];
    }
    
    return cell;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {

        [self addChildViews];
        self.selectionStyle = UITableViewCellSelectionStyleNone;

    }
    return self;
}
- (void)addChildViews
{
    _phone = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.contentView addSubview:_phone];
    
    _vertical = [[UIImageView alloc] init];
    [self.contentView addSubview:_vertical];
    
    _titleLabel = [[UILabel alloc] init];
    [self.contentView addSubview:_titleLabel];
    
    _address = [[UILabel alloc] init];
    [self.contentView addSubview:_address];
  
    _distance = [CustomItem buttonWithType:UIButtonTypeCustom];
    [self.contentView addSubview:_distance];
    
    _nearDistance = [UIButton buttonWithType:UIButtonTypeCustom];
    [self.contentView addSubview:_nearDistance];
}
- (void)setCouponFrame:(TTCouponViewFrame *)couponFrame
{
    _couponFrame = couponFrame;
    
//    if ([couponFrame isKindOfClass:[TTCouponViewFrame class]]) {
        TTCouponMerDetail *detail = couponFrame.couponDetail;
        _titleLabel.numberOfLines = 0;
        _titleLabel.frame = couponFrame.titleLabel;
        _titleLabel.text = detail.merchName;
        
        _address.frame = couponFrame.address;
        _address.numberOfLines = 0;
        _address.textColor = [UIColor grayColor];
        _address.text = detail.Address;
    
        _distance.frame = couponFrame.distance;
        [_distance setTitle:[NSString stringWithFormat:@"%@km",detail.Distance] forState:UIControlStateNormal];
//        [_distance setImage:[UIImage stretchImageWithName:@"map"] forState:UIControlStateNormal];
        _distance.titleLabel.font = kBtnFont;
        [_distance setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    _distance.titleLabel.textAlignment = NSTextAlignmentRight;
//    if (detail.nearDistance.length>0) {
//        
//        _nearDistance.frame = couponFrame.nearDistance;
//        _nearDistance.titleLabel.font = [UIFont systemFontOfSize:13];
//        [_nearDistance setTitle:detail.nearDistance forState:UIControlStateNormal];
//        [_nearDistance setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
//        [_nearDistance setBackgroundImage:[UIImage imageNamed:@"one_registerBtn"] forState:UIControlStateNormal];
//    }
        _phone.frame = couponFrame.phone;
        [_phone setBackgroundImage:[UIImage imageNamed:@"icon_call_normal"] forState:UIControlStateNormal];
    [_phone setBackgroundImage:[UIImage imageNamed:@"icon_call_selected"] forState:UIControlStateSelected];
//    _phone.backgroundColor = [UIColor blackColor];
        _vertical.frame = couponFrame.vertical;
        [_vertical setBackgroundColor:white3];

//    }else{
//        self.accessoryView = nil;
//    }
}

@end
