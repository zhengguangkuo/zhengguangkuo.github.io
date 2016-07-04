//
//  UIProgressHud.h
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
//#import "Singleton.h"

@interface UIProgressHud : UIView
{
   UIActivityIndicatorView* progressHud;
   UIView* progressBg;
   UIView* maskView;
   BOOL  isAnimation;
}
//singleton_for_interface(UIProgressHud)
@property  (nonatomic,strong) UIActivityIndicatorView* progressHud;
@property  (nonatomic,strong) UIView* progressBg;
@property  (nonatomic,strong) UIView* maskView;
@property  (nonatomic,assign) BOOL isAnimation;

-(id)init;
-(void)startWaiting;
-(void)stopWaiting;
@end
