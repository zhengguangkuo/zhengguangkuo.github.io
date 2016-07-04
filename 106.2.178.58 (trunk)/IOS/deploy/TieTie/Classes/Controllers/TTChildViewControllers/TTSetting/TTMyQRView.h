//
//  TTMyQRView.h
//  Miteno
//
//  Created by wg on 14-8-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTMyQRView : UIView
@property (weak, nonatomic) IBOutlet UIImageView *userIcon;
@property (weak, nonatomic) IBOutlet UILabel *nickName;
@property (weak, nonatomic) IBOutlet UILabel *userPhone;
@property (weak, nonatomic) IBOutlet UIImageView *qrImg;
+ (instancetype)qrcodeView;
@end
