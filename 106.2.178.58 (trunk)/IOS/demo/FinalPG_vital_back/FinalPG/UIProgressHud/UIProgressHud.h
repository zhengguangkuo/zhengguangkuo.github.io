//
//  UIProgressHud.h
//  
//
//  Created by guorong on 13-10-30.
//  Copyright 2013年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
#import "Singleton.h"



@interface UIProgressHud : UIView
{
   UIActivityIndicatorView* progressHud;
   UIView* progressBg;
   UIView* maskView;
   BOOL  isAnimation;
}
singleton_for_interface(UIProgressHud)
@property  (nonatomic,retain) UIActivityIndicatorView* progressHud;
@property  (nonatomic,retain) UIView* progressBg;
@property  (nonatomic,retain) UIView* maskView;
@property  (nonatomic,assign) BOOL isAnimation;

-(id)init;
-(void)startWaiting;
-(void)stopWaiting;
@end
