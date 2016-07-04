//
//  TTBaseCardSettingTableViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBaseCardSettingTableViewController.h"
#import "TTBaseCardTableViewCell.h"
#import "TTBaseCardInfo.h"
#import "TTBaseCardInfoViewController.h"
#import "UIColor+Extension.h"
#import "NSObject+Property.h"

@interface TTBaseCardSettingTableViewController ()
{
    NSMutableArray *arrayCards;
//    NSIndexPath *bindCardIndexPath;
}

@end

@implementation TTBaseCardSettingTableViewController

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
    
    self.navigationItem.title = @"可添加基卡类型";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    [self loadCardsFromServerAsync];
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

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [arrayCards count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 89;
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    TTBaseCardTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!customCell) {
        customCell = [[[NSBundle mainBundle] loadNibNamed:@"TTBaseCardTableViewCell" owner:self options:nil] lastObject];
        customCell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    
    TTBaseCardInfo *info = [arrayCards objectAtIndex:[indexPath row]];
    [customCell.cardFaceImageView setImageWithURL:[NSURL URLWithString:info.cardPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    customCell.cardName.text = info.cardName;
    customCell.cardType.text = info.cardDetail;
    customCell.cardCode.text = info.cardNo;
    
    customCell.bindAbleSwitch.hidden = YES;
    
//    BOOL isUnBind = [info.bindAble integerValue] == 1;
    [customCell setbindCardIconFlag:YES];
    
//    if (!isUnBind) {
//        bindCardIndexPath = indexPath;
//    }
    
    UIView *divider = [[UIView alloc] init];
    divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
    divider.frame = CGRectMake(0,customCell.size.height-1, customCell.frame.size.width,0.5);
    [customCell addSubview:divider];
    return customCell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTBaseCardInfoViewController *cardVC = [[TTBaseCardInfoViewController alloc]initWithNibName:@"TTBaseCardInfoViewController" bundle:nil];
    cardVC.baseCard = [arrayCards objectAtIndex:[indexPath row]];
    [self.navigationController pushViewController:cardVC animated:YES];
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}


#pragma mark - custom methods
- (void)loadCardsFromServerAsync
{
//    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *dict = @{/*@"Mobile":mobile,*/
                           @"sysPlat":@"5"};
    
    [TieTieTool tietieWithParameterMarked:TTAction_queryBaseCardType dict:dict succes:^(id respondObject) {
        TTLog(@"queryBaseCardType: %@",[respondObject objectForKey:rspDesc]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            NSArray *arr = [respondObject objectForKey:@"cardList"];
            NSMutableArray *cardInfos = [[NSMutableArray alloc]init];
            for (NSDictionary *dict in arr) {
                TTBaseCardInfo *baseCardInfo = [[TTBaseCardInfo alloc]initWithDictionary:dict];
                [cardInfos addObject:baseCardInfo];
            }
            arrayCards = cardInfos;
            [self.tableView reloadData];
        } else {
            [SystemDialog alert:[respondObject objectForKey:rspDesc]];
        }

    } fail:^(NSError *error) {
        TTLog(@"%@",error);
    }];
}

@end
