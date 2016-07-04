//
//  UIProgressHud.h
//  firesisiter
//
//  Created by guorong on 14-1-21.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@class UICheckbox;
//勾选框类型: UICheckGroupBox，单选框; UIMutipleGroupBox，多选框

typedef enum{
    UICheckGroupBox,
    UIMutipleGroupBox
}UIGroupStyle;

//勾选框的委托方法，text为勾选的文本信息，bflag标示是否勾选，path为勾选框的标示，包括所在的array的标示section, 以及自身标示row

@protocol checkdelegate <NSObject>

@optional
- (void)checkSelected:(NSString*)text
              checked:(BOOL)bflag
                 path:(NSIndexPath*)path;

@end


@interface CheckViewGroup : NSObject
//存放checkview的容器
@property (nonatomic, strong)  NSMutableArray* CheckViewArray;
//存放被勾选状态下标
@property (nonatomic, strong)  NSMutableArray* GroupIndexesArray;
//checkview容器的标记
@property (nonatomic, assign)  int nTag;
//group类型
@property (nonatomic, assign)  UIGroupStyle style;


- (id)initWithTag: (int)Tag type: (UIGroupStyle)type;

- (void)AddCheckView:(UICheckbox*)object;

- (NSArray* )getSelectedItemsIndex;

@end


@interface UICheckbox : UIView

@property (nonatomic, assign) id<checkdelegate> delegate;

@property (nonatomic, copy) void (^checkblock)(UICheckbox* checkbox);

- (id)initWithFrame:(CGRect)frame text:(NSString*)text nTag:(int)tag;

//将控件状态设置为true或false
- (void)setSelected:(BOOL)bFlag;

//获取勾选框的内容
- (NSString*)getCheckedText;

//设置标记勾选框所在的容器
- (void)setGroupTag:(int)Tag;

//获取勾选框路径
- (NSIndexPath*)getCheckedIndexpath;

//勾选框是否被选中
- (BOOL)isChecked;

@end
