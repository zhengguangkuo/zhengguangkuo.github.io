//
//  FileManager.h
//  Zaavio
//
//  Created by apple on 11-8-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
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
+(NSString*)GetFullPathofFile:(NSString*)file_name;
+(BOOL)FileExistCheck:(NSString*)file_name;
@end
