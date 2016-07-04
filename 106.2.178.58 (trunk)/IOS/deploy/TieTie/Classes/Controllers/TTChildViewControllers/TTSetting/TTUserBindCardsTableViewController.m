//
//  TTUserBindCardsTableViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTUserBindCardsTableViewController.h"
#import "TTBaseCardInfo.h"
#import "TTBaseCardSettingTableViewController.h"
#import "TTBaseCardTableViewCell.h"

@interface TTUserBindCardsTableViewController ()
{
    NSMutableArray *arrayData;
}

- (void)intentToSystemCards;
- (void)switchValueChange:(id)sender;
@end

@implementation TTUserBindCardsTableViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = TTGlobalBg;
    
    self.navigationItem.title = @"基卡信息";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"top_add_normal"] style:UIBarButtonItemStylePlain target:self action:@selector(intentToSystemCards)];
                                              
    self.tableView.allowsSelection = NO;
    [self loadUserBindCardsFromSever];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    if (arrayData.count > 0) {
        [self backgroundViewWithNoData:NO];
    } else {
        [self backgroundViewWithNoData:YES];
    }
}

- (void)backgroundViewWithNoData:(BOOL)hasBgView
{
    if (!hasBgView) {
        self.tableView.backgroundView = nil;
    } else {
        UIImageView *imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"icon_card_bg"]];
        [imageView setCenter:CGPointMake(160, 200)];
        
        UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
        label.text = @"您还没有绑定基卡，点击右上角添加吧！";
        label.textColor = [UIColor grayColor];
        label.textAlignment = NSTextAlignmentCenter;
        [label setCenter:CGPointMake(160, 240)];
        
        UIView *bgView = [[UIView alloc] initWithFrame:[UIScreen mainScreen].applicationFrame];
        [bgView addSubview:imageView];
        [bgView addSubview:label];
        
        self.tableView.backgroundView = bgView;
    }
}

-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [arrayData count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 89.0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    TTBaseCardTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!customCell) {
        customCell = [[[NSBundle mainBundle] loadNibNamed:@"TTBaseCardTableViewCell" owner:self options:nil] lastObject];
//        customCell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    
    TTBaseCardInfo *info = [arrayData objectAtIndex:[indexPath row]];
    [customCell.cardFaceImageView setImageWithURL:[NSURL URLWithString:info.cardPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    customCell.cardName.text = info.cardName;
    customCell.cardType.text = info.cardDetail;
    customCell.cardCode.text = info.cardNo;
    if ([info.bindAble isEqualToString:@"0"]) {
        customCell.bindAbleSwitch.hidden = YES;
    } else {
        customCell.bindAbleSwitch.hidden = NO;
        customCell.bindAbleSwitch.tag = [indexPath row];
        [customCell.bindAbleSwitch addTarget:self action:@selector(switchValueChange:) forControlEvents:UIControlEventValueChanged];
    }
    [customCell setbindCardIconFlag:NO];

    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,customCell.size.height-1, customCell.frame.size.width,0.5);
    [customCell addSubview:divider];
    return customCell;
}



// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
     TTBaseCardInfo *info = [arrayData objectAtIndex:[indexPath row]];
    //bindAble:用户是否可绑定（解绑） 0、是，1、否
    if ([info.bindAble isEqualToString:@"0"]) {
        return YES;
    }
    return NO;
}



// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [self unbindBaseCardRequest:indexPath];
        
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}


/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

