//
//  TTButton.m
//  Miteno
//
//  Created by wg on 14-7-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTButton.h"
#define kImageRatio 0.8
@implementation TTButton
- (void)setHighlighted:(BOOL)highlighted {}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        self.titleLabel.font = [UIFont systemFontOfSize:10];
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        //        self.imageView.contentMode = UIViewContentModeCenter;
        self.adjustsImageWhenHighlighted = NO;
        //        [self setBackgroundImage:[UIImage imageNamed:@"tabbar_slider.png"] forState:UIControlStateSelected];
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
@end
