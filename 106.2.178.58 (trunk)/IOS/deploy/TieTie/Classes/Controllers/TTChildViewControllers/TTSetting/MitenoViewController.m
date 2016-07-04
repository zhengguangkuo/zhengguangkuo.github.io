//
//  MitenoViewController.m
//  ExpansionTableCell
//
//  Created by zhengguangkuo on 14-5-30.
//  Copyright (c) 2014年 com.miteno. All rights reserved.
//

#import "MitenoViewController.h"
#import "MitenoTableViewCell.h"

@interface MitenoViewController ()
{
    NSDictionary *dataDic;
    NSMutableArray *flagArray;
}

@end

@implementation MitenoViewController

- (void)awakeFromNib
{

}

- (id)init
{
    self = [super init];
    if (self) {
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    NSString *plistPath = [[NSBundle mainBundle] pathForResource:@"QA" ofType:@"plist"];
    dataDic = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath];
    flagArray = [[NSMutableArray alloc] init];
    for (int i = 0,count = [dataDic count];i < count;i++) {
        NSNumber *flag = [NSNumber numberWithBool:NO];
        [flagArray addObject:flag];
    }
    
    self.tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    self.tableView.backgroundColor = TTGlobalBg;
    self.title = @"帮助中心";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
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


#pragma mark - Table View DataSource Delegate Methods.
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [dataDic count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    BOOL flag = [[flagArray objectAtIndex:[indexPath row]] boolValue];
    if (flag) {
        NSString *str = [self getContentFromPlist:indexPath];
        CGSize size = CGSizeZero;
        size = [self getStringRect:str];
        CGFloat addHeight = size.height;
        return 44 + addHeight;
    } else {
        return 44;
    }
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    MitenoTableViewCell *customCell = (MitenoTableViewCell*)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (customCell == nil) {
        customCell = [[[NSBundle mainBundle]loadNibNamed:@"MitenoTableViewCell" owner:self options:nil]lastObject];
        UIView *divider = [[UIView alloc] init];
        divider.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"line"]];
        divider.frame = CGRectMake(0,customCell.size.height-1, customCell.frame.size.width,0.5);
        [customCell addSubview:divider];
    }
    
    NSString *title = [self getTitleFromPlist:indexPath];
    NSString *content = [self getContentFromPlist:indexPath];
    
    customCell.tiltleLable.text = title;
    
    BOOL flag = [[flagArray objectAtIndex:[indexPath row]] boolValue];
    [customCell addDetailLable:content andFlag:flag];
    return customCell;
}


#pragma mark - Table View Delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger row = [indexPath row];
    BOOL flag = [[flagArray objectAtIndex:row] boolValue];
    [flagArray setObject:[NSNumber numberWithBool:!flag] atIndexedSubscript:row];
    [tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
}

- (CGSize)getStringRect:(NSString*)aString
{
    CGSize size = CGSizeZero;
    if (aString && (aString.length > 0)) {
        NSDictionary* dic = @{NSFontAttributeName: [UIFont systemFontOfSize:17]};
        size = [aString boundingRectWithSize:CGSizeMake(320, 0)  options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:dic context:nil].size;
    }
    return  size;
}

- (NSString*)getTitleFromPlist:(NSIndexPath *)indexPath
{
    NSDictionary *dic = [dataDic objectForKey:[NSString stringWithFormat:@"Q%d",[indexPath row]+1]];
    NSString *str = [dic objectForKey:@"title"];
    str = [str stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"];
    return str;
}

- (NSString *)getContentFromPlist:(NSIndexPath *)indexPath
{
    NSDictionary *dic = [dataDic objectForKey:[NSString stringWithFormat:@"Q%d",[indexPath row]+1]];
    NSString *str = [dic objectForKey:@"content"];
    str = [str stringByReplacingOccurrencesOfString:@"\\n" withString:@"\n"];
    return str;
}

@end
