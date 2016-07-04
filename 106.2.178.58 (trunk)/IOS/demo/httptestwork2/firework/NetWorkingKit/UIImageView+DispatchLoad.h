//
//  UIImageView+DispatchLoad.h
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UIImageView (DispatchLoad)
- (void) setImageFromUrl:(NSString*)urlString;

- (void) setImageFromUrl:(NSString*)urlString
              completion:(void (^)(id))completion;

- (void) setImageFromData:(NSData*)cachedata  targetPath:(NSString*)pathName completion:(void (^)(void))block;

- (void) setImageFromData:(NSData *)cachedata targetPath:(NSString *)pathName;

- (void) setImageFromCache:(NSString*)pathName;

@end