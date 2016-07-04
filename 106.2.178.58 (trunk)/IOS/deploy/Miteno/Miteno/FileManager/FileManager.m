//
//  FileManager.m
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//


#import "FileManager.h"


@implementation FileManager


+(BOOL)isFileExist: (NSString *)fileName
{
    //return false;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *path = [paths objectAtIndex:0];
    NSString *filepath = [path stringByAppendingPathComponent: fileName];
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    BOOL result = [fileManager fileExistsAtPath: filepath];
    return result;
}

+(NSString *)dataFilePath: (NSString *)fileName
{
    NSString *path = [[NSHomeDirectory() stringByAppendingPathComponent:@"Documents"] 
                      stringByAppendingFormat: @"/%@", fileName];
       return path;
}


+(NSString *)createFilePath: (NSString*)fileName
{
    NSString *path = [FileManager dataFilePath:fileName];
    BOOL result = FALSE;
    result = [FileManager isFileExist: path];
    if(!result)
  {
    [[NSFileManager defaultManager]
    createFileAtPath:path   contents:nil attributes:nil];
  }
    return path;
}




+(void) writeFile: (NSString *)fileName  content:(NSString*)fileContent
{
    NSString* fullpath = [FileManager dataFilePath:fileName];
    NSMutableData *writer = [[NSMutableData alloc] init];
    //将字符串添加到缓冲中
    [writer appendData:[fileContent dataUsingEncoding:NSASCIIStringEncoding]];
    [writer writeToFile:fullpath  atomically:NO];
}

+(NSString*)readFile:(NSString*)fileName
{
    NSString*  filePath = [FileManager dataFilePath:fileName];
    
    NSString* myFileContents;
	if([[NSFileManager defaultManager] fileExistsAtPath:filePath])
	{
       myFileContents  =  [NSString stringWithContentsOfFile:filePath encoding: NSUTF8StringEncoding error:nil];
        return myFileContents;	
    }
    return nil;

}


+(BOOL) delFile: (NSString *)fileName
{
    NSString*  filePath = [FileManager dataFilePath:fileName];
    if(![FileManager isFileExist: filePath])
    {
       return FALSE;
    }
    
    NSError *error = nil;
    NSFileManager *fileMgr = [NSFileManager defaultManager];
    if(fileMgr != nil)
    {
        BOOL result = [fileMgr removeItemAtPath: filePath error: &error];
        if(result != YES)
        {
            return FALSE;
        }
        return TRUE;
    }
    return FALSE;
}

+(NSArray *)getAllFileNames: (NSString *)dirName
{
    //获得此程序的沙盒路径 
    NSArray *patchs = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);    
    //[patchs objectAtIndex:0]获得 Documents 路径 
    NSString *fileDirectory = [[patchs objectAtIndex: 0] stringByAppendingFormat: @"/%@", dirName]; 
    // DLog(@"dir: %@", fileDirectory);
    //创建文件遍历管理类
    NSDirectoryEnumerator *dirEnumerater = [[NSFileManager defaultManager] enumeratorAtPath: fileDirectory]; 
    
    NSMutableArray *names = [[NSMutableArray alloc] init];
    
    NSString *fileName;
    //循环获得文件名 例：123.png 
    while((fileName = [dirEnumerater nextObject])) 
    { 
        int current = [fileName intValue];
       // DLog(@"current = %d", current);
        if(current > 0)
        {
            int pos = -1;
            
            for(int i=0; i<[names count]; ++i)
            {
                int intValue = [[names objectAtIndex: i] intValue];
                if(current < intValue)
                {
                    pos = i;
                    break;
                }
            }
            
            if(pos == -1)
            {
                pos = [names count];
            }
            
            [names insertObject: fileName atIndex: pos];
        }
        else
        {
            NSString *fullName = [NSString stringWithFormat: @"/%@", fileName];
            BOOL result = [FileManager delFile: [FileManager dataFilePath: fullName]];
            if(result == NO)
            {
               // DLog(@"can not del file.");
            }
        }
    }  
    return names;
}


+(NSString *)readResource:(NSString*)name type:(NSString*)type
{
    NSError *err = nil;
    NSBundle *bundle = [NSBundle mainBundle];
    NSString *path = [bundle pathForResource:name ofType:type];
    return [[NSString alloc] initWithContentsOfFile:path encoding:NSUTF8StringEncoding error:&err];
}


+(id)ReadUnArchive:(NSString*)path
{
    if(![FileManager isFileExist:path])
    {
       return nil;
    }
    
    NSString *fullpath = [FileManager dataFilePath:path];
    return [NSKeyedUnarchiver unarchiveObjectWithFile:fullpath];
}


+(void)SaveArchive:(id<NSCoding>)object path:(NSString*)path
{
    NSString* fullpath = [FileManager dataFilePath:path];
    [NSKeyedArchiver archiveRootObject:object toFile:fullpath];
}

@end
