//
//  ArchiveTool.h
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ArchiveTool : NSObject

+(void)ArchiveObject:(id<NSCoding>)object path:(NSString*)key;

+(id)unArchiveObject:(NSString*)key;

@end
