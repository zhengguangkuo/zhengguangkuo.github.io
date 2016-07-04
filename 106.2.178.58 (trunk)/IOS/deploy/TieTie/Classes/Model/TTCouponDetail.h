//
//  TTCouponDetail.h
//  Miteno
//
//  Created by wg on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <Foundation/Foundation.h>
#import "Base.h"
@interface TTCouponDetail : Base
@property (nonatomic, copy)NSString    *   titleLabel;
@property (nonatomic, copy)NSString    *   address;
@property (nonatomic, copy)NSString   *   phone;
@property (nonatomic, copy)NSString   *   distance;
@property (nonatomic, copy)NSString   *   nearDistance;
@property (nonatomic, copy)NSString *  vertical;//纵向

@property (nonatomic, copy)NSString   *  branch;  //分店

//-----
@property (nonatomic, copy)NSString    *   Rule;                //使用规则
@property (nonatomic, copy)NSString    *   actDetail;           //活动详情
@property (nonatomic, copy)NSString    *   actId;               //优惠券活动ID
@property (nonatomic, copy)NSString    *   actName;             //优惠券活动名称
@property (nonatomic, copy)NSString    *   picPath;             //活动图片url
@property (nonatomic, copy)NSString    *   sendType;            //派发类型
@property (nonatomic, copy) NSString   *   issuedCnt;           //已经发行数量
@property (nonatomic, copy) NSString   *    IconPic;            //机构logo

+ (TTCouponDetail *)couponDetail;
@end
