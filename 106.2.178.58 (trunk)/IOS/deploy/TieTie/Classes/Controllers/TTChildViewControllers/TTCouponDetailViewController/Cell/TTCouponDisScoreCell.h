//
//  TTCouponDetailCell.h
//  Miteno
//
//  Created by wg on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponViewCell.h"

@class TTCouponItem;
@interface TTCouponDisScoreCell : TTCouponViewCell
@property (nonatomic, strong) TTCouponItem   *item;
+ (instancetype)couponDisScoreCellWithTableView:(UITableView *)tableView;
@end
