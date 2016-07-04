//
//  CustomItem.m
//  Miteno
//
//  Created by wg on 14-6-14.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "CustomItem.h"
@implementation CustomItem
- (void)setHighlighted:(BOOL)highlighted {}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        self.titleLabel.font = [UIFont systemFontOfSize:17];
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
//        self.adjustsImageWhenHighlighted = NO;
        //        [self setBackgroundImage:[UIImage imageNamed:@"tabbar_slider.png"] forState:UIControlStateSelected];
    }
    return self;
}
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(contentRect.size.width/2-80,0, contentRect.size.width/2+20, contentRect.size.height);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(0, 15, contentRect.size.width/2-20, contentRect.size.height/2);
}
@end
