//
//  ImageTableViewCell.h
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@class SaleDetail;
@class CutomViewCell;



@interface CutomViewCell : UITableViewCell
{
    UIImageView*  cusImageview;
    UILabel *nameLabel;
    UILabel *artistLabel;
}
@property (retain, nonatomic)  UIImageView *cusImageview;
@property (retain, nonatomic)  UILabel *nameLabel;
@property (retain, nonatomic)  UILabel *artistLabel;


-(void)setDetailObject:(SaleDetail*)Object;


@end
