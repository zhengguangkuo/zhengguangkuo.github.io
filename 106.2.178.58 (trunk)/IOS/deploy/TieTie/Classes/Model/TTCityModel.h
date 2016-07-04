//
//  TTCityModel
//  Miteno
//
//  Created by wg on 14-7-14.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <Foundation/Foundation.h>
#import "Base.h"
#define TTsuperArea   @"superArea"
#define TTareaName    @"areaName"
#define TTareaLevel   @"areaLevel"
#define TTareaCode    @"areaCode"
@interface TTCityModel : Base <NSCoding>
@property (nonatomic, copy) NSString   *superArea;  //所属父级地区
@property (nonatomic, copy) NSString   *areaName;   //地区名称
@property (nonatomic, copy) NSString   *areaLevel;  //地区级别
@property (nonatomic, copy) NSString   *areaCode;   //地区编码

@end
