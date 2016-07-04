//
//  TTArrowCell.h
//  Miteno
//
//  Created by wg on 14-8-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTCouponBaseCell.h"
@interface TTArrowCell : TTCouponBaseCell
@property (weak, nonatomic) IBOutlet UILabel *arrowTitle;
+ (instancetype)arrowCellWithTableView:(UITableView *)tableView;
@end
