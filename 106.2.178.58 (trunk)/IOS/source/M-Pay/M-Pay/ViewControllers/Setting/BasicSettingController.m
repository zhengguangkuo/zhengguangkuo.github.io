//
//  HomeViewController.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//
#import "BasicSettingController.h"
#import "IIViewDeckController.h"
#import "HttpService.h"
#import "NSString(Additions).h"
#import "NSDictionary(JSON).h"
#import "BasicCard.h"
#import "CardCell.h"
#import "Config.h"



@interface BasicSettingController () <UITableViewDelegate,UITableViewDataSource>

@property (nonatomic, strong) NSMutableArray* cardArray;

@property (nonatomic, strong) UITableView* cardTableView;

@end

@implementation BasicSettingController
@synthesize cardArray = _cardArray;
@synthesize cardTableView = _cardTableView;

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self SetNaviationTitleName:@"基卡设置"];
    self.cardArray = [[NSMutableArray alloc] init];
    self.cardTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, 320, 480) style:UITableViewStylePlain];
    [_cardTableView setDelegate:self];
    [_cardTableView setDataSource:self];
    [_cardTableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    [_cardTableView setBackgroundColor:[UIColor clearColor]];
    [self.view addSubview:_cardTableView];
}

- (void)backToPrevious
{
    [self.viewDeckController toggleLeftViewAnimated:YES];
}



-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
    [self RequestLogin];
}


-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:YES];
    [self.cardArray removeAllObjects];
    [self.cardTableView reloadData];
}



-(NSInteger)tableView:(UITableView *)tableView
numberOfRowsInSection:(NSInteger)section
{
    return [self.cardArray count];
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 130;
}


-(UITableViewCell *)tableView:(UITableView *)tableView
		cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"CardCell";
    
    int row = [indexPath row];
    
    CardCell *cell = (CardCell*)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    BasicCard*  tempobject = self.cardArray[row];
    
    if(cell==nil)
    {
        cell = [[CardCell alloc] initCustom];
    }
    [cell setBasicCard:tempobject];
    
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{


}







-(void)RequestList
{
     NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_Basic_Card_Setting];
     NSLog(@"testURL = %@",testURL);
    
    [self.cardArray removeAllObjects];

    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:nil
                                                       withHud:NO];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"data = %@",data);
         id result = [NSDictionary dictionaryWithString:data];
         if(![result isKindOfClass:[NSNull class]])
      {
           if([result isKindOfClass:[NSArray class]]){
              NSMutableArray* array = [[NSMutableArray alloc] initWithArray:result];
              for(id object in array)
           {
              if([object isKindOfClass:[NSDictionary class]])
             {
                 NSDictionary* dic = (NSDictionary*)object;
                 
                 BasicCard*  tempobject = [[BasicCard alloc] initWithDictionary:dic];
                 [self.cardArray addObject:tempobject];
             }
           }
         }
      }
         
         dispatch_async(dispatch_get_main_queue(),
    ^{
        [self.cardTableView reloadData];
    });

         
         
         
     }
     ];
    
    [tempservice startOperation];
}








-(void)RequestLogin
{
    NSString* password1 =  @"123456{mt009}";
    
    NSString* password = [password1 md5];
    
    NSLog(@"password = %@",password);
    
    NSString*  testURL =  [NSString stringWithFormat:@"%@mpayFront/j_spring_security_check",  WEB_SERVICE_ENV_VAR];
    NSLog(@"testURL = %@",testURL);
    NSDictionary* PostDic = [[NSDictionary alloc] initWithObjectsAndKeys:
                             @"mt009",@"j_username",
                             password,@"j_password",
                             nil];
    
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
                                                          body:PostDic
                                                       withHud:NO];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"登录成功!");
         NSLog(@"login str = %@",data);
         
         [self RequestList];
     }
     ];
    [tempservice setDelegate:self];
    
    [tempservice startOperation];
    
    
}




-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:YES];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
