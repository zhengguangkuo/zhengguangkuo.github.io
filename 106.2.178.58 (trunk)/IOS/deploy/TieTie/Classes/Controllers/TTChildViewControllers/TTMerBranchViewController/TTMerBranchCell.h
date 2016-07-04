//
//  TTMerBranchCell.h
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponDetailCell.h"

@interface TTMerBranchCell : TTCouponDetailCell
@property (nonatomic, strong)NSIndexPath    * indexPath;
+ (instancetype)merBranchCellWithTableView:(UITableView *)tableView;
@end
