//
//  TempView.m
//  Miteno
//
//  Created by wg on 14-8-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TempView.h"

@implementation TempView
+ (instancetype)tempView
{
    return [[NSBundle mainBundle] loadNibNamed:@"TempView" owner:nil options:nil][0];
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
