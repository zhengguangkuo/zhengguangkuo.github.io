//
//  TTMoreCell.h
//  Miteno
//
//  Created by wg on 14-8-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTCouponBaseCell.h"
#import "TTArrowItem.h"
@interface TTMoreCell : TTCouponBaseCell

@property (weak, nonatomic) IBOutlet UILabel *moreTitle;
@property (strong, nonatomic) TTArrowItem   *   arrowItem;
+ (instancetype)moreCellWithTableView:(UITableView *)tableView;
@end
