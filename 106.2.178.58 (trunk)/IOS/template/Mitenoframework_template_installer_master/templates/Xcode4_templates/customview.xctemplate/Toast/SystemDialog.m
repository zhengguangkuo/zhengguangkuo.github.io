//
//  SystemDialog.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import "SystemDialog.h"
#import "AppDelegate.h"

@implementation UIView(dialog)
- (void)makeToast:(NSString *)text
{
    SystemDialog*  diallog = [[SystemDialog alloc] initWithText:text];
    [diallog show];
}

@end


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
