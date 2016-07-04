//
//  TTGridView.h
//  Miteno
//
//  Created by wg on 14-6-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
@protocol TTGridviewDelegate <NSObject>
@optional
- (void)clickCoupon:(UIButton *)btn;
- (void)clickDiscount:(UIButton *)btn;
- (void)clickScore:(UIButton *)btn;
- (void)clickFastLogin:(UIButton *)btn;
- (void)clickFriend:(UIButton *)btn;
- (void)clickExpect:(UIButton *)btn;
- (void)clickQuickLogin:(UIButton *)btn;
@end
@interface TTGridView : UIView

@property (weak, nonatomic) IBOutlet UIButton *portalView;

@property (weak, nonatomic) IBOutlet UIButton *tieCoupon;
@property (weak, nonatomic) IBOutlet UIButton *tieDiscount;
@property (weak, nonatomic) IBOutlet UIButton *tieScore;
@property (weak, nonatomic) IBOutlet UIImageView *bgImage;


@property (nonatomic, weak) id <TTGridviewDelegate> delegate;
+ (instancetype)gridView;
@end
