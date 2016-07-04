//  Dock.m
//  Miteno
//
//  Created by HWG on 14-3-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//


#import "Dock.h"
#import "TTButton.h"

@implementation  DockItem
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentRight;
        self.titleLabel.font = [UIFont systemFontOfSize:14];
        //        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        //        self.backgroundColor = [UIColor greenColor];
    }
    return self;
}
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(-10,0, contentRect.size.width/2+35,contentRect.size.height);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(contentRect.size.width/2+30,contentRect.size.height/3 + 5,7,7);
}
@end

@interface Dock()
{
    // 当前选中了哪个item
    UIButton *_currentBtn;
}
@end

@implementation Dock

// init方法内部会调用initWithFramne
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // 设置背景(拿到image进行平铺)
//        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"tabbar_background.png"]];
    }
    return self;
}
- (void)setDidSelectDefault:(NSInteger (^)(void))didSelectDefault
{
    self.selectIndex = didSelectDefault();
    if (self.selectIndex>=0) {
        if (self.selectIndex>=0) {
            [_delegate respondsToSelector:@selector(didSelectItem:)];
            //默认选中
                    [self itemClick:self.subviews[self.selectIndex]];
        }
        //默认选中

    }
    
}
#pragma mark 添加item
- (void)addDockBtnWithBgIcon:(NSString *)Bgicon selectIcon:(NSString *)selectIcon title:(NSString *)title titleLayout:(ItemTitleLayout)titleLayout
{
    
    if (titleLayout == ItemTitleLayoutLeft) {
        
        // 创建item
        _btn = [DockItem buttonWithType:UIButtonTypeCustom];
        [self addSubview:_btn];
        [_btn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
        [_btn setTitleColor:[UIColor whiteColor] forState:UIControlStateSelected];
        [_btn setTitle:title forState:UIControlStateNormal];
        [_btn setImage:[UIImage stretchImageWithName:@"control_d"]forState:UIControlStateNormal];
        [_btn setImage:[UIImage stretchImageWithName:@"control_u"] forState:UIControlStateSelected];
        // 设置文字属性
        //    btn.titleLabel.textAlignment = NSTextAlignmentCenter; // 文字居中
        //    btn.titleLabel.font = [UIFont systemFontOfSize:12];
        
        // 设置图片属性
        _btn.imageView.contentMode = UIViewContentModeScaleAspectFit;
        //    btn.adjustsImageWhenHighlighted = NO;
        
        // 设置选中时的背景
        [_btn setBackgroundImage:[UIImage stretchImageWithName:Bgicon] forState:UIControlStateNormal];
        
        [_btn setBackgroundImage:[UIImage stretchImageWithName:selectIcon] forState:UIControlStateSelected];
        
        // 监听点击
        [_btn addTarget:self action:@selector(itemClick:) forControlEvents:UIControlEventTouchDown];
    }else{
        self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"graybg"]];
        UIButton  *button= [UIButton buttonWithType:UIButtonTypeCustom];
        [self addSubview:button];
        [button setTitleColor:[UIColor blackColor] forState:UIControlStateSelected];
        [button setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
        [button setTitle:title forState:UIControlStateNormal];
        [button setBackgroundImage:[UIImage stretchImageWithName:Bgicon] forState:UIControlStateNormal];
        
        [button setBackgroundImage:[UIImage stretchImageWithName:selectIcon] forState:UIControlStateSelected];
        [button addTarget:self action:@selector(itemBottomClick:) forControlEvents:UIControlEventTouchDown];
    }
    // 调整item的边框
    
    [self adjustDockItemsFrame:titleLayout];
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
- (void)itemBottomClick:(UIButton *)btn
{

    _currentBtn.selected = NO;
    btn.selected = YES;
    _currentBtn = btn;
    if (_isSelectClickBlock) {
        _isSelectClickBlock(btn,btn.tag);
    }
    if ([_delegate respondsToSelector:@selector(didSelectItem:)]) {
        [_delegate didSelectItem:btn.tag];
    }
}
#pragma mark 调整item的边框
- (void)adjustDockItemsFrame:(ItemTitleLayout)titleLayout
{
    int count = self.subviews.count;
    
    // 算出item的尺寸
    CGFloat itemWidth = self.frame.size.width / count;
    CGFloat itemHeight = self.frame.size.height;
    
    for (int i = 0; i<count; i++) {
        //取出子控件
        UIButton *btn = self.subviews[i];
        
        // 算出边框
        btn.frame = CGRectMake(i * itemWidth, 0, itemWidth, itemHeight);

        if (titleLayout == ItemTitleLayoutBottom) {
            if (i == 0) { // 第0个item选中
                btn.selected = YES;
                _currentBtn = btn;
            }
        }
        
        //设置item的tag
        btn.tag = i;
    }
}

@end