//
//  TTTopDock.h
//  TTSecondListView
//
//  Created by wg on 14-8-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
typedef enum {
    TabarButtonTypeLeft,
    TabarButtonTypeRight
}TabarButtonType;
@class TTTopButton;
@interface TTTopDock : UIView
@property (nonatomic, strong) TTTopButton      * regionItem;   //leftBtn
@property (nonatomic, strong) TTTopButton      * categoryItem; //rightBtn
@property (nonatomic, copy) void (^itemClickBlock)(TTTopButton *);     //
@end
