//
//  MTTabar.h
//  MT_lottery(彩票)
//
//  Created by MT on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>


@class MTTabar;
@protocol MTTabarDelegate <NSObject>
@optional
- (void)tabar:(MTTabar *)tabar didSelectButtonto:(NSUInteger)to;
@end

@interface MTTabar : UIView

@property (nonatomic, weak) id <MTTabarDelegate> delegate;
@property (nonatomic, copy) NSInteger (^didSelectDefault)(void);
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon;
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon titile:(NSString *)title;

@end



