#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>

@interface UIView(category)

-(void)CoverMask;

-(void)ViewBorder:(UIColor*)color Radius:(CGFloat)value;

-(void)ViewWithBorder:(UIColor*)color;

-(void)MaskRemove;

-(id)viewController;

@end
