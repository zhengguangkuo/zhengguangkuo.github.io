//
//  TTGridView.m
//  Miteno
//
//  Created by wg on 14-6-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
#import "TTGridView.h"
@interface TTGridView()


- (IBAction)clickTieCoupon:(id)sender;
- (IBAction)clickTieDiscount:(id)sender;
- (IBAction)clickTieScore:(id)sender;


- (IBAction)clickTieFriend:(id)sender;
- (IBAction)clickTieExpect:(id)sender;
- (IBAction)quickLogin:(id)sender;

@end
@implementation TTGridView
+ (instancetype)gridView
{
    return [[NSBundle mainBundle] loadNibNamed:@"TTGridView" owner:nil options:nil][0];
}
- (void)awakeFromNib
{
//    self.portalView.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"getbumber_normal"]];
}
- (IBAction)fastLogin:(id)sender {
    [_delegate clickFastLogin:sender];
}
//- (void)awakeFromNib
//{
//    self.bgImage.backgroundColor = [UIColor whiteColor];
//    self.bgImage.userInteractionEnabled = YES;
//    [self.tieCoupon setImage:[UIImage stretchImageWithName:@"button_t"] forState:UIControlStateNormal];
//    [self.tieCoupon setTitle:@"优惠劵" forState:UIControlStateNormal];
//    [self.tieDiscount setImage:[UIImage stretchImageWithName:@"button_c"] forState:UIControlStateNormal];
//    [self.tieDiscount setTitle:@"折扣优惠" forState:UIControlStateNormal];
//    [self.tieScore setImage:[UIImage stretchImageWithName:@"button_s"] forState:UIControlStateNormal];
//    [self.tieScore setTitle:@"积分优惠" forState:UIControlStateNormal];
//    
//}
- (IBAction)clickTieCoupon:(id)sender {
    UIButton *btn = (UIButton *)sender;
    [_delegate clickCoupon:btn];
}

- (IBAction)clickTieDiscount:(id)sender {
    UIButton *btn = (UIButton *)sender;
    [_delegate clickDiscount:btn];
}

- (IBAction)clickTieFriend:(id)sender {
    UIButton *btn = (UIButton *)sender;
    [_delegate clickFriend:btn];
}

- (IBAction)clickTieScore:(id)sender {
    UIButton *btn = (UIButton *)sender;
    [_delegate clickScore:btn];
    
}
- (IBAction)clickTieExpect:(id)sender {
    UIButton *btn = (UIButton *)sender;
    [_delegate clickExpect:btn];
}
//快速登录入口
- (IBAction)quickLogin:(id)sender {
    UIButton *btn = (UIButton *)sender;
    [_delegate clickQuickLogin:btn];
}
- (void)clickH
{
    TTLog(@"clickH......");
}

@end
