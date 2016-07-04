//
//  NavItemView.m
//  Miteno
//
//  Created by wg on 14-4-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "NavItemView.h"
#define kLeftimgFrame(name) name.imageEdgeInsets = UIEdgeInsetsMake(0, -20, 0, 0)
#define kRightimgFrame(name) name.imageEdgeInsets = UIEdgeInsetsMake(0,0, 0, -10)

#define kBtnWidth   20
#define KBtnHeight  21

@implementation  NavItemButton
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentLeft;
        self.titleLabel.font = [UIFont systemFontOfSize:16];
//        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
//        self.backgroundColor = [UIColor greenColor];
    }
    return self;
}
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(20,0, contentRect.size.width/2+10,contentRect.size.height);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(contentRect.size.width/2+30,contentRect.size.height/3 + 3,7,7);
}
- (void)setBounds:(CGRect)bounds
{
    CGRect btn = bounds;
    btn.size.width += 20;
    bounds = btn;
    [super setBounds:bounds];
}
@end

@interface NavItemView ()
{
    NavItemButton   *_navItemButton;
}
@end

@implementation NavItemView

//- (id)initWithFrame:(CGRect)frame
//{
//    self = [super initWithFrame:frame];
//    if (self) {
//        // Initialization code
////        [self addNavRightViews];
//    }
//    return self;
//}
/*
//导航rightItem
- (void)addNavRightViews
{
    CGFloat w = 30;
    CGFloat h = 30;
   _mapItem = [UIButton buttonWithType:UIButtonTypeCustom];
    _mapItem.frame = CGRectMake(0, 0, w, h);
    _mapItem.tag = kSignInButtonMap;
    [_mapItem setImage:[UIImage stretchImageWithName:@"Location.png"] forState:UIControlStateNormal];
    _textItem = [UIButton buttonWithType:UIButtonTypeCustom];
    _textItem.frame = CGRectMake(w+5, 0, w, h);
    _textItem.tag = kSignInButtonContent;
    [_textItem setImage:[UIImage imageNamed:@"Search.png"] forState:UIControlStateNormal];
    _areaItem = [UIButton buttonWithType:UIButtonTypeCustom];
    _areaItem.frame = CGRectMake(2*w+15, 0, w, h);
    _areaItem.tag = kSignInButtonRegion;
    
    [_areaItem setImage:[UIImage imageNamed:@"List.png"] forState:UIControlStateNormal];
    [self addSubview:_mapItem];
    [self addSubview:_textItem];
    [self addSubview:_areaItem];
}
*/

//cityItem
- (id)itemWithFrame:(CGRect)frame icon:(NSString *)icon title:(NSString *)title
{
    if ([super initWithFrame:frame]) {
        
        _cityItem = [[NavItemButton alloc] init];
//        _cityItem.backgroundColor = [UIColor redColor];
        _cityItem.backgroundColor = [UIColor clearColor];
        _cityItem.frame = CGRectMake(0, 0,40,44);
//        if(IOS7)kRightimgFrame(_cityItem);
        [_cityItem setTitle:title forState:UIControlStateNormal];
        [_cityItem setBackgroundImage:[UIImage imageNamed:icon] forState:UIControlStateNormal];
        [self addSubview:_cityItem];
        self.backgroundColor = [UIColor blackColor];
    }
    return self;
    
}

