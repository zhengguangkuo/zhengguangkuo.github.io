//
//  TTAccountTool.m
//  Miteno
//
//  Created by wg on 14-6-19.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAccountTool.h"
#import "TTAccount.h"
#import "TTSettingKeys.h"
#import "TTCityModel.h"
#define TTFilePath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTFileName]
#define TTCurrentFilePath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTCurrentFileName]

#define TTCurrentCityFilePath   [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTCurrentCityName]
@interface TTAccountTool()
{
    NSMutableArray  *   _accounts;
    NSMutableArray  *   _citys;
}
@end
@implementation TTAccountTool
singleton_for_implementation(TTAccountTool)
- (id)init
{
    if (self = [super init]) {
        
        _accounts = [NSKeyedUnarchiver unarchiveObjectWithFile:TTFilePath];
        _currentAccount = [NSKeyedUnarchiver unarchiveObjectWithFile:TTCurrentFilePath];
        
        _currentCity = [NSKeyedUnarchiver unarchiveObjectWithFile:TTCurrentCityFilePath];
        
        if (_accounts == nil) {
            _accounts = [NSMutableArray array];
        }else{
            _isLogin = YES;
        }
        
        if (_citys == nil) {
            _citys = [NSMutableArray array];
        }
      }
    

    return self;
}

- (void)addAccount:(TTAccount *)account
{

    [_accounts addObject:account];
    _currentAccount = account;
    
    [NSKeyedArchiver archiveRootObject:_accounts toFile:TTFilePath];
    [NSKeyedArchiver archiveRootObject:_currentAccount toFile:TTCurrentFilePath];
}
- (void)addCity:(TTCityModel *)city
{
    if (_citys) {
        [_citys removeAllObjects];
    }
    [_citys addObject:city];
    _currentCity = city;
    [NSKeyedArchiver archiveRootObject:_currentCity toFile:TTCurrentCityFilePath];
}

@end