#pragma mark - Custom Methods
- (void)loadUserBindCardsFromSever
{
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *paramDic = @{@"mobile":mobile,@"sysPlat":@"5"};
    
    [TieTieTool tietieWithParameterMarked:TTAction_queryUserBindBaseCard dict:paramDic succes:^(id responseObj) {
        if ([[responseObj objectForKey:rspCode]isEqualToString:rspCode_success]) {
            if (arrayData) {
                [arrayData removeAllObjects];
            } else {
                arrayData = [[NSMutableArray alloc]init];
            }
            for (NSDictionary *dic in [responseObj objectForKey:@"cardList"]) {
                TTBaseCardInfo *baseCard = [[TTBaseCardInfo alloc]initWithDictionary:dic];
                [arrayData addObject:baseCard];
            }
            
            if (arrayData.count > 0) {
                [self backgroundViewWithNoData:NO];
            } else {
                [self backgroundViewWithNoData:YES];
            }
            
            [self.tableView reloadData];
        } else {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        TTLog(@"%@",error);
    }];
}


- (void)intentToSystemCards;
{
    TTBaseCardSettingTableViewController *baseCardVC = [[TTBaseCardSettingTableViewController alloc]init];
    [self.navigationController pushViewController:baseCardVC animated:YES];
}

- (void)unbindBaseCardRequest:(NSIndexPath *)indexPath
{
    //{"mobile":"13426364664","cardNo":"13426364664","cardTypeNo":"13426364664","action":"0","sysPlat":"5"}
    TTBaseCardInfo *info = [arrayData objectAtIndex:[indexPath row]];
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *cardTypeNo = info.cardTypeNo;
    NSString *cardNo = info.cardNo;
    NSString *action = @"2";//0,启用1，禁用2，解绑
    
    NSDictionary *dict = @{@"mobile":mobile,
                           @"cardTypeNo":cardTypeNo,
                           @"cardNo":cardNo,
                           @"action":action,
                           @"sysPlat":@"5"};
    
    [TieTieTool tietieWithParameterMarked:TTAction_unbindBaseCard dict:dict succes:^(id respondObject) {
        TTLog(@"unbindBaseCard: %@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode]isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"基卡解绑成功！"];
            [arrayData removeObjectAtIndex:[indexPath row]];
            [self.tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
            if (arrayData.count > 0) {
                [self backgroundViewWithNoData:NO];
            } else {
                [self backgroundViewWithNoData:YES];
            }
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }

    } fail:^(NSError *error) {
        TTLog(@"%@",error);
    }];
}

- (void)enableBaseCardRequest:(UISwitch*)bindAbleSwitch
{
    NSInteger row = bindAbleSwitch.tag;
    //{"mobile":"13426364664","cardNo":"13426364664","cardTypeNo":"13426364664","action":"0","sysPlat":"5"}
    TTBaseCardInfo *info = [arrayData objectAtIndex:row];
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *cardTypeNo = info.cardTypeNo;
    NSString *cardNo = info.cardNo;
    NSString *action = @"0";//0,启用1，禁用2，解绑
    
    NSDictionary *dict = @{@"mobile":mobile,
                           @"cardTypeNo":cardTypeNo,
                           @"cardNo":cardNo,
                           @"action":action,
                           @"sysPlat":@"5"};

    [TieTieTool tietieWithParameterMarked:TTAction_unbindBaseCard dict:dict succes:^(id respondObject) {
        TTLog(@"enableBaseCard: %@",[respondObject objectForKey:rspDesc]);
        if (![[respondObject objectForKey:rspCode]isEqualToString:rspCode_success]) {
            bindAbleSwitch.on = NO;
            [SystemDialog alert:@"基卡启用！"];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        TTLog(@"%@",error);
    }];
}

- (void)disableBaseCardRequest:(UISwitch*)bindAbleSwitch
{
    NSInteger row = bindAbleSwitch.tag;
    //{"mobile":"13426364664","cardNo":"13426364664","cardTypeNo":"13426364664","action":"0","sysPlat":"5"}
    TTBaseCardInfo *info = [arrayData objectAtIndex:row];
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSString *cardTypeNo = info.cardTypeNo;
    NSString *cardNo = info.cardNo;
    NSString *action = @"1";//0,启用1，禁用2，解绑
    
    NSDictionary *dict = @{@"mobile":mobile,
                           @"cardTypeNo":cardTypeNo,
                           @"cardNo":cardNo,
                           @"action":action,
                           @"sysPlat":@"5"};
    
    [TieTieTool tietieWithParameterMarked:TTAction_unbindBaseCard dict:dict succes:^(id respondObject) {
        TTLog(@"disableBaseCard: %@",[respondObject objectForKey:rspDesc]);
        if (![[respondObject objectForKey:rspCode]isEqualToString:rspCode_success]) {
            bindAbleSwitch.on = YES;
            [SystemDialog alert:@"基卡禁用！"];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        TTLog(@"%@",error);
    }];
}

- (void)switchValueChange:(id)sender
{
    UISwitch *bindAbleSwitch = (UISwitch*)sender;
    if (bindAbleSwitch.on) {
        [self enableBaseCardRequest:bindAbleSwitch];
    } else {
        [self disableBaseCardRequest:bindAbleSwitch];
    }
}
@end
