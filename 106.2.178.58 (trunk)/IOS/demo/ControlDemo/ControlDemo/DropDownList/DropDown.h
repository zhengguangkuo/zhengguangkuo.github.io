//
//  DropDown.h
//  ControlDemo
//
//  Created by HWG on 14-1-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol DropDownDelegate <NSObject>
//获取字符串
- (void)selectedValue:(NSString *)text;
//获取index
- (void)getIndex:(NSInteger)index;
@end

@interface DropDown : UIView<UITableViewDataSource,UITableViewDelegate>
@property (nonatomic, strong) UITableView   *tableView;     //下拉列表
@property (nonatomic, strong) UITextField   *textField;     //下拉列表数据
@property (nonatomic, strong) NSArray       *tableArray;    //下拉列表数据
@property (nonatomic, assign) BOOL          showList;       //是否弹出下拉
@property (nonatomic, assign) CGFloat       tabHeight;      //table下拉列表高度
@property (nonatomic, assign) CGFloat       frameHeight;    //frame的高度

@property (nonatomic, assign) id <DropDownDelegate> delegate;
@end
