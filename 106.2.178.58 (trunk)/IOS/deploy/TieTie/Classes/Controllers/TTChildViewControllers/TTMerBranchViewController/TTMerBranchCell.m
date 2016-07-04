//
//  TTMerBranchCell.m
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMerBranchCell.h"
#import "CustomItem.h"
@interface TTMerBranchCell()
{
    UIView          *   _divider;
}
@end
@implementation TTMerBranchCell
+ (instancetype)merBranchCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTMerBranchCell";
    TTMerBranchCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[TTMerBranchCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:ID];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
//        [cell setupDivider];
    }
    
    return cell;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
      
    }
    return self;
}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        
    }
    return self;
}
//- (void)setupDivider
//{
//    _divider = [[UIView alloc] init];
//    _divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
//    _divider.frame = CGRectMake(0,0, self.contentView.frame.size.width, 1.0);
//    [self.contentView addSubview:_divider];
//}
//- (void)setIndexPath:(NSIndexPath *)indexPath
//{
//    _indexPath = indexPath;
//    _divider.hidden = indexPath.row==0;
//
//}
- (void)layoutSubviews
{
    [super layoutSubviews];
    //调整控件frame
//    _divider.frame = CGRectMake(0,self.textLabel.origin.y,self.contentView.size.width+100,1.0);
//    CGRect frame = self.contentView.frame;
//    frame.origin.x = -20;
//    frame.size.width = [UIScreen mainScreen].bounds.size.width;
//
//    self.contentView.frame = frame;

    
//    CGRect ownFrame = self.frame;
//    ownFrame.origin.x-=-320;
//    self.frame = ownFrame;
//    CGRect titleF = self.titleLabel.frame;
//    titleF.origin.x = 0;
////    titleF.size.width = titleF.size.width+kActDetailSpace-5;
//    self.titleLabel.frame = titleF;
//   
//    
//    CGRect addRessF = self.address.frame;
//    addRessF.origin.x = 0;
////    addRessF.size.width = addRessF.size.width+kActDetailSpace;
//    self.address.frame = addRessF;
//    [self addSubview:self.address];
//    UIButton *distance= (CustomItem *)self.distance;
//    CGRect distanceF = distance.frame;
//    distanceF.origin.x = 0;
//    self.distance.frame = distanceF;
//
//    CGRect phoneF = self.phone.frame;
//    phoneF.size.width = 75;
//    self.phone.frame = phoneF;
//    
//}
//- (void)setFrame:(CGRect)frame
//{
//
//    frame.origin.x-=15;
//    frame.size.width += 2*kActDetailSpace;
//    [super setFrame:frame];
}

@end
