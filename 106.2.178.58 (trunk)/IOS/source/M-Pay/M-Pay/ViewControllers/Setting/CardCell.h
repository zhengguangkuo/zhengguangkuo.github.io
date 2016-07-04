//
//  ImageTableViewCell.h
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@class BasicCard;

@interface CardCell : UITableViewCell{
}

@property (weak, nonatomic) IBOutlet UIImageView *ImageView;
@property (weak, nonatomic) IBOutlet UILabel *NameLabel;

@property (weak, nonatomic) IBOutlet UILabel *NumLabel;
@property (weak, nonatomic) IBOutlet UIButton *LockButton;
@property (weak, nonatomic) IBOutlet UIImageView *Line;

-(id)initCustom;
-(void)setBasicCard:(BasicCard*)object;
-(void)drawinputBox;
@end