- (id)itemWithFrame:(CGRect)frame icon:(NSString *)icon
{
    if ([super initWithFrame:frame]) {
        _mapItem = [[UIButton alloc] init];
        _mapItem.backgroundColor = [UIColor clearColor];
        _mapItem.frame = CGRectMake(0, 0, kBtnWidth+10, KBtnHeight);
        _mapItem.tag = kSignInButtonMap;
        if(IOS7)kLeftimgFrame(_mapItem);
        [_mapItem setImage:[UIImage stretchImageWithName:icon] forState:UIControlStateNormal];
        [self addSubview:_mapItem];
    }
    return self;

}
- (id)itemWithFrame:(CGRect)frame LeftIcon:(NSString *)leftIcon isRithtTitle:(BOOL)isRithtTitle  rithtTitle:(NSString *)rithtTitle rightIcon:(NSString *)rightIcon
{
     if ([super initWithFrame:frame]) {
         self.backgroundColor = [UIColor clearColor];
        CGFloat w = 30;
        CGFloat h = 44;
     

        _mapItem = [[UIButton alloc] init];
        _mapItem.frame = CGRectMake(0, 0, w, h);
        _mapItem.tag = kSignInButtonMap;
//         _mapItem.backgroundColor = [UIColor redColor];
//       _mapItem.imageEdgeInsets = UIEdgeInsetsMake(0, 0, 0, 20);
        [_mapItem setImage:[UIImage stretchImageWithName:leftIcon] forState:UIControlStateNormal];
         
       if(IOS7)kLeftimgFrame(_mapItem);
         if(isRithtTitle==NO){
             if(IOS7)kRightimgFrame(_mapItem);
             _mapItem.frame = CGRectMake(10, 0, w, h);
             
            CGRect frame = CGRectMake(w+15, 0, w, h);
             _textItem = [UIButton buttonWithType:UIButtonTypeCustom];
             _textItem.frame = frame;
             _textItem.backgroundColor = [UIColor clearColor];
             _textItem.tag = kSignInButtonContent;
             [_textItem setImage:[UIImage stretchImageWithName:rightIcon] forState:UIControlStateNormal];
             if(IOS7) kRightimgFrame(_textItem);
             [self addSubview:_textItem];
         }else{
             if (rightIcon==nil) {
                 _areaItem = [UIButton buttonWithType:UIButtonTypeCustom];
                 _areaItem.frame = CGRectMake(w, 0, w, h);
                 _areaItem.backgroundColor = [UIColor clearColor];
                 [_areaItem setTitle:rithtTitle forState:UIControlStateNormal];
                 [self addSubview:_areaItem];
             }else{
//             CGRect frame = CGRectMake(w-5, 0, w+40, h);
//             _cityItem = [[NavItemButton alloc] init];
//             _cityItem.backgroundColor = [UIColor clearColor];
//             _cityItem.frame = frame;
////                 if (isRithtTitle==YES) {
////                     if(IOS7) kLeftimgFrame(_cityItem);
////                 }else{
////                 
////                     if(IOS7) kRightimgFrame(_cityItem);
////                 }
//             [_cityItem setTitle:rithtTitle forState:UIControlStateNormal];
//             [_cityItem setImage:[UIImage stretchImageWithName:rightIcon] forState:UIControlStateNormal];
//             [self addSubview:_cityItem];
             }
         }
         [self addSubview:_mapItem];

     }
    return self;
}

- (id)themeWithFrame:(CGRect)frame title:(NSString *)title flag:(NSInteger)flag
{
    if([super initWithFrame:frame]){
        self.backgroundColor = [UIColor clearColor];
        UILabel *titleLabel = [[UILabel alloc] init];
        titleLabel.backgroundColor = [UIColor clearColor];
        titleLabel.text = title;
        titleLabel.numberOfLines = 0;
        titleLabel.font = TTNavTitleFont;
        CGSize size = CGSizeMake(MAXFLOAT,44);
        titleLabel.textColor = [UIColor whiteColor];
        CGSize labelSize = [titleLabel.text sizeWithFont:titleLabel.font constrainedToSize:size lineBreakMode:NSLineBreakByClipping];
        titleLabel.frame = CGRectMake(titleLabel.frame.origin.x+(flag==0?10:20),titleLabel.frame.origin.y+8, labelSize.width, labelSize.height);
        if (flag==0) {
            _help = [UIButton buttonWithType:UIButtonTypeInfoDark];
            _help.backgroundColor = [UIColor clearColor];
            _help.frame = CGRectMake(titleLabel.frame.size.width+15,8, 30,titleLabel.frame.size.height);
//            help.backgroundColor = [UIColor blackColor];
//            [_help setBackgroundImage:[UIImage imageNamed:@"helpIcon"] forState:UIControlStateNormal];

            [self addSubview:_help];
        }
        [self addSubview:titleLabel];
    }
    return self;
}


@end
