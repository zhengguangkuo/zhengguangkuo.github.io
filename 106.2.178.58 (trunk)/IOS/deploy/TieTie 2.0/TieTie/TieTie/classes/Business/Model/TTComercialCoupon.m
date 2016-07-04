//
//  TTComercialCoupon.m
//  Miteno
//
//  Created by APPLE on 14-9-28.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTComercialCoupon.h"

@implementation TTComercialCoupon
-(id)initWithDic:(NSDictionary *)dic
{
    if (self==[super initWithDict:dic]) {
        _image      = dic[@"Image"];
        _merchName  = dic[@"merchName"];
        _merchTag   = dic[@"merchTag"];
        _Address    = dic[@"Address"];
        _Distance   = dic[@"Distance"];
        _longitude  = dic[@"longitude"];
        _latitude   = dic[@"latitude"];
        _merchId    = dic[@"merchId"];
        _integralState = dic[@"integralState"];
        _discountState = dic[@"discountState"];
        _ticketState   = dic[@"ticketState"];
    }
    
    return self;
}
@end
