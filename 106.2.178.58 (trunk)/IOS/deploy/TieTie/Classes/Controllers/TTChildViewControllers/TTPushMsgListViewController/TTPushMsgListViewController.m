//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "UIImage(addition).h"
#import "UIView(category).h"
#import "UIView+ViewFrameGeometry.h"
#import "UIButton(addtion).h"
#import "TTPushMsgListViewController.h"
#import "TextStyle.h"
#import "MsgPopBox.h"
#import "TTPushMsgTableViewCell.h"
#import "UIColor+Extension.h"
#import "DBManager.h"



#define     kTagMessage     0

#define     kTagDate        1


@interface TTPushMsgListViewController () <UITableViewDataSource, UITableViewDelegate,MJRefreshBaseViewDelegate>
{
    NSMutableArray *arrayData;
    NSMutableArray *arrayDataSelected;
    
    MJRefreshHeaderView *headerRefresh;
}

@property  (nonatomic, strong)  UITableView*      PushTableView;

- (void)LayoutTableView;

- (void)showDetailInfoBtnClicked:(id)sender;

- (void)handleMessage:(id)sender;

@end


@implementation TTPushMsgListViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"消息提醒";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    TTLog(@"消息 = TTPushMsgListViewController");
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif

    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.imageEdgeInsets = btnEdgeLeft;
    btn.frame = CGRectMake(0, 0, 57, 20);
    btn.userInteractionEnabled = NO;
    [btn setBackgroundImage:[UIImage imageNamed:@"tietie_top_logo"] forState:UIControlStateNormal];
    self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
    [self NavigationRightButtons];
    [self.view setBackgroundColor:TTGlobalBg];
    [self LayoutTableView];
    // Do any additional setup after loading the view.
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleMessage:) name:KNOTIFICATIONCENTER_MESSAGEFROMXMPP object:nil];
    headerRefresh = [[MJRefreshHeaderView alloc] init];
    headerRefresh.delegate = self;
    headerRefresh.scrollView = self.PushTableView;
    headerRefresh.backgroundColor = [UIColor clearColor];
}

- (void)viewWillAppear:(BOOL)animated
{
    [self getMesssageFromDB];
}

- (void)handleMessage:(id)sender
{
    NSNotification *notification = (NSNotification*)sender;
    NSString *message = notification.object;
    if (!arrayData) {
        arrayData = [[NSMutableArray alloc]init];
    }
    
    if (message) {
        [arrayData addObject:message];
        [self.PushTableView reloadData];
    }

}

- (void)getMesssageFromDB
{
    DBManager *db = [DBManager sharedDBManager];
    NSArray *array = [db queryTable:msgTableName where:nil];
    if (array) {
        array = [[array reverseObjectEnumerator] allObjects];
        if (arrayData && [arrayData count] == [array count]) {
            [SystemDialog alert:@"暂时没有新消息！"];
        } else {
             arrayData = [[NSMutableArray alloc]initWithArray:array];
            [self.PushTableView reloadData];
        }
    }
}

- (void)deleteMessageFromDB:(NSArray*)array
{
    if (!array) {
        return;
    }
    DBManager *db = [DBManager sharedDBManager];
    
    for (NSDictionary *dic in array) {
        NSString *time = [dic objectForKey:@"time"];
        NSDictionary *conditionDic = @{@"time": time};
        [db deleteTable:msgTableName where:conditionDic];
    }
}


- (void)NavigationRightButtons
{
    self.navigationItem.rightBarButtonItem =
    [[UIBarButtonItem alloc] initWithTitle:@"编辑" style:UIBarButtonItemStylePlain target:self action:@selector(editTableCells)];

}


- (void)editTableCells
{
    TTLog(@"edit");
    if (!arrayData || [arrayData count] == 0) {
        return;
    }
    
    [self.PushTableView setEditing:!self.PushTableView.editing animated:YES];
    if (self.PushTableView.editing) {
        self.navigationItem.rightBarButtonItem.title = @"删除";
        
    } else {
        self.navigationItem.rightBarButtonItem.title = @"编辑";
        if (!self.PushTableView.editing) {
            NSMutableArray *removeArray = [[NSMutableArray alloc]init];
            for (NSIndexPath *indexPath in arrayDataSelected) {
                NSDictionary *dic = [arrayData objectAtIndex:[indexPath section]];
                [removeArray addObject:dic];
            }
            //从数组列表中删除选中数据
            for (int i = 0,count = [removeArray count]; i < count; i++) {
                [arrayData removeObject:[removeArray objectAtIndex:i]];
            }
            //从数据库中删除选中数据
            [self deleteMessageFromDB:removeArray];
            [arrayDataSelected removeAllObjects];
            [self.PushTableView reloadData];
        }
    }
}


- (void)LayoutTableView
{
    self.PushTableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 2, ScreenWidth, ScreenHeight-64-44)];
    [self.PushTableView setDataSource:self];
    [self.PushTableView setDelegate:self];
    self.PushTableView.backgroundView = nil;
    [self.PushTableView setBackgroundColor:TTGlobalBg];
     self.PushTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:self.PushTableView];
}

#pragma mark - UITableDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return [arrayData count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return 1;
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *dic = [arrayData objectAtIndex:[indexPath section]];
    NSString *string = dic[@"message"];
    float height = [TextStyle deriveTextHeight:string width:300.0 font:[UIFont systemFontOfSize:17]];
    return height+20;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    if ([arrayData count] == (section + 1)) {
        return 1;
    }
    return 15.0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    TTPushMsgTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!customCell) {
        customCell = [[[NSBundle mainBundle] loadNibNamed:@"TTPushMsgTableViewCell" owner:self options:nil] lastObject];
        customCell.layer.masksToBounds = YES;
        customCell.layer.cornerRadius = 5;
    }
    NSMutableDictionary *dic = [arrayData objectAtIndex:[indexPath section]];
    customCell.textLab.text = [dic objectForKey:@"message"];
    return customCell;
}


- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    return YES;
}

- (UITableViewCellEditingStyle)tableView:(UITableView *)tableView editingStyleForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return UITableViewCellEditingStyleDelete | UITableViewCellEditingStyleInsert;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.PushTableView.editing) {
        if (!arrayDataSelected) {
            arrayDataSelected = [[NSMutableArray alloc] init];
        };
        [arrayDataSelected addObject:indexPath];
    }
}

- (void)tableView:(UITableView *)tableView didDeselectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (self.PushTableView.editing) {
        [arrayDataSelected removeObject:indexPath];
    }
}


#pragma mark - MJRefreshBaseViewDelegate
- (void)refreshViewBeginRefreshing:(MJRefreshBaseView *)refreshView
{
    if (refreshView == headerRefresh) {
        [self getMesssageFromDB];
    }
    [self performSelector:@selector(stopRefresh) withObject:self afterDelay:0.01];
}
- (void)stopRefresh
{
    [headerRefresh endRefreshing];
}

- (void)dealloc
{
    [headerRefresh free];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
}

- (void)showDetailInfoBtnClicked:(id)sender
{
    MsgPopBox*  popBox = [MsgPopBox msgpopbox];
    [popBox show:self];
}

@end
