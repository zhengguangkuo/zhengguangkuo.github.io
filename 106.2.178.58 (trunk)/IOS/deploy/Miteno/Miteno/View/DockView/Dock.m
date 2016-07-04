//  Dock.m
//  Miteno
//
//  Created by HWG on 14-3-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//


#import "Dock.h"
@interface Dock()
{
    // 当前选中了哪个item
    UIButton *_currentBtn;
    NSUserDefaults  *_states;
}
@end

@implementation Dock

// init方法内部会调用initWithFramne
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // 设置背景(拿到image进行平铺)
        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"tabbar_background.png"]];
        _states = [NSUserDefaults standardUserDefaults];
    }
    return self;
}

#pragma mark 添加item
- (void)addDockBtnWithBgIcon:(NSString *)Bgicon selectIcon:(NSString *)selectIcon title:(NSString *)title
{
    // 创建item
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    [self addSubview:btn];
    [btn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [btn setTitle:title forState:UIControlStateNormal];
    // 设置文字属性
//    btn.titleLabel.textAlignment = NSTextAlignmentCenter; // 文字居中
//    btn.titleLabel.font = [UIFont systemFontOfSize:12];

    // 设置图片属性
    btn.imageView.contentMode = UIViewContentModeScaleAspectFit;
//    btn.adjustsImageWhenHighlighted = NO;
    
    // 设置选中时的背景
    [btn setBackgroundImage:[UIImage stretchImageWithName:Bgicon] forState:UIControlStateNormal];
    
    [btn setBackgroundImage:[UIImage stretchImageWithName:selectIcon] forState:UIControlStateSelected];
    
    // 监听点击
    [btn addTarget:self action:@selector(itemClick:) forControlEvents:UIControlEventTouchDown];
    
    // 调整item的边框
    
    [self adjustDockItemsFrame];
}

#pragma mark 点击了某个item
- (void)itemClick:(UIButton *)btn
{
    if (_isSelectClickBlock) {
            _isSelectClickBlock(btn,btn.tag);
        return;
    }
    _currentBtn.selected = NO;
    btn.selected = YES;
    _currentBtn = btn;
    if (_itemClickBlock) {
        _itemClickBlock(btn.tag);
    }
}

#pragma mark 调整item的边框
- (void)adjustDockItemsFrame
{
    NSInteger count = self.subviews.count;
    
    // 算出item的尺寸
    CGFloat itemWidth = self.frame.size.width / count;
    CGFloat itemHeight = self.frame.size.height;
    
    for (int i = 0; i<count; i++) {
        //取出子控件
        UIButton *btn = self.subviews[i];
        
        // 算出边框
        btn.frame = CGRectMake(i * itemWidth, 0, itemWidth, itemHeight);
        
//        if (i == 0) { // 第0个item选中
//            btn.selected = YES;
//            _currentBtn = btn;
//        }
        
        //设置item的tag
        btn.tag = i;
    }
}

@end