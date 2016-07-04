//
//  TTCouponViewFrame.h
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
@class TTCouponDetail,TTCouponMerDetail;
@interface TTCouponViewFrame : NSObject

//@property (nonatomic, strong) TTCouponDetail *couponDetail;
@property (nonatomic, strong) TTCouponMerDetail *couponDetail;
@property (nonatomic, assign) CGFloat  cellHeight;

@property (nonatomic, assign) CGRect  titleLabel;
@property (nonatomic, assign) CGRect  address;
@property (nonatomic, assign) CGRect  distance;
@property (nonatomic, assign) CGRect  nearDistance;
@property (nonatomic, assign) CGRect  vertical;
@property (nonatomic, assign) CGRect  phone;

//- (CGSize)sizeWithText:(NSString *)text;
@end
