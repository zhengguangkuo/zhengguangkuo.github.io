//
//  TTCouponBaseCell.h
//  Miteno
//
//  Created by wg on 14-8-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
@class TTTopItem;
@interface TTCouponBaseCell : UITableViewCell
@property (nonatomic, strong)   TTTopItem   *topItem;
+ (instancetype)couponBaseCellWithTableView:(UITableView *)tableView;
@end
