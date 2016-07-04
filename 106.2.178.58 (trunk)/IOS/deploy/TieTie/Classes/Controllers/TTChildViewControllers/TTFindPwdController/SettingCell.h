//
//  SettingCell.h
//  Miteno
//
//  Created by wg on 14-7-30.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface SettingCell : UITableViewCell
@property (nonatomic, strong) NSIndexPath   * indexPath;

+ (instancetype)settingCellWithTableView:(UITableView *)tableView;
@end
