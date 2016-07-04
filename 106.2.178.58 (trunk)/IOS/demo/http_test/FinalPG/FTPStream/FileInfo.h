//
//  FileInfo.h
//  iPhoneFTP
//
//  Created by Zhou Weikuan on 10-6-15.
//  Copyright 2010 sino. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface FileInfo : NSObject {

}

+ (NSURL *)smartURLForString:(NSString *)str;
+ (uint64_t) getFTPStreamSize:(CFReadStreamRef)stream;

+ (NSString*) pathForDocument;
+ (uint64_t) getFileSize:(NSString *)filePath;
@end
