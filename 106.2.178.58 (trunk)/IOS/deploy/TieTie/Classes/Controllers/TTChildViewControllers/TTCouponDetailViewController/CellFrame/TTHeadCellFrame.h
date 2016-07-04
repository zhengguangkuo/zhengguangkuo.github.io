//
//  TTHeadCellFrame.h
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
#define kHeadFont [UIFont systemFontOfSize:16]
#define kHeadSubFont [UIFont systemFontOfSize:10]
@class TTCouponDetail;
@interface TTHeadCellFrame : NSObject
@property (nonatomic, assign) CGRect  couTitleLabel;   //折扣信息frame
@property (nonatomic, assign) CGRect  couSubTitleLabel;
@property (nonatomic, assign) CGRect  couDisContent;   //折扣内容frame
@property (nonatomic, assign) CGFloat  cellHeight;
@property (nonatomic, strong) TTCouponDetail *headMeg;
@end
