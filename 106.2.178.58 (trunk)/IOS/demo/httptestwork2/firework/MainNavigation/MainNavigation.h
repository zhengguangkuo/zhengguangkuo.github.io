//
//  MainNavigation.h
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MainNavigation : UINavigationBar<UINavigationBarDelegate>

-(void)NavigationViewBackBtn:(UIButton*)back;

-(void)NavigationTitleText:(NSString*)title;

-(void)NavigationViewRightBtns:(NSArray*)buttons;

-(void)NavigationBgImage:(NSString*)imageName;

-(void)NavigationTitle;


@end
