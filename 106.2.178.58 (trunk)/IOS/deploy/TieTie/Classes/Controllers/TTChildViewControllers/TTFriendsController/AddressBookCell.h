//
//  AddressBookCell.h
//  Miteno
//
//  Created by wg on 14-8-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface AddressBookCell : UITableViewCell
+ (instancetype)AddressBookCellWithTableView:(UITableView *)tableView;
@property (weak, nonatomic) IBOutlet UILabel *friendName;

@end
