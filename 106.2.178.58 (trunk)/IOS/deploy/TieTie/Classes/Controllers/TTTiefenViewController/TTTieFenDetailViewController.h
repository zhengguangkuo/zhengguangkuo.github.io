//
//  TTTieFenDetailViewController.h
//  Miteno
//
//  Created by APPLE on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"

@interface TTTieFenDetailViewController : TTRootViewController
@property (weak, nonatomic) IBOutlet UIImageView *headImage;
@property (weak, nonatomic) IBOutlet UILabel *headName;
@property (weak, nonatomic) IBOutlet UILabel *headTiefen;
@property (weak, nonatomic) IBOutlet UILabel *headTiefenText;

@property(nonatomic,retain)NSDictionary * headDic;
@end
