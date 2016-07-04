//
//  DbHelper.h
//  
//
//  Created on 12-12-15.
//
//

#import <Foundation/Foundation.h>
#import "NSObject+Property.h"
#import "FMDatabase.h"
#import "FMDatabaseQueue.h"
#import "Singleton.h"
@interface DbHelper : NSObject
{
    FMDatabase* _db;
    FMDatabaseQueue* _dbQueue;
}
singleton_for_interface(DbHelper)

+(DbHelper *)instance;
-(FMDatabaseQueue *)queue;
-(FMDatabase *)db;
-(BOOL)isExistsTable:(NSString *)tablename;
-(BOOL)DropExistsTable:(NSString*)tableName;
-(BOOL)createTableByClassName:(NSString *)classname;
-(BOOL)createTableByClass:(Class)clazz;
-(void)insert:(Class)clazz dict:(NSDictionary *)dict;
-(NSString *)createInsertSqlByClass:(Class)clazz;

/*
 *  把字典NSDictionary对象插入到数据库中
 在与服务器进行交互时候，我们一般采用Json进行数据通讯，从服务端获取Json字符，通过JSONKit框架，反序列化成NSDictionary对象，然后插入到数据库
 
 生成插入的sql语句
 */
-(NSString *)createInsertSqlByDictionary:(NSDictionary *)dict tablename:(NSString *)table;
//把字典插入到数据库中
-(void)insertBySql:(NSString *)sql dict:(NSDictionary *)dict;

//把id类型的数据对象插入到数据库
-(void)insertObject:(id)object;
-(void)executeByQueue:(NSString *)sql;

//查询数据到字典数组，字典的Key对应列
-(NSArray *)queryDbToDictionaryArray:(NSString *)tablename;
//从数据库取数据，封装成字典，然后放入到数组中
-(NSArray *)queryDbToDictionaryArray:(NSString *)tablename sql:(NSString *)sql;

//查询数据到对象数组，数据多时考虑效率问题，另注意列名与对象属性之间的对应关系
//从数据库中取数据，封装成对象，然后放入数组中。
-(NSArray *)queryDbToObjectArray:(Class )clazz sql:(NSString *)sql;
-(NSArray *)queryDbToObjectArray:(Class )clazz;


//********************  获取数据库信息      ************
////获取表中所有二级城市
//- (NSArray *)cityName;
////根据城市名称获取ID （AREA_CODE表）
//- (NSString *)queryCityCode:(NSString *)cityName;
////查询返回区域的数组
//- (NSArray *)queryArea:(NSString *)tablename cityCode:(NSString *)cityCode;
//- (NSArray *)queryDbToObjectArray:(NSString *)tablename condition:(NSString *)condion;
//- (NSArray *)queryArea:(NSString *)tablename titleConditon:(NSString *)titleConditon;
////查询返回美食数组与之对应的数组
//- (NSArray *)queryCate:(NSString *)tablename;
//- (NSArray *)queryDbToObjectArray:(NSString *)cateTablename catecondition:(NSString *)catecondition;

////********************  获取数据库信息      ************
////获取表中所有二级城市
//- (NSArray *)cityName;
////根据城市名称获取ID （AREA_CODE表）
//- (NSString *)queryCityCode:(NSString *)cityName;
////查询返回区域的数组
//- (NSArray *)queryArea:(NSString *)tablename cityCode:(NSString *)cityCode;
//- (NSArray *)queryDbToObjectArray:(NSString *)tablename condition:(NSString *)condion;
//- (NSArray *)queryArea:(NSString *)tablename titleConditon:(NSString *)titleConditon;
////查询返回美食数组与之对应的数组
//- (NSArray *)queryCate:(NSString *)tablename;
//- (NSArray *)queryDbToObjectArray:(NSString *)cateTablename catecondition:(NSString *)catecondition;

@end
