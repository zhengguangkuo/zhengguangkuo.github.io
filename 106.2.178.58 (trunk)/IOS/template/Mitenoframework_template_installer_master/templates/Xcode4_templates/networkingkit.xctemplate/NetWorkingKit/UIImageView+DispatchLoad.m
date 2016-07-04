//
//  UIImageView+DispatchLoad.m
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "UIImageView+DispatchLoad.h"
#import "FileManager.h"
@implementation UIImageView (DispatchLoad)

#pragma mark- download image and refresh
//多线程下载图片，传参url
- (void) setImageFromUrl:(NSString*)urlString {
    [self setImageFromUrl:urlString completion:NULL];
}

//多线程下载图片，传参url以及block
- (void) setImageFromUrl:(NSString*)urlString
              completion:(void (^)(id object))completion {
   
    __unsafe_unretained id blockSelf = self;
    
    void (^blk)(id) = [completion copy];//blkInHeap在堆里

    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        if([FileManager isFileExist:[urlString lastPathComponent]])
    {
        
        UIImage *avatarImage = [UIImage imageWithContentsOfFile:[FileManager dataFilePath:[urlString lastPathComponent]]];
        
        if(blk)
        {
            blk(blockSelf);
        }

        dispatch_async(dispatch_get_main_queue(),
        ^{
             [self setImage:avatarImage];
         });
    }
        else
    {
        UIImage *avatarImage = nil;
        NSURL *url = [NSURL URLWithString:urlString];
        NSData *responseData=[NSData dataWithContentsOfURL:url];
        avatarImage = [UIImage imageWithData:responseData];
        
        if (avatarImage)
        {
            [responseData writeToFile:[FileManager dataFilePath:[urlString lastPathComponent]] atomically:NO];
            
           if(blk)
        {
            blk(blockSelf);
        }
    
        dispatch_async(dispatch_get_main_queue(),
        ^{
             [self setImage:avatarImage];
         });
            
        }
        else {
            NSLog(@"-- impossible download: %@", urlString);
            
        }
   
    }
    
    });
    
}

#pragma mark- refresh image from assign path
//二进制转为图片，并保存,参数为路径以及block
- (void) setImageFromData:(NSData*)cachedata  targetPath:(NSString*)pathName completion:(void (^)(void))block
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    if(![cachedata isKindOfClass:[NSNull class]]){
        UIImage* cacheImage = [UIImage imageWithData:cachedata];
       if(cacheImage){
        [cachedata writeToFile:[FileManager dataFilePath:[pathName lastPathComponent]] atomically:NO];
        if(block){
              dispatch_async(dispatch_get_main_queue(),block);
         }else{
            dispatch_async(dispatch_get_main_queue(),
           ^{
              [self setImage:cacheImage];
            });
         }
      }
    }
    else{
       NSLog(@"empty data!");
    }
    
  });
}


//二进制转为图片，并保存,参数为路径
- (void) setImageFromData:(NSData *)cachedata targetPath:(NSString *)pathName
{
   [self setImageFromData:cachedata targetPath:pathName completion:NULL];
}


//从目标路径读取图片
- (void) setImageFromCache:(NSString*)pathName
{
     if([FileManager isFileExist:[pathName lastPathComponent]])
   {
      UIImage *avatarImage = [UIImage imageWithContentsOfFile:[FileManager dataFilePath:[pathName lastPathComponent]]];
      
       dispatch_async(dispatch_get_main_queue(),
      ^{
          [self setImage:avatarImage];
       });

   }
}

@end