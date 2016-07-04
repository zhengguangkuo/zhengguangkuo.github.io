//
//  UIDevice(Identifier).h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>
#include <sys/socket.h> // Per msqr
#include <sys/sysctl.h>
#include <net/if.h>
#include <net/if_dl.h>

@interface UIDevice(Identifier)

+(NSString *) macaddress;

+(NSString *) uuidString;

+(NSString *) uniqueDeviceIdentifier;

+(NSString *) uniqueGlobalDeviceIdentifier;

@end