//
//  BusinessDetailsViewController.m
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-10.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "BusinessDetailsViewController.h"
#import "BusinessDetailsFirstView.h"
#import "BusinessDetailsCustemView.h"
#import "BusinessDetailsLastView.h"
#import "BusinessDetailsCustemTableView.h"
//#import "TTCouponDetailViewController.h"
#import "TieTieTool.h"
#import "TTCoupon.h"


@interface BusinessDetailsViewController ()<UIWebViewDelegate>
{
    NSDictionary * _dic;

    BusinessDetailsCustemView * _CustemView1;
    BusinessDetailsCustemView * _CustemView2;
    BusinessDetailsCustemView * _CustemView3;
    
    UIScrollView * _sv;
}
@end

@implementation BusinessDetailsViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}
-(void)viewWillAppear:(BOOL)animated
{
    [self loadBusinessDetailsFromServerAsync];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
//    UILabel *TitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 50, 44)];
//    TitleLabel.font = [UIFont boldSystemFontOfSize:18];
//    TitleLabel.text = @"商家详情";
//    TitleLabel.textColor = [UIColor whiteColor];
//    TitleLabel.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
//    self.navigationItem.titleView = TitleLabel;
    self.title = @"商家详情";

}

int n;
-(void)addSubViews
{
    n=0;
    _sv = [[UIScrollView alloc]initWithFrame:CGRectMake(0, 0, 320, ScreenHeight)];
    _sv.backgroundColor = [UIColor colorWithRed:237/255.0 green:237/255.0 blue:237/255.0 alpha:1];
    [self.view addSubview:_sv];
    
    BusinessDetailsFirstView * firstView = [[BusinessDetailsFirstView alloc]initWithFrame:CGRectMake(5, 5, 310, 250) andDic:_dic andImageURL:_dic[@"Image"] andBusinessDetailsViewController:self];
    [_sv addSubview:firstView];
    
    NSArray * textArr1 = [NSArray arrayWithArray:_dic[@"CouponActList"]];
    if(textArr1.count)
    {
    BusinessDetailsCustemTableView * CustemViewTV = [[BusinessDetailsCustemTableView alloc]initWithDictionary:_dic andSwitchNum:1];
    _CustemView1 = [[BusinessDetailsCustemView alloc]initCustemViewWithImage:@"icon_quan.png" andLabelText:@"优惠券" andViewFrame:CGRectMake(0,firstView.frame.size.height+12, 320, 34+CustemViewTV.frame.size.height)];
    [_sv addSubview:_CustemView1];
    CustemViewTV.tag = 100;
    CustemViewTV.delegate = self;
    [_CustemView1 addSubview:CustemViewTV];
        
        n++;
    }
    
    NSArray * textArr2 = [NSArray arrayWithArray:_dic[@"DiscountActList"]];
    if(textArr2.count)
    {
    BusinessDetailsCustemTableView * CustemViewTV2 = [[BusinessDetailsCustemTableView alloc]initWithDictionary:_dic andSwitchNum:2];
    _CustemView2 = [[BusinessDetailsCustemView alloc]initCustemViewWithImage:@"icon_zhe.png" andLabelText:@"折扣活动" andViewFrame:CGRectMake(0,firstView.frame.size.height+12+_CustemView1.frame.size.height+7*n, 320, 34+CustemViewTV2.frame.size.height)];
    [_sv addSubview:_CustemView2];
    CustemViewTV2.tag = 200;
    CustemViewTV2.delegate = self;
    [_CustemView2 addSubview:CustemViewTV2];
    
        n++;
    }
    
    NSArray * textArr3 = [NSArray arrayWithArray:_dic[@"CredisActList"]];
    if (textArr3.count) {
        BusinessDetailsCustemTableView * CustemViewTV3 = [[BusinessDetailsCustemTableView alloc]initWithDictionary:_dic andSwitchNum:3];
        _CustemView3 = [[BusinessDetailsCustemView alloc]initCustemViewWithImage:@"icon_fen.png" andLabelText:@"积分优惠" andViewFrame:CGRectMake(0,firstView.frame.size.height+12+_CustemView1.frame.size.height+_CustemView2.frame.size.height+7*n, 320, 34+CustemViewTV3.frame.size.height)];
        [_sv addSubview:_CustemView3];
        CustemViewTV3.tag = 300;
        CustemViewTV3.delegate = self;
        [_CustemView3 addSubview:CustemViewTV3];
        
        n++;
    }
    
        BusinessDetailsLastView * lastView = [[BusinessDetailsLastView alloc]initWithFrame:CGRectMake(0, firstView.frame.size.height+12+_CustemView1.frame.size.height+_CustemView2.frame.size.height+_CustemView3.frame.size.height+7*n, 320, 244)];
    _y = lastView.frame.origin.y;
    [_sv addSubview:lastView];
    
    
    NSString * HTMLString = _dic[@"Description"];
    UIWebView * lastWebView = [[UIWebView alloc]initWithFrame:CGRectMake(0, 44, 320, 200)];
    lastWebView.backgroundColor = [UIColor whiteColor];
    [lastWebView loadHTMLString:HTMLString baseURL:nil];
    lastWebView.delegate = self;
    [lastView addSubview:lastWebView];
    
}

