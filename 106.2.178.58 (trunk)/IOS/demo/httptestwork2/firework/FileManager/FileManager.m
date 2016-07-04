//
//  FileManager.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
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
    if(fileName == nil)
        return false;
    
    NSError *error = nil;
    NSFileManager *fileMgr = [NSFileManager defaultManager];
    if(fileMgr != nil)
    {
        BOOL result = [fileMgr removeItemAtPath: fileName error: &error];
        if(result != YES)
        {
        //    DLog(@"unable to delete file: %@, for reason: %@", fileName, [error localizedDescription]);
            return false;
        }
        
        return true;
    }
    
    return false;
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

@end
