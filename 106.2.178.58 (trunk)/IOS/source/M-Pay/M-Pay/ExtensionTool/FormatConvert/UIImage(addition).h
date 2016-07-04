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

+ (UIImage *)scaleToSize:(UIImage *)img size:(CGSize)newsize;

+ (UIImage *)generateFromColor:(UIColor *)color;

@end
