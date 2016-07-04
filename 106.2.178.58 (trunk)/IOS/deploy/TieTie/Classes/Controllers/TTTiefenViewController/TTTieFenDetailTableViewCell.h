//
//  TTTieFenDetailTableViewCell.h
//  Miteno
//
//  Created by APPLE on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTTieFenDetailTableViewCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *BusinessName;
@property (weak, nonatomic) IBOutlet UILabel *tranType;
@property (weak, nonatomic) IBOutlet UILabel *cPoint;
@property (weak, nonatomic) IBOutlet UILabel *tranTime;
-(void)setTieFenDetailWithDic:(NSDictionary*)dic;
@end
