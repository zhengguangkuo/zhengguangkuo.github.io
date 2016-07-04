//
//  RootViewController.h
//  WG_lottery(彩票)
//
//  Created by wg on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SystemDialog.h"
#import "Reachability.h"
@interface TTRootViewController : UIViewController

@property (nonatomic, strong) NSString *  loginText;
- (void)SetNaviationRightButtons:(NSArray*)buttons;

- (void)backToPrevious;

- (void)NavigationViewBackBtn;

/*
 *      贴贴
 */
- (void)showLoading:(BOOL)show;
//优惠劵下载按钮删除
- (void)removeDown;
//重新定位获取最新位置
- (void)getUserLocation;

//根据当前位置获取城市列表数据
//[main getUserLocation];
//[main loadCitylist];
@end
