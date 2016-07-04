//
//  UIDevice(Identifier).m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "UIDevice(Identifier).h"
#import "NSString(Additions).h"

@implementation UIDevice(Identifier)


//获取字符串mac地址
+ (NSString *) macaddress{
    
    int                 mib[6];
    size_t              len;
    char                *buf;
    unsigned char       *ptr;
    struct if_msghdr    *ifm;
    struct sockaddr_dl  *sdl;
    
    mib[0] = CTL_NET;
    mib[1] = AF_ROUTE;
    mib[2] = 0;
    mib[3] = AF_LINK;
    mib[4] = NET_RT_IFLIST;
    
    if ((mib[5] = if_nametoindex("en0")) == 0) {
        printf("Error: if_nametoindex error\n");
        return NULL;
    }
    
    if (sysctl(mib, 6, NULL, &len, NULL, 0) < 0) {
        printf("Error: sysctl, take 1\n");
        return NULL;
    }
    
    if ((buf = malloc(len)) == NULL) {
        printf("Could not allocate memory. error!\n");
        return NULL;
    }
    
    if (sysctl(mib, 6, buf, &len, NULL, 0) < 0) {
        printf("Error: sysctl, take 2");
        free(buf);
        return NULL;
    }
    
    ifm = (struct if_msghdr *)buf;
    sdl = (struct sockaddr_dl *)(ifm + 1);
    ptr = (unsigned char *)LLADDR(sdl);
    NSString *outstring = [NSString stringWithFormat:@"%02X:%02X:%02X:%02X:%02X:%02X",
                           *ptr, *(ptr+1), *(ptr+2), *(ptr+3), *(ptr+4), *(ptr+5)];
    free(buf);
    
    return outstring;
}

//获取UUID
+ (NSString *) uuidString {
      CFUUIDRef puuid = CFUUIDCreate(nil);
      CFStringRef uuidString = CFUUIDCreateString( nil, puuid);
      NSString * result = (NSString *)CFBridgingRelease(CFStringCreateCopy(NULL, uuidString));
      CFRelease(puuid);
      CFRelease(uuidString);
    return result;
}


//获取追踪app的唯一标示,mac地址相关
+(NSString *) uniqueDeviceIdentifier
{
    NSString *macaddress = [UIDevice  macaddress];
    NSString *bundleIdentifier = [[NSBundle mainBundle] bundleIdentifier];
    
    NSString *stringToHash = [NSString stringWithFormat:@"%@%@",macaddress,bundleIdentifier];
    NSString *uniqueIdentifier = [stringToHash md5];
    
    return uniqueIdentifier;
}

//获取追踪设备的唯一标示,mac地址相关
+(NSString *) uniqueGlobalDeviceIdentifier
{
    NSString *macaddress = [UIDevice macaddress];
    NSString *uniqueIdentifier = [macaddress md5];
    
    return uniqueIdentifier;
}

@end

