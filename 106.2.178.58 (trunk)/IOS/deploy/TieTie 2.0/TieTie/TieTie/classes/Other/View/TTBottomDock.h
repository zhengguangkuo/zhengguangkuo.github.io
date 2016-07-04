//
//  TTBottomDock.h
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@class TTBottomDock,TTDockItem;
#define kBottomDockH 55
@protocol TTBottomDockDelegate <NSObject>
@optional
- (void)bottomDock:(TTBottomDock *)bottomDock btnClickFrom:(int)from to:(int)to;
@end

@interface TTBottomDock : UIView

+ (instancetype)bottomDock;
- (void)andBottomButton:(NSString *)icon selectIcon:(NSString *)selectIcon;
- (void)andBottomButton:(NSString *)icon selectIcon:(NSString *)selectIcon title:(NSString *)title;

@property (nonatomic, weak) id<TTBottomDockDelegate> delegate;

@end
