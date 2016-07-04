//  Dock.h
//  Miteno
//
//  Created by HWG on 14-3-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//
// 通用Dock

#import <UIKit/UIKit.h>
#define kDockHeight 44
typedef enum {
        ItemTitleLayoutBottom,
        ItemTitleLayoutLeft,
}ItemTitleLayout;
@protocol DockDelegate <NSObject>

- (void)didSelectItem:(NSInteger )itemIndex;

@end
@interface DockItem :UIButton
@end
@interface Dock : UIView

// 添加一个选项卡（图标、文字标题）
- (void)addDockBtnWithBgIcon:(NSString *)Bgicon selectIcon:(NSString *)selectIcon title:(NSString *)title titleLayout:(ItemTitleLayout)titleLayout;
@property (nonatomic, copy) NSInteger (^didSelectDefault)(void);
@property (nonatomic, assign) id <DockDelegate> delegate;
@property (nonatomic, strong) DockItem  *btn;
@property (nonatomic, copy) void (^itemClickBlock)(int index);
@property (nonatomic, assign)NSInteger selectIndex;
@property (nonatomic, copy) void (^isSelectClickBlock)(UIButton *,int index);
@end