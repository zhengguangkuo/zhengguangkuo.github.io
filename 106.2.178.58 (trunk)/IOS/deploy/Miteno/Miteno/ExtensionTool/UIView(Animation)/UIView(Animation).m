#import "UIView(Animation).h"


@implementation UIView(Animation)

- (void)EaseIn
{
    CATransition *animation = [CATransition animation];
    [animation setDuration:0.7f];
    [animation setTimingFunction:[CAMediaTimingFunction
                                  functionWithName:kCAMediaTimingFunctionEaseIn]];
    [animation setType:kCATransitionFade];
    [animation setSubtype: kCATransitionFromLeft];
    [self.layer addAnimation:animation forKey:@"Reveal"];
}

- (void)EaseOut
{
    CATransition *animation = [CATransition animation];
    [animation setDuration:0.7f];
    [animation setTimingFunction:[CAMediaTimingFunction
                                  functionWithName:kCAMediaTimingFunctionEaseOut]];
    [animation setType:kCATransitionFade];
    [animation setSubtype: kCATransitionFromLeft];
    [self.layer addAnimation:animation forKey:@"Reveal"];
}

- (void)TurnPage
{
    [UIView beginAnimations:@"animationID1" context:nil];
    [UIView setAnimationDuration:0.7f];
    [UIView setAnimationCurve:UIViewAnimationCurveEaseInOut];
    [UIView setAnimationRepeatAutoreverses:NO];//设置是否自动反转当前的动画效果
    [UIView setAnimationTransition:UIViewAnimationTransitionFlipFromLeft forView:self cache:YES];//设置过渡的动画效果，此处第一个参数可
    [UIView commitAnimations];//提交动画
}


- (void)TransformerCube
{
    CATransition *animation = [CATransition animation];
    [animation setDuration:0.5f];
    [animation setTimingFunction:[CAMediaTimingFunction
                                  functionWithName:kCAMediaTimingFunctionEaseOut]];
    [animation setType:@"cube"];
    [animation setDelegate:self];
    animation.removedOnCompletion=NO;
    [animation setSubtype: kCATransitionFromLeft];
    [self.layer addAnimation:animation forKey:@"Reveal"];
}


- (void)RotiaTion:(ClockWiseType)type
{
    int nValue = (type==kClockWise?1:-1);
    [self.layer removeAllAnimations];
    CABasicAnimation* rotationAnimation;
    rotationAnimation = [CABasicAnimation animationWithKeyPath:@"transform.rotation.z"];
    rotationAnimation.fromValue = [NSNumber numberWithFloat: 0 * M_PI * 2.0 * nValue];
    rotationAnimation.toValue = [NSNumber numberWithFloat: M_PI * -2.0 * nValue];
    rotationAnimation.byValue = [NSNumber numberWithFloat: M_PI * -1.0 * nValue];
    rotationAnimation.duration = 0.3;
    rotationAnimation.cumulative = YES;
    rotationAnimation.repeatCount = 999;
    [self.layer addAnimation:rotationAnimation forKey:@"rotationAnimation"];
}
@end

