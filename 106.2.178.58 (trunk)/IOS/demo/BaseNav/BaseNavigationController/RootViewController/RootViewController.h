//
//  RootViewController.h
//  BaseNavigationController
//
//  Created by HWG on 14-1-17.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import <UIKit/UIKit.h>
/*
 *  导航栏标题按钮
 */
typedef enum {
    KNavigationBarButtonTitle = 100100,
}KNavigationBarButton;
@interface RootViewController : UIViewController
@property (nonatomic, strong) UIButton * leftButton;        //导航栏左按钮
@property (nonatomic, strong) UIButton * midButton;        //导航栏左按钮
@property (nonatomic, strong) UIButton * rightButton;       //导航栏右按钮
@property (nonatomic, strong) UIButton * searchButton;      //导航栏上的搜索按钮
/*
 *  初始化导航栏按钮
 *  leftImageName   为左按钮背景图片
 *  rightImageName  为右按钮背景图片
 */
- (void)initNavigationBarLeftItem:(NSString *)leftImageName RightItem:(NSString *)rightImageName;

/*
 *  初始化导航栏标题视图
 *  titleString      为标题名称
 *  titleButtonImage 为按钮背景图片
 */
- (void)initNavigationBarTitle:(NSString *)titleString titleButton:(NSString *)titleButtonImage;
@end
