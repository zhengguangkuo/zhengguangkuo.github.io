//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTGiftConpusViewController.h"
#import "UIImage(addition).h"
#import "UIView(category).h"
#import "TTConpus.h"
#import "GiftConpusCell.h"
#import "TTPickFriendViewController.h"


@interface TTGiftConpusViewController ()

@property  (nonatomic, strong)  UITableView* GiftTableView;

@property  (nonatomic, strong)  NSMutableArray* GiftList;

@property  (nonatomic, strong)  NSIndexPath* lastindexpath;


- (void)ParseJsonData: (NSString*)JsonData;

- (void)LayoutTableView;

- (void)LayoutBottomBtn;

@end


@implementation TTGiftConpusViewController

@synthesize GiftTableView = _GiftTableView;

@synthesize GiftList = _GiftList;

@synthesize UserID = _UserID;

@synthesize FriendName = _FriendName;

@synthesize lastindexpath = _lastindexpath;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"选择贴券";
        self.UserID = [[NSString alloc] init];
        self.FriendName = [[NSString alloc] init];
        self.GiftList = [[NSMutableArray alloc] init];
    }
    return self;
}



- (void)ParseJsonData: (NSString*)JsonData
{
    NSDictionary *dict1 = [[NSDictionary alloc] initWithObjectsAndKeys:
                            @"31441",@"ACT_ID",
                            @"满100减50",@"ACT_NAME",
                            @"http://s1.lashouimg.com/zt/201110/27/131972570125915500.jpg",@"PIC_PATH",
                            @"40021",@"MERCH_ID",
                            @"符离集炸鸡",@"MERCH_NAME",
                            @"2014-3-11",@"START_DATE",
                            @"2014-3-28",@"END_DATE",
                            @"112",@"ISSUED_CNT",
                           nil];

    
    NSDictionary *dict2 = [[NSDictionary alloc] initWithObjectsAndKeys:
                           @"31442",@"ACT_ID",
                           @"买手机送卡",@"ACT_NAME",
                           @"http://s1.lashouimg.com/zt/201110/25/131954190510086200.jpg",@"PIC_PATH",
                           @"40022",@"MERCH_ID",
                           @"天翼IOS土豪金",@"MERCH_NAME",
                           @"2014-5-18",@"START_DATE",
                           @"2014-5-31",@"END_DATE",
                           @"98",@"ISSUED_CNT",
                           nil];

    
    NSDictionary *dict3 = [[NSDictionary alloc] initWithObjectsAndKeys:
                           @"31443",@"ACT_ID",
                           @"黄金周大出血",@"ACT_NAME",
                           @"http://s1.lashouimg.com/zt/201110/27/131972321636938600.jpg",@"PIC_PATH",
                           @"40023",@"MERCH_ID",
                           @"世界杯2014吉祥物",@"MERCH_NAME",
                           @"2014-6-09",@"START_DATE",
                           @"2014-6-25",@"END_DATE",
                           @"512",@"ISSUED_CNT",
                           nil];
    
    NSArray* dicArray = [NSArray arrayWithObjects:dict1,dict2,dict3,nil];
    
    for(id object in dicArray)
    {
        if(object!=nil)
       {
          NSDictionary* dic = (NSDictionary*)object;
          TTConpus* ttp = [[TTConpus alloc] initWithDict:dic];
          [self.GiftList addObject:ttp];
       }
     }
}



- (void)RequestData
{
    [self.GiftList removeAllObjects];
    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_TT_Gift_List];
    
    NSDictionary* dic = [NSDictionary dictionaryWithObjectsAndKeys:
                         @"1",@"nPage",
                         @"220",@"nValue",
                         nil];
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:dic
                                                       withHud:YES];
    [tempservice setDelegate:self];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"data = %@",data);
         [self ParseJsonData:data];
         [self.GiftTableView reloadData];
     }
     ];
    
    [tempservice startOperation];
}



