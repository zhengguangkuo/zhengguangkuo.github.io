//
//  TTCouponBaseCell.m
//  Miteno
//
//  Created by wg on 14-8-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponBaseCell.h"
#import "TTTopItem.h"
@implementation TTCouponBaseCell
+ (instancetype)couponBaseCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTCouponDetailCell";
    TTCouponBaseCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[TTCouponBaseCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:ID];
        cell.textLabel.font = [UIFont systemFontOfSize:16];
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
- (void)setTopItem:(TTTopItem *)topItem
{
    _topItem = topItem;
    self.textLabel.text = topItem.title;
    [self setContentText:_topItem.title];
//    dispatch_async(dispatch_get_main_queue(), ^{
//        UIWebView *moreWeb = [[UIWebView alloc] init];
//        //        _moreWeb.delegate = self;
//        //            _moreWeb.backgroundColor = [UIColor whiteColor];
//        moreWeb.frame = CGRectMake(0, 35,ScreenWidth,200);
//        [moreWeb loadHTMLString:_topItem.title baseURL:nil];
//        [self.contentView addSubview:moreWeb];
//    });
    
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
//    return frame;
}

-(void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
