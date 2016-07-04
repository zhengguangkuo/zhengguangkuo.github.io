//
//  BasicCard.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Base.h"

@interface BasicCard : Base

@property (nonatomic, copy)  NSString* bind_flag;
@property (nonatomic, copy)  NSString* input_tip;
@property (nonatomic, copy)  NSString* card_no_len;
@property (nonatomic, copy)  NSString* card_type;
@property (nonatomic, copy)  NSString* pic_path;
@property (nonatomic, copy)  NSString* card_no;
@property (nonatomic, copy)  NSString* card_bin;
@property (nonatomic, copy)  NSString* card_name;

@end
