//
//  NSString+File.h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSString (File)
- (NSString *)filenameAppend:(NSString *)append;
//md5加密 16位
- (NSString *)md5Encrypt;
+ (NSString *)processDateMethod:(NSString *)date;
+ (NSString *)replaceUnicode:(NSString *)unicodeStr;
//获取uuid
+ (NSString*)uuid;
//判断沙盒文件是否存在
+ (BOOL) isFileExist:(NSString *)fileName;

//32位
+ (NSString *)md5:(NSString *)str;
@end
