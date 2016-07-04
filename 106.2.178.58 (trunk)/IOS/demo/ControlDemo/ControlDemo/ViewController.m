//
//  ViewController.m
//  ControlDemo
//
//  Created by HWG on 14-1-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "ViewController.h"
#import "DropDown.h"
#import "IDJDatePickerView.h"
#import "IDJTimePickerView.h"

@interface ViewController ()<IDJDatePickerViewDelegate,IDTimePickerViewDelegate,DropDownDelegate>{
    DropDown    *_dd1;
    IDJTimePickerView   *_timePickerView;
}
@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	self.view.backgroundColor = [UIColor grayColor];

    //1.添加下拉列表框
    [self addDropDownList];
    
    //2.添加时间选择器
    [self addDatePickerView];
}
/*
 * 
 */

#pragma mark -下拉列表
- (void)addDropDownList
{
    _dd1 = [[DropDown alloc] initWithFrame:CGRectMake(40, 20, 240, 100)];
    _dd1.delegate = self;
    _dd1.textField.placeholder = @"请输入人名";
    NSArray* arr=[[NSArray alloc]initWithObjects:@"东方不败",@"西门吹雪",@"lucy",@"wewe",@"bbb",@"ccc",nil];
    _dd1.tableArray = arr;
    [self.view addSubview:_dd1];

}
#pragma DropDownDelegate
- (void)selectedValue:(NSString *)text
{
    NSLog(@"选取了:%@",text);
}

- (void)getIndex:(NSInteger)index
{
    NSLog(@"选定的是第%ld行",index);
}

/*
 *  --------------------------------------------------------------------------------
 */

#pragma mark -addDatePicker
- (void)addDatePickerView
{
    //公历日期选择器
    IDJDatePickerView *djdateGregorianView=[[IDJDatePickerView alloc]initWithFrame:CGRectMake(10, 270, 300, 200) type:Gregorian1];
    [self.view addSubview:djdateGregorianView];
    djdateGregorianView.delegate=self;

    //时间选择器
    _timePickerView=[[IDJTimePickerView alloc]initWithFrame:CGRectMake(10, 70, 300, 200)];
    [self.view addSubview:_timePickerView];
    _timePickerView.delegate = self;
}
//接收日期选择器选项变化的通知
- (void)notifyNewCalendar:(IDJCalendar *)cal {
    if ([cal isMemberOfClass:[IDJCalendar class]]) {
        NSLog(@"%@:era=%@, year=%@, month=%@, day=%@, weekday=%@", cal, cal.era, cal.year, cal.month, cal.day, cal.weekday);
    } else if ([cal isMemberOfClass:[IDJChineseCalendar class]]) {
        IDJChineseCalendar *_cal=(IDJChineseCalendar *)cal;
        NSLog(@"%@:era=%@, year=%@, month=%@, day=%@, weekday=%@, animal=%@", cal, cal.era, cal.year, cal.month, cal.day, cal.weekday, _cal.animal);
    }
}
- (void)newChangeValue:(IDJCalendar *)cal
{
    if (cal) {
        NSLog(@"当前选定时间:%@--%@--%@",cal.hour,cal.minute,cal.second);
    }
}



//began屏幕
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    [_dd1.textField resignFirstResponder];
    
    [UIView animateWithDuration:0.2 animations:^{
        _dd1.tableView.hidden = YES;
    }];
}
@end
