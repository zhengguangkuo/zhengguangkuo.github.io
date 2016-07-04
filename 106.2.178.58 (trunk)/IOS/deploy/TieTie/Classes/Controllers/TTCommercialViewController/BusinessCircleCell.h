//
//  BusinessCircleCell.h
//  BusinessCircleViewController
//
//  Created by APPLE on 14-6-4.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "TTComercialCoupon.h"
@interface BusinessCircleCell : UITableViewCell
+ (instancetype)businessCircleCellWithTableView:(UITableView *)tableView;
@property (weak, nonatomic) IBOutlet UIImageView *BusinessImageView;
@property (weak, nonatomic) IBOutlet UILabel *activityName;
@property (weak, nonatomic) IBOutlet UILabel *BusinessName;
@property (weak, nonatomic) IBOutlet UILabel *adressName;
@property (weak, nonatomic) IBOutlet UILabel *range;
@property (nonatomic,retain)UIImageView * quanImageView;
@property (nonatomic,retain)UIImageView * zheImageView;
@property (nonatomic,retain)UIImageView * fenImageView;
-(void)setBusinessInformationWithCoupon:(TTComercialCoupon*)coupon;
@end
