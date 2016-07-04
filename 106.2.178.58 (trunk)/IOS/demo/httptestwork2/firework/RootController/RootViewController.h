//
//  RootViewController.h
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HttpService.h"
#import "SystemDialog.h"
#import "MainNavigation.h"

@interface RootViewController : UIViewController<ConnectFailureDelegate>

-(void)SetNaviationTitleName:(NSString*)str;

-(void)SetNaviationRightButtons:(NSArray*)buttons;

@end
