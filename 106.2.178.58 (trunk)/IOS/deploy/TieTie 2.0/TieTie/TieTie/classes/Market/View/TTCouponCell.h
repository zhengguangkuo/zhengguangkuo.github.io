//
//  TTCouponCell.h
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
@class TTCoupon,TTCreditsCoupon;
@interface TTCouponCell : UITableViewCell
@property (nonatomic, strong) TTCoupon *coupon;
@property (nonatomic, strong) TTCreditsCoupon   *creditsCoupon;
+ (instancetype)couponCellWithTableView:(UITableView *)tableView;
@end
