//
//  TTTopButton.m
//  Miteno
//
//  Created by wg on 14-7-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTTopButton.h"
#define kImageRatio 0.8
@implementation TTTopButton
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentRight;
        self.titleLabel.font = [UIFont systemFontOfSize:14];
        //        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        //        self.backgroundColor = [UIColor greenColor];
    }
    return self;
}
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(-10,0, contentRect.size.width/2+35,contentRect.size.height);
}
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(contentRect.size.width/2+30,contentRect.size.height/3 + 5,7,7);
}
@end
