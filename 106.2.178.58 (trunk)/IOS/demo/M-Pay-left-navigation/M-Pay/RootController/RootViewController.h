//
//  RootViewController.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HttpService.h"
#import "SystemDialog.h"
#import "UINavigationController(addition).h"
#import "Config.h"


@interface RootViewController : UIViewController<ConnectFailureDelegate>

-(void)SetNaviationTitleName:(NSString*)str;

-(void)SetNaviationBgImg:(UIImage*)image;

-(void)NavigationTitleColor:(UIColor*)color;

- (CGFloat)GetNavigationBarPosY;

- (void)backToPrevious;

-(void)NavigationViewBackBtn;

-(void)NavigationHiddenBack;

@end
