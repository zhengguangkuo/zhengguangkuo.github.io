//
//  UINavigationItem+TTItem.h
//  WG_lottery(彩票)
//
//  Created by wg on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UINavigationItem (TTItem)
//替换所有的item
- (void)copyFromItem:(UINavigationItem *)other;
@end
