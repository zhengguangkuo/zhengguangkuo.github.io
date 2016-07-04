//
//  UICheckbox.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>

@class UICheckbox;

typedef enum{
    UICheckGroupBox,
    UIMutipleGroupBox
}UIGroupStyle;


@protocol checkdelegate <NSObject>

@optional
- (void)checkSelected:(NSString*)text
              checked:(BOOL)bflag
                 path:(NSIndexPath*)path;

@end


@interface CheckViewGroup : NSObject

@property (nonatomic, strong)  NSMutableArray* CheckViewArray;

@property (nonatomic, strong)  NSMutableArray* GroupIndexesArray;

@property (nonatomic, assign)  int nTag;

@property (nonatomic, assign)  UIGroupStyle style;


- (id)initWithTag:(int)Tag type:(UIGroupStyle)type;

- (void)AddCheckView:(UICheckbox*)object;

- (NSArray*)getSelectedItemsIndex;

@end


@interface UICheckbox : UIView

@property (nonatomic, assign) id<checkdelegate> delegate;

@property (nonatomic,copy) void (^checkblock)(UICheckbox* checkbox);

- (id)initWithFrame:(CGRect)frame text:(NSString*)text nTag:(int)tag;

- (void)setSelected:(BOOL)bFlag;

- (NSString*)getCheckedText;

- (void)setGroupTag:(int)Tag;

- (NSIndexPath*)getCheckedIndexpath;

- (BOOL)isChecked;

@end
