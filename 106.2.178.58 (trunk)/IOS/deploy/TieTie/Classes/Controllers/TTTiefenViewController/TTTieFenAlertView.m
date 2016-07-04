//
//  TTTieFenAlertView.m
//  TTTieFenViewController
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import "TTTieFenAlertView.h"
#import "TTTieFenAVTableViewCell.h"
@implementation TTTieFenAlertView
{
    NSArray * _arr;
}


- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _timeArr = [NSMutableArray arrayWithObjects:@"2010-05-01",@"2011-06-01",@"2012-09-21",@"2013-03-08",@"2014-05-21", nil];
        _chalengeListArr = [NSMutableArray arrayWithObjects:@"蛋糕",@"馅饼",@"鸡蛋",@"生煎",@"油条", nil];
        _numArr = [NSMutableArray arrayWithObjects:@"+3",@"-1",@"+8",@"+6",@"-10", nil];
        // Initialization code
        [self setBackgroundColor:[UIColor grayColor]];
        [self addAnyUI];
    }
    return self;
}
-(void)addAnyUI
{
    UILabel * FirstLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, self.frame.size.width, 60)];
    [FirstLabel setBackgroundColor:[UIColor blackColor]];
    [FirstLabel setTextColor:[UIColor whiteColor]];
    [FirstLabel setFont:[UIFont fontWithName:@"Helvetica" size:20]];
    FirstLabel.text = @"贴币消费记录";
    [self addSubview:FirstLabel];
    
    UILabel * Label1 = [[UILabel alloc]initWithFrame:CGRectMake(0, 60, 100, 40)];
    Label1.text = @"  交易时间";
    [self addSubview:Label1];
    
    UILabel * Label2 = [[UILabel alloc]initWithFrame:CGRectMake(100, 60, 100, 40)];
    Label2.text = @"  交易内容";
    [self addSubview:Label2];

    UILabel * Label3 = [[UILabel alloc]initWithFrame:CGRectMake(200, 60, 100, 40)];
    Label3.text = @"  交易数量";
    [self addSubview:Label3];
    
    UITableView * listTV = [[UITableView alloc]initWithFrame:CGRectMake(0, 100, 300, 200)];
    [self addSubview:listTV];
    listTV.delegate = self;
    listTV.dataSource = self;
    
    UIButton * exitBTN = [[UIButton alloc]initWithFrame:CGRectMake(270, 15, 30, 30)];
    [exitBTN setTitle:@"X" forState:UIControlStateNormal];
    [exitBTN addTarget:self action:@selector(releaseUI) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:exitBTN];
}
-(void)releaseUI
{
    [self removeFromSuperview];
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _numArr.count;
//  return _arr.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    TTTieFenAVTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"TTTieFenAVTableViewCell" owner:self options:nil]lastObject];
    }

//    NSDictionary * dic = [_arr objectAtIndex:indexPath.row];
//    cell.timeLB.text = dic[@"tranTime"];
//    cell.nameLB.text = dic[@"tranType"];
//    cell.numLB.text  = dic[@"tranAmount"];

    cell.timeLB.text = [_timeArr objectAtIndex:indexPath.row];
    cell.nameLB.text = [_chalengeListArr objectAtIndex:indexPath.row];
    cell.numLB.text = [_numArr objectAtIndex:indexPath.row];
    return cell;

}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

-(id)initWithFrame:(CGRect)frame andFenDetailListArr:(NSMutableArray *)arr
{
    _arr = [NSArray arrayWithArray:arr];
    return [self initWithFrame:frame];
}
@end
