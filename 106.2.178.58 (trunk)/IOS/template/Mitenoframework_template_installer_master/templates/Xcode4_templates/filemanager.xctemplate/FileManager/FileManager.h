//
//  FileManager.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface FileManager : NSObject {
    
}
//file operation
+(BOOL) isFileExist: (NSString *)fileName;
+(NSString *)dataFilePath: (NSString *)fileName;
+(BOOL) delFile: (NSString *)fileName;
+(NSArray *) getAllFileNames: (NSString *)dirName;
+(void) writeFile: (NSString *)fileName  content:(NSString*)fileContent; 
+(NSString*)readFile:(NSString*)fileName;
+(NSString *)createFilePath: (NSString*)fileName;
@end
