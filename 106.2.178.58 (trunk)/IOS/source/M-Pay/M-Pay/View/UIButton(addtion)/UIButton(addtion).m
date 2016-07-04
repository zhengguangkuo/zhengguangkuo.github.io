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
    [btn setBackgroundImage:imgPress forState:UIControlEventTouchUpInside];
    
    [btn addTarget:target action:action forControlEvents:UIControlEventTouchUpInside];
    return btn;
}

@end
