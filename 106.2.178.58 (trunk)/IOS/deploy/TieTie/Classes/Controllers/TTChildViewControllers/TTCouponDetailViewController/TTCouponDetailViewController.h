//
//  TTCouponDetailViewController.h
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
#define kflagExitDetal @"kflagExitDetal"
@class TTCoupon,TTCreditsCoupon;
@interface TTCouponDetailViewController : TTRootViewController

@property (nonatomic, strong) id    object;
@property (nonatomic, strong) TTCoupon  *   coupon;
@property (nonatomic, strong) TTCreditsCoupon   *   creditsCoupon;
@end
