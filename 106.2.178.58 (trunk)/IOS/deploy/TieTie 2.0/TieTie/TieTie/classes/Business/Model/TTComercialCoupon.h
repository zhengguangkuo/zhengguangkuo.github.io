//
//  TTComercialCoupon.h
//  Miteno
//
//  Created by APPLE on 14-9-28.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTBaseModel.h"

@interface TTComercialCoupon : TTBaseModel
@property(nonatomic,retain)NSString * image;
@property(nonatomic,retain)NSString * merchName;
@property(nonatomic,retain)NSString * merchTag;
@property(nonatomic,retain)NSString * Address;
@property(nonatomic,retain)NSString * Distance;

@property(nonatomic,retain)NSString * longitude;
@property(nonatomic,retain)NSString * latitude;
@property(nonatomic,retain)NSString * merchId;

@property(nonatomic,retain)NSString * integralState;
@property(nonatomic,retain)NSString * discountState;
@property(nonatomic,retain)NSString * ticketState;

-(id)initWithDic:(NSDictionary*)dic;
@end
