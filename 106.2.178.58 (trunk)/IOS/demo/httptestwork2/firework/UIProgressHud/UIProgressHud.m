//
//  UIProgressHud.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import "UIProgressHud.h"

#define KEYWINDOW  [[UIApplication sharedApplication] keyWindow]

@implementation UIProgressHud
@synthesize progressHud;
@synthesize progressBg;
@synthesize maskView;
@synthesize isAnimation;
//single_for_implementation(UIProgressHud)

float  Keywindow_width;
CGRect Keywindow_frame;

- (id)init
{
    self = [super init];
    if(self)
  {
      Keywindow_frame = KEYWINDOW.frame;
      Keywindow_width = Keywindow_frame.size.width;
      self.isAnimation = NO;
      self.maskView = [[UIView alloc] initWithFrame:Keywindow_frame];
      self.maskView.backgroundColor = [UIColor clearColor];
      
      self.progressBg = [[UIView alloc] init];
      [progressBg setFrame:CGRectMake(0, 0, Keywindow_width/3.5, 60)];
      [progressBg setCenter:maskView.center];
      [progressBg setBackgroundColor:[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.5]];
      
      CALayer*  BgLayer = [progressBg layer];
      BgLayer.borderColor = [[UIColor colorWithRed:0.0 green:0.0 blue:0.0 alpha:0.5]CGColor];
      BgLayer.borderWidth = 1.0;
      BgLayer.cornerRadius = 8.0;
      BgLayer.masksToBounds = NO;
      [maskView addSubview:progressBg];
      
      self.progressHud = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhiteLarge];
      [progressHud setFrame:CGRectMake(0, 0, Keywindow_width/4, 60)];
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


@end
