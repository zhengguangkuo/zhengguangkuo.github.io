//
//  Area.h
//  Miteno
//
//  Created by wg on 14-8-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "Base.h"

@interface Area : Base <NSCoding>
@property (nonatomic, copy)NSString * superArea;
@property (nonatomic, copy)NSString * areaName;
@property (nonatomic, copy)NSString * areaCode;
@property (nonatomic, copy)NSString * areaLevel;
@end
