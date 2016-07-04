//
//  TTHeadCellFrame.m
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTHeadCellFrame.h"
#import "TTCouponDetail.h"
#define kLeftDis 15
#define kHeadTopDis 7

#define cotentWidth [UIScreen mainScreen].bounds.size.width-20-kLeftDis

#define kCouSubTitleLabelW  150
@implementation TTHeadCellFrame
- (void)setHeadMeg:(TTCouponDetail *)headMeg
{
    _headMeg = headMeg;
    CGFloat cellWidth = [UIScreen mainScreen].bounds.size.width-10;
    
    //折扣
//    CGSize titileSize = [self sizeWithText:_headMeg.couTitleLabel];
//    _couTitleLabel = (CGRect){ {kLeftDis, kHeadTopDis}, titileSize};
    _couTitleLabel = CGRectMake(kLeftDis, kHeadTopDis, 280, 44);
    //子标题  已领取
//    CGSize  subSize = [self sizeWithText:_headMeg.couSubTitleLabel];
    _couSubTitleLabel = CGRectMake(cellWidth-kLeftDis-kCouSubTitleLabelW, kHeadTopDis, kCouSubTitleLabelW, 44);
    
    //折扣内容
    CGSize contentSize = [self sizeWithText:_headMeg.actDetail];
    _couDisContent = (CGRect){ {kLeftDis,_couTitleLabel.size.height+kHeadTopDis},contentSize};

    //总高度
    CGFloat cellH = CGRectGetMaxY(_couDisContent)+kHeadTopDis;

    _cellHeight = cellH;
    

}
- (CGSize)sizeWithText:(NSString *)text
{
    return [text sizeWithFont:kHeadFont constrainedToSize:CGSizeMake(cotentWidth,MAXFLOAT)];
}
@end
