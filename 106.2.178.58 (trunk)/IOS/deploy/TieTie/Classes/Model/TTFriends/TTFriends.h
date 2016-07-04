//
//  TTFriends.h
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <Foundation/Foundation.h>
#import "Base.h"
@interface TTFriends : Base
@property (nonatomic, assign)   int       userID;
@property (nonatomic, copy)   NSString  *  userName;
@property (nonatomic, copy)   NSString  *  headPic;

@property (nonatomic, copy)   NSString  *  ID;
@property (nonatomic, copy)   NSString  *  nickName;
@property (nonatomic, copy)   NSString  *  tel;
@property (nonatomic, copy)   NSString  *  email;

@property (nonatomic, copy)   UIImage   *  HeaderImage;
@end
