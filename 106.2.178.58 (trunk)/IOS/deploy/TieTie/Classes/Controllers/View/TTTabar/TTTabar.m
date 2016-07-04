//
//  TTTabar.m
//  TT_lottery
//
//  Created by TT on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTabar.h"
#import "TTButton.h"
@interface TTTabar()
{
    TTButton    *_button;
    TTButton    *_currentBtn;
}

@end
@implementation TTTabar

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"bottombackground"]];
//        self.backgroundColor = [UIColor blackColor];
    }
    return self;
}
- (void)setDidSelectDefault:(NSInteger (^)(void))didSelectDefault
{
    TTLog(@"这里delegagte");
    self.selectIndex = didSelectDefault();
    if (self.selectIndex>=0) {
        [_delegate tabar:self didSelectButtonto:self.selectIndex];
        //默认选中
        [self buttonClick:self.subviews[self.selectIndex]];
    }
    
}
- (void)andTabBarButton:(NSString *)icon selectIcon:(NSString *)selectIcon
{
     _button = [TTButton buttonWithType:UIButtonTypeCustom];
    UIImage *imageNormal = [UIImage imageNamed:icon];
    [_button setImage:imageNormal forState:UIControlStateNormal];
    [_button setImage:[UIImage imageNamed:selectIcon] forState:UIControlStateSelected];
    [_button addTarget:self action:@selector(buttonClick:) forControlEvents:UIControlEventTouchDown];
    [_button setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
    [_button setTitleColor:[UIColor whiteColor] forState:UIControlStateSelected];
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
- (void)buttonClick:(TTButton *)TTBtn
{    
    //获取选定btn
    if ([self.delegate respondsToSelector:@selector(tabar:didSelectButtonto:)]) {
        [self.delegate tabar:self didSelectButtonto:TTBtn.tag];
        TTLog(@"select:%ld",(long)TTBtn.tag);
    }
    if ([self.delegate respondsToSelector:@selector(tabarDidSelectButtonto:)]) {
        [self.delegate tabarDidSelectButtonto:TTBtn];
    }
    _currentBtn.selected = NO;
    TTBtn.selected = YES;
    _currentBtn = TTBtn;
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
