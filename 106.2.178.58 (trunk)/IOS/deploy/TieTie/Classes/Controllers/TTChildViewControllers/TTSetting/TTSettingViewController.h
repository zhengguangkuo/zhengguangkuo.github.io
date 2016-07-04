//
//  TTSettingViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTSettingViewController : UIViewController<UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,UIImagePickerControllerDelegate,UINavigationControllerDelegate,UIAlertViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *table;
//- (IBAction)showMessages:(id)sender;
//- (IBAction)modifyInfo:(id)sender;
//@property (weak, nonatomic) IBOutlet UIButton *headerImage;

@property (weak, nonatomic) IBOutlet UIImageView *imageView;//用户头像

@property (weak, nonatomic) IBOutlet UILabel *name;//用户名
@property (weak, nonatomic) IBOutlet UILabel *tietieNo;//贴贴账号




//- (IBAction)updateHeaderImage:(id)sender;
@end
