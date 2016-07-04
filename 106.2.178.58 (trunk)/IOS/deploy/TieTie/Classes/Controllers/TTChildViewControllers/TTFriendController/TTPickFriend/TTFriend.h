//
//  CityModel.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Base.h"

@interface TTFriend : Base

@property (nonatomic, copy)   NSString  *  userID;
@property (nonatomic, copy)   NSString  *  userName;
@property (nonatomic, copy)   NSString  *  headPic;
@property (nonatomic, copy)   NSString  *  nickName;
@property (nonatomic, copy)   NSString  *  mobile;
@property (nonatomic, copy)   NSString  *  email;

@property (nonatomic, copy)   UIImage   *  HeaderImage;
@end


@interface TTPickFriend : TTFriend
@property BOOL  bCheckFlag;

@end





