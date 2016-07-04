//
//  TTRootViewController.h
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTRootViewController : UIViewController

/*
 *      贴贴
 */
@property (nonatomic, strong) NSString *  loginText;
- (void)showLoading:(BOOL)show;

//toast
- (void)makeTopToast:(NSString *)message;
- (void)makeBottomToast:(NSString *)message;

- (void)backToPrevious;
@end
