//
//  UIImage+Image.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Image)
+ (UIImage *)fullscreenImageWithName:(NSString *)name;
// 返回一张已经经过拉伸处理的图片
+ (UIImage *)stretchImageWithName:(NSString *)name;


+ (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)newsize;
//设置圆角图片
+ (id)createRoundedRectImage:(UIImage*)image size:(CGSize)size radius:(NSInteger)r;
//图片裁剪
+ (UIImage*) OriginImage:(UIImage *)image scaleToSize:(CGSize)size;
+ (UIImage*)scaleImage:(UIImage *)image size:(CGSize )size;
@end
