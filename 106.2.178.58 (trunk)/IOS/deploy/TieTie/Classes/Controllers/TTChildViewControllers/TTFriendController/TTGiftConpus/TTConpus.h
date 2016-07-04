//
//  Account.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BaseModel.h"

@interface TTConpus : BaseModel

@property  (nonatomic, copy)   NSString* act_id;
@property  (nonatomic, copy)   NSString* act_name;
@property  (nonatomic, copy)   NSString* pic_path;
@property  (nonatomic, copy)   NSString* merch_id;
@property  (nonatomic, copy)   NSString* merch_name;
@property  (nonatomic, copy)   NSString* start_date;
@property  (nonatomic, copy)   NSString* end_date;
@property  (nonatomic, assign) int issued_cnt;

@end
