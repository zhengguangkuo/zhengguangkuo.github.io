//
//  TextStyle.h
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TextStyle : NSObject

+ (CGFloat)deriveTextWidth:(NSString*)text  height:(CGFloat)height font:(UIFont*)font;

+ (CGFloat)deriveTextHeight:(NSString*)text width:(CGFloat)width font:(UIFont*)font;

+ (void)alignLabelWithTop:(UILabel *)label;

@end
