//
//  TTFriendsMgr.m
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFriendsMgr.h"
#import "TTFriends.h"
@interface TTFriendsMgr()
{
    sqlite3 *_db;
}
@end
@implementation TTFriendsMgr
singleton_for_implementation(TTFriendsMgr)

- (id)init
{
    if (self = [super init]) {
        // 打开数据库
        [self openDB];
        
        // 创表
        [self createTables];
    }
    return self;
}
#pragma mark 打开数据库
- (void)openDB
{
    NSString *doc = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
    NSString *path = [doc stringByAppendingPathComponent:@"friends.sqlite"];
    
    // 打开数据库（如果没有数据库，就会自动创建）
    int result = sqlite3_open(path.UTF8String, &_db);
    
    if (result == SQLITE_OK) {
        TTLog(@"成功打开数据库");
    } else {
        TTLog(@"打开数据库失败");
    }
}
#pragma mark 创表
- (void)createTables
{
    NSString *sql = @"create table if not exists t_friend(id integer primary key autoincrement,userID integer, userName text, tel text);";
    
    [self execute:sql action:@"创表"];
}
- (void)execute:(NSString *)sql action:(NSString *)action
{
    const char *sqlStr = sql.UTF8String;
    
    char *error = NULL;
    // 执行sql语句
    int result = sqlite3_exec(_db, sqlStr, NULL, NULL, &error);
    
    if (result == SQLITE_OK) {
        TTLog(@"成功%@", action);
    } else {
        TTLog(@"%@失败-%s", action, error);
    }
}
- (void)addFriend:(TTFriends *)friend
{
    NSString *sql = [NSString stringWithFormat:@"insert into t_friend(userID,userName, tel) values(%d,'%@', %@);",friend.userID,friend.userName, friend.tel];
    
    [self execute:sql action:@"添加数据"];
}
- (void)updatePerson:(TTFriends *)friend
{
    NSString *sql = [NSString stringWithFormat:@"update t_friend set userName = '%@', tel = %@ where userID = %d;", friend.userName, friend.tel, friend.userID];
    
    [self execute:sql action:@"更新数据"];
}
- (void)deletePersonWithId:(NSInteger)userID
{
    NSString *sql = [NSString stringWithFormat:@"delete from t_friend where userID = %d;", userID];
    
    [self execute:sql action:@"删除数据"];
}
- (TTFriends *)queryFriendWithName:(NSString *)name
{
    NSString *sql = [NSString stringWithFormat:@"select userID,userName,tel from t_friend where userName = '%@';", name];
    
    const char *sqlStr = sql.UTF8String;
    
    // 用来获得所有的记录
    sqlite3_stmt *stmt = NULL;
    
    TTFriends *friend = nil;
    
    // 1.检测SQL语句的合法性
    int result = sqlite3_prepare_v2(_db, sqlStr, -1, &stmt, NULL);
    if (result == SQLITE_OK) {
        // 2.真正执行SQL语句进行查询数据
        if(sqlite3_step(stmt) == SQLITE_ROW) { // 成功返回一条记录
            // 取出0位置字段对应的整数-id
            int userID = sqlite3_column_int(stmt, 0);
            // 取出1位置字段对应的字符串-name
            const unsigned char *userName = sqlite3_column_text(stmt, 1);
            // 取出2位置字段对应的整数-age
            const unsigned char *tel = sqlite3_column_text(stmt, 2);
            // 封装模型数据
            friend = [[TTFriends alloc] init];
            friend.userID = userID;
            friend.userName = [NSString stringWithUTF8String:(const char *)userName];
            friend.tel = [NSString stringWithUTF8String:(const char *)tel];
        }
    } else {
        TTLog(@"查询语句语法有问题");
    }
    
    return friend;
}
- (NSArray *)queryFriendsWithSql:(NSString *)sql
{
    const char *sqlStr = sql.UTF8String;
    
    // 用来获得所有的记录
    sqlite3_stmt *stmt = NULL;
    
    NSMutableArray *friends = nil;
    
    // 1.检测SQL语句的合法性
    int result = sqlite3_prepare_v2(_db, sqlStr, -1, &stmt, NULL);
    if (result == SQLITE_OK) {
        friends = [NSMutableArray array];
        
        // 2.真正执行SQL语句进行查询数据
        while(sqlite3_step(stmt) == SQLITE_ROW) { // 成功返回一条记录
            int userID = sqlite3_column_int(stmt, 0);
            const unsigned char *userName = sqlite3_column_text(stmt, 1);
            const unsigned char *tel = sqlite3_column_text(stmt, 2);
            // 封装模型数据
            TTFriends *friend = [[TTFriends alloc] init];
            friend.userID = userID;
            friend.userName = [NSString stringWithUTF8String:(const char *)userName];
            friend.tel = [NSString stringWithFormat:@"%s",(const char *)tel];
            [friends addObject:friend];
        }
    } else {
        TTLog(@"查询语句语法有问题");
    }
    
    return friends;
}

- (NSArray *)queryFriends
{
    NSString *sql = @"select userID,userName,tel from t_friend;";
    
    return [self queryFriendsWithSql:sql];
}

- (NSArray *)queryFriendsWithCondition:(NSString *)condition
{
    //NSString *sql = [NSString stringWithFormat:@"select id,name,age from t_person where name = '%@';", condition] ;
    NSString *sql = [NSString stringWithFormat:@"select userID,userName,tel from t_friend where userName like '%%%@%%';", condition];
    
    TTLog(@"%@", sql);
    
    return [self queryFriendsWithSql:sql];
}
@end
