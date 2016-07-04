//
//  BusinessDetailsLastView.m
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-11.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "BusinessDetailsLastView.h"

@implementation BusinessDetailsLastView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {

        UIImageView * lineIV = [[UIImageView alloc]initWithFrame:CGRectMake(52, 33, 263, 1.5)];
        [lineIV setImage:[UIImage imageNamed:@"u32_line.png"]];
        [self addSubview:lineIV];

        // Initialization code
        self.backgroundColor = [UIColor whiteColor];
        [self addUI];

    }
    return self;
}
-(void)addUI
{
    UILabel * lb = [[UILabel alloc]initWithFrame:CGRectMake(22.5, 2, 150, 30)];
    lb.text = @"商家简介:";
    [lb setFont:[UIFont systemFontOfSize:14]];
    [self addSubview:lb];
    
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
