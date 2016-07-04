//
//  循环滚动视图，这个类实现了UIScrollView上的内容循环滚动的效果，但要注意每一个添加在这个类中的views中的UIView长宽必须必须恰好是UIScrollView的内容区域的长宽
//  ScrollComponent.h
//
//于是我就实现了上图中的滚轮选择器，它与UIPickerView的区别是：
//(1.)皮肤图片提供了方法可以做替换的。
//(2.)可以选择滚轮上的数据是否循环滚动。
//(3.)可以指定选择条的位置。
//为了代码的复用，也就是所谓的OOP，我按照以下的方式实现：
//(1.)pickerview目录：这个目录中的IDJPickerView.h是滚轮选择器视图，没有任何和与数据相关的东西，也就是你可以在IDJPickerView上显示任何数据。IDJPickerView通过委托获取需要显示的数据。这个目录下的IDJScrollComponent.h实现了UIScrollView上的内容可以反复循环滚动的功能。
//(2.)datepicker目录：IDJDatePickerView.h实现了IDJPickerView里的协议，并把IDJPickerView做为自己的一个私有成员，实现了一个可以根据type字段显示公历、农历的日期选择器，IDJDatePickerView.m里的农历算法用的是solar_chinese_calendar目录中的C++算法，因此农历的相关数据类使用了C++混编的代码。其实iOS自己支持农历的，但是存在BUG，具体的原因大家可以看我的IDJChineseCalendar.mm的源码，里面的注释比较详细的。我在这里也使用了一层封装，也就是IDJDatePickerView.m本身也不提供数据，而是通过IDJCalendar.h、IDJChineseCalendar.h、IDJCalendarUtil.h来提供，这样既实现了数据与视图的分离（因为农历算法太复杂了，直接写在IDJDatePickerView.m里会使得代码看起来可读性太差了），而且把C++的调用封装在了数据层，使得IDJDatePickerView.m的视图层代码不会出现C++的API。
//(3.)timepicker目录：这个目录纯粹是一个Demo，展示了一个不循环滚动、显示行数与选中条位置不对称的选择器，没有什么实际的含义。
//
//一开始我是纯粹写农历的选择器，代码都是耦合在一起的，后来逐个拆开的，这样代码就可以复用了，不仅仅是农历选择器，而是可以承载任何数据。
//  Created by Lihaifeng on 11-11-25, QQ:61673110.
//  Copyright (c) 2011年 www.idianjing.com. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol IDJScrollComponentDelegate;

@interface IDJScrollComponent : UIScrollView<UIScrollViewDelegate> {
	NSArray *views;
	int curentIdx;
    id<IDJScrollComponentDelegate> idjsDelegate;
}
@property (retain, nonatomic) NSArray *views;
@property (assign, nonatomic) int curentIdx;
@property (assign, nonatomic) id<IDJScrollComponentDelegate> idjsDelegate;
- (id)initWithFrame:(CGRect)rect withViews:(NSArray*)_views;
@end

@protocol IDJScrollComponentDelegate <NSObject>
//通知父容器我已经停止滚动
@required - (void)stopScroll:(IDJScrollComponent *)sc;
@end

