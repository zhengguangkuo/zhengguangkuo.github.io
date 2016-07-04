//
//  Account.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import "TTConpus.h"

@implementation TTConpus

@synthesize act_id;
@synthesize act_name;
@synthesize pic_path;
@synthesize merch_id;
@synthesize merch_name;
@synthesize start_date;
@synthesize end_date;
@synthesize issued_cnt;


- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super init]) {
        self.act_id = dict[@"ACT_ID"];
        self.act_name = dict[@"ACT_NAME"];
        self.pic_path = dict[@"PIC_PATH"];
        self.merch_id = dict[@"MERCH_ID"];
        self.merch_name = dict[@"MERCH_NAME"];
        self.start_date = dict[@"START_DATE"];
        self.end_date = dict[@"END_DATE"];
        self.issued_cnt = [dict[@"ISSUED_CNT"]  intValue];
    }
    return self;
}





@end
