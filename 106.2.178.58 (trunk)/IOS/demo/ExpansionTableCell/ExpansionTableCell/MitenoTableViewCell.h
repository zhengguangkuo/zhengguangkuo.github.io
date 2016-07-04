//
//  MitenoTableViewCell.h
//  ExpansionTableCell
//
//  Created by zhengguangkuo on 14-5-30.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MitenoTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *tiltleLable;
@property (weak, nonatomic) IBOutlet UIImageView *iconImgView;
@property (nonatomic)BOOL isExpansion;
- (void)changeArrowIcon:(BOOL)up;
- (void)addDetailLable:(NSString*)textStr andFlag:(BOOL)isAdd;
@end
