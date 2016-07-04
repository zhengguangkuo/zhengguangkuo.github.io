//
//  AlertHeadTitleView.m
//  Miteno
//
//  Created by wg on 14-6-6.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "AlertHeadTitleView.h"
@implementation AlertHeadTitleView
+ (instancetype)alertHeadTitleView
{
    return [[NSBundle mainBundle] loadNibNamed:@"AlertHeadTitleView" owner:nil options:nil][0];
}
- (void)awakeFromNib
{
    self.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"alertBg"]];
    [self.cancelSel setBackgroundImage:[UIImage imageNamed:@"alertBg_Sel"] forState:UIControlStateNormal];
}
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
