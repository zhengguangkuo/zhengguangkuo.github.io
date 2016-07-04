//
//  NSDictionary(JSON).m
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import "NSDictionary(JSON).h"
@implementation NSDictionary(JSON)
+(NSDictionary*)dictionaryWithString:(NSString*)stringContnent
{
   NSError* error = nil;
   NSData*  dataContent = [stringContnent dataUsingEncoding:NSUTF8StringEncoding];
   NSDictionary* result = (NSDictionary*)[NSJSONSerialization JSONObjectWithData:dataContent options:kNilOptions error:&error];
   if(error!=nil)return nil;
   return result;
}

-(NSString*)toJSON
{
    NSError* error =nil;
    NSData* data = [NSJSONSerialization dataWithJSONObject:self
                                               options:kNilOptions error:&error];
    NSString*  result = [[NSString  alloc] initWithData:data encoding:NSUTF8StringEncoding];
    if(error!=nil)return nil;
    return result;
}
@end