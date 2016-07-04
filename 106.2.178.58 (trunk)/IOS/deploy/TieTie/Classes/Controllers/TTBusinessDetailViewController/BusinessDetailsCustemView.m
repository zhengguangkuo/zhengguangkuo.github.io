//
//  BusinessDetailsCustemView.m
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-11.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "BusinessDetailsCustemView.h"
#import "TTCouponDetailViewController.h"
#import "BusinessDetailsViewController.h"
@implementation BusinessDetailsCustemView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.backgroundColor = [UIColor whiteColor];
        UIImageView * lineIV = [[UIImageView alloc]initWithFrame:CGRectMake(52, 33, 263, 2)];
        [lineIV setImage:[UIImage imageNamed:@"u32_line.png"]];
        [self addSubview:lineIV];
       
        [self addUI];
    }
    return self;
}
-(void)addUI
{
    _btn = [[UIButton alloc]initWithFrame:CGRectMake(22.5, 7, 18, 20)];
    [_btn setBackgroundImage:[UIImage imageNamed:_imageName] forState:UIControlStateNormal];
    [_btn setTitleColor:[UIColor lightTextColor] forState:UIControlStateNormal];
    _btn.enabled = NO;
    [self addSubview:_btn];
    
    _label = [[UILabel alloc]initWithFrame:CGRectMake(52.5, 2, 150, 30)];
    _label.text = _labelStr;
    [_label setFont:[UIFont systemFontOfSize:14]];
    [self addSubview:_label];
    
//    _CustemViewTV = [[UITableView alloc]initWithFrame:CGRectMake(0, 50, 300, 44*_arr.count)];
//    if (_arr.count>2) {
//        _CustemViewTV = [[UITableView alloc]initWithFrame:CGRectMake(0, 50,300, 88)];
//    }
//    _CustemViewTV.delegate = self;
//    _CustemViewTV.dataSource = self;
//    [self addSubview:_CustemViewTV];
}
//-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
//{
//    return _arr.count;
//}
//-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
//{
//    static NSString *CellIdentifier = @"Cell";
//    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
//    if (cell == nil) {
//        //cell = [[[NSBundle mainBundle]loadNibNamed:@"BusinessDetailsFirstViewTableViewCell" owner:self options:nil]lastObject];
//        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
//    }
//    cell.textLabel.text = [_arr objectAtIndex:indexPath.row];
//    return cell;
//
//}
//-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
//{
//
//}
-(id)initCustemViewWithImage:(NSString*)imageName andLabelText:(NSString*)labelStr andViewFrame:(CGRect)frame
{
   // _arr = [NSMutableArray arrayWithArray:arr];
    _imageName = imageName;
    _labelStr = labelStr;
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
