//
//  TTModifyInfoViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void (^setImageBlock)(UIImage *image);

@interface TTModifyInfoViewController : UIViewController<UITableViewDelegate,UITableViewDataSource,UIActionSheetDelegate,UIImagePickerControllerDelegate,UIAlertViewDelegate,UINavigationControllerDelegate>

@property (weak, nonatomic) IBOutlet UITableView *table;
@property (nonatomic,copy) setImageBlock block_setImage;
@end
