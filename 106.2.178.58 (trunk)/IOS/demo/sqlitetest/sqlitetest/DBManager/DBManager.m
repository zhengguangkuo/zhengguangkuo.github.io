
#import "DBManager.h"
#import "FileManager.h"
#import "NSObject(Dictionary).h"



#define CNil(A) (A==nil?@"":A)

#define GETDIC(B)  ([B isKindOfClass:[NSDictionary class]])?((NSDictionary*)B):([B ConvertToDicionary])


@interface DBManager()
//产生insert语句的字符串
-(NSString*)GenerateInsertCmd:(NSString*)tableName dic:(NSDictionary*)tempdic;
//产生where条件的字符串
-(NSString*)GenerateWhereCmd:(NSDictionary*)tempdic;
//产生update语句的字符串
-(NSString*)GenerateUpdateCmd:(NSString*)tableName dic:(NSDictionary*)tempdic;

@end


@implementation DBManager
@synthesize db = _db;

#pragma mark-  create db and table
//创建数据库以及指定路径
-(void)createDataBase:(NSString*)path
{
    NSError *error;
    if (![FileManager isFileExist:path]) {
        @try {
             [FileManager createFilePath:path];
        }
        @catch (NSException *exception) {
            NSLog(@"db create error meet: %@",error);
            abort();
        }
    }
    self.db = [FMDatabase databaseWithPath:[FileManager dataFilePath:path]];
}

//创建表传入sql结构字典
-(void)createTable:(NSString*)tableName sqldic:(NSDictionary*)dic
{
     NSError *error2;
     if([self.db open])
    {
        @try {
            NSString* sql = [NSString stringWithFormat:@"CREATE TABLE %@(",tableName];
            int n = 0;
            for(NSString* key in [dic allKeys])
        {
            if(n++>0)
            sql = [sql stringByAppendingString:@","];
            sql = [sql stringByAppendingFormat:@"%@ %@",key,dic[key]];
        }
            sql = [sql stringByAppendingString:@")"];
            NSLog(@"sql = %@",sql);
            
            [self.db executeUpdate:sql];
        }
        @catch (NSException *exception) {
            NSLog(@"table create error meet: %@",error2);
            abort();
        }
        [self.db close];
    }
}

#pragma mark-  create sql string

//产生insert命令的字符串
-(NSString*)GenerateInsertCmd:(NSString*)tableName dic:(NSDictionary*)tempdic
{
    NSString* sql = [NSString  stringWithFormat:@"INSERT INTO %@ values(",tableName];
    
    NSArray*  keyArray = [tempdic allKeys];
    for(int i = 0; i < [keyArray count]; i++)
    {
        if(i>0)
            sql = [sql stringByAppendingString:@","];
        
        sql = [sql stringByAppendingFormat:@":%@",keyArray[i]];
    }
    sql = [sql stringByAppendingString:@")"];
    return sql;
}


//产生一个update命令的字符串
-(NSString*)GenerateUpdateCmd:(NSString*)tableName dic:(NSDictionary*)tempdic
{
    NSString* sql = [NSString  stringWithFormat:@"UPDATE %@ SET ",tableName];
    NSArray*  keyArray = [tempdic allKeys];
    
    for(int i = 0; i < [keyArray count]; i++)
    {
        NSString* key = [keyArray objectAtIndex:i];
        if(i>0)
            sql = [sql stringByAppendingString:@","];
        
        sql = [sql stringByAppendingFormat:@"%@ = :%@",key, key];
    }
    return sql;
}

//产生一个查询条件where的字符串
-(NSString*)GenerateWhereCmd:(NSDictionary*)tempdic
{
    NSString* sql = [NSString stringWithFormat:@" WHERE "];
    
    NSArray*  keyArray = [tempdic allKeys];
    
    for(int i = 0; i < [keyArray count]; i++)
    {
        NSString* key = [keyArray objectAtIndex:i];
        NSString* value = [tempdic valueForKey:key];
        if(i>0)
            sql = [sql stringByAppendingString:@" AND "];
        
        if([value isKindOfClass:[NSString class]])
            value = [NSString stringWithFormat:@"'%@'",value];
        
        sql = [sql stringByAppendingFormat:@"%@ == %@",key,value];
    }
    return sql;
}

