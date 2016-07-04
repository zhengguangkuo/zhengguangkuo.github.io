////
////  OrderDetailsTableViewController.m
////  Miteno
////
////  Created by zhengguangkuo on 14-6-9.
////  Copyright (c) 2014年 wenguang. All rights reserved.
////
//
//#import "OrderDetailsTableViewController.h"
//#import "OrderInfoTableViewCell.h"
//#import "DetailValueTableViewCell.h"
//#import "OrderInfo.h"
//#import "OrderInfoWithoutPriceTableViewCell.h"
//#import "SDImageCache.h"
//#define kcouponsInfo            0
//#define kcouponsCode            1
//#define kcouponsmerchant        2
//#define kcouponsNotice          3
//#define kcouponsorderInfo       4
//
//@interface OrderDetailsTableViewController ()
//{
//    NSDictionary *orderInfoArray;
//    BOOL isHasPrice;
//}
//
//@end
//
//@implementation OrderDetailsTableViewController
//
//- (id)initWithStyle:(UITableViewStyle)style
//{
//    self = [super initWithStyle:style];
//    if (self) {
//        // Custom initialization
//    }
//    return self;
//}
//
//- (void)viewDidLoad
//{
//    [super viewDidLoad];
//    
//    // Uncomment the following line to preserve selection between presentations.
//    // self.clearsSelectionOnViewWillAppear = NO;
//    
//    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
//    self.navigationItem.title = @"订单详情";
//
//}
//
//- (void)didReceiveMemoryWarning
//{
//    [super didReceiveMemoryWarning];
//    // Dispose of any resources that can be recreated.
//}
//
//#pragma mark - Table view data source
//
//- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
//{
//#warning Potentially incomplete method implementation.
//    // Return the number of sections.
//    return 5;
//}
//
//- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
//{
//    NSInteger count = 0;
//    switch (section) {
//        case kcouponsInfo:
//        case kcouponsCode:
//        case kcouponsmerchant:
//            count = 1;
//            break;
//        case kcouponsNotice:
//        case kcouponsorderInfo:
//            count = 4;
//            break;
//        default:
//            break;
//    }
//    return count;
//}
//
//- (float)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
//{
//    float count = 0;
//    switch (section) {
//        case kcouponsInfo:
//        case kcouponsCode:
//            count = 1;
//            break;
//        case kcouponsmerchant:
//        case kcouponsNotice:
//        case kcouponsorderInfo:
//            count = 44;
//            break;
//        default:
//            break;
//    }
//    return count;
//}
//
////- (float)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
////{
////    return 20;
////}
//
//- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    float height = 0;
//    switch (indexPath.section) {
//        case kcouponsInfo:
//            height = isHasPrice ? 113 : 88;
//            break;
//        case kcouponsCode:
//            height = 60;
//            break;
//        case kcouponsmerchant:
//        {
//            CGSize size = [self getStringRect:@"对撒看撒了快减肥萨福；阿斯利康；记得放松啦；看见方式撒了；艰苦奋斗了；ask 减肥啦睡觉" andWidth:300 fontOfSize:17];
//            height = size.height;
//            break;
//        }
//        case kcouponsNotice:
//        case kcouponsorderInfo:
//        {
//            CGSize size = [self getStringRect:@"根据字符串的字体和控健宽度设置控健的高度" andWidth:218 fontOfSize:17];
//            height = size.height;
//        }
//            break;
//        default:
//            break;
//    }
//    return height;
//}
//
//
//- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    OrderInfo *info = [self laodTmpOrderInfoFromServer];
//    switch (indexPath.section) {
//        case kcouponsInfo:
//        {
//            if (isHasPrice) {
//                static NSString *kCustomCell = @"CustomCell";
//                OrderInfoTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kCustomCell];
//                if (!customCell) {
//                    customCell = [[[NSBundle mainBundle]loadNibNamed:@"OrderInfoTableViewCell" owner:self.tableView options:nil] lastObject];
//                }
//                
//                [customCell.cuponsImage setImageWithURL:[NSURL URLWithString:info.couponImageUrl] placeholderImage:[UIImage imageNamed:@"load.png"]];
//                customCell.cuponsName.text          = info.couponName;
//                customCell.cuponsCentent.text       = info.couponContent;
//                customCell.cuponsMoney.text         = info.couponNewMoney;
//                customCell.cuponsMoneyDelete.text   = info.couponOldMoney;
//                customCell.cuponsValidity.text      = info.couponUsedValidity;
//                [customCell.cuponsDateCount setTitle:@"还剩19天" forState:UIControlStateNormal];
//                [customCell.cuponsDateCount setBackgroundColor:[UIColor redColor]];
//                return customCell;
//            } else {
//                static NSString *kCustomCell = @"CustomCell";
//                OrderInfoWithoutPriceTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kCustomCell];
//                if (!customCell) {
//                    customCell = [[[NSBundle mainBundle]loadNibNamed:@"OrderInfoWithoutPriceTableViewCell" owner:self.tableView options:nil] lastObject];
//                }
//                
//                [customCell.couponImageUrl setImageWithURL:[NSURL URLWithString:info.couponImageUrl] placeholderImage:[UIImage imageNamed:@"load.png"]];
//                customCell.couponName.text = info.couponName;
//                customCell.couponContent.text = info.couponContent;
//                customCell.couponValidity.text = info.couponValidity;
//                return customCell;
//            }
//            
//        }
//        case kcouponsCode:
//        {
//            static NSString *cellIdentifier = @"CuponsCell";
//            UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
//            if (cell == nil) {
//                cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentifier];
//            }
//            
//            cell.textLabel.text = @"优惠码";
//            cell.detailTextLabel.text = info.couponCode;
//            UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(240, 20, 60, 30)];
//            [btn setTitle:(info.isUsed?@"已使用":@"未使用") forState:UIControlStateNormal];
//            [btn setTitleColor:[UIColor blueColor] forState:UIControlStateNormal];
//            cell.accessoryView = btn;
//            return cell;
//        }
//        case kcouponsmerchant:
//        {
//            static NSString *cellIdentifier = @"CuponsCell";
//            UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
//            if (cell == nil) {
//                cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:cellIdentifier];
//            }
//            
//            cell.textLabel.text = info.merchantContent;
//            cell.textLabel.numberOfLines = 0;
//            return cell;
//            
//        }
//        case kcouponsNotice:
//        case kcouponsorderInfo:
//        {
//            static NSString *kCustomCell = @"DetailValueCell";
//            DetailValueTableViewCell *customCell = [tableView dequeueReusableCellWithIdentifier:kCustomCell];
//            if (!customCell) {
//                customCell = [[[NSBundle mainBundle]loadNibNamed:@"DetailValueTableViewCell" owner:self.tableView options:nil] lastObject];
//            }
//            
//            NSDictionary *dicInfo = [self laodTmpDicData:info];
//            NSString *key = [[dicInfo allKeys] objectAtIndex:[indexPath row]];
//            customCell.title.text = key;
//            customCell.detail.text = [dicInfo objectForKey:key];
//            return customCell;
//            
//        }
//        default:
//            break;
//    }
//    return nil;
//}
//
//- (UIView*)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
//{
//    switch (section) {
//        case kcouponsInfo:
//            break;
//        case kcouponsCode:
//            break;
//        case kcouponsmerchant:
//            return [self addHeaderViewForCouponsMerchant];
//        case kcouponsNotice:
//            return [self addHeaderViewForCouponsNotice];
//        case kcouponsorderInfo:
//            return [self addHeaderViewForCouponsOrderInfo];
//        default:
//            break;
//    }
//    return nil;
//}
//
//- (UIView*)addHeaderViewForCouponsMerchant
//{
//    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
//    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 160, 44)];
//    label.text = @"金凤呈祥";
//    [view addSubview:label];
//    UIButton *btn = [[UIButton alloc]initWithFrame:CGRectMake(200, 0, 120, 44)];
//    [btn addTarget:self action:@selector(showMerchantDetailInfo) forControlEvents:UIControlEventTouchUpInside];
//    [btn setTitle:@"查看商家详情" forState:UIControlStateNormal];
//    [btn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
//    [view addSubview:btn];
//    [view setBackgroundColor:[UIColor whiteColor]];
//    return  view;
//}
//
//- (UIView*)addHeaderViewForCouponsNotice
//{
//    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
//    label.text = @"优惠券须知";
//    label.textAlignment = NSTextAlignmentLeft;
//    label.backgroundColor = [UIColor whiteColor];
//    return label;
//}
//
//- (UIView*)addHeaderViewForCouponsOrderInfo
//{
//    UIView *view = [[UIView alloc]initWithFrame:CGRectMake(0, 0, 320, 44)];
//    UIImageView *iconView = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 30, 44)];
//    iconView.image = [UIImage imageNamed:@"icon.png"];
//    iconView.contentMode = UIViewContentModeScaleToFill;
//    [view addSubview:iconView];
//    UILabel *label = [[UILabel alloc]initWithFrame:CGRectMake(40, 0, 160, 44)];
//    [view setBackgroundColor:[UIColor whiteColor]];
//    label.text = @"订单信息";
//    [view addSubview:label];
//    
//    return  view;
//}
//
//- (OrderInfo*)laodTmpOrderInfoFromServer
//{
//    OrderInfo *info = [[OrderInfo alloc]init];
//    info.couponImageUrl = @"http://www.touxiang.cn/uploads/20140606/06-020432_63.png";
//    info.couponName = @"金凤呈祥代金券";
//    info.couponContent = @"原价30元代金券现价28元，细品闻香赶紧买吧";
//    info.couponNewMoney = @"28";
//    info.couponOldMoney = @"30元";
//    info.couponUsedValidity = @"2014-05-31";
//    info.couponCode = @"080744910079";
//    info.isUsed = NO;
//    
//    info.merchantName = @"金凤呈祥";
//    info.merchantContent = @"金凤呈祥小西天面包店，原价30元代金券现价10元抢购，优惠券不设找零。";
//    
//    info.couponValidity = @"2014.4.30(周末，法定节假日通用)";
//    info.couponUseTime = @"14:00至21:00";
//    info.reservation = @"需要预约";
//    info.instructions = @"优惠券不能与店内其他优惠共享，优惠券不能叠加使用。";
//    
//    info.orderCode = @"112034618927346192";
//    info.orderCount = @"2份";
//    info.payTime = @"2014-03-06 11:00";
//    info.totalMoney = @"28.0元";
//    
//    return info;
//}
//
//- (NSDictionary*)laodTmpDicData:(OrderInfo *)info
//{
//    if (!orderInfoArray) {
//        orderInfoArray = @{
//                           @"有效期至": info.couponValidity,
//                           @"使用时间": info.couponUseTime,
//                           @"预约提醒": info.reservation,
//                           @"使用规则": info.instructions,
//                           @"订单编号": info.orderCode,
//                           @"购买份数": info.orderCount,
//                           @"下单时间": info.payTime,
//                           @"总金额": info.totalMoney
//                           };
//    }
//    
//    return orderInfoArray;
//}
//
//- (void)loadOrderInfoFromServer
//{
//    NSDictionary *dict = @{@"page":@"1"};
//    HttpService  *tempservice = [HttpService  HttpInitPostForm:@"url"
//                                                          body:dict
//                                                       withHud:YES];
//    [tempservice setDataHandler:^(NSString *data) {
//        NSDictionary *dicts = [data objectFromJSONString];
//        NSArray *arr = dicts[@"rows"];
//        NSMutableArray *newCoupons = [NSMutableArray array];
//        for (NSDictionary *dict in arr) {
//            
//        }
//        [self.tableView reloadData];
//        
//    }];
//    [tempservice startOperation];
//    [self getError:tempservice];
//}
//
//- (void)getError:(HttpService *)tempservice
//{
//    [tempservice setErrorHandler:^(NSError *error) {
//        dispatch_async(dispatch_get_main_queue(), ^{
//            [SystemDialog alert:kConnectFailure];
//        });
//    }];
//}
//
//- (CGSize)getStringRect:(NSString*)aString
//               andWidth:(CGFloat)width
//             fontOfSize:(CGFloat)fontSize
//{
//    CGSize size;
//    if (aString && (aString.length > 0)) {
//        NSDictionary* dic = @{NSFontAttributeName: [UIFont systemFontOfSize:fontSize]};
//        size = [aString boundingRectWithSize:CGSizeMake(width, 0)  options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:dic context:nil].size;
//    }
//    return  size;
//}
//
//- (void)showMerchantDetailInfo
//{
//    
//}
//
//@end
