//
//  RootViewController.h
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface testObject : NSObject
@property (nonatomic,strong) NSString*  name;
@property (nonatomic,assign) int age;

-(id)init;
@end


@interface RootViewController : UIViewController

-(void)SetNaviationTitleName:(NSString*)str;

-(void)SetNaviationRightButtons:(NSArray*)buttons;

@end
