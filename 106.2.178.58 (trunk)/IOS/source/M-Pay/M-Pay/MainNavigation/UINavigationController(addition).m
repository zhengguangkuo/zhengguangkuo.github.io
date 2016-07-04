//
//  MainNavigation.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "UINavigationController(addition).h"
#import "UIImage(addition).h"

@implementation UINavigationController(addition)


- (void)SetBackButton:(id)target action:(SEL)action
{
    UIButton* barbutton=[UIButton buttonWithType:UIButtonTypeCustom];
    UIImage*  iconImage = [UIImage imageNamed:@"gb_button"];
    [barbutton setImage:iconImage forState:UIControlStateNormal];
    [barbutton setFrame:CGRectMake(-12, 7, 30, 30)];
    [barbutton addTarget:target action:action forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem* item = [[UIBarButtonItem alloc] initWithCustomView:barbutton];
    [self.navigationItem setLeftBarButtonItem:item];
    
}

- (void)SetLeftButtons:(NSArray*)buttons
{
    //[self.navigationItem setLeftBarButtonItems:buttons];
}

- (void)SetRightButtons:(NSArray*)buttons
{
    [self.navigationItem setRightBarButtonItems:buttons];
}


- (void)SetBackHidden
{
    [self.navigationItem setLeftBarButtonItems:nil];
}

- (void)SetTitleText:(NSString *)title
{
    UILabel* titleLabel =  [[UILabel alloc] initWithFrame:CGRectMake(127, 16, 100, 32)];
    [titleLabel setText:title];
    [titleLabel setTextColor:[UIColor whiteColor]];
    [titleLabel setBackgroundColor:[UIColor clearColor]];
    [titleLabel setFont:[UIFont boldSystemFontOfSize:16.0f]];
    [self.navigationItem setTitleView:titleLabel];
}

- (void)SetTitleColor:(UIColor *)color
{
    UILabel*  TitleLabel = (UILabel*)self.navigationItem.titleView;
    [TitleLabel setTextColor:color];
}

- (void)SetBackgroundImage:(UIImage *)image
{
    UIImage* bgimage = image;
    self.navigationBar.barStyle = UIBarStyleBlackTranslucent;
    self.navigationBar.backgroundColor = [UIColor whiteColor];

    
    if ([self.navigationBar respondsToSelector:@selector(setBackgroundImage:forBarMetrics:)])
    {

        UIImage*  bgviewImage = [UIImage scaleToSize:bgimage size:self.navigationBar.bounds.size];
        [self.navigationBar setBackgroundImage:bgviewImage forBarMetrics:UIBarMetricsDefault];
        
        UIView* view = [[UIView alloc] initWithFrame:CGRectMake(0, -20, self.navigationBar.bounds.size.width, 20)];
        [self.navigationBar addSubview:view];
        [view setBackgroundColor:[UIColor whiteColor]];

        NSLog(@"catch therer");
    }
    else
    {
        UIImageView* imageview = (UIImageView*)[self.navigationBar viewWithTag:2];
        
        if(!imageview)
    {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:bgimage];
        imageView.frame = self.navigationBar.bounds;
        imageView.backgroundColor = [UIColor whiteColor];
        [imageView setTag:2];
        
        [self.navigationBar insertSubview:imageView atIndex:0];
    }
       else
       {
           [imageview setImage:image];
       }
        
    }
        
}

@end
