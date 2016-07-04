//
//  TTAccount.m
//  Miteno
//
//  Created by wg on   14-6-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAccount.h"

@implementation TTAccount 
- (id)initWithCoder:(NSCoder *)decoder
{
    if (self = [super init]) {
        self.userPhone = [decoder decodeObjectForKey:TTUserPhone];
        self.passWord = [decoder decodeObjectForKey:TTPassWord];
        self.payPwdFlag = [decoder decodeObjectForKey:TTPayPwdFlag];
        self.mipauFlag = [decoder decodeObjectForKey:TTMipauFlag];
        self.nowUserId = [decoder decodeObjectForKey:TTUserID];
    }
    return self;
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
        [encoder encodeObject:self.userPhone forKey:TTUserPhone];
        [encoder encodeObject:self.passWord forKey:TTPassWord];
        [encoder encodeObject:self.payPwdFlag forKey:TTPayPwdFlag];
        [encoder encodeObject:self.mipauFlag forKey:TTMipauFlag];
        [encoder encodeObject:self.nowUserId forKey:TTUserID];
}

//+(TTAccount *)account
//{
//    TTAccount *acc = [[TTAccount alloc] init];
//    acc.tieCoupon = @"80";
//    acc.tieFriend = @"24";
//    acc.tieScore = @"99";
//    
//    return acc;
//}
@end
