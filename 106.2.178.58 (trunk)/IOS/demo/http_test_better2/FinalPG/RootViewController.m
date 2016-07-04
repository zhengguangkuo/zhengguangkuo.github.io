#import <Foundation/NSObject.h>
#import "RootViewController.h"
//#import "AppDelegate.h"

@interface RootViewController ()

@property  (nonatomic,retain)  UIView*   titleView;

@property  (nonatomic,retain)  UILabel*  titleLabel;


@end



@implementation RootViewController

#define  FULL_SCREEN_Frame    [UIScreen mainScreen].applicationFrame

#define RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:1]


-(void)SetTitleText:(NSString*)text
{
    [self.titleLabel  setText:text];
}




-(void)AddBackgroundImage:(NSString*)ImageName
{
    UIImageView* BgImageView = [[UIImageView alloc]  initWithImage:[UIImage imageNamed:ImageName]];
    BgImageView.frame  = FULL_SCREEN_Frame;
    [self.view addSubview:BgImageView];
    [BgImageView release];
}


- (void)dealloc
{
    [_titleView release];
    [_titleLabel release];
    [super dealloc];
}



- (void)viewDidLoad
{
    self.titleView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 320, 44)];
    [self.titleView setBackgroundColor:RGBCOLOR(85, 85, 85)];
    
    self.titleLabel = [[UILabel alloc]  initWithFrame:CGRectMake(60, 6, 200, 30)];
    self.titleLabel.backgroundColor=[UIColor clearColor];
    self.titleLabel.textAlignment=NSTextAlignmentCenter;
    self.titleLabel.textColor=[UIColor whiteColor];
    [self.titleLabel setFont:[UIFont systemFontOfSize:14.f]];
    [self.titleLabel setTextColor:[UIColor whiteColor]];
    
    [self.titleView addSubview:self.titleLabel];
    [self.titleLabel release];

    [self.navigationController.navigationBar addSubview:self.titleView];
    [self.titleView release];
}



- (void)viewDidUnload
{

}


-(void)viewDidAppear:(BOOL)animated
{
    
    
}


-(void)viewDidDisappear:(BOOL)animated
{
    
}


@end
