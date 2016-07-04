//
//  SystemDialog.m
//  Mpay
//
//  Created by HWG on 13-12-19.
//  Copyright (c) 2013年 miteno. All rights reserved.
//

#import "SystemDialog.h"
#import "AppDelegate.h"

@implementation SystemDialog
- (id)initWithText:(NSString *)text
{
    self = [super init];
    if (self) {
        
        self.backgroundColor=[UIColor blackColor];
        self.alpha=0.0;
        self.layer.cornerRadius = 6;
        self.layer.masksToBounds = YES;
        self.layer.borderWidth = 0;
        self.layer.borderColor =[[UIColor clearColor]CGColor];
        CGSize size=[text sizeWithFont:[UIFont boldSystemFontOfSize:16.0] constrainedToSize:CGSizeMake(220, 100) lineBreakMode:NSLineBreakByCharWrapping];
        UILabel * titleLable=[[UILabel alloc] initWithFrame:CGRectMake(8,8,size.width,size.height)];
        titleLable.lineBreakMode = NSLineBreakByCharWrapping;
        titleLable.numberOfLines = 0;
        titleLable.textColor=[UIColor whiteColor];
        titleLable.textAlignment=NSTextAlignmentLeft;
        titleLable.backgroundColor=[UIColor clearColor];
        titleLable.font=[UIFont boldSystemFontOfSize:16.0];
        titleLable.text=text;
        [self addSubview:titleLable];
        
        //self.frame=CGRectMake((320-kControlWidth(titleLable)-16)/2,460-216-60,kControlWidth(titleLable)+16, kControlRbY(titleLable)+8);
        self.frame = CGRectMake((ScreenWidth - titleLable.frame.size.width -16)/2, (ScreenHeight-64-216)+200, (titleLable.frame.size.width + 16), (titleLable.frame.size.height + 16));
        
        [((AppDelegate*)[UIApplication sharedApplication].delegate).window addSubview:self];
    }
    return self;
}


- (void) show {
    
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:0.7f];
    self.alpha=0.8;
    [self performSelector:@selector(hidden:) withObject:nil afterDelay:1.7];
    [UIView commitAnimations];
}

- (void) hidden:(id)sender{
    if ([self superview]) {
        [UIView beginAnimations:nil context:nil];
        [UIView setAnimationDuration:0.7f];
        self.alpha=0.0;
        [UIView commitAnimations];
    }
    
}
@end
