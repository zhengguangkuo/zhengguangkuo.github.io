//
//  SettingCell.m
//  Miteno
//
//  Created by wg on 14-7-30.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "SettingCell.h"

const int TTCellMargin = 10;

@interface SettingCell()
{
    UIImageView *_arrow;
    UISwitch *_switch;
    UILabel *_label;
    
    UIView *_divider;
    UIView  *   _botDivider;
}
@end
@implementation SettingCell

+ (instancetype)settingCellWithTableView:(UITableView *)tableView
{

    static NSString *ID = @"Cell";
    
    SettingCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[SettingCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:ID];
    }
    
    return cell;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
//        [self setupBg];
        
//      [self setupSubviews];
        
//        [self setupDivider];
    }
    return self;
}
#pragma mark 添加分隔线
- (void)setupDivider
{
    _divider = [[UIView alloc] init];
    _divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];

        _divider.frame = CGRectMake(0,self.contentView.frame.size.height, self.contentView.frame.size.width, 1.0);
    
    [self.contentView addSubview:_divider];

}


#pragma mark 设置子控件属性
- (void)setupSubviews
{
    self.textLabel.backgroundColor = [UIColor clearColor];
    self.textLabel.font = [UIFont systemFontOfSize:14];
    
    self.detailTextLabel.backgroundColor = [UIColor clearColor];
    self.detailTextLabel.font = [UIFont systemFontOfSize:12];

}


//- (void)layoutSubviews
//{
//    [super layoutSubviews];
//
//}
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
