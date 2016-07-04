//
//  TTCouponCell.m
//  Miteno
//
//  Created by wg on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponDisScoreCell.h"
#import "TTCouponItem.h"
#import "TTCouponArrowItem.h"
#import "TTCouponImageItem.h"
#define kActDetailSpace 15
@interface TTCouponDisScoreCell()
{
    UIImageView *_arrow;

}
@end
@implementation TTCouponDisScoreCell
+ (instancetype)couponDisScoreCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTCouponDisScoreCell";
    TTCouponDisScoreCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[TTCouponDisScoreCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:ID];
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
- (void)setItem:(TTCouponItem *)item
{
    _item = item;
    
    self.textLabel.text = item.title;
    self.imageView.image = [UIImage imageNamed:item.icon];
    
    if ([item isKindOfClass:[TTCouponArrowItem class]]) {
        //箭头
        self.textLabel.font = [UIFont systemFontOfSize:12];
        self.textLabel.textColor = [UIColor blueColor];
        UIImageView *view = [[UIImageView alloc] init];
        view.image = [UIImage imageNamed:@"list_bt2"];
        self.backgroundView = view;

//        [self settingArrow];
    }else if([item isKindOfClass:[TTCouponImageItem class]]){
        self.backgroundColor = nil;
        //图片标题
        [self settingArrow];
         self.textLabel.textColor = [UIColor blackColor];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }else{
        //标题
        self.backgroundColor = nil;
        self.textLabel.textColor = [UIColor blackColor];
          self.accessoryView = nil;
          self.selectionStyle = UITableViewCellSelectionStyleNone;
        [self setContentText:item.title];
      }
}
- (void)settingArrow
{
//    if (_arrow == nil) {
//        _arrow = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"CellArrow"]];
//    }
//    self.accessoryView = _arrow;
//    self.selectionStyle = UITableViewCellSelectionStyleDefault;
    
}
-(void)setContentText:(NSString *)text{
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
}


@end
