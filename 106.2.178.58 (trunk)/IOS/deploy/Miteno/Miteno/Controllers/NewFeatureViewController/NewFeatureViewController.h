//
//  NewFeatureViewController.h
//  Miteno
//
//  Created by wg on 14-3-28.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"
#import "UIButton+Bg.h"

@interface NewFeatureViewController : TTRootViewController<UIScrollViewDelegate>
@property (nonatomic, copy) void (^startBlock)(BOOL share);
@end
