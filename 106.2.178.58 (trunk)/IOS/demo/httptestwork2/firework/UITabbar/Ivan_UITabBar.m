//
//  Ivan_UITabBar.m
//  JustForTest
//
//  Created by Ivan on 11-5-15.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "Ivan_UITabBar.h"
#import "UIBadgeView.h"

@implementation Ivan_UITabBar
@synthesize currentSelectedIndex;
@synthesize buttons;

static BOOL FIRSTTIME =YES;


- (void)viewDidAppear:(BOOL)animated{
	if (FIRSTTIME) {
		[[NSNotificationCenter defaultCenter] removeObserver:self name:@"hideCustomTabBar" object:nil];
		[[NSNotificationCenter defaultCenter] addObserver:self
												 selector:@selector(hideCustomTabBar)
													 name: @"hideCustomTabBar"
												   object: nil];
		[[NSNotificationCenter defaultCenter] removeObserver:self name:@"bringCustomTabBarToFront" object:nil];
		[[NSNotificationCenter defaultCenter] addObserver:self
												 selector:@selector(bringCustomTabBarToFront)
													 name: @"bringCustomTabBarToFront"
												   object: nil];
		[[NSNotificationCenter defaultCenter] removeObserver:self name:@"setBadge" object:nil];
		[[NSNotificationCenter defaultCenter] addObserver:self
												 selector:@selector(setBadge:)
													 name: @"setBadge"
												   object: nil];
		
		slideBg = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"slide"]];
		[self hideRealTabBar];
		[self customTabBar];
        FIRSTTIME = NO;
	}
}

- (void)hideRealTabBar{
	for(UIView *view in self.view.subviews){
		if([view isKindOfClass:[UITabBar class]]){
			view.hidden = YES;
			break;
		}
	}
}



- (void)SetCurrentSelected:(int)index
{
    self.selectedIndex = index;
    self.currentSelectedIndex = self.selectedIndex;
    UIViewController *v = [self.viewControllers objectAtIndex:index];
    [v viewDidAppear:NO];
    UIButton*  tempbutton = [buttons objectAtIndex:index];
    NSLog(@"tag of button = %d",[tempbutton tag]);
    [tempbutton setImage:v.tabBarItem.finishedSelectedImage forState:UIControlStateNormal];
}


//设置badge
- (void)setBadge:(NSNotification *)_notification{
	NSString *badgeValue = [_notification object];
	UIButton *btn = [self.buttons objectAtIndex:self.selectedIndex];
	UIBadgeView *badgeView = [[UIBadgeView alloc] initWithFrame:CGRectMake(btn.bounds.size.width/2, 0, 30, 20)];
	badgeView.badgeString = badgeValue;
	badgeView.badgeColor = [UIColor blueColor];
	badgeView.tag = self.selectedIndex;
	badgeView.delegate = self;
	[btn addSubview:badgeView];
}

//自定义tabbar
- (void)customTabBar{
	//获取tabbar的frame
	CGRect tabBarFrame = self.tabBar.frame;
	backGroundImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 320, 49)];
	cusTomTabBarView = [[UIView alloc] initWithFrame:tabBarFrame];
	//设置tabbar背景
	
	//backGroundImageView.image = [UIImage imageNamed:@"banner.png"];
	[cusTomTabBarView addSubview:backGroundImageView];
    cusTomTabBarView.backgroundColor = [UIColor blackColor];
	
	
	//创建按钮
	int viewCount = self.viewControllers.count > 5 ? 5 : self.viewControllers.count;
	self.buttons = [NSMutableArray arrayWithCapacity:viewCount];
	double _width = 320 / viewCount;
	double _height = self.tabBar.frame.size.height;
	for (int i = 0; i < viewCount; i++) {
		UIViewController *v = [self.viewControllers objectAtIndex:i];
		UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
		btn.frame = CGRectMake(i*_width, 0, _width, _height);
		if(FIRSTTIME)
    {
        self.selectedIndex = 0;
        self.currentSelectedIndex = self.selectedIndex;
    }
        
        int oldindex = self.currentSelectedIndex;
        
        [btn addTarget:self action:@selector(selectedTab:) forControlEvents:UIControlEventTouchDown];
		btn.tag = i;
		
        if(oldindex!=self.selectedIndex)
{
        if(btn.tag==oldindex)
        {
            NSLog(@"aaa");
            [btn setImage:v.tabBarItem.finishedUnselectedImage forState:UIControlStateNormal];
           
        }
        else
        if(btn.tag==currentSelectedIndex)
        {
            NSLog(@"bbbb");
            [btn setImage:v.tabBarItem.finishedSelectedImage forState:UIControlStateNormal];
        }
        else
        {
            NSLog(@"ccc");
            [btn setImage:v.tabBarItem.image forState:UIControlStateNormal];
        }
}
        else
{
        if(btn.tag==currentSelectedIndex)
        {
            NSLog(@"bbbb");
            [btn setImage:v.tabBarItem.finishedSelectedImage forState:UIControlStateNormal];
        }
        else
        {
            NSLog(@"ccc");
            [btn setImage:v.tabBarItem.image forState:UIControlStateNormal];
        }
}
        
        [btn setImageEdgeInsets:UIEdgeInsetsMake(-10, 0, 0, 0)];
		//添加标题
		UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, _height-18, _width, _height-30)];
		titleLabel.backgroundColor = [UIColor clearColor];
		titleLabel.text = v.tabBarItem.title;
		[titleLabel setFont:[UIFont systemFontOfSize:12]];
		titleLabel.textAlignment = 1;
		titleLabel.textColor = [UIColor whiteColor];
		[btn addSubview:titleLabel];
		//[titleLabel release];
		
		[self.buttons addObject:btn];
		//添加按钮之间的分割线,第一个位置和最后一个位置不需要添加
		if (i>0 && i<4) {
			UIImageView *splitView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"split"]];
			splitView.frame = CGRectMake(i*_width-1,0,splitView.frame.size.width,splitView.frame.size.height);
			[cusTomTabBarView addSubview:splitView];
			//[splitView release];
		}
		
		//添加Badge
		if (v.tabBarItem.badgeValue) {
			UIBadgeView *badgeView = [[UIBadgeView alloc] initWithFrame:CGRectMake(_width/2, 0, 30, 20)];
			badgeView.badgeString = v.tabBarItem.badgeValue;
			badgeView.badgeColor = [UIColor orangeColor];
			[btn addSubview:badgeView];
			//[badgeView release];
		}
		[cusTomTabBarView addSubview:btn];
	}
	[self.view addSubview:cusTomTabBarView];
	[cusTomTabBarView addSubview:slideBg];
	//[cusTomTabBarView release];
	//[self performSelector:@selector(slideTabBg:) withObject:[self.buttons objectAtIndex:self.selectedIndex]];
}

