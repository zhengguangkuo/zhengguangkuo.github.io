
#import "Message.h"

@implementation Message

- (void)setDict:(NSDictionary *)dict{
    
    _dict = dict;
    
    self.icon = dict[@"icon"];
    self.time = dict[@"time"];
    self.content = dict[@"content"];
    self.type = [dict[@"type"] intValue];
}

- (id)initWithDict:(NSDictionary*)dict
{
    self = [super init];
    if (self) {
        self.type = [dict[@"Recieved"] integerValue];
        self.content = dict[@"Message"];
        
        NSDateFormatter *fmt = [[NSDateFormatter alloc]init];
        fmt.dateFormat = @"MM-dd";
        NSDate *date = [NSDate dateWithTimeIntervalSince1970:[dict[@"CreateTime"] doubleValue]];
        NSString *time = [fmt stringFromDate:date];
        
        self.time = time;
    }
    return  self;
}

@end
