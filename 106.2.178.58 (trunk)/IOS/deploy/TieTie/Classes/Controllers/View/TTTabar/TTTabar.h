//
//  TTTabar.h
//  TT_lottery
//
//  Created by TT on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
@class TTTabar;
@protocol TTTabarDelegate <NSObject>
@optional
- (void)tabar:(TTTabar *)tabar didSelectButtonto:(NSUInteger)to;
- (void)tabarDidSelectButtonto:(UIButton *)btn;
@end


@interface TTTabar : UIView
@property (nonatomic, assign) NSInteger selectIndex;
@property (nonatomic, weak) id <TTTabarDelegate> delegate;
@property (nonatomic, copy) NSInteger (^didSelectDefault)(void);
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon;
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon titile:(NSString *)title;

@end



