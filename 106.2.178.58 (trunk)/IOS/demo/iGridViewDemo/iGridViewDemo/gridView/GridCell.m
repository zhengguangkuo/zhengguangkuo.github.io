//
//  GridCell.m
//  iGridViewDemo
//
//  Created by HWG on 14-2-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "GridCell.h"
#import "Shop.h"
#import "ImageButton.h"

#define kTagPrefix 10

@implementation GridCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        // 按钮宽度
        CGFloat btnWidth = self.contentView.bounds.size.width / kColumn;
        
        for (int i = 0; i<kColumn; i++) {
            ImageButton *btn = [[ImageButton alloc] init];
            btn.tag = kTagPrefix + i;
            [btn addTarget:self action:@selector(itemClick:) forControlEvents:UIControlEventTouchUpInside];
            btn.frame = CGRectMake(btnWidth * i, 0, btnWidth, kCellHeight);
            //btn.backgroundColor = [UIColor redColor];
            [self.contentView addSubview:btn];
        }
        
    }
    
    return self;
}

- (void)setRowShops:(NSArray *)shops
{
    int count = shops.count;
    for (int i = 0; i < kColumn; i ++) {
        ImageButton *btn = (ImageButton *)[self.contentView viewWithTag:kTagPrefix + i];
        
        //设置数据
        if (i < count) {
            btn.hidden = NO;
            Shop *shop = [shops objectAtIndex:i];
            //设置背景上的图片
            [btn setImage:[UIImage imageNamed:shop.icon] forState:UIControlStateNormal];
            [btn setTitle:shop.name forState:UIControlStateNormal];
        }else{
            btn.hidden = YES;
        }
    }
    
    
}
- (void)itemClick:(ImageButton *)item
{
    _blcok(item);
}
@end
