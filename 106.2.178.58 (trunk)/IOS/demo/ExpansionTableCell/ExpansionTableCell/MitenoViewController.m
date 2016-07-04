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
    NSIndexPath *selectedIndexPath;
    
    NSIndexPath *lastSelectedIndexPath;
    
    BOOL expansionFlag;
    
    NSDictionary *dataDic;
}

@end

@implementation MitenoViewController

- (void)awakeFromNib
{
    NSString *plistPath = [[NSBundle mainBundle] pathForResource:@"QA" ofType:@"plist"];
    dataDic = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
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
    if (selectedIndexPath && [selectedIndexPath compare:indexPath] == NSOrderedSame) {
    BOOL isTheSameCell = lastSelectedIndexPath && ([lastSelectedIndexPath compare:selectedIndexPath] == NSOrderedSame);
        if (isTheSameCell && expansionFlag) {
            return 44;
        }
        NSString *str = [self getContentFromPlist:indexPath];
        return 44+[self getStringRect:str].height;
    }
    return 44;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kCustomCellId = @"CustomeCell";
    static BOOL registNib = NO;
    
    if (!registNib) {
        UINib *nib = [UINib nibWithNibName:@"MitenoTableViewCell" bundle:nil];
        [tableView registerNib:nib forCellReuseIdentifier:kCustomCellId];
        registNib = YES;
    }
    MitenoTableViewCell *customCell = (MitenoTableViewCell*)[tableView dequeueReusableCellWithIdentifier:kCustomCellId];

    
    NSString *title = [self getTitleFromPlist:indexPath];
    NSString *content = [self getContentFromPlist:indexPath];
    
    customCell.tiltleLable.text = title;
    
    BOOL isTheSameCell = lastSelectedIndexPath && selectedIndexPath && ([lastSelectedIndexPath compare:selectedIndexPath] == NSOrderedSame);
    if (isTheSameCell) {
        expansionFlag = !expansionFlag;
        [customCell changeArrowIcon:expansionFlag];
        
        [customCell addDetailLable:content andFlag:expansionFlag];
    } else {
        [customCell changeArrowIcon:([indexPath compare:selectedIndexPath] == NSOrderedSame)];
        [customCell addDetailLable:content andFlag:([indexPath compare:selectedIndexPath] == NSOrderedSame)];
        expansionFlag = YES;
    }
    return customCell;
}


#pragma mark - Table View Delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSArray *array;
    if (selectedIndexPath && !([selectedIndexPath compare:indexPath]==NSOrderedSame)) {
        
        array = [NSArray arrayWithObjects:selectedIndexPath,indexPath, nil];

    } else {
        array = [NSArray arrayWithObject:indexPath];
    }

    lastSelectedIndexPath = selectedIndexPath;
    selectedIndexPath = indexPath;
    
    [tableView reloadRowsAtIndexPaths:array withRowAnimation:UITableViewRowAnimationFade];
}

- (CGSize)getStringRect:(NSString*)aString
{
    CGSize size;
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
