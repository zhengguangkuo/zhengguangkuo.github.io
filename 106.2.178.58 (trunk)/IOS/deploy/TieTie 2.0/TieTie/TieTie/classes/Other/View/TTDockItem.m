//
//  TTDockItem.m
//  TieTie
//
//  Created by wg on 14-9-25.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTDockItem.h"
#define kImageRatio 0.8
@implementation TTDockItem

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        self.titleLabel.font = [UIFont systemFontOfSize:10];
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        //        self.imageView.contentMode = UIViewContentModeCenter;
        self.adjustsImageWhenHighlighted = NO;
    }
    return self;
}

- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    CGFloat titleY = contentRect.size.height * kImageRatio - 5;
    CGFloat titleHeight = contentRect.size.height - titleY;
    return CGRectMake(0, titleY-2, contentRect.size.width, titleHeight);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(0,7, contentRect.size.width, contentRect.size.height-12 * kImageRatio-12);
}
- (void)setHighlighted:(BOOL)highlighted {}

#pragma mark -设置图片
- (void)setIcon:(NSString *)icon
{
    _icon = icon;
    [self setImage:[UIImage imageNamed:icon] forState:UIControlStateNormal];
}

- (void)setSelectedIcon:(NSString *)selectedIcon
{
    _selectedIcon = selectedIcon;
    [self setImage:[UIImage imageNamed:selectedIcon] forState:UIControlStateDisabled];
}
- (void)setIcon:(NSString *)icon selectedIcon:(NSString *)selectedIcon
{
    self.icon = icon;
    self.selectedIcon = selectedIcon;
}
+ (instancetype)dockItem
{
    return [[TTDockItem alloc] init];
}
@end
