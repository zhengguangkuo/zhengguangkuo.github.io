//
//  RootViewController.h
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HttpService.h"

@interface RootViewController : UIViewController<ConnectFailureDelegate>

-(void)SetNaviationTitleName:(NSString*)str;

-(void)SetNaviationRightButtons:(NSArray*)buttons;

@end
