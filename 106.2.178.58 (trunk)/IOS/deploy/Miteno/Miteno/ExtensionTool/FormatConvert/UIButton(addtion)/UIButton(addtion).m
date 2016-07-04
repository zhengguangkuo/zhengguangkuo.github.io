#import "UIButton(addtion).h"


@implementation UIButton(addtion)


+ (UIButton *)ButtonWithParms:(UIColor*)titleColor title:(NSString*)title  bgnormal:(UIImage *)strImg  imgHighlight:(UIImage *)strImgHighlight target:(id)target action:(SEL)action
{
    UIImage *imgNormal = strImg;
    UIImage *imgPress = strImgHighlight;
    
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    [btn setTitle:title forState:UIControlStateNormal];

    [btn setTitleColor:titleColor forState:UIControlStateNormal];
    
    
    if(imgNormal!=nil)
    [btn setBackgroundImage:imgNormal forState:UIControlStateNormal];
    
    if(imgPress!=nil)
    [btn setBackgroundImage:imgPress forState:UIControlStateSelected];
    
    [btn addTarget:target action:action forControlEvents:UIControlEventTouchUpInside];
    return btn;
}


- (void)SetButtonStatus:(UIColor*)titleColor bgImage:(UIImage*)backGroundImage
{
    [self setTitleColor:titleColor forState:UIControlStateNormal];
    [self setBackgroundImage:backGroundImage forState:UIControlStateNormal];
}


- (void)SetTitleInsets:(CGFloat)up left:(CGFloat)left down:(CGFloat)down right:(CGFloat)right
{
    UIEdgeInsets insets = UIEdgeInsetsMake(up, left, down, right);
    [self setTitleEdgeInsets:insets];
}
@end
