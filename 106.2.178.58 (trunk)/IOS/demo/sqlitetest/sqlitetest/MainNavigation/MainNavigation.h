//
//  MainNavigation.h
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MainNavigation : UINavigationController<UINavigationControllerDelegate>

-(void)NavigationViewBackBtn:(UIButton*)back;

-(void)NavigationTitle:(NSString*)title;

-(void)NavigationViewRightBtns:(NSArray*)buttons;

-(void)NavigationBgImage:(NSString*)imageName;


@end
