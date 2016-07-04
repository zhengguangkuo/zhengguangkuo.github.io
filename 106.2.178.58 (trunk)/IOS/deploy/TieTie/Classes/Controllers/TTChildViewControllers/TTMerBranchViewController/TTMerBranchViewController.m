//
//  TTMerBranchViewController.m
//  Miteno
//
//  Created by wg on 14-6-15.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMerBranchViewController.h"
#import "TTCouponDetailCell.h"
#import "TTCouponViewFrame.h"
#import "TTCouponDetail.h"
#import "NavItemView.h"
#import "TTMerBranchCell.h"
#import "TTCouponMerDetail.h"
#import "BusinessDetailsViewController.h"
@interface TTMerBranchViewController ()<UIActionSheetDelegate>
{
    NSMutableArray      *   _allBranch;
}
@end

@implementation TTMerBranchViewController

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
//移动space
//    self.tableView.contentInset = UIEdgeInsetsMake(0,-kActDetailSpace, 0, 0);
    self.title = @"商家列表";
    self.view.backgroundColor = [UIColor whiteColor];
//    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self setNavTheme];
    
    _allBranch = [NSMutableArray array];
//    TTCouponMerDetail *couponDetail = self.couponFrame.couponDetail;
//    couponDetail.nearDistance = nil;
//    int num = [couponDetail.branch intValue];
//    for (int i = 1; i<=num; i ++) {
//        TTCouponViewFrame *branchFram = [[TTCouponViewFrame alloc] init];
//        branchFram.couponDetail = couponDetail;
//        
//        [_allBranch addObject:branchFram];
//    }
    
}
- (void)setNavTheme
{    
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backPrevious) direction:ItemDirectionLeft];

    self.navigationItem.rightBarButtonItem =  [UIBarButtonItem barButtonItemWithIcon:@"top_map_" target:self action:@selector(goToMapVC) direction:ItemDirectionRight];
}
- (void)backPrevious
{
    [self.navigationController popViewControllerAnimated:YES];
}
- (void)goToMapVC
{
    TTLog(@"Map");
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{

    return self.merdatas.count;
}
#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTMerBranchCell *branchCell = [TTMerBranchCell merBranchCellWithTableView:tableView];
    branchCell.indexPath = indexPath;
    TTLog(@"branchCell == %@",NSStringFromCGRect(branchCell.frame));
    TTCouponViewFrame *cellframe = [[TTCouponViewFrame alloc] init];
    cellframe.couponDetail = _merdatas[indexPath.row];
    [branchCell setCouponFrame:cellframe];
    [(UIButton *)branchCell.distance addTarget:self action:@selector(getMerDistance) forControlEvents:UIControlEventTouchUpInside];
    [branchCell.phone addTarget:self action:@selector(call) forControlEvents:UIControlEventTouchUpInside];
    return branchCell;
}
- (void)getMerDistance
{
    TTLog(@"-----距离");
}
- (void)call
{
    UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:@"010-2327343" otherButtonTitles:nil];
    sheet.tag = 10001;
    [sheet showInView:self.view.window];
}
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        if (actionSheet.tag==10001) {
            TTLog(@"%d",buttonIndex);
            NSString *tel = [NSString stringWithFormat:@"tel://%@",@"2327343"];
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:tel]];
        }
    }
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTCouponMerDetail *mer = _merdatas[indexPath.row];
    BusinessDetailsViewController *merDetail = [[BusinessDetailsViewController alloc] init];
    merDetail.merchId = mer.merchId;
    [self.navigationController pushViewController:merDetail animated:YES];
}
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTCouponViewFrame *frame = [[TTCouponViewFrame alloc] init];
    frame.couponDetail = _merdatas[indexPath.row];
    return frame.cellHeight;
}
- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0,tableView.frame.size.width,2*kActDetailSpace)];
    headView.backgroundColor = [UIColor clearColor];
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(kActDetailSpace,0,headView.frame.size.width,headView.frame.size.height)];
    label.font = [UIFont systemFontOfSize:13];
    label.text = [NSString stringWithFormat:@"%d店通用",self.merdatas.count];
    [headView addSubview:label];
    return headView;
}
- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 30;
}
//- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section
//{
//    return ;
//}
@end
