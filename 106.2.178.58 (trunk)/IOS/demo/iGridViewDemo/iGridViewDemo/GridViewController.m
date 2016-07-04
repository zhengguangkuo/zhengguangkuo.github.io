//
//  GridViewController.m
//  iGridViewDemo
//
//  Created by HWG on 14-2-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "GridViewController.h"
#import "Shop.h"
#import "GridCell.h"

@interface GridViewController ()

@property (nonatomic, strong)NSMutableArray *shops;
@end

@implementation GridViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        self.title = @"九宫格Demo";
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.shops = [NSMutableArray array];
    
    for (int i = 1; i<=33; i++) {
        
        Shop *shop = [[Shop alloc] init];
        shop.name = [NSString stringWithFormat:@"大衣-%i", i];

        shop.icon = [NSString stringWithFormat:@"TM.bundle/tmall_icon_cat_outing_%i.png", i%12+1];
        
        [self.shops addObject:shop];
    }

    // 不需要分隔线
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
}
#pragma mark - Table view data source
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return (self.shops.count + kColumn - 1)/kColumn;
    
}

#pragma mark 每当有新的Cell进入视野范围内时，就会调用这个方法
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *identifier = @"GridCell";
    GridCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell == nil) {
        cell = [[GridCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
    }

    // 从哪个位置开始截取
    NSInteger location = indexPath.row * kColumn;
    // 截取的长度
    NSInteger length = kColumn;
    
    if (location + length >= self.shops.count) {
        length = self.shops.count - location;
    }
    
    NSRange range = NSMakeRange(location, length);
    NSArray *rowShops = [self.shops subarrayWithRange:range];
    [cell setRowShops:rowShops];


    //点击的item
    cell.blcok = ^(ImageButton *item){
        NSLog(@"点击了----%@",item.titleLabel.text);
    };
    
    
    return cell;
}

#pragma mark 设置Cell的高度
- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return kCellHeight;
}

@end
