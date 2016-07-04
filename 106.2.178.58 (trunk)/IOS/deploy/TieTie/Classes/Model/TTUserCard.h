//
//  TTUserCard.h
//  Miteno
//
//  Created by wg on 14-8-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Base.h"

@interface TTUserCard : Base
@property (nonatomic, copy) NSString   *    mail;
@property (nonatomic, copy) NSString   *    name;
@property (nonatomic, copy) NSString   *    nickName;
@property (nonatomic, copy) NSString   *    phone;
@property (nonatomic, copy) NSString   *    tel;
@property (nonatomic, copy) NSString   *    title;
@property (nonatomic, copy) NSString   *    company;
@property (nonatomic, strong) NSData     *    image;
@end
