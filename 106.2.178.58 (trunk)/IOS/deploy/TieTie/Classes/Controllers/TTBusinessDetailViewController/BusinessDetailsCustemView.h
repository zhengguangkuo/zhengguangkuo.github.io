//
//  BusinessDetailsCustemView.h
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-11.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BusinessDetailsCustemView : UIView//<UITableViewDelegate,UITableViewDataSource>
{
    UILabel * _label;
    UIButton * _btn;
    NSString * _imageName;
    NSString * _labelStr;
 //   UITableView * _CustemViewTV;
 //   NSMutableArray * _arr;
}
-(id)initCustemViewWithImage:(NSString*)imageName andLabelText:(NSString*)labelStr andViewFrame:(CGRect)frame;
@end
