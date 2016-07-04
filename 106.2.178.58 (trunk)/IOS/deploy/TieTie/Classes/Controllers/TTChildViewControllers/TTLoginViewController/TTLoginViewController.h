//
//  TTLoginViewController.h
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
typedef void (^completionBlock)(void);
@interface TTLoginViewController : TTRootViewController
- (void)loginUserInContentServer:(NSString *)userName pwd:(NSString*)passWord completion:(completionBlock)block;

@property (nonatomic)BOOL isGoHome;
@end
