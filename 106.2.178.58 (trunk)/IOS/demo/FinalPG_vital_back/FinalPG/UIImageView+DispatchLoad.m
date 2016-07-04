
#import "UIImageView+DispatchLoad.h"
#import "FileManager.h"
@implementation UIImageView (DispatchLoad)
- (void) setImageFromUrl:(NSString*)urlString {
    [self setImageFromUrl:urlString completion:NULL];
}

- (void) setImageFromUrl:(NSString*)urlString
              completion:(void (^)(void))completion {
   
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        UIImage *avatarImage = nil;
        NSURL *url = [NSURL URLWithString:urlString];
        NSData *responseData=[NSData dataWithContentsOfURL:url];
        avatarImage = [UIImage imageWithData:responseData];
        
        if (avatarImage)
        {
            [responseData writeToFile:[FileManager GetFullPathofFile:urlString] atomically:NO];

            dispatch_async(dispatch_get_main_queue(),
           ^{
               [self setImage:avatarImage];
            });
            
            if(completion)
            {
                dispatch_async(dispatch_get_main_queue(),
                               completion);
            }
        }
        else {
            NSLog(@"-- impossible download: %@", urlString);
        }
    });
}



- (void) setImageFromData:(NSData*)cachedata  targetPath:(NSString*)pathName completion:(void (^)(void))block
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    if(![cachedata isKindOfClass:[NSNull class]]){
        UIImage* cacheImage = [UIImage imageWithData:cachedata];
       if(cacheImage){
        [cachedata writeToFile:[FileManager GetFullPathofFile:pathName] atomically:NO];
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



- (void) setImageFromData:(NSData *)cachedata targetPath:(NSString *)pathName
{
   [self setImageFromData:cachedata targetPath:pathName completion:NULL];
}



- (void) setImageFromCache:(NSString*)pathName
{
     if([FileManager FileExistCheck:pathName])
   {
      UIImage *avatarImage = [UIImage imageWithContentsOfFile:[FileManager GetFullPathofFile:pathName]];
      
       dispatch_async(dispatch_get_main_queue(),
      ^{
          [self setImage:avatarImage];
       });

   }
}







@end