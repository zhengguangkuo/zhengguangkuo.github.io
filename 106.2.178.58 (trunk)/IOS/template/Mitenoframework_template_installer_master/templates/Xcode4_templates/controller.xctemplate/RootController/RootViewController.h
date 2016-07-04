//
//  RootViewController.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HttpService.h"
#import "SystemDialog.h"
#import "MainNavigation.h"

@interface RootViewController : UIViewController<ConnectFailureDelegate>

-(void)SetNaviationTitleName:(NSString*)str;

-(void)SetNaviationRightButtons:(NSArray*)buttons;

@end
