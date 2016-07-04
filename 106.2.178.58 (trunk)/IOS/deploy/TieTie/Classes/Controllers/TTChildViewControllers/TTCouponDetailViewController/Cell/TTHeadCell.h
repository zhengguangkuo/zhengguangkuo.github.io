//
//  TTHeadCell.h
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TTHeadCellFrame;
@interface TTHeadCell : UITableViewCell<UIWebViewDelegate>
@property (nonatomic, strong) UILabel   * couTitleLabel;   //折扣信息
@property (nonatomic, strong) UILabel   * couSubTitleLabel;
@property (nonatomic, strong) UILabel   * couDisContent;   //折扣内容
@property (nonatomic, strong) TTHeadCellFrame * headMegFrame;

@property (nonatomic, strong) UIWebView *web;
+ (instancetype)headCellWithTableView:(UITableView *)tableView;
@end
