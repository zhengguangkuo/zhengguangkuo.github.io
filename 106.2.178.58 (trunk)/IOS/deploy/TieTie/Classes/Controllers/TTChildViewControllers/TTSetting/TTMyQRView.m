//
//  TTMyQRView.m
//  Miteno
//
//  Created by wg on 14-8-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMyQRView.h"

@implementation TTMyQRView

+ (instancetype)qrcodeView
{
    return [[NSBundle mainBundle]loadNibNamed:@"TTMyQRView" owner:nil options:nil][0];
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