//切换tabbar
- (void)selectedTab:(UIButton *)button{
	if (self.currentSelectedIndex == button.tag) {
		 NSLog(@"tttttt");
        NSLog(@"button.tag = %d",button.tag);
        if ([[self.viewControllers objectAtIndex:button.tag] isKindOfClass:[UINavigationController class]])
        {
            [(UINavigationController *)[self.viewControllers objectAtIndex:button.tag] popToRootViewControllerAnimated:YES];
        }

    }
    NSLog(@"herere");
    
    UIViewController *v1 = [self.viewControllers objectAtIndex:currentSelectedIndex];
    UIButton*  b1 = [buttons objectAtIndex:currentSelectedIndex];
    [b1 setImage:v1.tabBarItem.finishedUnselectedImage forState:UIControlStateNormal];
    
    
    UIViewController *v2 = [self.viewControllers objectAtIndex:button.tag];
    UIButton*  b2 = button;
    [b2 setImage:v2.tabBarItem.finishedSelectedImage forState:UIControlStateNormal];
    
    self.currentSelectedIndex = button.tag;
	self.selectedIndex = self.currentSelectedIndex;
	[self performSelector:@selector(slideTabBg:) withObject:button];
}

//将自定义的tabbar显示出来
- (void)bringCustomTabBarToFront{
    [self performSelector:@selector(hideRealTabBar)];
    [cusTomTabBarView setHidden:NO];
    CAKeyframeAnimation * animation; 
	animation = [CAKeyframeAnimation animationWithKeyPath:@"transform"]; 
	animation.duration = 0.25;
	animation.delegate = self;
	animation.removedOnCompletion = YES;
	animation.fillMode = kCAFillModeForwards;
	NSMutableArray *values = [NSMutableArray array];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.0, 0.0, 0.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.5, 0.5, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.0, 1.0, 1.0)]];
	animation.values = values;
	[cusTomTabBarView.layer addAnimation:animation forKey:nil];
}

//隐藏自定义tabbar
- (void)hideCustomTabBar{
	[self performSelector:@selector(hideRealTabBar)];
    CAKeyframeAnimation * animation; 
	animation = [CAKeyframeAnimation animationWithKeyPath:@"transform"]; 
	animation.duration = 0.1;
	animation.delegate = self;
	animation.removedOnCompletion = YES;
	animation.fillMode = kCAFillModeForwards;
	NSMutableArray *values = [NSMutableArray array];
	[values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.0, 1.0, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.5, 0.5, 1.0)]];
    [values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.0, 0.0, 0.0)]];
	animation.values = values;
	[cusTomTabBarView.layer addAnimation:animation forKey:nil];
}

//动画结束回调
- (void)animationDidStop:(CAAnimation *)anim finished:(BOOL)flag{
    if (anim.duration==0.1) {
        [cusTomTabBarView setHidden:YES];
    }
}

//切换滑块位置
- (void)slideTabBg:(UIButton *)btn{
	[UIView beginAnimations:nil context:nil];  
	[UIView setAnimationDuration:0.20];  
	[UIView setAnimationDelegate:self];
	slideBg.frame = btn.frame;
	[UIView commitAnimations];
	CAKeyframeAnimation * animation; 
	animation = [CAKeyframeAnimation animationWithKeyPath:@"transform"]; 
	animation.duration = 0.50; 
	animation.delegate = self;
	animation.removedOnCompletion = YES;
	animation.fillMode = kCAFillModeForwards;
	NSMutableArray *values = [NSMutableArray array];
	[values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.1, 0.1, 1.0)]];
	[values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.2, 1.2, 1.0)]]; 
	[values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(0.9, 0.9, 0.9)]]; 
	[values addObject:[NSValue valueWithCATransform3D:CATransform3DMakeScale(1.0, 1.0, 1.0)]];
	animation.values = values;
	[btn.layer addAnimation:animation forKey:nil];
}


//- (void) dealloc{
//	
//	[cusTomTabBarView release];
//	[slideBg release];
//	[buttons release];
//	[backGroundImageView release];
//	[super dealloc];
//}


@end