#pragma mark-  operate table for insert,delete,update,query
//插入数据对象，传参表名，以及对象或字典
-(void)insertTable:(NSString*)tableName object:(id)object
{
    if ([self.db open]) {
        NSDictionary*  tempdic = GETDIC(object);
        
        NSString* sql = [self GenerateInsertCmd:tableName dic:tempdic];
        
        BOOL isRollBack = NO;
        [self.db beginTransaction];
        @try
        {
            [self.db executeUpdate:sql withParameterDictionary:tempdic];
        }
        @catch (NSException *exception) {
            isRollBack = YES;
            [self.db rollback];
        }
        @finally
        {
            if(!isRollBack)
            {
                [self.db commit];
            }
        }
        [self.db close];
    }
}

//插入一个集合给指定名称的表
-(void)insertTable:(NSString*)tableName array:(NSArray*)array
{
    if ([self.db open])
 {
     id tempobject = [array objectAtIndex:0];
     NSDictionary*  tempdic = GETDIC(tempobject);
     NSString* sql = [self GenerateInsertCmd:tableName dic:tempdic];
     
     BOOL isRollBack = NO;
     [self.db beginTransaction];
     @try {
         for(id object in array)
         {
             NSDictionary* dic = GETDIC(object);
             [self.db executeUpdate:sql withParameterDictionary:dic];
         }
     }
     @catch (NSException *exception) {
         isRollBack = YES;
         [self.db rollback];
     }
     @finally {
         if(!isRollBack)
       {
         [self.db commit];
       }
     }
     
     [self.db close];
 }

}

//删除一条符合要求的记录
-(void)deleteTable:(NSString*)tableName
             where:(NSDictionary*)condition
{
    if([self.db open]) {
        NSString *mainsql = [NSString stringWithFormat:@"DELETE FROM %@",tableName];
        NSString* wherersql = [self GenerateWhereCmd:condition];
        
        mainsql = [mainsql stringByAppendingString:wherersql];
        
        NSLog(@"mainsql = %@",mainsql);
        
        BOOL isRollBack = NO;
        [self.db beginTransaction];
        
        @try {
            [self.db executeUpdate:mainsql];
        }
        @catch (NSException *exception) {
            isRollBack = YES;
            [self.db rollback];
        }
        @finally {
           if(!isRollBack)
         {
           [self.db commit];
         }
        }
        [self.db close];
    }
}


//更新一个符合条件的记录
-(void)updateTable:(NSString*)tableName
             where:(NSDictionary*)condition
            object:(id)object
{
    if([self.db open]) {
        
        NSDictionary*  tempdic = GETDIC(object);
        
        NSString* sqlmain = [self GenerateUpdateCmd:tableName dic:tempdic];
        
        NSString* sqlwhere = [self GenerateWhereCmd:condition];
        
        NSString* sql = [sqlmain stringByAppendingString:sqlwhere];
        
        NSLog(@"sql update = %@",sql);
        
        BOOL isRollBack = NO;

        [self.db  beginTransaction];
        
        @try {
            [self.db executeUpdate:sql withParameterDictionary:tempdic];
        }
        @catch (NSException *exception) {
            isRollBack = YES;
            [self.db rollback];
        }
        @finally {
            if(!isRollBack)
            [self.db  commit];
        }
        [self.db close];
    }
}

//查询符合条件的记录
-(NSArray*)queryTable:(NSString*)tableName
                where:(NSDictionary*)condition
{
   NSMutableArray *array=[NSMutableArray arrayWithCapacity:20];

    if([self.db open]) {
        
        NSString *mainsql =  [NSString stringWithFormat:@"SELECT * FROM %@",tableName];
        
        NSString* wheresql = [self GenerateWhereCmd:condition];
        
        mainsql = [mainsql stringByAppendingString:wheresql];
        
        NSLog(@"query mainsql = %@",mainsql);
        
        BOOL isRollBack = NO;
        
        [self.db beginTransaction];
        @try {
            FMResultSet *rs= [self.db executeQuery:mainsql];
            while ([rs next]) {
                NSDictionary* dic = [rs resultDictionary];
                [array addObject:dic];
            }
        }
        @catch (NSException *exception) {
            isRollBack = YES;
            [self.db rollback];
        }
        @finally {
            if(!isRollBack)
          {
              [self.db commit];
          }
        }
        
        [self.db close];
    }
    return array;
}

@end
