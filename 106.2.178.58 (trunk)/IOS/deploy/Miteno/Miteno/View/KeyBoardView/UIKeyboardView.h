//
//  UIKeyboardView.h
//  baseLongShine
//
//  Created by Longshine on 13-12-4.
//  Copyright (c) 2013年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol UIKeyboardViewDelegate;

@interface UIKeyboardView : UIView {

	UIToolbar *keyboardToolbar;
}

@property (nonatomic, assign) id <UIKeyboardViewDelegate> delegate;

@end

@interface UIKeyboardView (UIKeyboardViewAction)

- (UIBarButtonItem *)itemForIndex:(NSInteger)itemIndex;

@end

@protocol UIKeyboardViewDelegate <NSObject>

- (void)toolbarButtonTap:(UIButton *)button;

@optional

@end
