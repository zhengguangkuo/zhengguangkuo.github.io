//
//  NSObject(Dictionary).h
//  Miteno
//
//  Created by HWG on 14-2-24.
//  Copyright wenguang 2014年. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSObject(Dictionary)
- (NSDictionary*)ConvertToDicionary;
- (id)getObjectInternal:(id)obj;
- (NSString *)className;
- (id)initWithDictionary:(NSDictionary*)dict;

//add by zgk ======start=============================================
- (NSArray*)propertyKeys;
- (BOOL)reflectDataFromOtherObject:(NSObject*)objectData;
//add by zgk ======end=============================================
@end