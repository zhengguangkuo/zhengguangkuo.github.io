//
//  IDJTimePickerView.m
//
//  Created by Lihaifeng on 11-12-2, QQ:61673110.
//  Copyright (c) 2011年 www.idianjing.com. All rights reserved.
//

#import "IDJTimePickerView.h"
#import "IDJCalendar.h"
#import "IDJPickerView.h"
#define HOURS_START 0
#define HOURS_END 23

@interface IDJTimePickerView ()<IDJPickerViewDelegate>
@end
@implementation IDJTimePickerView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _calendar = [[IDJCalendar alloc] initWithYearStart:HOURS_START end:HOURS_END];

        self.backgroundColor = [UIColor clearColor];
        [self setYears];
        [self setTimeMiute:[_calendar.minute intValue]];
//        [self setTimeSecond:[_calendar.second intValue]];
        
        _picker=[[IDJPickerView alloc]initWithFrame:CGRectMake(0, 0, frame.size.width, frame.size.height) dataLoop:YES];
        self.picker.delegate=self;
        
        [self addSubview:_picker];
        //程序启动 显示当前时间
        NSString *strHour = [NSString stringWithFormat:@"%@ 时",_calendar.hour];
        NSLog(@"Time:%@--%@",_calendar.hour,_hours);
        [_picker selectCell:[_hours indexOfObject:strHour] inScroll:0];
        
        NSString *strMinutes = [NSString stringWithFormat:@"%@ 分",_calendar.minute];
        [_picker selectCell:[_minutes indexOfObject:strMinutes] inScroll:1];
        
//        NSString *strSecnd = [NSString stringWithFormat:@"%@ 秒",_calendar.second];
//        [_picker selectCell:[_seconds indexOfObject:strSecnd] inScroll:2];

        [_delegate newChangeValue:_calendar];
    }
    return self;
}
//填充小时
- (void)setYears {
    [_hours release];
    _hours=[[_calendar yearsInRange]retain];
}

- (void)setTimeMiute:(NSUInteger)minute {
    [_minutes release];
    _minutes=[[_calendar timessInTime:minute]retain];
   
}
- (void)setTimeSecond:(NSUInteger)second {
    [_seconds release];
    _seconds = [[_calendar timessInsecond:second] retain];
}
//指定每一列的滚轮上的Cell的个数
- (NSUInteger)numberOfCellsInScroll:(NSUInteger)scroll {
    switch (scroll) {
        case 0:
            return _hours.count;
            break;
        case 1:
            return _minutes.count;
            break;
//        case 2:
//            return _seconds.count;
//            break;
        default:
            return 0;
            break;
    }
}

//指定每一列滚轮所占整体宽度的比例，以:分隔
- (NSString *)scrollWidthProportion {
    return @"1:1";
}

//指定有多少个Cell显示在可视区域
- (NSUInteger)numberOfCellsInVisible {
    return 3;
}

//为指定滚轮上的指定位置的Cell设置内容
- (void)viewForCell:(NSUInteger)cell inScroll:(NSUInteger)scroll reusingCell:(UITableViewCell *)tc {
    tc.textLabel.font=[UIFont systemFontOfSize:15.0];
    tc.textLabel.textAlignment=UITextAlignmentCenter;
    switch (scroll) {
        case 0:
            tc.textLabel.text=[_hours objectAtIndex:cell];
            break;
        case 1:
            tc.textLabel.text=[_minutes objectAtIndex:cell];
            break;
//        case 2:
//            tc.textLabel.text=[_seconds objectAtIndex:cell];
//            break;
        default:
            break;
    }
}

//设置选中条的位置
- (NSUInteger)selectionPosition {
    return 1.9;
}

//当滚轮停止滚动的时候，通知调用者哪一列滚轮的哪一个Cell被选中
- (void)didSelectCell:(NSUInteger)cell inScroll:(NSUInteger)scroll {
    switch (scroll) {
        case 0:
            _calendar.hour =  [_hours objectAtIndex:cell];
            break;
        case 1:
          _calendar.minute = [_minutes objectAtIndex:cell];
            break;
//        case 2:
//          _calendar.second =  [_seconds objectAtIndex:cell];
//            break;
        default:
            break;
    }

    [_delegate newChangeValue:_calendar];
}

- (void)dealloc{
    [super dealloc];
    [_hours release];
    [_minutes release];
    [_picker release];
}

@end
