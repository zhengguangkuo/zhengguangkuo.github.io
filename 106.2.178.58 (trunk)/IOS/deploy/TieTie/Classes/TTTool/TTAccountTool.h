//
//  TTAccountTool.h
//  Miteno
//
//  Created by wg on 14-6-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "Singleton.h"
@class TTAccount,TTCityModel;
@interface TTAccountTool : NSObject
singleton_for_interface(TTAccountTool)

@property (nonatomic, strong) TTAccount     *   currentAccount;
@property (nonatomic, strong) TTCityModel     *   currentCity;
@property (nonatomic, assign) BOOL isLogin;
- (void)addAccount:(TTAccount *)account;
- (void)addCity:(TTCityModel *)city;
@end
