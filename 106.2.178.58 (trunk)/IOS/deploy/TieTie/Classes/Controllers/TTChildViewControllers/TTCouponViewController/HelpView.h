//
//  HelpView.h
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HelpView : UIView<UIGestureRecognizerDelegate>
- (IBAction)removeHelpView:(id)sender;
@property (nonatomic, strong)UIView *bgView;
- (void)show:(UIViewController *)ctrl;
+ (instancetype)helpView;
@end
