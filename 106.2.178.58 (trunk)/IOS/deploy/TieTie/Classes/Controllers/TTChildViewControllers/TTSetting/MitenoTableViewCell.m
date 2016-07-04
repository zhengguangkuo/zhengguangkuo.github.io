//
//  MitenoTableViewCell.m
//  ExpansionTableCell
//
//  Created by zhengguangkuo on 14-5-30.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import "MitenoTableViewCell.h"

@implementation MitenoTableViewCell
{
    UILabel *label;
}

- (void)awakeFromNib
{
    // Initialization code
    label = [[UILabel alloc]init];
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)changeArrowIcon:(BOOL)isExpansion
{
    if (isExpansion) {
        [self.iconImgView setImage:[UIImage imageNamed:@"list_d"]];
    } else {
        [self.iconImgView setImage:[UIImage imageNamed:@"list_l"]];
    }
}

- (void)addDetailLable:(NSString*)textStr andFlag:(BOOL)isAdd;
{
    [self changeArrowIcon:isAdd];
    if (isAdd) {
        CGSize size = CGSizeZero;
        size = [self getStringRect:textStr];
        [label setFrame:CGRectMake(0, 44, 320, size.height)];
        label.numberOfLines = 0;
        label.text = textStr;
        [label sizeToFit];
        [self addSubview:label];
    } else {
        [label setFrame:CGRectMake(0, 44, 320, 0)];
    }
    
}

- (CGSize)getStringRect:(NSString*)aString
{
    CGSize size = CGSizeZero;
    if (aString && (aString.length > 0)) {
        NSDictionary* dic = @{NSFontAttributeName: [UIFont systemFontOfSize:17]};
        size = [aString boundingRectWithSize:CGSizeMake(320, 0)  options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:dic context:nil].size;
    }
    return  size;
}

@end
