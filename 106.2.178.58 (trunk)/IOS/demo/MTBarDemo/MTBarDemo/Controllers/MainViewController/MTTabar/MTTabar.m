//
//  MTTabar.m
//  MT_lottery(彩票)
//
//  Created by MT on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "MTTabar.h"
#define kImageRatio 0.6
@interface MTButton :UIButton

@end
@implementation  MTButton
- (void)setHighlighted:(BOOL)highlighted {}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        self.titleLabel.font = [UIFont systemFontOfSize:12];
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        self.adjustsImageWhenHighlighted = NO;
        [self setBackgroundImage:[UIImage imageNamed:@"tabbar_slider.png"] forState:UIControlStateSelected];
    }
    return self;
}
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    CGFloat titleY = contentRect.size.height * kImageRatio - 5;
    CGFloat titleHeight = contentRect.size.height - titleY;
    return CGRectMake(0, titleY, contentRect.size.width, titleHeight);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(0, 0, contentRect.size.width, contentRect.size.height * kImageRatio);
}
@end

@interface MTTabar()
{
    MTButton    *_button;
    MTButton    *_currentBtn;

}
@property (nonatomic, assign) NSInteger selectIndex;
@end
@implementation MTTabar

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"TabBarBack"]];
    }
    return self;
}
- (void)setDidSelectDefault:(NSInteger (^)(void))didSelectDefault
{
    self.selectIndex = didSelectDefault();
    
    if (self.selectIndex>0) {
        [_delegate tabar:self didSelectButtonto:self.selectIndex];
        //默认选中
        [self buttonClick:self.subviews[self.selectIndex]];
    }
    
}
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon
{
     _button = [MTButton buttonWithType:UIButtonTypeCustom];
    [_button setBackgroundImage:[UIImage imageNamed:icon] forState:UIControlStateNormal];
    [_button setBackgroundImage:[UIImage imageNamed:selectIcon] forState:UIControlStateSelected];
    
    [_button addTarget:self action:@selector(buttonClick:) forControlEvents:UIControlEventTouchDown];
    
    [self addSubview:_button];
    
    //根据要添加的item个数 重新调整位置
    [self adjustButtonFrames];
    
    //重新调整后才能确定tag
    _button.tag = self.subviews.count-1;
    
}
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon titile:(NSString *)title
{
    [self andTabBarButton:icon selectIcon:selectIcon];
    [_button setTitle:title forState:UIControlStateNormal];
}
- (void)buttonClick:(MTButton *)MTBtn
{
    //获取选定btn
    if ([self.delegate respondsToSelector:@selector(tabar:didSelectButtonto:)]) {
        [self.delegate tabar:self didSelectButtonto:MTBtn.tag];
        MTLog(@"select:%ld",(long)MTBtn.tag);
    }
    
    _currentBtn.selected = NO;
    MTBtn.selected = YES;
    _currentBtn = MTBtn;
}
#pragma mark 重新调整所有item
- (void)adjustButtonFrames
{
    NSInteger btnCount = self.subviews.count;
    for (int i = 0; i < btnCount ; i ++) {
        UIButton *btn = self.subviews[i];
        
        CGFloat btnY = 0;
        CGFloat btnW = self.frame.size.width/btnCount;
        CGFloat btnX= btnW * i;
        CGFloat btnH = self.frame.size.height;
       
        btn.frame = CGRectMake(btnX, btnY, btnW, btnH);
    }

}
@end
