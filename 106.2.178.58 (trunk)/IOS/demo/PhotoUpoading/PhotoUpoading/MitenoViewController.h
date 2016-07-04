//
//  MitenoViewController.h
//  PhotoUpoading
//
//  Created by zhengguangkuo on 14-5-27.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AFNetworking.h"

@interface MitenoViewController : UIViewController<UIActionSheetDelegate,UIImagePickerControllerDelegate,UINavigationControllerDelegate>
@property (weak, nonatomic) IBOutlet UIButton *photoButton;

- (IBAction)buttonClicked:(id)sender;
@end
