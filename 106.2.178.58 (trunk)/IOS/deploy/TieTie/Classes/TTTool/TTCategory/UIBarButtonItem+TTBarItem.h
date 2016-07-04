//
//  UIBarButtonItem+TTBarItem.h
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef enum {
    ItemDirectionLeft,
    ItemDirectionRight
}ItemDirection;
@interface UIBarButtonItem (TTBarItem)
#define btnEdgeLeft  UIEdgeInsetsMake(0, -48, 0, 0) //左
#define btnEdgeRight  UIEdgeInsetsMake(0, 0, 0, -38) //右
+ (UIBarButtonItem *)barButtonItemWithIcon:(NSString *)normalIcon target:(id)target action:(SEL)action direction:(ItemDirection)direction;

+ (UIBarButtonItem *)barButtonItemWithleftIcon:(NSString *)leftIcon rightIcon:(NSString *)rightIcon  target:(id)target actionLeft:(SEL)actionLeft  actionRitht:(SEL)actionRitht;

@end
