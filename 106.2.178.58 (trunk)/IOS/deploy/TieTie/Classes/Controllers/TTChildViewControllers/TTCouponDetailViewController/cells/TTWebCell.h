//
//  TTWebCell.h
//  Miteno
//
//  Created by wg on 14-8-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTCouponBaseCell.h"
@interface TTWebCell : TTCouponBaseCell
+ (instancetype)webCellWithTableView:(UITableView *)tableView;
- (CGRect)setContentText:(NSString *)text;
@end
