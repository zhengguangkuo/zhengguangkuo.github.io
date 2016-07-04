//
//  TextStyle.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "TextStyle.h"

@implementation TextStyle

//字符串宽度
+ (CGFloat)deriveTextWidth:(NSString*)text  height:(CGFloat)height font:(UIFont*)font
{
    CGSize titleSize = [text sizeWithFont:font constrainedToSize:CGSizeMake(MAXFLOAT, height)];
    return titleSize.width;
}

//字符串高度
+ (CGFloat)deriveTextHeight:(NSString*)text width:(CGFloat)width font:(UIFont*)font
{
    CGSize titleSize = [text sizeWithFont:font
                        constrainedToSize:CGSizeMake(width, MAXFLOAT)
                            lineBreakMode:UILineBreakModeWordWrap];
    return titleSize.height;
}

//根据文本获取默认宽高
+ (CGSize)deriveTextSize:(NSString*)text
{
    CGSize titleSize = [text sizeWithFont:[UIFont systemFontOfSize:14.0]
                        constrainedToSize:CGSizeMake(220, 100)
                            lineBreakMode:UILineBreakModeWordWrap];
    return titleSize;
}

//文字顶端对齐
+ (void)alignLabelWithTop:(UILabel *)label
{
    CGSize maxSize = CGSizeMake(label.frame.size.width, 999);
    label.adjustsFontSizeToFitWidth = NO;
    CGSize actualSize = [label.text sizeWithFont:label.font constrainedToSize:maxSize lineBreakMode:label.lineBreakMode];
    CGRect rect = label.frame;
    rect.size.height = actualSize.height;
    label.frame = rect;
}

@end
