//
//  UIImage+Image.m
//  UIImageDemo
//
//  Created by HWG on 14-1-13.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import "UIImage+Image.h"

@implementation UIImage (Image)

//返回全屏图片
+ (UIImage *)fullscreenImageWithName:(NSString *)name
{
    if (iPhone5) {
        // 1.获取没有拓展名的文件名
        NSString *filename = [name stringByDeletingPathExtension];
        
        // 2.拼接-568h@2x
        filename = [filename stringByAppendingString:@"-568h@2x"];
        
        // 3.拼接拓展名
        NSString *extension = [name pathExtension];
        
        //4.生成新的文件名
        name = [filename stringByAppendingPathExtension:extension];
        
    }
    return [UIImage imageNamed:name];
}

//图片拉伸处理
+ (UIImage *)stretchImageWithName:(NSString *)name
{
    UIImage *image = [UIImage imageNamed:name];
    return [image stretchableImageWithLeftCapWidth:image.size.width * 0.5 topCapHeight:image.size.height * 0.5];
}

//等比例缩放
+ (UIImage *)scaleImage:(UIImage *)image toScale:(float)scaleSize
{
    CGSize size = image.size;
    //开始绘制
    UIGraphicsBeginImageContext(CGSizeMake(size.width *scaleSize,size.height *scaleSize));
    [image drawInRect:CGRectMake(0, 0, size.width *scaleSize, size.height *scaleSize)];
    UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
    //结束
    UIGraphicsEndImageContext();
    return scaledImage;
}
//存储图片至沙盒
- (void)saveSandBoxImage:(UIImage *)image name:(NSString *)name
{
    NSString *path = [[NSHomeDirectory()stringByAppendingPathComponent:@"Documents"]stringByAppendingPathComponent:@"image.png"];
    [UIImagePNGRepresentation(image) writeToFile:path atomically:YES];
}
//自定长宽
- (UIImage *)reSizeImage:(UIImage *)image toSize:(CGSize)reSize
{
    UIGraphicsBeginImageContext(CGSizeMake(reSize.width, reSize.height));
    [image drawInRect:CGRectMake(0, 0, reSize.width, reSize.height)];
    UIImage *reSizeImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return reSizeImage;
}
//截取
- (UIImage *)imageFromImage:(UIImage *)image inRect:(CGRect)rect {
    
    CGImageRef sourceImageRef = [image CGImage];
    
    CGImageRef newImageRef = CGImageCreateWithImageInRect(sourceImageRef, rect);
    
    UIImage *newImage = [UIImage imageWithCGImage:newImageRef];
    
    return newImage;
    
}
//截屏
- (UIImage *)screenShotsImage :(UIView *)currentView
{
     //currentView = self.view
    UIGraphicsBeginImageContext(currentView.bounds.size);
    [currentView.layer renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return image;
}
@end

