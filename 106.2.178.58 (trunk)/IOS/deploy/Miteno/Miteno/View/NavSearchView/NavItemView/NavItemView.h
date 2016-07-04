//
//  NavItemView.h
//  Miteno
//
//  Created by wg on 14-4-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum{
    kSignInButtonMap=100,   //地图搜索
    kSignInButtonContent,    //内容搜索
    kSignInButtonRegion     //区域搜索
}kSignInButton;
//@class NavItemButton;

@interface NavItemButton : UIButton

@end

@interface NavItemView : UIView
@property (nonatomic, strong) UIButton  * mapItem;  //地图搜索按钮
@property (nonatomic, strong) UIButton  * textItem; //文字搜索按钮
@property (nonatomic, strong) UIButton  * cityItem; //文字搜索按钮
@property (nonatomic, strong) UIButton  * areaItem; //区域搜索按钮
@property (nonatomic, strong) UIButton  * help;
@property (nonatomic, strong) UILabel   * theme;


//标题
- (id)themeWithFrame:(CGRect)frame title:(NSString *)title flag:(NSInteger)flag;
//城市选择
- (id)itemWithFrame:(CGRect)frame icon:(NSString *)icon title:(NSString *)title;

/*
 * discard
 */
- (id)itemWithFrame:(CGRect)frame LeftIcon:(NSString *)leftIcon isRithtTitle:(BOOL)isRithtTitle  rithtTitle:(NSString *)rithtTitle rightIcon:(NSString *)rightIcon;
- (id)itemWithFrame:(CGRect)frame icon:(NSString *)icon;


@end
