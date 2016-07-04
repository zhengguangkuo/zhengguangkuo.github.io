//
//  ImageButton.m
//  iGridViewDemo
//
//  Created by HWG on 14-2-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "ImageButton.h"

#define kImageRatio 0.6         //图片比例

#define kMarginRatio 0.1        //图片之间的距离比例

#define kLabelRatio (1 - kImageRatio - 2 * kMarginRatio)

#define kFontSize    15         //文字大小

@implementation ImageButton
- (id)init {
    if (self = [super init]) {
        // 设置文字属性
        [self setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
   
        self.titleLabel.font = [UIFont systemFontOfSize:kFontSize];
    
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        
        // 设置图片不要拉伸，保持原来的比例
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        // 高亮显示的时候不需要调整图片的颜色
        self.adjustsImageWhenHighlighted = NO;
    }
    
    return self;
}

#pragma mark 设置文字的位置
- (CGRect)titleRectForContentRect:(CGRect)contentRect {
    return CGRectMake(0, contentRect.size.height * (kImageRatio + kMarginRatio), contentRect.size.width, contentRect.size.height * kLabelRatio);
}
#pragma mark 设置图片的位置
- (CGRect)imageRectForContentRect:(CGRect)contentRect {
    return CGRectMake(0, contentRect.size.height * kMarginRatio, contentRect.size.width, contentRect.size.height * kImageRatio);
}
@end
