//
//  RootViewController.m
//  BaseNavigationController
//
//  Created by HWG on 14-1-17.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import "RootViewController.h"
@interface RootViewController()

@end
@implementation RootViewController
#pragma mark -
#pragma mark - 初始化导航栏
/*
 *  初始化导航栏按钮
 *  leftImageName   为左按钮背景图片
 *  rightImageName  为右按钮背景图片
 */
- (void)initNavigationBarLeftItem:(NSString *)leftImageName RightItem:(NSString *)rightImageName
{
    if (leftImageName != nil && leftImageName.length != 0) {
        _leftButton = [UIButton buttonWithType:UIButtonTypeCustom];
        _leftButton.frame = CGRectMake(0, 0, 44, 44);
        _leftButton.showsTouchWhenHighlighted = YES;
        [_leftButton setImage:[UIImage imageNamed:leftImageName] forState:UIControlStateNormal];
        //    [_leftButton addTarget:self action:@selector(showLeftController) forControlEvents:UIControlEventTouchUpInside];
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_leftButton];
    }
    
    if (rightImageName != nil && rightImageName.length != 0) {
        _rightButton = [UIButton buttonWithType:UIButtonTypeCustom];
        _rightButton.frame = CGRectMake(0, 0, 44, 44);
        _rightButton.showsTouchWhenHighlighted = YES;
        
        //后期部分导航栏右边变成2个按钮  需要传入的两张图片名称之间用:隔开即可
        NSArray * array = [rightImageName componentsSeparatedByString:@":"];
        
        if ([array count] == 1) {
            /*
             *  如果右Item上只有1个按钮时的处理
             */
            [_rightButton setImage:[UIImage imageNamed:rightImageName] forState:UIControlStateNormal];
            //    [_rightButton addTarget:self action:@selector(showRightController) forControlEvents:UIControlEventTouchUpInside];
            self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:_rightButton];
            
        } else if ([array count] == 2) {
            /*
             *  如果右Item上有2个按钮时的处理
             */
            UIView * rightItmeView = [[UIView alloc] init];
            rightItmeView.frame = CGRectMake(0, 0, 80, 44);
            
            _searchButton = [UIButton buttonWithType:UIButtonTypeCustom];
            _searchButton.frame = CGRectMake(0, 0, 45, 44);
            _searchButton.showsTouchWhenHighlighted = YES;
            [_searchButton setImage:[UIImage imageNamed:[array objectAtIndex:0]] forState:UIControlStateNormal];
            _rightButton.frame = CGRectMake(45, 0, 35, 44);
            [_rightButton setImage:[UIImage imageNamed:[array objectAtIndex:1]] forState:UIControlStateNormal];
            [rightItmeView addSubview:_searchButton];
            [rightItmeView addSubview:_rightButton];
            
            self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:rightItmeView];
        }else if([array count] == 3){
            /*
             *  如果右Item上有2个按钮时的处理
             */
            UIView * rightItmeView = [[UIView alloc] init];
            rightItmeView.frame = CGRectMake(0, 0, 80, 44);
            
            _searchButton = [UIButton buttonWithType:UIButtonTypeCustom];
            _searchButton.frame = CGRectMake(0, 0, 29, 44);
            _searchButton.showsTouchWhenHighlighted = YES;
            [_searchButton setImage:[UIImage imageNamed:[array objectAtIndex:0]] forState:UIControlStateNormal];
            
            _midButton = [UIButton buttonWithType:UIButtonTypeCustom];
            _midButton.frame = CGRectMake(29, 0, 29, 44);
            _midButton.showsTouchWhenHighlighted = YES;
            [_midButton setImage:[UIImage imageNamed:[array objectAtIndex:0]] forState:UIControlStateNormal];
            
            _rightButton.frame = CGRectMake(58, 0, 29, 44);
            [_rightButton setImage:[UIImage imageNamed:[array objectAtIndex:1]] forState:UIControlStateNormal];
            
            [rightItmeView addSubview:_midButton];
            [rightItmeView addSubview:_searchButton];
            [rightItmeView addSubview:_rightButton];
            
            
            
            self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:rightItmeView];
        }
    }
}
@end
