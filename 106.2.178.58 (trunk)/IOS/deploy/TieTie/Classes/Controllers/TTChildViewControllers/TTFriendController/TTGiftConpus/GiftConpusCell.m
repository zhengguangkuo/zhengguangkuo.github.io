//
//  ImageTableViewCell.m
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import "GiftConpusCell.h"
#import "UIImageView+DispatchLoad.h"
#import "UIView(category).h"
#import "TTConpus.h"

@implementation GiftConpusCell

@synthesize LeftImageView;
@synthesize ConpusName;
@synthesize ActiveName;
@synthesize ApplyCount;
@synthesize ExpireData;
@synthesize mTTConpus;



- (id)initCustom
{
    self = [super init];
    if(self)
    {
        self = [[[NSBundle mainBundle] loadNibNamed:@"GiftConpusCell" owner:self options:nil] lastObject];
        self.mTTConpus = [[TTConpus alloc] init];
        [self setBackgroundColor:[UIColor clearColor]];
        [self.contentView setBackgroundColor:[UIColor clearColor]];
    }
    return self;
}

-(void)SetGiftObject:(TTConpus*)object
{
    [self CleanData];
    self.mTTConpus = object;
    [self.ConpusName setText:self.mTTConpus.merch_name];
    [self.ActiveName setText:self.mTTConpus.act_name];
    int nCnt = self.mTTConpus.issued_cnt;
    
    NSString* cntStr = [NSString stringWithFormat:@"领取数量: %d张",nCnt];
    [self.ApplyCount setText:cntStr];
    
    NSString* dataStr = self.mTTConpus.end_date;
    
    NSString* endDate = [NSString stringWithFormat:@"有效期至: %@",dataStr];
    [self.ExpireData setText:endDate];
    
    NSString* url_path = self.mTTConpus.pic_path;
    
    [self.LeftImageView setImageFromUrl:url_path];
    
}


-(void)CleanData
{
    [self.LeftImageView setImage:[UIImage imageNamed:@"load.png"]];
    [self.ConpusName setText:@"商品名"];
    [self.ActiveName setText:@"活动名"];
    [self.ApplyCount setText:@"领取数量:"];
    [self.ExpireData setText:@"有效期至:"];
}



@end
