//
//  FileManager.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
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
+(id)ReadUnArchive:(NSString*)path;
+(void)SaveArchive:(id<NSCoding>)object path:(NSString*)path;

@end
