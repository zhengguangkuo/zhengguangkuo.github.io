//
//  Ivan_UITabBar.h
//  JustForTest
//
//  Created by Ivan on 11-5-15.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface Ivan_UITabBar : UITabBarController {
	NSMutableArray *buttons;
	int currentSelectedIndex;
	UIImageView *slideBg;
	UIView *cusTomTabBarView;
	UIImageView *backGroundImageView;
}

@property (nonatomic, assign) int currentSelectedIndex;

@property (nonatomic,strong) NSMutableArray *buttons;

- (void)hideRealTabBar;
- (void)customTabBar;
- (void)selectedTab:(UIButton *)button;
- (void)SetCurrentSelected:(int)index;

@end
