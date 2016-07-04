//
//  XHMessageTableViewCell.h
//  MessageDisplayExample
//
//  Created by qtone-1 on 14-4-24.
//  Copyright (c) 2014年 曾宪华 开发团队(http://iyilunba.com ) 本人QQ:543413507 本人QQ群（142557668）. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "XHMessageBubbleView.h"

@interface XHMessageTableViewCell : UITableViewCell

@property (nonatomic, weak, readonly) XHMessageBubbleView *messageBubbleView;

@property (nonatomic, weak, readonly) UIButton *avatorButton;

@property (nonatomic, weak, readonly) UILabel *timestampLabel;

- (XHBubbleMessageType)bubbleMessageType;

- (instancetype)initWithMessage:(id <XHMessageModel>)message
              displaysTimestamp:(BOOL)displayTimestamp
                reuseIdentifier:(NSString *)cellIdentifier;

- (void)configureCellWithMessage:(id <XHMessageModel>)message
               displaysTimestamp:(BOOL)displayTimestamp;

+ (CGFloat)calculateCellHeightWithMessage:(id <XHMessageModel>)message
                        displaysTimestamp:(BOOL)displayTimestamp;

@end
