//
//  TTDockItem.h
//  TieTie
//
//  Created by wg on 14-9-25.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTDockItem : UIButton
@property (nonatomic, copy) NSString *icon; 
@property (nonatomic, copy) NSString *selectedIcon;
- (void)setIcon:(NSString *)icon selectedIcon:(NSString *)selectedIcon;
+ (instancetype)dockItem;
@end
