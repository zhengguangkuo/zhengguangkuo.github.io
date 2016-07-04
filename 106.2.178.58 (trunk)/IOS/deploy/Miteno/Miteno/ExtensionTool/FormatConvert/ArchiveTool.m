//
//  ArchiveTool.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import "ArchiveTool.h"
#import "FileManager.h"

@implementation ArchiveTool

+(void)ArchiveObject:(id<NSCoding>)object path:(NSString*)key
{
    if(![FileManager isFileExist:key]){
       [FileManager createFilePath:key];
    }
    [NSKeyedArchiver archiveRootObject:object toFile:[FileManager dataFilePath:key]];
}


+(id)unArchiveObject:(NSString*)key
{
    if([FileManager isFileExist:key]){
    return [NSKeyedUnarchiver unarchiveObjectWithData:[NSData dataWithContentsOfFile:[FileManager dataFilePath:key]]];
    }
    return nil;
}

@end
