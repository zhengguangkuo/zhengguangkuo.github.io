//
//  MainNavigation.m
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import "MainNavigation.h"
#import "Config.h"
@interface MainNavigation()
@end


@implementation MainNavigation

static NSUInteger TitleTag = 15280;


-(void)NavigationBgImage:(NSString*)imageName
{
    UIImage* bgimage = [UIImage imageNamed:imageName];
    self.barStyle = UIBarStyleBlackTranslucent;
    self.backgroundColor = [UIColor clearColor];
    
    if ([self respondsToSelector:@selector(setBackgroundImage:forBarMetrics:)])
    {
        [self setBackgroundImage:bgimage forBarMetrics:UIBarMetricsDefault];
    }
    else
    {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:bgimage];
        imageView.frame = self.bounds;
        imageView.backgroundColor = [UIColor whiteColor];
        [self insertSubview:imageView atIndex:0];
    }
}


-(void)NavigationViewBackBtn:(UIButton*)back
{
    UIBarButtonItem *toggleLeft=[[UIBarButtonItem alloc]initWithCustomView:back];
    
    self.backItem.leftBarButtonItem=toggleLeft;
}


-(void)NavigationViewRightBtns:(NSArray*)buttons
{
    NSMutableArray* buttonArray =  [[NSMutableArray alloc] initWithCapacity:10];
    for(UIButton*  tempbutton in buttons)
    {
        UIBarButtonItem *toggleRight=[[UIBarButtonItem alloc]initWithCustomView:tempbutton];
        [buttonArray addObject:toggleRight];
    }
    self.backItem.rightBarButtonItems=buttonArray;
}


-(void)NavigationTitle
{
    UILabel* titleLabel =  [[UILabel alloc] initWithFrame:CGRectMake(127, 5, 100, 32)];
    [titleLabel setTag:TitleTag];
    [titleLabel setText:@""];
    [titleLabel setTextColor:[UIColor whiteColor]];
    [titleLabel setBackgroundColor:[UIColor clearColor]];
    [titleLabel setFont:[UIFont systemFontOfSize:16.0f]];
    [self addSubview:titleLabel];
}

-(void)NavigationTitleText:(NSString*)title
{
    UIView*  titleview = [self viewWithTag:TitleTag];
    UILabel*  titleLabel = (UILabel*)titleview;
    CGSize titleSize = [title sizeWithFont:[UIFont systemFontOfSize:16.0f] constrainedToSize:CGSizeMake(MAXFLOAT, 30)];
    [titleLabel setText:title];
    
    CGFloat width = titleSize.width;
    
    CGSize  tempsize = titleLabel.frame.size;
    
    CGPoint tempoint = titleLabel.frame.origin;
    
    [titleLabel setFrame:CGRectMake((kScreenWidth - width)/2, tempoint.y, width, tempsize.height)];
}


@end
