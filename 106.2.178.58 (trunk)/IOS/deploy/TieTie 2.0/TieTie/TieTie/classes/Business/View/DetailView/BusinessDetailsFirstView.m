//
//  BusinessDetailsFirstView.m
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-10.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "BusinessDetailsFirstView.h"
#import "BusinessDetailsFirstViewTableViewCell.h"
//#import "MapsViewController.h"
@implementation BusinessDetailsFirstView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self setBackgroundColor:[UIColor colorWithRed:237/255.0 green:237/255.0 blue:237/255.0 alpha:1]];

        // Initialization code
        [self addUI];
    }
    return self;
}
-(void)addUI
{
    _businessIV = [[UIImageView alloc]initWithFrame:CGRectMake(0,0,310,130)];
    [_businessIV setBackgroundColor:[UIColor whiteColor]];
    _businessIV.layer.cornerRadius = 5;
    _businessIV.layer.masksToBounds = YES;
    [self addSubview:_businessIV];
    [_businessIV setImageWithURL:[NSURL URLWithString:_businessImageURL] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
//    UIImageView * businessLogo = [[UIImageView alloc]init];
//    [businessLogo setImageWithURL:[NSURL URLWithString:_businessImageURL] placeholderImage:[UIImage imageNamed:@"coupon_normal"]];
//     _businessIV.image = [UIImage scaleImage:businessLogo.image size:CGSizeMake(_businessIV.size.width, _businessIV.size.height)];

    
    _businessNameLB = [[UILabel alloc]initWithFrame:CGRectMake(0, 130, 300, 40)];
    _businessNameLB.text = _businessName;
//    [_businessNameLB setLineBreakMode:UILineBreakModeWordWrap];
    [_businessNameLB setFont:[UIFont systemFontOfSize:16]];
    [self addSubview:_businessNameLB];
    
//    _keyWordsLB = [[UILabel alloc]initWithFrame:CGRectMake(140, 50, 180, 25)];
//    _keyWordsLB.text = [NSString stringWithFormat:@"关键字:%@",_keyWords];
//    [_keyWordsLB setFont:[UIFont systemFontOfSize:14]];
//    [self addSubview:_keyWordsLB];
//   
//    _collectBTN = [[UIButton alloc]initWithFrame:CGRectMake(140, 80, 150, 30)];
//    [_collectBTN setBackgroundImage:[UIImage imageNamed:@"u57_normal.png"] forState:UIControlStateNormal];
//    [_collectBTN setBackgroundImage:[UIImage imageNamed:@"u55_normal.png"] forState:UIControlStateHighlighted];
//    [_collectBTN setTitle:@"收藏店铺" forState:UIControlStateNormal];
//    [self addSubview:_collectBTN];
    
    _FirstViewTV = [[UITableView alloc]initWithFrame:CGRectMake(0,170,310,80)/*(0, 80, 310, 80)*/];
    _FirstViewTV.delegate = self;
    _FirstViewTV.dataSource = self;
    _FirstViewTV.scrollEnabled = NO;
    _FirstViewTV.separatorInset = UIEdgeInsetsMake(0, 0, 0, 0);
    [self addSubview:_FirstViewTV];
    
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 40;
}
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return 2;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    BusinessDetailsFirstViewTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"BusinessDetailsFirstViewTableViewCell" owner:self options:nil]lastObject];
        //cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    if (indexPath.row==0) {
        //cell.textLabel.text = [NSString stringWithFormat:@"地址:%@",[_arr objectAtIndex:indexPath.row]];
        cell.textLabel.text = [NSString stringWithFormat:@"地址:%@",_businessAddress];
    }else{
        cell.textLabel.text = [NSString stringWithFormat:@"电话:%@",_businessTelphone];
    }
    [cell.textLabel setFont:[UIFont systemFontOfSize:12]];
    [cell.textLabel setTextColor:[UIColor colorWithRed:0/255.0 green:176/255.0 blue:250/255.0 alpha:1]];
    cell.backgroundColor = [UIColor colorWithRed:249/255.0 green:249/255.0 blue:249/255.0 alpha:1];
    return cell;
}
-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (indexPath.row==1) {
        [self call:indexPath.row];
    }else{
        TTLog(@"商家位置");
        
//        MapsViewController *mapViewController = [[MapsViewController alloc]init];
//        CLLocationCoordinate2D coordinate;
//        coordinate.longitude = [_mapDic[@"longitude"] floatValue];
//        coordinate.latitude = [_mapDic[@"latitude"] floatValue];
//        [mapViewController addAnnotationViewWithCLLocationCoordinate2D:coordinate andMerchName:_mapDic[@"merchName"]];
//        [_VC.navigationController pushViewController:mapViewController animated:YES];

    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}
- (void)call:(int)num
{
    UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:_businessTelphone otherButtonTitles:nil];
    sheet.tag = 1001;
    [sheet showInView:self.superview.superview.window];
}
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex==0) {
        NSString *num = [[NSString alloc] initWithFormat:@"tel://%@",_businessTelphone];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:num]];
    }
}
//-(id)initWithBusinessImage:(NSString*)businessImage andBusinessName:(NSString*)businessName andKeyWords:(NSString*)keyWords andAdressAndPhoneArr:(NSMutableArray*)arr andFrame:(CGRect)rect
//{
//    _businessImage = businessImage;
//    _businessName = businessName;
//   // _keyWords = keyWords;
//    _arr = [NSMutableArray arrayWithArray:arr];
//    return [self initWithFrame:rect];
//}
-(id)initWithFrame:(CGRect)frame andDic:(NSDictionary*)dic andImageURL:(NSString*)ImageURL andBusinessDetailsViewController:(UIViewController*)VC
{
    _businessName = dic[@"merchName"];
    _businessAddress = dic[@"Address"];
    _businessTelphone = dic[@"Telphone"];
    
    _businessImageURL = dic[@"Image"];
    
    _mapDic = dic;
    
    _VC = VC;
    return [self initWithFrame:frame];
}
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

@end
