//
//  BusinessDetailsViewController.h
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-10.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"
@interface BusinessDetailsViewController : TTRootViewController<UITableViewDelegate>
@property(nonatomic,copy)NSString * MerchId;
//@property(nonatomic,copy)NSString * ImageURL;
@property(nonatomic,assign)float y;
@end
