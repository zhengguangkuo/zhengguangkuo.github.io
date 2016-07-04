//
//  TTFriendsCell.h
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTFriendsCell : UITableViewCell
+ (instancetype)FriendCellWithTableView:(UITableView *)tableView;

@property (weak, nonatomic) IBOutlet UIImageView *headImage;
@property (weak, nonatomic) IBOutlet UILabel *nickName;

@property (weak, nonatomic) IBOutlet UIButton *removeBtn;
@property (weak, nonatomic) IBOutlet UIButton *commentBtn;
@property (weak, nonatomic) IBOutlet UIButton *giveBtn;
@end
