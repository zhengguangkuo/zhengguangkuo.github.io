//
//  NSObject+Property.h
//   
//
//  Created on 12-12-15.
//
//
#import <objc/runtime.h>
#import <Foundation/Foundation.h>

@interface NSObject (Property)

//1. 利用反射取得NSObject的属性，并存入到数组中
- (NSArray *)getPropertyList;
- (NSArray *)getPropertyList: (Class)clazz;

//2. 根据属性生成创建Sqlite表的语句
- (NSString *)tableSql:(NSString *)tablename;
- (NSString *)tableSql;

//3.把一个实体对象，封装成字典Dictionary
- (NSDictionary *)convertDictionary;

//4.从一个字典中还原成一个实体对象
- (id)initWithDictionary:(NSDictionary *)dict;

//5.返回一个对象的类型名称返回一个对象的类型名称
- (NSString *)className;
@end
