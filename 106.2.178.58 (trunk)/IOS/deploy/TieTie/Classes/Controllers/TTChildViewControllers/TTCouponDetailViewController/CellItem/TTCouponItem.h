//
//  TTConponItem.h
//  Miteno
//
//  Created by wg on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//


#import <Foundation/Foundation.h>

@interface TTCouponItem : NSObject
@property (nonatomic, copy) NSString   *icon;
@property (nonatomic, copy) NSString   *title;
@property (nonatomic, copy) NSString   *content;
@property (nonatomic, copy) void (^operation)();

@property (nonatomic, assign) Class  showVCClass;
+ (id)couponItemWithIcon:(NSString *)icon title:(NSString *)title;
//内容部确定
+ (id)couponItemWithTitle:(NSString *)title;
@end
