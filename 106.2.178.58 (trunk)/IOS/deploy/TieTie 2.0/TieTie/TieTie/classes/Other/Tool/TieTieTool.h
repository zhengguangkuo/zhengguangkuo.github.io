//
//  TieTieTool.h
//  Miteno
//
//  Created by wg on 14-6-12.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TieTieTool : NSObject

////优惠劵列表数据
//+ (void)tietieWithdict:(NSDictionary *)dict requestURL:(NSString *)requestURL succes:(void (^)(NSMutableArray *))succes fail:(void (^)(NSError*))fail flag:(NSInteger )flag;


+ (void)tietieWithParameterMarked:(NSString *)parameterMarked dict:(NSDictionary *)dict succes:(void (^)(id))succes fail:(void (^)(NSError*))fail;
//提交请求体含中文(GBK格式)
+ (void)tietieWitheEncodParameterMarked:(NSString *)parameterMarked dict:(NSDictionary *)dict succes:(void (^)(id))succes fail:(void (^)(NSError*))fail;

//城市列表数据


//验证码倒计时
+ (void)startTimer:(UIButton*)btn;

+ (void)clear;
@end
