//
//  ArchiveTool.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
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
