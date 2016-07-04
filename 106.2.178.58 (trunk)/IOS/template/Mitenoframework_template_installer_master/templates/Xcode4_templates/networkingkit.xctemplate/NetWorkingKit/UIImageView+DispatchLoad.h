//
//  UIImageView+DispatchLoad.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
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