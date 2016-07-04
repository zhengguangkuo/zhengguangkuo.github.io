//
//  UINavigationItem+MTItem.m
//  WG_lottery(彩票)
//
//  Created by wg on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "UINavigationItem+MTItem.h"

@implementation UINavigationItem (MTItem)
- (void)copyFromItem:(UINavigationItem *)other
{
    self.leftBarButtonItem = other.leftBarButtonItem;
    self.leftBarButtonItems = other.leftBarButtonItems;
    self.rightBarButtonItem = other.rightBarButtonItem;
    self.rightBarButtonItems = other.rightBarButtonItems;
    self.titleView = other.titleView;
    self.title = other.title;
}
@end
