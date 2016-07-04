//
//  ArchiveTool.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
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
