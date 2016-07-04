//
//  UIImage(addition).m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "UIImage(addition).h"

@implementation UIImage(addition)

+ (id)UIImageScretchImage:(NSString*)imageName
{
    UIImage* tempImage = [UIImage imageNamed:imageName];
    NSInteger leftCapWidth = tempImage.size.width * 0.5f;
    NSInteger topCapHeight = tempImage.size.height * 0.5f;
    UIImage* bgImage = [tempImage stretchableImageWithLeftCapWidth:leftCapWidth topCapHeight:topCapHeight];
    return bgImage;
}


+ (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)newsize
{
    // 创建一个bitmap的context
    // 并把它设置成为当前正在使用的context
    UIGraphicsBeginImageContext(newsize);
    // 绘制改变大小的图片
    [img drawInRect:CGRectMake(0, 0, newsize.width, newsize.height)];
    // 从当前context中创建一个改变大小后的图片
    UIImage* scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    // 使当前的context出堆栈
    UIGraphicsEndImageContext();
    // 返回新的改变大小后的图片
    return scaledImage;
}
@end
