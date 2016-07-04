//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSearchFriendViewController.h"
#import "UIImage(addition).h"
#import "UIView(category).h"
#import "UIButton(addtion).h"
#import "TTPersonViewController.h"
#import "TTBookListViewController.h"
#import "TTFriend.h"



@interface TTSearchFriendViewController ()

@property  (nonatomic, strong)  TTFriend* mFriend;

- (void)ParseJsonData: (NSString*)JsonData;

- (void)LayoutAllView;

@end


@implementation TTSearchFriendViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"添加贴友";
        self.mFriend = [[TTFriend alloc] init];
    }
    return self;
}


- (void)LayoutAllView
{
    [self LayoutTitle];
    [self LayoutSearchBar];
    [self LayoutNote];
}


- (void)LayoutTitle
{
    UILabel*  Lb_Title = [[UILabel alloc] initWithFrame:CGRectMake(15, 0, 320, 40)];
    [Lb_Title setTextColor:[UIColor blackColor]];
    [Lb_Title setFont:[UIFont systemFontOfSize:14.0f]];
    [Lb_Title setText:@"搜索号码添加贴友"];
    [Lb_Title setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:Lb_Title];
}


- (void)LayoutSearchBar
{
    UIView* bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 40, ScreenWidth, 40)];
    [bgView setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:bgView];
    
    UISearchBar*  searchBar = [[UISearchBar alloc] initWithFrame:CGRectMake(0, 0, ScreenWidth - 50, 40)];
    searchBar.placeholder=@"输入好友贴贴帐号/手机号                    ";
    searchBar.keyboardType=UIKeyboardTypeDefault;
    
    if(IOS7)
    {
        searchBar.barTintColor = [UIColor whiteColor];
        [searchBar ViewBorder:[UIColor whiteColor] Radius:1.0f];
    }
    else
    {
        for (UIView *subview in searchBar.subviews) {
            if ([subview isKindOfClass:NSClassFromString(@"UISearchBarBackground")]) {
                [subview removeFromSuperview];
                break;
            }
        }
        
        UITextField *searchField = nil;
        NSUInteger numViews = [searchBar.subviews count];
        for(int i = 0; i < numViews; i++) {
            if([[searchBar.subviews objectAtIndex:i] isKindOfClass:[UITextField class]]) { //conform?
                searchField = [searchBar.subviews objectAtIndex:i];
            }
        }
        
        if(nil!=searchField)
        {
            searchField.textColor = [UIColor blackColor];
            [searchField setBorderStyle:UITextBorderStyleRoundedRect];
            [searchField ViewBorder:[UIColor whiteColor] Radius:10.0f];
            searchField.layer.masksToBounds = YES;
        }
    }
    
    UIImage *image = [UIImage scaleToSize:[UIImage generateFromColor:[UIColor whiteColor]] size:CGSizeMake(1, 1)];
    [searchBar setImage:image
       forSearchBarIcon:UISearchBarIconSearch
                  state:UIControlStateNormal];
    [searchBar setPositionAdjustment:UIOffsetMake(-10, 0) forSearchBarIcon:UISearchBarIconSearch];
    [bgView addSubview:searchBar];
    
//    UIButton*  tempBtn = [UIButton ButtonWithParms:[UIColor blackColor] title:@"" bgnormal:[UIImage imageNamed:@"u25.png"] imgHighlight:[UIImage imageNamed:@"u25.png"] target:self action:@selector(SearchByUserID)];
    UIButton* tempBtn = [UIButton buttonWithType:UIButtonTypeSystem];
    [tempBtn setBackgroundImage:[UIImage imageNamed:@"u25.png"] forState:UIControlStateNormal];
    [tempBtn setBackgroundImage:[UIImage imageNamed:@"u25.png"] forState:UIControlStateNormal];
    [tempBtn addTarget:self action:@selector(SearchByUserID) forControlEvents:UIControlEventTouchUpInside];
    [tempBtn setFrame:CGRectMake(275, 10, 20, 20)];
    [bgView addSubview:tempBtn];
}


- (void)LayoutNote
{
    UIButton*  tempBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [tempBtn addTarget:self action:@selector(SearchByNote) forControlEvents:UIControlEventTouchUpInside];
    [tempBtn setFrame:CGRectMake(0, 90, 320, 40)];
    [tempBtn setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:tempBtn];
    //[tempBtn ViewBorder:[UIColor blackColor] Radius:0.0f];
    
    
    
    UILabel*  Lb_Note = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 150, 40)];
    [Lb_Note setTextColor:[UIColor blackColor]];
    [Lb_Note setFont:[UIFont systemFontOfSize:14.0f]];
    [Lb_Note setText:@"    从通讯录添加"];
    [Lb_Note setBackgroundColor:[UIColor clearColor]];
    [tempBtn addSubview:Lb_Note];
    
    UIImage* bgImage = [UIImage imageNamed:@"ul_12.png"];
    UIImageView* tempImageView = [[UIImageView alloc] initWithImage:bgImage];
    CGSize tempSize = bgImage.size;
    [tempImageView setFrame:CGRectMake(279, 7, tempSize.width*3/4, tempSize.height*3/4)];
    [tempBtn addSubview:tempImageView];
}


- (void)SearchByNote
{
    NSLog(@"telephone note");
    TTBookListViewController*  bookList = [[TTBookListViewController alloc] init];
    [self.navigationController pushViewController:bookList animated:YES];
}


- (void)SearchByUserID
{
    NSLog(@"User id");
    [self RequestData];
}


- (void)ParseJsonData: (NSString*)JsonData
{
    NSLog(@"search begin");
    NSDictionary *dict0 = [NSDictionary dictionaryWithObjectsAndKeys:
                           @"07903",@"USERID",
                           @"王小二",@"USERNAME",
                           @"小二",@"NICKNAME",
                           @"13689200453",@"MOBILE",
                           @"smile123@163.com",@"EMAIL",
                           @"http://s1.lashouimg.com/zt/201110/25/131954190510086200.jpg",@"HEADPIC",
                           nil];
    
    self.mFriend = [[TTFriend alloc] initWithDict:dict0];
    NSLog(@"search end");
}



- (void)RequestData
{
    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_TT_Find_Friend];
    
    NSDictionary* dic = [NSDictionary dictionaryWithObjectsAndKeys:
                         @"Ada",@"KeyWord",
                         nil];
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:dic
                                                       withHud:YES];
    [tempservice setDelegate:self];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"data = %@",data);
         [self ParseJsonData:data];
         TTPersonViewController* ttc = [[TTPersonViewController alloc] initWithNibName:@"TTPersonViewController" bundle:nil];
         [self.navigationController pushViewController:ttc animated:YES];
         [ttc setMFriend:self.mFriend];
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
