//
//  UIImage(addition).h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UIImage(addition)

+ (id)UIImageScretchImage:(NSString*)imageName;

+ (id)UIImageResize:(UIImage *)img up:(CGFloat)up left:(CGFloat)left down:(CGFloat)down right:(CGFloat)right;

+ (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)newsize;
//纯色图片
+ (UIImage *)generateFromColor:(UIColor *)color;

//可填充的缩略图
+ (UIImage *)fillimage:(UIImage *)image fillSize:(CGSize) viewsize;

//+(UIImage *)imageRotatedByRadians:(CGFloat)radians;

+ (UIImage *)RotationImage:(UIImage *)img Rotate:(CGFloat)Rotate;
@end
