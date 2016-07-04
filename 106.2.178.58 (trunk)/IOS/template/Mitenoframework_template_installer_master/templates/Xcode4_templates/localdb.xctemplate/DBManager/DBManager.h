//
//  DBManager.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
#import "Singleton.h"

@interface DBManager : NSObject

singleton_for_interface(DBManager)

@property (strong, nonatomic) FMDatabase* db;
//创建数据库以及指定路径
-(void)createDataBase:(NSString*)path;
//创建表，字典对应表结构
-(void)createTable:(NSString*)tableName sqldic:(NSDictionary*)dic;
//插入一个数组,传参表名,以及对应对象或者字典
-(void)insertTable:(NSString*)tableName array:(NSArray*)array;
//插入一个对象
-(void)insertTable:(NSString*)tableName object:(id)object;
//删除一个记录,传参表名以及字典，字典对应where结构
-(void)deleteTable:(NSString*)tableName
             where:(NSDictionary*)condition;
//删除一个记录，传参同上
-(void)updateTable:(NSString*)tableName
             where:(NSDictionary*)condition
             object:(id)object;
//查询符合条件的集合，参数同上
-(NSArray*)queryTable:(NSString*)tableName
                where:(NSDictionary*)condition;

@end
