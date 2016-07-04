//
//  TTTopCell.h
//  Miteno
//
//  Created by wg on 14-8-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTCouponBaseCell.h"
#import "TTCouponDetail.h"
@interface TTTopCell : TTCouponBaseCell
@property (weak, nonatomic) IBOutlet UILabel * actName;      //商家名称
@property (weak, nonatomic) IBOutlet UILabel * actTypeText;  //领用
@property (nonatomic, strong) TTCouponDetail *  coupondetail;
+ (instancetype)topCellWithTableView:(UITableView *)tableView;
@end
