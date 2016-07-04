//
//  TTAlertView.h
//  Miteno
//
//  Created by wg on 14-6-6.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
//城市保存
#define TTCityFilePath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTCitysPlist]
@class AlertHeadTitleView;

@protocol TTAlertViewDelegate <NSObject>
@optional
- (void)getCityName:(NSString *)cityName;
- (void)isShowAlertView;
@end

@interface TTAlertView : UIView
@property (nonatomic, strong)UIView *bgView;
@property (nonatomic, weak) id <TTAlertViewDelegate> deletage;
@property (nonatomic, strong) AlertHeadTitleView    * alertHeadTitleView;
- (void)show:(UIViewController *)ctrl;
- (void)dismiss;
@end
