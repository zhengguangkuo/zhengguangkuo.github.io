//
//  FileManager.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
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
+(NSString *)readResource:(NSString*)name type:(NSString*)type;
@end
