//
//  MainNavigation.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UINavigationController(addition)

- (void)SetBackButton:(id)target action:(SEL)action;

- (void)SetLeftButtons:(NSArray*)buttons;

- (void)SetRightButtons:(NSArray*)buttons;

- (void)SetBackHidden;

- (void)SetTitleText:(NSString *)title;

- (void)SetTitleColor:(UIColor *)color;

- (void)SetBackgroundImage:(UIImage *)image;

@end
