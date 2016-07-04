//
//  NSDictionary(JSON).h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface NSDictionary(JSON)
+(NSDictionary*)dictionaryWithString:(NSString*)stringContnent;
-(NSString*)toJSON;
@end