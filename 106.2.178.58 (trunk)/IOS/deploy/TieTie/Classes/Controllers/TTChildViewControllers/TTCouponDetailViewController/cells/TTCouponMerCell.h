//
//  TTCouponMerCell.h
//  Miteno
//
//  Created by wg on 14-8-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTCouponBaseCell.h"
#import "TTCouponMerDetail.h"
@interface TTCouponMerCell : TTCouponBaseCell
@property (weak, nonatomic) IBOutlet UILabel * merName;     //使用商家名称
@property (weak, nonatomic) IBOutlet UILabel * address;     //地址
@property (weak, nonatomic) IBOutlet UILabel * distance;    //距离

+ (instancetype)couponMerCellWithTableView:(UITableView *)tableView;
@property (strong, nonatomic)   TTCouponMerDetail   *   couponMer;  
@end
