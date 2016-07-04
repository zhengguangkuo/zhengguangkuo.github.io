//
//  TTAccount.h
//  Miteno
//
//  Created by wg on 14-6-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>
@interface TTAccount : NSObject<NSCoding>
@property (nonatomic, copy) NSString    * userPhone;
@property (nonatomic, copy) NSString    * passWord;
@property (nonatomic, copy) NSString    * payPwdFlag;   //是否有支付密码
@property (nonatomic, copy) NSString    * mipauFlag;   //是否登录密保
@property (nonatomic, copy) NSString    * nowUserId;   //当前ID

@property (nonatomic, copy) NSString    * tieCoupon;
@property (nonatomic, copy) NSString    * tieFriend;
@property (nonatomic, copy) NSString    * tieScore;
@property (nonatomic, copy) NSString    * tieOther;

//+ (TTAccount *)account;
@end
