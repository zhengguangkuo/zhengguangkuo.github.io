//
//  TwoItoastAndHUDViewController.h
//  HUDAndiToast
//
//  Created by HWG on 14-1-26.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TwoItoastAndHUDViewController : UIViewController

@property (strong, nonatomic) IBOutlet UIButton *activityButton;

- (IBAction)showText:(id)sender;
- (IBAction)showTitleAndText:(id)sender;
- (IBAction)showImageText:(id)sender;
- (IBAction)showTitleWithTextAndImage:(id)sender;
- (IBAction)showViews:(id)sender;
- (IBAction)showLoad:(id)sender;
- (IBAction)showImage:(id)sender;

@end
