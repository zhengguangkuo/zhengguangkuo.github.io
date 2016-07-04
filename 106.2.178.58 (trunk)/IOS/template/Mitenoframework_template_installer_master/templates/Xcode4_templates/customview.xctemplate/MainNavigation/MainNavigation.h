//
//  MainNavigation.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MainNavigation : UINavigationBar<UINavigationBarDelegate>

-(void)NavigationViewBackBtn:(UIButton*)back;

-(void)NavigationTitleText:(NSString*)title;

-(void)NavigationViewRightBtns:(NSArray*)buttons;

-(void)NavigationBgImage:(NSString*)imageName;

-(void)NavigationTitle;


@end
