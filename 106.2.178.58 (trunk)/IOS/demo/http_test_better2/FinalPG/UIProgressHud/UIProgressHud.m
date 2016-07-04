//
//  UIProgressHud.m
//  
//
//  Created by guorong on 13-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import "UIProgressHud.h"

#define  KEYWINDOW       [[UIApplication sharedApplication] keyWindow]

#define  KEYWINDOW_WIDTH   [[UIApplication sharedApplication] keyWindow].frame.size.width


@implementation UIProgressHud
@synthesize progressHud;
@synthesize progressBg;
@synthesize maskView;
@synthesize isAnimation;
//single_for_implementation(UIProgressHud)

- (id)init
{
    self = [super init];
    if(self)
  {
      self.isAnimation = NO;
      self.maskView = [[UIView alloc] initWithFrame:KEYWINDOW.frame];
      self.maskView.backgroundColor = [UIColor clearColor];
      
      self.progressBg = [[UIView alloc] init];
      [progressBg setFrame:CGRectMake(0, 0, KEYWINDOW_WIDTH/3.5, 60)];
      [progressBg setCenter:maskView.center];
      [progressBg setBackgroundColor:[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.5]];
      
      CALayer*  BgLayer = [progressBg layer];
      BgLayer.borderColor = [[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.5]CGColor];
      BgLayer.borderWidth = 1.0;
      BgLayer.cornerRadius = 8.0;
      BgLayer.masksToBounds = NO;
      [maskView addSubview:progressBg];
      
      self.progressHud = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
      [progressHud setFrame:CGRectMake(0, 0, KEYWINDOW_WIDTH/4, 60)];
      CGSize  CenterSize = progressBg.frame.size;
      [progressHud setCenter:CGPointMake(CenterSize.width/2,CenterSize.height/2)];
      
      [progressBg addSubview:progressHud];
   }
    return self;
}

-(void)startWaiting
{
    if(self.isAnimation==NO){
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.progressHud  startAnimating];
          [KEYWINDOW addSubview:self.maskView];
          self.isAnimation = YES;
       });
    }
}

-(void)stopWaiting
{
    if(self.isAnimation==YES){
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.progressHud  stopAnimating];
         [self.maskView removeFromSuperview];
         self.isAnimation = NO;
       });
    }
}



- (void)dealloc
{
    [progressHud release];
    [progressBg release];
    [super dealloc];
}

@end
