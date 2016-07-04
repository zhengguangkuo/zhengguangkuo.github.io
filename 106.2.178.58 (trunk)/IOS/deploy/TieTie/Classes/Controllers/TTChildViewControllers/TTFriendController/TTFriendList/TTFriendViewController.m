//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFriendViewController.h"
#import "UIImage(addition).h"
#import "UIView(category).h"
#import "UIView+ViewFrameGeometry.h"
#import "TTFriend.h"
#import "FriendCell.h"
#import "UIButton(addtion).h"
#import "TTSearchFriendViewController.h"


@interface TTFriendViewController ()

@property  (nonatomic, strong)  UITableView*  FriendTableView;

@property  (nonatomic, strong)  NSMutableArray* FriendList;

- (void)ParseJsonData: (NSString*)JsonData;

- (void)LayoutSearchBar;

- (void)LayoutTableView;

@end


@implementation TTFriendViewController

@synthesize FriendList = _FriendList;

@synthesize FriendTableView = _FriendTableView;


#define  kSearchBarTag             0

#define  kFriendTableViewTag       1


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"贴友";
        self.FriendList = [[NSMutableArray alloc] init];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self NavigationRightButtons];
    [self LayoutSearchBar];
    [self LayoutTableView];
    
    // Do any additional setup after loading the view.
}

  
- (void)NavigationRightButtons
{
    UIButton* NewCard = [UIButton ButtonWithParms:[UIColor clearColor] title:@"" bgnormal:[UIImage imageNamed:@"jh.png"] imgHighlight:nil target:self action:@selector(NewFriend)];
    [NewCard setFrame:CGRectMake(260, 7, 20, 20)];
    NSArray*  temArray = [NSArray arrayWithObjects:NewCard, nil];
    [self SetNaviationRightButtons:temArray];
}

- (void)NewFriend
{
    NSLog(@"new Friend");
    TTSearchFriendViewController*  SFVCtr = [[TTSearchFriendViewController alloc] init];
    [self.navigationController pushViewController:SFVCtr animated:NO];
}




- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
    //self.FriendList = [[NSMutableArray  alloc] initWithArray:[self ParseJsonData:nil]];
    //[self.FriendTableView reloadData];
    [self  RequestFriendList];
}


- (void)LayoutSearchBar
{
    UISearchBar*  searchBar = [[UISearchBar alloc] initWithFrame:CGRectMake(5, 5, ScreenWidth - 10, 40)];
    [searchBar setTag:kSearchBarTag];
    searchBar.placeholder=@"";
    searchBar.keyboardType=UIKeyboardTypeDefault;
    if(IOS7)
    {
        searchBar.barTintColor = [UIColor whiteColor];
        [searchBar ViewBorder:[UIColor orangeColor] Radius:10.0f];
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
            [searchField ViewBorder:[UIColor orangeColor] Radius:10.0f];
            searchField.layer.masksToBounds = YES;
        }
    }

    UIImage *image = [UIImage scaleToSize:[UIImage generateFromColor:[UIColor whiteColor]] size:CGSizeMake(1, 1)];
    [searchBar setImage:image
        forSearchBarIcon:UISearchBarIconSearch
                   state:UIControlStateNormal];
    [searchBar setPositionAdjustment:UIOffsetMake(-15, 0) forSearchBarIcon:UISearchBarIconSearch];
    //[searchBar ViewBorder:[UIColor orangeColor] Radius:10.0f];
    //[searchBar becomeFirstResponder];
    [self.view addSubview:searchBar];
}

- (void)RequestFriendList
{
    [self.FriendList removeAllObjects];
    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_TT_Friend_List];
    
    NSDictionary* dic = [NSDictionary dictionaryWithObjectsAndKeys:
                         @"0",@"page",
                         @"999",@"value",
                         nil];
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:dic
                                                       withHud:YES];
    [tempservice setDelegate:self];
        
    [tempservice  setDataHandler:^(NSString* data)
    {
       NSLog(@"data = %@",data);
       [self ParseJsonData:data];
       [self.FriendTableView reloadData];
    }
    ];
        
    [tempservice startOperation];
}


