//
//  TTCouponDetailCell.h
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponViewCell.h"
@class CustomItem,TTCouponViewFrame;
@interface TTCouponDetailCell : TTCouponViewCell

@property (nonatomic, strong) TTCouponViewFrame *couponFrame;

@property (nonatomic, strong)UILabel      *   titleLabel;
@property (nonatomic, strong)UILabel      *   address;
@property (nonatomic, strong)UIButton     *   phone;
@property (nonatomic, strong)CustomItem   *   distance;
@property (nonatomic, strong)UIButton     *   nearDistance;
@property (nonatomic, strong)UIImageView  *  vertical;//纵向
+ (instancetype)couponDetailCellWithTableView:(UITableView *)tableView;

@end
