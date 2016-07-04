#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>

@interface UICustAlertView: UIView

@property (nonatomic, copy)     void (^YesBlock)(void);

- (id)initWithAlertContent:(UIView*)content title:(NSString*)str;

@end
