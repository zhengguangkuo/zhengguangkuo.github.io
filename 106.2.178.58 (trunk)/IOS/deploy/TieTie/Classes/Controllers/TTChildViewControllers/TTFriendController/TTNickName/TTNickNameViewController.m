//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTNickNameViewController.h"
#import "UIImage(addition).h"
#import "UIView(category).h"



@interface TTNickNameViewController ()

@property (nonatomic, strong) UITextField* UI_TextFd;

- (void)ParseJsonData: (NSString*)JsonData;

- (void)LayoutAllView;

@end


@implementation TTNickNameViewController

@synthesize  UI_TextFd;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"备注昵称";
    }
    return self;
}


- (void)LayoutAllView
{
    UILabel*  Indicator = [[UILabel alloc] initWithFrame:CGRectMake(10, 0, 320, 40)];
    [Indicator setTextColor:[UIColor blackColor]];
    [Indicator setFont:[UIFont boldSystemFontOfSize:14.0f]];
    [Indicator setText:@"设置备注昵称"];
    [Indicator setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:Indicator];
    
    
    self.UI_TextFd = [[UITextField alloc] initWithFrame:CGRectMake(0, 40, 320, 40)];
    [self.UI_TextFd setBackgroundColor:[UIColor whiteColor]];
    [self.UI_TextFd setTextColor:[UIColor grayColor]];
    [self.UI_TextFd setFont:[UIFont systemFontOfSize:15.0f]];
    UIView *lview = [[UIView alloc]
                     initWithFrame:CGRectMake(0.0, 0.0, 5.0, 40.0)];
    [self.UI_TextFd setLeftView:lview];
    self.UI_TextFd.leftViewMode = UITextFieldViewModeAlways;
    [self.UI_TextFd setPlaceholder:@"请输入昵称"];
    [self.view addSubview:self.UI_TextFd];
    
    
    UIButton * btnTemp = [UIButton buttonWithType:UIButtonTypeCustom];
    btnTemp.frame = CGRectMake(15, 90, ScreenWidth - 30, 40);
    [btnTemp setBackgroundImage:[UIImage imageNamed:@"u184.png"] forState:UIControlStateNormal];
    //btnTemp.backgroundColor = [UIColor greenColor];
    [btnTemp setTitle:@"提交" forState:UIControlStateNormal];
    [btnTemp.titleLabel setFont:[UIFont boldSystemFontOfSize:16.0f]];
    [btnTemp ViewBorder:[UIColor clearColor] Radius:8.0f];
    [btnTemp setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btnTemp addTarget:self action:@selector(submit) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnTemp];
}


- (void)submit
{
    [self RequestData];
}



- (void)ParseJsonData: (NSString*)JsonData
{
    
}



- (void)RequestData
{
    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_TT_NickName];
    
    NSDictionary* dic = [NSDictionary dictionaryWithObjectsAndKeys:
                         self.UI_TextFd.text,@"NickName",
                         nil];
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:dic
                                                       withHud:YES];
    [tempservice setDelegate:self];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"data = %@",data);
         [self ParseJsonData:data];
     }
     ];
    
    [tempservice startOperation];
}



- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor colorWithRed:0.9 green:0.9 blue:0.9 alpha:1]];
    [self NavigationViewBackBtn];
    [self LayoutAllView];
    
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
}
@end
