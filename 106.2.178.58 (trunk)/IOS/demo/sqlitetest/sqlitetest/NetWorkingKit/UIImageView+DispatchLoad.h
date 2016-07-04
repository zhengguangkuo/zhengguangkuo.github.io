//
//  UIImageView+DispatchLoad.h
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UIImageView (DispatchLoad)
- (void) setImageFromUrl:(NSString*)urlString;
- (void) setImageFromUrl:(NSString*)urlString
              completion:(void (^)(void))completion;
- (void) setImageFromData:(NSData*)cachedata  targetPath:(NSString*)pathName completion:(void (^)(void))block;
- (void) setImageFromData:(NSData *)cachedata targetPath:(NSString *)pathName;
- (void) setImageFromCache:(NSString*)pathName;

@end