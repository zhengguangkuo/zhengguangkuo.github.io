#import "UIView(category).h"

@implementation UIView(category)

-(void)CoverMask
{
    //遮罩
    CGRect appFrame = [UIScreen mainScreen].applicationFrame;
    UIView*  SheetMask = [[UIView alloc] initWithFrame:appFrame];
    [SheetMask setBackgroundColor:[UIColor colorWithRed:28/255.0 green:28/255.0 blue:28/255.0 alpha:0.8]];
        
    [SheetMask setTag:433];
        
    [[[UIApplication sharedApplication] keyWindow] addSubview:SheetMask];
        
    UITapGestureRecognizer*tap=[[UITapGestureRecognizer alloc]initWithTarget:[[UIApplication sharedApplication] keyWindow] action:@selector(MaskRemove)];
        [SheetMask addGestureRecognizer:tap];
    
    
    CGRect tpRect = self.frame;
    
    [self setFrame:CGRectMake(10, 10, 300, tpRect.size.height)];
    
    CGPoint CenterPoint = [[UIApplication sharedApplication] keyWindow].center;
    
    CGPoint tPoint = CGPointMake(CenterPoint.x, 220.0f);
    
    [self setCenter:tPoint];
    
    [SheetMask addSubview:self];
}

-(void)MaskRemove
{
    NSLog(@"tetete");
    UIView* maskView = [[[UIApplication sharedApplication] keyWindow] viewWithTag:433];
    [maskView removeFromSuperview];
}


-(void)ViewWithBorder:(UIColor*)color
{
    self.layer.cornerRadius = 1.0;
    self.layer.borderWidth = 2.0;
    self.layer.borderColor = [color CGColor];
    self.layer.masksToBounds = NO;
}

-(void)ViewBorder:(UIColor*)color Radius:(CGFloat)value
{
    self.layer.cornerRadius = value;
    self.layer.borderWidth = 1.5;
    self.layer.borderColor = [color CGColor];
    self.layer.masksToBounds = YES;
}



- (id)viewController {
    for (UIView* next = [self superview]; next; next = next.superview) {
        UIResponder *nextResponder = [next nextResponder];
        if ([nextResponder isKindOfClass:[UIViewController class]]) {
            return nextResponder;
        }
    }
    return nil;
}

@end
