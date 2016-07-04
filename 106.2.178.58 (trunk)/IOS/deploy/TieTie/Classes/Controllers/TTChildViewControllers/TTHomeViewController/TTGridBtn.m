//
//  TTGridBtn.m
//  Miteno
//
//  Created by wg on 14-6-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTGridBtn.h"
#define kImageRatio 0.5
@implementation TTGridBtn

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        self.titleLabel.font = [UIFont systemFontOfSize:12];
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        self.adjustsImageWhenHighlighted = NO;
 
        //        [self setBackgroundImage:[UIImage imageNamed:@"tabbar_slider.png"] forState:UIControlStateSelected];
    }
    return self;
}
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    CGFloat titleY = contentRect.size.height * kImageRatio - 5;
    CGFloat titleHeight = contentRect.size.height - titleY;
    return CGRectMake(6, titleY+(iPhone5?5:0), contentRect.size.width, titleHeight);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    /*
     *  主页icon
     */
    CGFloat homeIconH = 80;
    return CGRectMake(2.5, 15,homeIconH,homeIconH);
}

@end