-(void)webViewDidFinishLoad:(UIWebView *)webView
{
    CGSize actualSize = [webView sizeThatFits:CGSizeZero];
    CGRect newFrame = webView.frame;
    newFrame.size.height = actualSize.height;
    webView.frame = newFrame;
    
    [_sv setContentSize:CGSizeMake(320,_y+webView.frame.size.height+70+44)];
}


//加载商家信息
- (void)loadBusinessDetailsFromServerAsync
{
    NSDictionary * dict = @{@"MerchId":_MerchId,@"sysPlat":@"5"};//@{@"MerchId":@"111111111111113",@"sysPlat":@"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_findMerch dict:dict succes:^(id responseObject) {
        [self showLoading:NO];
        _dic = responseObject;
        TTLog(@"%@***************商家详情",_dic);
            [self addSubViews];
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@--**********************error商家详情",error);
    }];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTLog(@"%d",tableView.tag);
    
    NSString * str;
    switch (tableView.tag) {
        case 100:
            str = @"CouponActList";
            break;
        case 200:
            str = @"DiscountActList";
            break;
        default:
            str = @"CredisActList";
            break;
    }
    
//    [self loadCouponData:indexPath andKey:str];
    
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 34;
}
//- (void)loadCouponData:(NSIndexPath*)indexPath andKey:(NSString*)str
//{
//    NSMutableArray * CouponsArr = [NSMutableArray array];
//    for (NSDictionary * Coupon in _dic[str]) {
//        TTLog(@"%@ = 商家",_dic);
//        [CouponsArr addObject:Coupon];
//    }
//    NSDictionary * dict = @{@"actId":[CouponsArr objectAtIndex:indexPath.row][@"ActId"],@"sysPlat":@"5"};
//    
//    [self showLoading:YES];
//    [TieTieTool tietieWithParameterMarked:@"findCouponAct" dict:dict succes:^(id responseObject) {
//        [self showLoading:NO];
//        TTLog(@"%@--优惠券详情",responseObject);
//        NSDictionary * CouponDic = responseObject;
//        TTCoupon *coupon = [[TTCoupon alloc] initWithDict:CouponDic];
//        id obj = coupon;
//        BOOL hasVC = NO;
//        
//        for (UIViewController *VC in self.navigationController.viewControllers) {
//            if ([VC isKindOfClass:[TTCouponDetailViewController class]]) {
//                hasVC = YES;
//                TTCouponDetailViewController * CDVC = (TTCouponDetailViewController*)VC;
//                CDVC.object = obj;
//                [self.navigationController popToViewController:CDVC animated:NO];
//            }
//        }
//        
//        if (!hasVC) {
//            TTCouponDetailViewController * CDVC = [[TTCouponDetailViewController alloc]init];
//            CDVC.object = obj;
//            [self.navigationController pushViewController:CDVC animated:NO];
//        }
//
//    } fail:^(NSError *error) {
//        [self showLoading:NO];
//        TTLog(@"%@--优惠券详情error",error);
//    }];
//
//}

@end
