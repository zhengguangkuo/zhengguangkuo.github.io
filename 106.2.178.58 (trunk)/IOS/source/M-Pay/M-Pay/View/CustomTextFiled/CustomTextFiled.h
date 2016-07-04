#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@interface CustomTextFiled : UITextField

@property (nonatomic, weak)  id target;

+(id)CustomViewWithFrame:(CGRect)frame target:(id)target;

@end
