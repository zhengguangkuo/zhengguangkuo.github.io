//
//  TTTabar.m
//  TTSecondListView
//
//  Created by wg on 14-8-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTopDock.h"
#define tabarHeihght    44
#import "TTTopButton.h"
@interface TTTopDock()
{
    TTTopButton    *_currentBtn;
}
@end
@implementation TTTopDock

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        
        [self addChildView];

    }
    return self;
}
- (void)addChildView
{
    _regionItem = [[TTTopButton alloc] initWithFrame:CGRectMake(0, 0, 320/2, tabarHeihght)];

//    [_regionItem setTitle:@"不限分类" forState:UIControlStateNormal];
    _regionItem.tag = TabarButtonTypeLeft;
    [self setBg:_regionItem];
    [self addSubview:_regionItem];
    
    _categoryItem = [[TTTopButton alloc] initWithFrame:CGRectMake(_regionItem.frame.size.width, 0, 320/2, tabarHeihght)];
//    [_categoryItem setTitle:@"不限地区" forState:UIControlStateNormal];
    _categoryItem.tag = TabarButtonTypeRight;
    [self setBg:_categoryItem];
    [self addSubview:_categoryItem];

}
- (void)setBg:(TTTopButton *)btn
{
    [btn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [btn setImage:[UIImage imageNamed:@"control_d"]forState:UIControlStateNormal];
    [btn setImage:[UIImage imageNamed:@"control_u"] forState:UIControlStateSelected];
    
    [btn setBackgroundImage:[UIImage stretchImageWithName:@"dockbg"] forState:UIControlStateNormal];
//    [btn setBackgroundImage:[UIImage imageNamed:@"bg01"] forState:UIControlStateSelected];
    
    btn.adjustsImageWhenHighlighted = NO;

    [btn addTarget:self action:@selector(clickBtn:) forControlEvents:UIControlEventTouchUpInside];
}
- (void)clickBtn:(TTTopButton *)btn
{
   if (btn.tag == TabarButtonTypeLeft) {
        _categoryItem.selected = NO;
        btn.selected = !btn.isSelected;
    }else{
        _regionItem.selected = NO;
   btn.selected = !btn.isSelected;
    }
    //传参
    if (_itemClickBlock) {
        _itemClickBlock(btn);
    }
}
+ (UIImage *)stretchImageWithName:(NSString *)name
{
    UIImage *image = [UIImage imageNamed:name];
    
    return [image stretchableImageWithLeftCapWidth:image.size.width * 0.5 topCapHeight:image.size.height * 0.5];
}
@end
