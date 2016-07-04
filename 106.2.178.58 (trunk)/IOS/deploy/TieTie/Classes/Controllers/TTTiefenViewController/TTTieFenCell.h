//
//  TTTieFenCell.h
//  TTTieFenViewController
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTTieFenCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UILabel *Namelb;
@property (weak, nonatomic) IBOutlet UILabel *Fenlb;
@property (weak, nonatomic) IBOutlet UIImageView *TubiaoImageView;

-(void)setCellWithDic:(NSDictionary*)dic;
@end
