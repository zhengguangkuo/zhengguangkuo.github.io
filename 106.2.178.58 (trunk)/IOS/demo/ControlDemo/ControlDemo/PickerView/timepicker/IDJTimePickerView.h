//
//  时间选择器
//  IDJTimePickerView.h
//
//  本类演示了滚动条、可视区域的行数都是可以任意设置的，选择条看起来比较大，因为图片没有更换为合适高度的图片。
//
//  Created by Lihaifeng on 11-12-2, QQ:61673110.
//  Copyright (c) 2011年 www.idianjing.com. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "IDJPickerView.h"
@class IDJCalendar,IDJPickerView;
@protocol IDTimePickerViewDelegate <NSObject>
//通知使用这个控件的类，用户选取的日期
- (void)newChangeValue:(IDJCalendar *)cal;
@end
@interface IDJTimePickerView : UIView

@property (nonatomic, retain) IDJPickerView *picker;
@property (nonatomic, retain) IDJCalendar * calendar;
@property (nonatomic, retain) NSArray * hours;
@property (nonatomic, retain) NSArray * minutes;
@property (nonatomic, retain) NSArray * seconds;

@property (nonatomic, assign) id<IDTimePickerViewDelegate> delegate;
@end