- (void)LayoutTableView
{
//    UIButton * btnTemp = [UIButton buttonWithType:UIButtonTypeCustom];
//    btnTemp.frame = CGRectMake(ScreenWidth/2 + 30, 5, 110, 30);
//    btnTemp.backgroundColor = [UIColor clearColor];
//    btnTemp.showsTouchWhenHighlighted = YES;
//    btnTemp.titleLabel.font = [UIFont systemFontOfSize:13];
//    [btnTemp setTitle:@"测试按钮" forState:UIControlStateNormal];
//    [btnTemp setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
//    [btnTemp addTarget:self action:@selector(SetSelected) forControlEvents:UIControlEventTouchUpInside];
//    [self.view addSubview:btnTemp];
    UIImageView*  bgImageView = [[UIImageView alloc] initWithFrame:CGRectMake(2, 5, 316,40)];
    [bgImageView setImage:[UIImage imageNamed:@"u62.png"]];
    [self.view addSubview:bgImageView];
    
    UILabel* lb = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 300, 40)];
    [lb setTextColor:[UIColor blackColor]];
    [lb setFont:[UIFont boldSystemFontOfSize:15.0f]];
    [lb setBackgroundColor:[UIColor clearColor]];
    [bgImageView addSubview:lb];
    [lb setText:self.FriendName];
    
    
    self.GiftTableView = [[UITableView alloc] initWithFrame:CGRectMake(5, 52, ScreenWidth - 10, 360)];
    [_GiftTableView setDataSource:self];
    [_GiftTableView setDelegate:self];
    [_GiftTableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    [_GiftTableView setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:self.GiftTableView];
}

- (void)LayoutBottomBtn
{
    UIButton * btnTemp = [UIButton buttonWithType:UIButtonTypeCustom];
        btnTemp.frame = CGRectMake(0, 416, ScreenWidth, 40);
    [btnTemp setBackgroundImage:[UIImage imageNamed:@"u184.png"] forState:UIControlStateNormal];
    //btnTemp.backgroundColor = [UIColor greenColor];
    [btnTemp setTitle:@"发送" forState:UIControlStateNormal];
    [btnTemp.titleLabel setFont:[UIFont boldSystemFontOfSize:16.0f]];
    [btnTemp setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btnTemp addTarget:self action:@selector(SendConpus) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btnTemp];
}

- (void)SendConpus
{
    NSLog(@"test");
//    TTPickFriendViewController* tf = [[TTPickFriendViewController alloc] init];
//    [self.navigationController pushViewController:tf animated:YES];
}


- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self NavigationViewBackBtn];
    [self LayoutTableView];
    [self LayoutBottomBtn];
    
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
    NSLog(@"userid = %@",self.UserID);
    [self RequestData];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return  [self.GiftList count];
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 120;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static  NSString *identifier = @"GiftConpus";
    int n = [indexPath row];
    
    GiftConpusCell *cell = (GiftConpusCell*)[tableView dequeueReusableCellWithIdentifier:identifier];
    
    if(cell==nil)
    {
        cell = [[GiftConpusCell alloc] initCustom];
    }
    
    if(n>=0&&n<[self.GiftList count])
    {
        TTConpus* tempObject = self.GiftList[n];
        [cell SetGiftObject:tempObject];
    }
    
    return cell;
}


//
//
//- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    return UITableViewCellEditingStyleDelete | UITableViewCellEditingStyleInsert;
//}

//
//#pragma mark -
//#pragma mark Table view delegate
//
//- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    int newRow = [indexPath row];
//    int oldRow = (self.lastindexpath!= nil) ? [self.lastindexpath row] : -1;
//    
//    if (newRow!=oldRow)
//    {
//        UITableViewCell *oldCell = [tableView cellForRowAtIndexPath:self.lastindexpath];
//        oldCell.accessoryType = UITableViewCellAccessoryNone;
//
//        UITableViewCell *newCell = [tableView cellForRowAtIndexPath:indexPath];
//        newCell.accessoryType = UITableViewCellAccessoryCheckmark;
//        
//        self.lastindexpath = [indexPath copy];//一定要这么写，要不报错
//    }
//    [tableView deselectRowAtIndexPath:indexPath animated:YES];
//    
//}
//

@end
