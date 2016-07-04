#import "MsgDB.h"
#import "PushMsg.h"
#import "FileManager.h"
#import "NSObject(Dictionary).h"


#define GETDIC(B)  ([B isKindOfClass:[NSDictionary class]])?((NSDictionary*)B):([B ConvertToDicionary])

@implementation Msg_DB

singleton_for_implementation(Msg_DB)

@synthesize db = _db;

- (id)init
{
    self = [super init];
    if(self)
  {
      [self createDataBase:@"PushMsg.sqlite"];
      [self createTable];
  }
    return self;
}


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
    MyLog(@"%@",path);
    self.db = [FMDatabase databaseWithPath:[FileManager dataFilePath:path]];
}


-(void)createTable
{
    NSError *error2;
    if([self.db open])
    {
        @try {
            NSString* sql = @"CREATE TABLE PushMsg (msg_id text PRIMARY KEY, user_id text, msg_title text, msg_content text, send_time text, hasRead boolean)";
            [self.db executeUpdate:sql];
        }
        @catch (NSException *exception) {
            NSLog(@"table create error meet: %@",error2);
            abort();
        }
        [self.db close];
    }
}
    


- (BOOL)InsertRecord:(PushMsg*)record
{
    NSLog(@"insert");
    
    BOOL insertSuccess = NO;
    if ([self.db open]) {
        
        NSDictionary*  tempdic = GETDIC(record);
        NSString*  cmdSql = @"INSERT INTO PushMsg VALUES(:msg_id, :user_id, :msg_title, :msg_content, :send_time, :hasRead)";
        //INSERT INTO myTable VALUES (:id, :name, :value)
        
        BOOL isRollBack = NO;
        [self.db beginTransaction];
        @try
        {
           insertSuccess = [self.db executeUpdate:cmdSql withParameterDictionary:tempdic];
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
                NSLog(@"success");
            }
        }
        [self.db close];
    }
    return insertSuccess;
}

- (void)DeleteRecord:(NSString*)msg_id
{
    if([self.db open]) {
        NSString *mainsql = [NSString stringWithFormat:@"delete from PushMsg where msg_id = %@",msg_id];
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

- (void)UpdateRecord:(PushMsg*)record msg_id:(NSString*)msg_id
{
    if([self.db open])
    {
        NSDictionary*  tempdic = GETDIC(record);
        NSString* sql = [NSString stringWithFormat:
        @"UPDATE PushMsg SET user_id = :user_id, msg_title = :msg_title, msg_content = :msg_content, send_time = :send_time, hasRead = :hasRead where msg_id = %@",msg_id];
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

- (NSArray*)FetchRecords:(NSString*)user_id
{
    NSMutableArray *array=[NSMutableArray arrayWithCapacity:20];
    if([self.db open]) {
        
        NSString *mainsql =  [NSString stringWithFormat:@"select* from PushMsg where user_id = %@",user_id];
        
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

- (NSMutableArray *)SelectedAllRecords
{
    NSMutableArray *array;
    if ([self.db open]) {
        NSString *sqlStr = [NSString stringWithFormat:@"select * from PushMsg"];
        array = [[NSMutableArray alloc]init];
        @try {
            FMResultSet *rs = [self.db executeQuery:sqlStr];
            while ([rs next]) {
                NSDictionary *dic = [rs resultDictionary];
                PushMsg *pushmsg = [[PushMsg alloc]initWithDictionary:dic];
                [array addObject:pushmsg];
            }
        }
        @catch (NSException *exception) {
            NSLog(@"name:%@;reason:%@",[exception name],[exception reason]);
        }
        
        [self.db close];
    }
    return  array;
}

@end

