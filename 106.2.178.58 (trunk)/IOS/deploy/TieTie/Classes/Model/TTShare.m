//
//  TTShare.m
//  Miteno
//
//  Created by wg on 14-6-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTShare.h"

@implementation TTShare
+ (id)shareWithDict:(NSDictionary *)dict
{
    TTShare *s = [[self alloc] init];
    s.title = dict[@"title"];
    s.icon = dict[@"icon"];
    return s;
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
