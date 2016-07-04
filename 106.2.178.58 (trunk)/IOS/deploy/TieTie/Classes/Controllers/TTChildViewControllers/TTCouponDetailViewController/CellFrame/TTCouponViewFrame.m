//
//  TTCouponViewFrame.m
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponViewFrame.h"
#import "TTCouponMerDetail.h"
#define kFont [UIFont systemFontOfSize:17]
#define kLeftDis 15
#define kTopDis 5
#define kPhoneWidth 50
#define kVertical   0.5
#define cotentWidth [UIScreen mainScreen].bounds.size.width - 2 * kLeftDis - kVertical - kPhoneWidth-kLeftDis
//距离按钮 宽高
#define kDistanceW 80
#define kDistanceH 30
@implementation TTCouponViewFrame
 - (void)setCouponDetail:(TTCouponMerDetail *)couponDetail
{
    _couponDetail = couponDetail;
  CGFloat cellWidth = [UIScreen mainScreen].bounds.size.width;
    
    //标题
    CGSize titileSize = [self sizeWithText:couponDetail.merchName];
    _titleLabel = (CGRect){ {kLeftDis, kTopDis}, titileSize};
    
    //地址
    CGSize addressSize = [self sizeWithText:couponDetail.Address];
    _address = (CGRect){ {kLeftDis, CGRectGetMaxY(_titleLabel)+kTopDis}, addressSize};
   
    _phone = CGRectMake(cellWidth-40-kLeftDis,kTopDis,40,47);
    //距离
    _distance = CGRectMake(_phone.origin.x-kLeftDis+kDistanceW-kLeftDis+3,CGRectGetMaxY(_phone),_phone.size.width,CGRectGetMaxY(_address)/2);

    //最近距离
//    if (couponDetail.nearDistance.length>0) {
//        _nearDistance = CGRectMake(kLeftDis+_distance.size.width+5,_distance.origin.y+10/2,kDistanceW-15, kDistanceH-10);
//    }
  
    //总高度
    CGFloat cellH = CGRectGetMaxY(_distance)+5;

    //按钮
//    _phone = CGRectMake(cellWidth-kPhoneWidth,kTopDis,kPhoneWidth,cellH-10);
    //竖线
//    _vertical = CGRectMake(_phone.origin.x-0.5,2*kTopDis, 0.5,cellH-4*kTopDis);
    
    
    _cellHeight = cellH;
    
}
- (CGSize)sizeWithText:(NSString *)text
{
    return [text sizeWithFont:kFont constrainedToSize:CGSizeMake(cotentWidth,MAXFLOAT)];
}

@end
