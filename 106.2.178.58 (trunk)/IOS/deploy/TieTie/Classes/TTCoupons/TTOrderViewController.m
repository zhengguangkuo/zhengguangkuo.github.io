//
//  TTOrderViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-8-1.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTOrderViewController.h"
#import "TTOrederCouponTableViewCell.h"
#import "TTCouponDetailViewController.h"
#import "TTCoupon.h"
#import "TTMyGiftsViewController.h"

@interface TTOrderViewController ()
{
    NSMutableArray *arrayData;
//    OrderInfo *orderInfo;
}

@end

@implementation TTOrderViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [self.navigationItem setTitle:@"订单详情"];
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    [self queryCouponOrderView];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - UItableviewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [arrayData count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 88.0;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *identifier = @"cell";
    TTOrederCouponTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"TTOrederCouponTableViewCell" owner:nil options:nil]lastObject];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    }
    OrderInfo *info = [arrayData firstObject];
    [cell.coupondImage setImageWithURL:[NSURL URLWithString:info.picPath] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
    cell.coupondName.text = info.act_name;
    cell.coupondContent.text = @"";
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    OrderInfo *info = [arrayData firstObject];
    TTCoupon *coupon = [[TTCoupon alloc]init];
    coupon.actId = info.act_id;
    coupon.picPath = info.picPath;
    TTCouponDetailViewController * CDVC = [[TTCouponDetailViewController alloc]init];
    CDVC.object = coupon;
    [self.navigationController pushViewController:CDVC animated:YES];

    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

- (void)loadCouponData
{
    OrderInfo *info = [arrayData firstObject];
    NSDictionary * dict = @{@"actId":info.act_id,@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:@"findCouponAct" dict:dict succes:^(id responseObject) {
        [self showLoading:NO];
        TTLog(@"优惠劵详情");
        NSDictionary * CouponDic = responseObject;
        TTCoupon *coupon = [[TTCoupon alloc] initWithDict:CouponDic];
        
        TTCouponDetailViewController * CDVC = [[TTCouponDetailViewController alloc]init];
        CDVC.coupon = coupon;
        [self.navigationController pushViewController:CDVC animated:YES];
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@--优惠劵详情error",error);
    }];
    
}


- (void)updateViewContent
{
    OrderInfo *info = [arrayData firstObject];
    
    if ([info.from_mobile isEqualToString:[TTAccountTool sharedTTAccountTool].currentAccount.userPhone]) {
        self.senderName.text = info.to_mobile;
    } else {
        self.senderName.text = info.from_mobile;
    }
    self.status.text = [self statusText:info.status];
    self.couponsNum.text = [NSString stringWithFormat:@"%@", info.num];
    self.orderNo.text = info.order_no;
    self.addTime.text = [NSString stringWithFormat:@"%@",info.start_date];
    if (info.close_date && info.close_date.length > 0) {
        self.recivedTime.text = [NSString stringWithFormat:@"%@",info.close_date];
    } else {
        self.recivedTime.text = @"--";
    }
    
}

//0待处理，1已接收，2已拒绝，3已过期
- (NSString *)statusText:(NSString*)status
{
    if ([status isEqualToString:@"0"]) {
        return @"待处理";
    } else if ([status isEqualToString:@"1"]) {
        return @"已接收";
    } else if ([status isEqualToString:@"2"]) {
        return @"已拒绝";
    } else {
        return @"已过期";
    }
}


- (void)queryCouponOrderView
{
    [self showLoading:YES];

    NSString *orderNo = self.orderNoStr;
    NSDictionary *paramsDic = @{@"Order_no":orderNo,@"sysPlat":@"5"};
    [TieTieTool tietieWithParameterMarked:TTAction_queryCouponOrderView dict:paramsDic succes:^(id responseObj) {
        TTLog(@"queryCouponOrderView:%@",[responseObj objectForKey:rspDesc]);
        if ([[responseObj objectForKey:rspCode] isEqualToString:rspCode_success]) {
            if (!arrayData) {
                arrayData = [[NSMutableArray alloc] init];
            } else {
                [arrayData removeAllObjects];
            }
            OrderInfo *info = [[OrderInfo alloc] initWithDictionary:responseObj];
            [arrayData addObject:info];
        }
        [self.table reloadData];
        [self setButtonState:[arrayData firstObject]];
        [self updateViewContent];
        [self showLoading:NO];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (void)setButtonState:(OrderInfo*)info
{
    NSString *phone = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    if ([info.status integerValue]==0 && [info.to_mobile isEqualToString:phone]) {
        self.recieveBtn.hidden = NO;
        self.refuseBtn.hidden = NO;
    } else {
        self.recieveBtn.hidden = YES;
        self.refuseBtn.hidden = YES;
    }
    
}

- (IBAction)recieveBtnClicked:(id)sender {
    [self handleOrder:YES];
}

- (IBAction)refuseBtnClicked:(id)sender {
    [self handleOrder:NO];
}

- (void)handleOrder:(BOOL)receive
{
    OrderInfo *info = [arrayData firstObject];
    if ([info.status integerValue] != 0) {
        [SystemDialog alert:@"您的订单已经处理过了"];
        return;
    }
    //{"orderId":" Uid776656","handleState":"00","sysPlat":"5"}
    NSString *orderId = info.order_no;
    NSString *handleState = receive ? @"00":@"01";//00代表用户已接受01代表用户已拒绝
    NSDictionary *paramDic = @{@"orderId":orderId,@"handleState":handleState,@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_handleOrder dict:paramDic succes:^(id responseObj) {
        [self showLoading:NO];
        TTLog(@"handleOrder:%@",[responseObj objectForKey:rspDesc]);
        if ([[responseObj objectForKey:rspCode]isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"处理成功"];
            for (UIViewController *vc in self.navigationController.viewControllers) {
                if ([vc isKindOfClass:[TTMyGiftsViewController class]]) {
                    TTMyGiftsViewController *giftVC = (TTMyGiftsViewController*)vc;
                    NSString *status = receive ? @"1" : @"2";
                    [giftVC updateTableAfterHandleOrder:orderId status:status];
                    
                    self.recieveBtn.hidden = YES;
                    self.refuseBtn.hidden  = YES;
                    break;
                }
            }
        } else {
            [SystemDialog alert:[responseObj objectForKey:rspDesc]];
        }
        
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];

}
@end
