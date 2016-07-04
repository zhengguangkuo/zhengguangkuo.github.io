//
//  ArchiveTool.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ArchiveTool : NSObject

+(void)ArchiveObject:(id<NSCoding>)object path:(NSString*)key;

+(id)unArchiveObject:(NSString*)key;

@end
