//
//  UIImage(addition).m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//
#define DegreesToRadians(x) (M_PI*(x)/180.0)


#import "UIImage(addition).h"

@implementation UIImage(addition)

//可拉伸范围leftCapWidth与topCapHeight后边一个像素
+ (id)UIImageScretchImage:(NSString*)imageName
{
    
    UIImage* tempImage = [UIImage imageNamed:imageName];
    NSInteger leftCapWidth = tempImage.size.width * 0.2f;
    NSInteger topCapHeight = tempImage.size.height * 0.2f;
    UIImage* bgImage = [tempImage stretchableImageWithLeftCapWidth:leftCapWidth topCapHeight:topCapHeight];
    return bgImage;
}

//中间被拉伸，边缘不拉伸
+ (id)UIImageResize:(UIImage *)img up:(CGFloat)up left:(CGFloat)left down:(CGFloat)down right:(CGFloat)right
{
    UIEdgeInsets insets = UIEdgeInsetsMake(up, left, down, right);
    return [img resizableImageWithCapInsets:insets];
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
//纯色图片
+ (UIImage *)generateFromColor:(UIColor *)color
{
    CGRect rect = CGRectMake(0, 0, 1, 1);
    UIGraphicsBeginImageContext(rect.size);
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetFillColorWithColor(context,
                                   [color CGColor]);
    CGContextFillRect(context, rect);
    UIImage * imge;// = [[UIImage alloc] init];
    imge = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return imge;
}

//可填充的缩略图
+ (UIImage *)fillimage:(UIImage *)image fillSize:(CGSize) viewsize
{
    CGSize size = image.size;
    
    CGFloat scalex = viewsize.width / size.width;
    CGFloat scaley = viewsize.height / size.height;
    CGFloat scale = MAX(scalex, scaley);
    
    UIGraphicsBeginImageContext(viewsize);
    
    CGFloat width = size.width * scale;
    CGFloat height = size.height * scale;
    
    float dwidth = ((viewsize.width - width) / 2.0f);
    float dheight = ((viewsize.height - height) / 2.0f);
    
    CGRect rect = CGRectMake(dwidth, dheight, size.width * scale, size.height * scale);
    [image drawInRect:rect];
    
    UIImage *newimg = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return newimg;
}

+ (UIImage *)RotationImage:(UIImage *)img Rotate:(CGFloat)Rotate
{
    CGSize size = img.size;
    UIGraphicsBeginImageContext(size);
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    CGContextTranslateCTM(ctx, img.size.width, img.size.width);
    CGContextRotateCTM(ctx, DegreesToRadians(Rotate));
    CGContextDrawImage(UIGraphicsGetCurrentContext(), CGRectMake(0,0,size.width, size.height),[img CGImage]);
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}


//- (UIImage *)imageRotatedByRadians:(CGFloat)radians
//{
//    return [self imageRotatedByDegrees:DegreesToRadians(radians)];
//}
//- (UIImage *)imageRotatedByDegrees:(CGFloat)degrees
//{
//    // calculate the size of the rotated view's containing box for our drawing space
//    UIView *rotatedViewBox = [[UIView alloc] initWithFrame:CGRectMake(0,0,self.size.width, self.size.height)];
//    CGAffineTransform t = CGAffineTransformMakeRotation(DegreesToRadians(degrees));
//    rotatedViewBox.transform = t;
//    CGSize rotatedSize = rotatedViewBox.frame.size;
//    
//    // Create the bitmap context
//    UIGraphicsBeginImageContext(rotatedSize);
//    CGContextRef bitmap = UIGraphicsGetCurrentContext();
//    
//    // Move the origin to the middle of the image so we will rotate and scale around the center.
//    CGContextTranslateCTM(bitmap, rotatedSize.width/2, rotatedSize.height/2);
//    
//    //   // Rotate the image context
//    CGContextRotateCTM(bitmap, DegreesToRadians(degrees));
//    
//    // Now, draw the rotated/scaled image into the context
//    CGContextScaleCTM(bitmap, 1.0, -1.0);
//    CGContextDrawImage(bitmap, CGRectMake(-self.size.width / 2, -self.size.height / 2, self.size.width, self.size.height), [self CGImage]);
//    
//    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
//    UIGraphicsEndImageContext();
//    return newImage;
//    
//}

@end
