//
//  CityModel.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import "TTFriend.h"


@implementation TTFriend

@synthesize userID;
@synthesize userName;
@synthesize headPic;
@synthesize nickName;
@synthesize mobile;
@synthesize email;


- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super init]) {
        self.userID = dict[@"USERID"];
        self.userName = dict[@"USERNAME"];
        self.headPic = dict[@"HEADPIC"];
        self.nickName = dict[@"NICKNAME"];
        self.mobile = dict[@"MOBILE"];
        self.email = dict[@"EMAIL"];
    }
    return self;
}
@end




@implementation TTPickFriend
@synthesize bCheckFlag;

- (id)initWithDict:(NSDictionary *)dict
{
    if (self = [super initWithDict:dict]) {
        self.bCheckFlag = FALSE;
    }
    return self;
}
@end






