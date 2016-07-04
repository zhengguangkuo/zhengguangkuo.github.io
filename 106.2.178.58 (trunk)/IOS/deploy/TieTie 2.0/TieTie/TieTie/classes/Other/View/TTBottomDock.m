//
//  TTBottomDock.m
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBottomDock.h"
#import "TTDockItem.h"
@interface TTBottomDock()
{
    UIButton *_selectedBtn;
}
@end
@implementation TTBottomDock

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"tab_bg"]];
        
    }
    return self;
}
- (void)andBottomButton:(NSString *)icon selectIcon:(NSString *)selectIcon
{
    [self andBottomButton:icon selectIcon:selectIcon title:nil];
}
- (void)andBottomButton:(NSString *)icon selectIcon:(NSString *)selectIcon title:(NSString *)title
{
    TTDockItem *btn = [TTDockItem dockItem];
    [btn setIcon:icon selectedIcon:selectIcon];
    [btn setTitle:title forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateDisabled];
    [btn addTarget:self action:@selector(buttonClick:) forControlEvents:UIControlEventTouchDown];

    [self addSubview:btn];
    
    //根据要添加的item个数 重新调整位置
    [self adjustButtonFrames];
    
    //重新调整后才能确定tag
    btn.tag = self.subviews.count-1;

    if (btn.tag == 0) {
        [self buttonClick:btn];
    }
}
- (void)buttonClick:(UIButton *)btn
{
    if ([self.delegate respondsToSelector:@selector(bottomDock:btnClickFrom:to:)]) {
        [_delegate bottomDock:self btnClickFrom:_selectedBtn.tag to:btn.tag];
    }
    
    _selectedBtn.enabled = YES;
    btn.enabled = NO;
    _selectedBtn = btn;
    
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
+ (instancetype)bottomDock
{
    return [[TTBottomDock alloc] init];
}
@end
