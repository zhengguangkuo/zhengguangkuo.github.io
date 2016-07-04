//
//  ArchiveTool.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ArchiveTool : NSObject

+(void)ArchiveObject:(id<NSCoding>)object path:(NSString*)key;

+(id)unArchiveObject:(NSString*)key;

@end
