//
//  TTDetailHeadView.m
//  Miteno
//
//  Created by wg on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTDetailHeadView.h"

@implementation TTDetailHeadView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
    }
    return self;
}

- (IBAction)clickShare:(id)sender {
}

+ (instancetype)detailHeadView
{
    return [[NSBundle mainBundle]loadNibNamed:@"TTDetailHeadView" owner:nil options:nil][0];
}
- (void)awakeFromNib
{
    _headIcon.layer.cornerRadius = 5;
    _headIcon.layer.masksToBounds = YES;
    
//    _headDownBg.backgroundColor = TTGlobalBg;
    self.backgroundColor = TTGlobalBg;
    
    self.headDownBg.image = [UIImage stretchImageWithName:@"whightbg_t2_border_radius"];
    self.headImageView.backgroundColor = [UIColor clearColor];
    self.headBg.backgroundColor = [UIColor clearColor];
    self.headDate.backgroundColor = [UIColor clearColor];
    
}
@end
