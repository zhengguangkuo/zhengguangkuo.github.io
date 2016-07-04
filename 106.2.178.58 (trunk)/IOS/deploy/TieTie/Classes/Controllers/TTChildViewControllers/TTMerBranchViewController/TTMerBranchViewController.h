//
//  TTMerBranchViewController.h
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
@class TTCouponViewFrame,TTCouponDetail;
@interface TTMerBranchViewController : UITableViewController
@property (nonatomic, strong) TTCouponViewFrame *couponFrame;

@property (nonatomic, strong) TTCouponDetail *  couponDetail;

@property (nonatomic, strong) NSMutableArray    *   merdatas;

@end
