//
//  TextStyle.m
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
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
