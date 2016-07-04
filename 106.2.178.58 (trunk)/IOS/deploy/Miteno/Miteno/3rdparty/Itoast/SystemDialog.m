//
//  SystemDialog.m
//  Mpay
//
//  Created by HWG on 13-12-19.
//  Copyright (c) 2013年 miteno. All rights reserved.
//

#import "SystemDialog.h"
#import "AppDelegate.h"
@implementation UIView(dialog)
- (void)makeToast:(NSString *)text
{
    SystemDialog*  diallog = [[SystemDialog alloc] initWithText:text];
    [diallog show];
}



@end
@interface SystemDialog()
{
    UILabel    *_titleLable;
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
        _titleLable=[[UILabel alloc] initWithFrame:CGRectMake(8,8,size.width,size.height)];
        _titleLable.lineBreakMode = NSLineBreakByCharWrapping;
        _titleLable.numberOfLines = 0;
        _titleLable.textColor=[UIColor whiteColor];
        _titleLable.textAlignment=NSTextAlignmentLeft;
        _titleLable.backgroundColor=[UIColor clearColor];
        _titleLable.font=[UIFont boldSystemFontOfSize:16.0];
        _titleLable.text = text;;
        [self addSubview:_titleLable];
        
        //self.frame=CGRectMake((320-kControlWidth(titleLable)-16)/2,460-216-60,kControlWidth(titleLable)+16, kControlRbY(titleLable)+8);
        self.frame = CGRectMake((ScreenWidth - _titleLable.frame.size.width -16)/2, (ScreenHeight-64-216)+170, (_titleLable.frame.size.width + 16), (_titleLable.frame.size.height + 16));
        
        UIWindow *window = [[[UIApplication sharedApplication] windows] lastObject];
        [window addSubview:self];
//        [((AppDelegate*)[UIApplication sharedApplication].delegate).window addSubview:self];
    }
    return self;
}

- (void) show {
//    [UIView beginAnimations:nil context:nil];
//    [UIView setAnimationDuration:0.7f];
//    self.alpha=0.8;
//    [self performSelectorOnMainThread:@selector(hidden:) withObject:nil waitUntilDone:YES];
    
//    [self performSelector:@selector(hidden:) withObject:nil afterDelay:0.0f];
//    [UIView commitAnimations];
    
    double delayInSeconds = 0.5;
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, delayInSeconds * NSEC_PER_SEC);
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        self.alpha=0.8;
        //    [self performSelectorOnMainThread:@selector(hidden:) withObject:nil waitUntilDone:YES];
        
        [self performSelector:@selector(hidden:) withObject:nil afterDelay:0.0f];
    });
}

- (void) hidden:(id)sender{
    if ([self superview]) {
//        [UIView beginAnimations:nil context:nil];
//        [UIView setAnimationDuration:1.9f];
//        self.alpha=0.0;
//        [UIView commitAnimations];
        double delayInSeconds = 1.3f;
        dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, delayInSeconds * NSEC_PER_SEC);
        dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
            self.alpha=0.0;
        });
    }
    
}

@end
