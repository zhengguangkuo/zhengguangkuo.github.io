//
//  BusinessDetailsCustemTableView.m
//  Miteno
//
//  Created by APPLE on 14-6-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "BusinessDetailsCustemTableView.h"
#import "BusinessDetailsCustemTableViewCell.h"
@implementation BusinessDetailsCustemTableView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.dataSource = self;
        self.separatorInset = UIEdgeInsetsMake(0, 52, 0, 5);
        self.scrollEnabled = NO;
    }
    return self;
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
}
*/

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return _arr.count;
}
-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    BusinessDetailsCustemTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[[NSBundle mainBundle]loadNibNamed:@"BusinessDetailsCustemTableViewCell" owner:self options:nil]lastObject];
        //cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
//    cell.textLabel.text = [_arr objectAtIndex:indexPath.row];
//    [cell.textLabel setFont:[UIFont systemFontOfSize:14]];
//    [cell setAccessoryType:UITableViewCellAccessoryDisclosureIndicator];
    cell.ActIDLabel.text = [_arr objectAtIndex:indexPath.row][@"ActId"];
    cell.ActNameLabel.text = [_arr objectAtIndex:indexPath.row][@"ActName"];
    return cell;
    
}

-(id)initWithDictionary:(NSDictionary *)dict andSwitchNum:(int)num
{
    switch (num) {
        case 1:
            _arr = [NSArray arrayWithArray:dict[@"CouponActList"]];
            break;
        case 2:
            _arr = [NSArray arrayWithArray:dict[@"DiscountActList"]];
            break;
        case 3:
            _arr = [NSArray arrayWithArray:dict[@"CredisActList"]];
            break;
        default:
            break;
    }
    CGRect frame = CGRectMake(0, 34, 320, 34*_arr.count);
//    if (_arr.count>2) {
//        frame = CGRectMake(0, 34,320, 68);
//    }
    return [self initWithFrame:frame];
}
@end
