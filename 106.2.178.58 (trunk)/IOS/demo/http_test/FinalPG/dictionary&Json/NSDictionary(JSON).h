#import <Foundation/Foundation.h>


@interface NSDictionary(JSON)
+(NSDictionary*)dictionaryWithString:(NSString*)stringContnent;
-(NSString*)toJSON;

@end