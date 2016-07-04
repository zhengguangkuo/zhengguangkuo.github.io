#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>

@interface UIButton(addtion)

+ (UIButton *)ButtonWithParms:(UIColor*)titleColor title:(NSString*)title  bgnormal:(UIImage *)strImg  imgHighlight:(UIImage *)strImgHighlight target:(id)target action:(SEL)action;

- (void)SetButtonStatus:(UIColor*)titleColor bgImage:(UIImage*)backGroundImage;

- (void)SetTitleInsets:(CGFloat)up left:(CGFloat)left down:(CGFloat)down right:(CGFloat)right;

@end
