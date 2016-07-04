#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>

@interface UIButton(addtion)

+ (UIButton *)ButtonWithParms:(UIColor*)titleColor title:(NSString*)title  bgnormal:(UIImage *)strImg  imgHighlight:(UIImage *)strImgHighlight target:(id)target action:(SEL)action;

@end
