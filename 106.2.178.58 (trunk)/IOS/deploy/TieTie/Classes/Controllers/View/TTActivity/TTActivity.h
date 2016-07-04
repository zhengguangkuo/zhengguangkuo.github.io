//
//  TTActivity.h
//  Miteno
//
//  Created by wg on 14-6-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol TTActivityDelegate <NSObject>
- (void)didClickOnImageIndex:(NSInteger)imageIndex;
@optional
- (void)didClickOnCancelButton;
@end

@interface TTActivity : UIView
@property (nonatomic,assign) id<TTActivityDelegate>delegate;
- (id)initWithTitle:(NSString *)title delegate:(id<TTActivityDelegate>)delegate cancelButtonTitle:(NSString *)cancelButtonTitle ShareButtonTitles:(NSArray *)shareButtonTitlesArray withShareButtonImagesName:(NSArray *)shareButtonImagesNameArray;
- (void)showInView:(UIView *)view;
@end