- (void)ParseJsonData: (NSString*)JsonData
{
    NSLog(@"parse begin");
    
    NSDictionary *dict1 = [NSDictionary dictionaryWithObjectsAndKeys:
                           @"04912",@"USERID",
                           @"张无忌",@"USERNAME",
                           @"阿牛",@"NICKNAME",
                           @"13800138000",@"MOBILE",
                           @"yitian@163.com",@"EMAIL",
                           @"http://s1.lashouimg.com/zt/201110/27/131972321636938600.jpg",@"HEADPIC",
                           nil];
  
    
    NSDictionary *dict2 = [NSDictionary dictionaryWithObjectsAndKeys:
                 @"04913", @"USERID",
                 @"孙悟空", @"USERNAME",
                 @"猴哥",   @"NICKNAME",
                 @"13900119000",@"MOBILE",
                 @"monkey@qq.com",@"EMAIL",
                 @"http://s1.lashouimg.com/zt/201110/25/131954190510086200.jpg",           @"HEADPIC",nil];

    
    NSDictionary *dict3 = [NSDictionary dictionaryWithObjectsAndKeys:
                @"04914", @"USERID",
                @"王铁锤", @"USERNAME",
                @"铁锤",   @"NICKNAME",
                @"18643119800",@"MOBILE",
                @"wtc@126.com",@"EMAIL",
                @"http://s1.lashouimg.com/zt/201110/27/131972570125915500.jpg",           @"HEADPIC",nil];
    
    
    NSDictionary *dict4 = [NSDictionary dictionaryWithObjectsAndKeys:
                @"04915", @"USERID",
                @"钢铁侠", @"USERNAME",
                @"Mike",   @"NICKNAME",
                @"15087654321",@"MOBILE",
                @"gtx@163.com",@"EMAIL",
                @"http://s1.lashouimg.com/zt/201110/25/131953847613105000.jpg",           @"HEADPIC",nil];
  

    NSDictionary *dict5 = [NSDictionary dictionaryWithObjectsAndKeys:
                @"04916", @"USERID",
                @"机器猫", @"USERNAME",
                @"Doliamen",   @"NICKNAME",
                @"18688888888",@"MOBILE",
                @"robert@163.com",@"EMAIL",
                @"http://s1.lashouimg.com/zt/201110/25/131954190510086200.jpg",           @"HEADPIC",nil];
    
    NSArray* dicArray = [NSArray arrayWithObjects:dict1,dict2,dict3,dict4,dict5,nil];
  
    for(id object in dicArray)
  {
      if(object!=nil)
    {
      NSDictionary* dic = (NSDictionary*)object;
      TTFriend* ttf = [[TTFriend alloc] initWithDict:dic];
      [self.FriendList addObject:ttf];
    }
  }
    
    NSLog(@"parse end");
}


- (void)LayoutTableView
{
    self.FriendTableView = [[UITableView alloc] initWithFrame:CGRectMake(5, 50, ScreenWidth - 10, 380)];
    [_FriendTableView setTag:kFriendTableViewTag];
    [_FriendTableView setDataSource:self];
    [_FriendTableView setDelegate:self];
    [_FriendTableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    [_FriendTableView setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:self.FriendTableView];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.FriendList count];
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 70;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
}


#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static  NSString *identifier = @"FriendList";
    int nRow = [indexPath row];
    
    FriendCell *cell = (FriendCell*)[tableView dequeueReusableCellWithIdentifier:identifier];
    
    if(cell==nil)
    {
        cell = [[FriendCell alloc] initCustom];
        [cell setEditing:NO];
    }
    
    if(nRow>=0&&nRow<[self.FriendList count])
    {
        TTFriend* tempFriend = self.FriendList[nRow];
        [cell SetObject:tempFriend];
    }
    return cell;
}

@end
