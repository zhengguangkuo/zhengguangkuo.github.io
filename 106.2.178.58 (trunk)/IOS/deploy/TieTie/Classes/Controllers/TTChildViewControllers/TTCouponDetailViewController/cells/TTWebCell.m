//
//  TTWebCell.m
//  Miteno
//
//  Created by wg on 14-8-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTWebCell.h"

@implementation TTWebCell
+ (instancetype)webCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTWebCell";
    TTWebCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
         cell = [[TTWebCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:ID];
    }
    return cell;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}
-(CGRect)setContentText:(NSString *)text{
    //获得当前cell高度
    CGRect frame = [self frame];
    self.textLabel.text = text;
    self.textLabel.numberOfLines = 0;
    CGSize size = CGSizeMake(self.frame.size.width, MAXFLOAT);
    CGSize labelSize = [self.textLabel.text sizeWithFont:self.textLabel.font constrainedToSize:size lineBreakMode:NSLineBreakByClipping];
    self.textLabel.frame = CGRectMake(self.textLabel.frame.origin.x, self.textLabel.frame.origin.y, labelSize.width, labelSize.height);
    //计算出自适应的高度
    frame.size.height = labelSize.height+35 ;
    
    self.frame = frame;
      return frame;
}
- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
