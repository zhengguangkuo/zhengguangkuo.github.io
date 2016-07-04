//
//  TTShare.h
//  Miteno
//
//  Created by wg on 14-6-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTShare : UIActionSheet
@property (nonatomic, copy) NSString *title;
@property (nonatomic, copy) NSString *icon;

+ (id)shareWithDict:(NSDictionary *)dict;
@end
