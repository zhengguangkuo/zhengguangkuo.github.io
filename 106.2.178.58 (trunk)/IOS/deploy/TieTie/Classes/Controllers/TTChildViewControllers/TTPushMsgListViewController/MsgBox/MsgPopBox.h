//
//  HelpView.h
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MsgPopBox : UIView<UIGestureRecognizerDelegate>

@property (weak, nonatomic) IBOutlet UIImageView *leftimage;

@property (weak, nonatomic) IBOutlet UILabel *storeName;

@property (weak, nonatomic) IBOutlet UILabel *ActiveName;

@property (weak, nonatomic) IBOutlet UILabel *SaleCount;

@property (weak, nonatomic) IBOutlet UILabel *Expiredate;

@property (nonatomic, strong)UIView *bgView;

+ (instancetype)msgpopbox;

- (void)show:(UIViewController *)ctrl;

- (IBAction)ViewDetail:(id)sender;

- (IBAction)reject:(id)sender;

- (IBAction)Accept:(id)sender;



@end
