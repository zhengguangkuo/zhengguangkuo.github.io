//
//  UserInfo.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import "UserInfo.h"
#import "ArchiveTool.h"

@interface UserInfo()

@property (nonatomic, assign) BOOL isLoginEitherNot;

@end


@implementation UserInfo

@synthesize  returnVal;
@synthesize  userId;
@synthesize  sessionKey;
@synthesize  userMobile;
@synthesize  userName;
@synthesize  isLoginEitherNot;

singleton_for_implementation(UserInfo)

#define  CheckLogin(string)  ((string.length > 0)?YES:NO)

- (id)init{
    self = [super init];
    if (self) {
        self.returnVal = @"";
        self.userId = @"";
        self.sessionKey = @"";
        self.userMobile = @"";
		self.userName = @"";
        self.isLoginEitherNot = NO;
    }    
    return self;
}

- (void)resetAccout
{
    self.returnVal = @"";
    self.userId = @"";
    self.sessionKey = @"";
    self.userMobile = @"";
    self.userName = @"";
    self.isLoginEitherNot = NO;
}

- (void)loginAccout:(NSDictionary*)dic
{
    [self objectFromDictionary:dic];
    [self setLoginEitherNot];
}

- (void)setLoginEitherNot
{
    self.isLoginEitherNot = CheckLogin(self.userName);
}

- (BOOL)isLogIn
{
    return self.isLoginEitherNot;
}

- (void)readAcount:(NSString*)filename
{
    UserInfo* tempobject = (UserInfo*)[ArchiveTool unArchiveObject:filename];
    [self objectFromObject:tempobject];
    [self setLoginEitherNot];
}

- (void)saveAccout:(NSString*)filename
{
    [ArchiveTool ArchiveObject:self path:filename];
}

@end
