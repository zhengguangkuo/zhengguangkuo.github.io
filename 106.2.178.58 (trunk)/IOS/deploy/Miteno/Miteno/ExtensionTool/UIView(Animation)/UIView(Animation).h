#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>



typedef enum {
    kClockWise,         //顺时针方向
    kCounterClockWise   //逆时针方向
}ClockWiseType;


@interface UIView(Animation)

- (void)EaseIn;

- (void)EaseOut;

- (void)TurnPage;

- (void)TransformerCube;

- (void)RotiaTion:(ClockWiseType)type;

@end
