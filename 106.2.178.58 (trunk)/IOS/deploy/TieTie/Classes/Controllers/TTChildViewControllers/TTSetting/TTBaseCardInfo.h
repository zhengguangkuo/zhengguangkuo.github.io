//
//  TTBaseCardInfo.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TTBaseCardInfo : NSObject
@property (nonatomic,copy)NSString *cardNo;//卡号
@property (nonatomic,copy)NSString *cardLength;//卡号长度
@property (nonatomic,copy)NSString *cardHoldState;//卡持有状态 1为有，0为无
@property (nonatomic,copy)NSString *cardDetail;//基卡描述
@property (nonatomic,copy)NSString *cardPath;//图片路径
@property (nonatomic,copy)NSString *cardName;//基卡名称
@property (nonatomic,copy)NSString *cardTypeNo;//基卡类型编号
@property (nonatomic,copy)NSString *cardBin;//卡 bin码
@property (nonatomic,copy)NSString *total;//总条数
@property (nonatomic,copy)NSString *bindAble;
@property (nonatomic)BOOL isBaseCard;
@end
