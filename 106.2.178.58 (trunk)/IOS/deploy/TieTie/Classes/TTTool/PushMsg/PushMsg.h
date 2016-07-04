//
//  BasicCard.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Base.h"
   
@interface PushMsg : Base
    
@property  (nonatomic, copy)  NSString* msg_id;

@property  (nonatomic, copy)  NSString* user_id;

@property  (nonatomic, copy)  NSString* msg_title;

@property  (nonatomic, copy)  NSString* msg_content;

@property  (nonatomic, copy)  NSString* send_time;

@property  (assign,  nonatomic) BOOL hasRead;

@end
