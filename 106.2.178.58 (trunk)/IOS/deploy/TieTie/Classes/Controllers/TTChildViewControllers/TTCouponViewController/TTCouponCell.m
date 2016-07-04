//
//  TTCouponCell.m
//  Miteno
//
//  Created by wg on 14-6-8.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCouponCell.h"
#import "TTCoupon.h"
#import "TTCreditsCoupon.h"
@interface TTCouponCell()
{
    UIView  *   _divider;
}
@property (weak, nonatomic) IBOutlet UIImageView *merImage;
@property (weak, nonatomic) IBOutlet UILabel *merName;
@property (weak, nonatomic) IBOutlet UILabel *merActive;
@property (weak, nonatomic) IBOutlet UILabel *merActiveDate;
@property (weak, nonatomic) IBOutlet UILabel *merRebate;
@property (weak, nonatomic) IBOutlet UIButton *merRebateBtn;
@property (weak, nonatomic) IBOutlet UIButton *arrow;


@end
@implementation TTCouponCell
+ (instancetype)couponCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTCouponCell";
    TTCouponCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    if (cell == nil) {
        cell = [[NSBundle mainBundle] loadNibNamed:ID owner:nil options:nil][0];
//        cell.backgroundColor =
        cell.accessoryType = UITableViewCellAccessoryNone;
    }
    return cell;
}
- (void)awakeFromNib
{
    // Initialization code
//    [self setupDivider];
}
//- (void)setupDivider
//{
//    _divider = [[UIView alloc] init];
//    _divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
//    _divider.frame = CGRectMake(0,self.contentView.size.height, self.contentView.frame.size.width, 1.0);
//    [self.contentView addSubview:_divider];
//}
//- (void)layoutSubviews
//{
//    [super layoutSubviews];
//    _divider.frame = CGRectMake(self.textLabel.frame.origin.x, 0, self.contentView.size.width + 100,1.0);
//}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (void)setCoupon:(TTCoupon *)coupon
{
    _coupon = coupon;
    _merImage.layer.cornerRadius = 5;
    _merImage.layer.masksToBounds = YES;
    _merName.text = _coupon.actName;
    _merActive.text = _coupon.merchName;
    _merRebate.text = _coupon.merRebate;
    
//    [_merImage setImageWithURL:[NSURL URLWithString:_coupon.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    
    [_merImage setImageWithURL:[NSURL URLWithString:_coupon.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"] options:SDWebImageRetryFailed | SDWebImageLowPriority|SDWebImageRefreshCached];
    
    _merRebate.textColor = [UIColor whiteColor];
    if ([_coupon.sendType isEqualToString:@"2"]) {
        //派发
        _merRebate.backgroundColor = [UIColor blueColor];
         _merActiveDate.text = [NSString stringWithFormat:@"已发放:%@张",_coupon.issuedCnt];
        [_merRebateBtn setBackgroundImage:[UIImage imageNamed:@"icon_b"] forState:UIControlStateNormal];
        
        _arrow.hidden = YES;
    }else if([_coupon.sendType isEqualToString:@"0"]){
        //领用
        _merRebate.backgroundColor = [UIColor redColor];
        _merActiveDate.text = [NSString stringWithFormat:@"已领取:%@张",_coupon.issuedCnt];
    [_merRebateBtn setBackgroundImage:[UIImage imageNamed:@"icon_g"] forState:UIControlStateNormal];
        _arrow.hidden = YES;
    } else{
        _merRebate.backgroundColor = [UIColor clearColor];
        _merRebate.textColor = [UIColor clearColor];
        _merActiveDate.text = [NSString stringWithFormat:@"活动时间:%@",_coupon.merActiveDate];
    }
    
}
- (void)setCreditsCoupon:(TTCreditsCoupon *)creditsCoupon
{
    _merRebateBtn.hidden = YES;
    _arrow.hidden = NO;
    
    _creditsCoupon = creditsCoupon;
    _merImage.layer.cornerRadius = 5;
    _merImage.layer.masksToBounds = YES;
    
//    [_merImage setImageWithURL:[NSURL URLWithString:_creditsCoupon.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    
    [_merImage setImageWithURL:[NSURL URLWithString:_creditsCoupon.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"] options:SDWebImageRetryFailed | SDWebImageLowPriority|SDWebImageRefreshCached];
    
    _merName.text = _creditsCoupon.activityName;
    _merActive.text = _creditsCoupon.instName;

    if (_creditsCoupon.startDate.length>0) {
        
        NSString *startDate = [NSString processDateMethod:_creditsCoupon.startDate];
        NSString *endDate = [NSString processDateMethod:_creditsCoupon.endDate];
        _merActiveDate.text = [NSString stringWithFormat:@"%@ - %@",startDate,endDate];
    }else{
        _merActiveDate.text = @"长期有效";
        _merActiveDate.font = [UIFont systemFontOfSize:10];
    }
    
    
}

@end
