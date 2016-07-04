//
//  GridCell.h
//  iGridViewDemo
//
//  Created by HWG on 14-2-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ImageButton.h"
#define kColumn 4           //设置每行的个数

#define kCellHeight 100     //cell行的高度

@interface GridCell : UITableViewCell

//设置这行的所有数据
- (void)setRowShops:(NSArray *)shops;

@property (nonatomic, copy) void (^blcok) (ImageButton *);
@end
