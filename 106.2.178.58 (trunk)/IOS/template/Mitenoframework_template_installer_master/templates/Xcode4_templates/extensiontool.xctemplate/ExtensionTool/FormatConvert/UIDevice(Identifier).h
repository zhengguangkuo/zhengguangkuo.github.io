//
//  UIDevice(Identifier).h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
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