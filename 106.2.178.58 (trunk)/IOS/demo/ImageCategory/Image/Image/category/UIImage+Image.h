//
//  UIImage+Image.h
//  UIImageDemo
//
//  Created by HWG on 14-1-13.
//  Copyright (c) 2014年 miteno. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Image)
/*
 *  根据屏幕尺寸返回全屏的图片
 */
+ (UIImage *)fullscreenImageWithName:(NSString *)name;

/*
 *  返回一张已经经过拉伸处理的图片
 */
+ (UIImage *)stretchImageWithName:(NSString *)name;

/*
 * 等比例缩放
 */
+ (UIImage *)scaleImage:(UIImage *)image toScale:(float)scaleSize;

/*
 *截取
 */
- (UIImage *)imageFromImage:(UIImage *)image inRect:(CGRect)rect;

/*
 * 截屏
 */
- (UIImage *)screenShotsImage :(UIView *)currentView;

/*
 *  自定size
 */
- (UIImage *)reSizeImage:(UIImage *)image toSize:(CGSize)reSize;

/*
 * 图片存储沙盒
 */
- (void)saveSandBoxImage:(UIImage *)image name:(NSString *)name;

@end
#pragma mark -系统自带常用
/*
 一、在Iphone上读取图片数据的简单方法
 
 1.返回的图片数据量较小，参数:图片的引用、压缩系数 (一般使用在对图片清晰度不是很高的情况下使用)
 UIImageJPEGRepresentation(<#UIImage *image#>, <#CGFloat compressionQuality#>)
 
 2.返回的图片数据量大
 UIImagePNGRepresentation(<#UIImage *image#>)
 */


#pragma mark - 利用第三方SDWebImage 进行图片数据处理的一些常用方法
/*
SDWebImage 图片异步加载及缓存
 
SDWebImage用于异步下载网络上的图片，并支持对图片的缓存等。
多数情况下是使用UIImageView+WebCache为UIImageView异步加载图片：
 
#import <SDWebImage/UIImageView+WebCache.h>
 
 1.加载图片方式一
 [cell.imageView setImageWithURL:[NSURL URLWithString:@"http://www.domain.com/path/to/image.jpg"] placeholderImage:[UIImage imageNamed:@"placeholder.png"]];
需要注意的是，pladeholderImage的大小一定要大于UIImageView的大小，否则可能不显示placeholderImage图片。
 2.加载方式二 利用block
[cell.imageView setImageWithURL:[NSURL URLWithString:@"http://www.domain.com/path/to/image.jpg"]               placeholderImage:[UIImage imageNamed:@"placeholder.png"]                      completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType) {... completion code here ...}];
 
 3.加载方式三 SDWebImageManager
SDWebImageManager *manager = [SDWebImageManager sharedManager];
 [manager downloadWithURL:imageURL options:0 progress:^(NSUInteger receivedSize, long long expectedSize){
                    // 下载进度
            }completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType)                 { if (image){
                    // 下载完成
    } }];
    或者使用Image Downloader也是一样的效果：
    [SDWebImageDownloader.sharedDownloader downloadImageWithURL:imageURL options:0       progress:^(NSUInteger receivedSize, long long expectedSize){ 
                // 进度
        }completed:^(UIImage *image, NSData *data, NSError *error, BOOL finished)       {if (image && finished) {
                // 下载完成
    } }];
*/