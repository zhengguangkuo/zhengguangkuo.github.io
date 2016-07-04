#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>
#import "NSObject+Property.h"
#import "FMDatabase.h"
#import "FMDatabaseQueue.h"
#import "Singleton.h"

@class PushMsg;



@interface Msg_DB : NSObject
{

}

singleton_for_interface(Msg_DB)

@property  (nonatomic, strong)  FMDatabase* db;
    
- (id)init;

- (BOOL)InsertRecord:(PushMsg*)record;

- (void)DeleteRecord:(NSString*)msg_id;

- (void)UpdateRecord:(PushMsg*)record msg_id:(NSString*)msg_id;

- (NSArray*)FetchRecords:(NSString*)user_id;

- (NSMutableArray*)SelectedAllRecords;

@end

