//
//  TTHomeViewController.h
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
#import "TTGridView.h"
@interface TTHomeViewController : TTRootViewController
@property (nonatomic, strong)TTGridView * girdView;
- (void)upDateHomeView;
@end
