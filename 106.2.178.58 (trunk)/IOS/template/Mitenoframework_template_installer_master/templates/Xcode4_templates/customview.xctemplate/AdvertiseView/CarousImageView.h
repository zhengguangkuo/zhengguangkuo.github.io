//
//  CarousImageView.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>

@class CarousImageView;

//触摸方向
typedef enum{
    kTouchLeft,
    kTouchRight,
    kTouchUp,
    KTouchDown
}UITouchDirection;


//自定义的委托方法,处理触摸事件
@protocol TouchControllerDelegate <NSObject>

@optional

- (void)TouchesBeganEvent: (CarousImageView*)controller;

- (void)TouchesEndEvent: (CarousImageView*)controller;

- (void)TouchesMoveEvent: (CarousImageView*)controller
               direction:(UITouchDirection)direction;

@end



@interface CarousImageView : UIImageView<UIGestureRecognizerDelegate>

@property (nonatomic, assign) id<TouchControllerDelegate> controllerDelegate;

@property (nonatomic, assign) int nTag;

-(id)initWithMyFrame:(CGRect)aRect  x:(NSString*)ImageName Tag:(int)tag block:(void(^)(id object))finish;


-(void)downLoadImage;

@end

















