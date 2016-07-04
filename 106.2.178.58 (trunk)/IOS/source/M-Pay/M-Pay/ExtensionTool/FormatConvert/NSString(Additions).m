//
//  NSString(Additions).m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//


#import "NSString(Additions).h"
#import "RegexKitLite.h"


@implementation NSString(Additions)

#define USER_NAME_FORMAT @"^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$"

#define PHONE_NUMBER_FORMAT @"^(14|13|15|18)[0-9]{9}$"

#define EMAIL_FORMAT     @"\\b([a-zA-Z0-9%_.+\\-]+)@([a-zA-Z0-9.\\-]+?\\.[a-zA-Z]{2,6})\\b"


#pragma mark-  input string check for assign format
//校验字符串格式
+(BOOL)checkFormat:(NSString*)string withformat:(NSString*)format
{
     if(!CNil(string)&&!CNil(format))
  {
     return [string isMatchedByRegex:format];
  }
     return FALSE;
}

//校验字符串长度
+(BOOL)checkLength:(NSString*)string
              over:(int)mini
              less:(int)max
{
     if(!CNil(string))
  {
      int length = string.length;
      if(length>=mini&&length<=max)
      return TRUE;
  }
     return FALSE;
}

//校验字符串相等
+(BOOL)checkEqual:(NSString*)src dest:(NSString*)dest
{
    if(!CNil(src)&&!CNil(dest))
  {
      return [src isEqualToString:dest];
  }
    return FALSE;
}

//校验字符串包含
+(BOOL)checkInclude:(NSString*)mainstr
             substr:(NSString*)string;
{
    if(!CNil(mainstr)&&!CNil(string))
  {
    NSRange range = [mainstr rangeOfString:string];
    if(range.location!=NSNotFound)
    {
        return TRUE;
    }
  }
    return FALSE;
}

//校验用户名
+(BOOL)checkUserName:(NSString*)username
{
    return [self checkFormat:username withformat:USER_NAME_FORMAT];
}

//校验电子邮箱
+(BOOL)checkEmail:(NSString*)email
{
    return [self checkFormat:email withformat:EMAIL_FORMAT];
}

//校验电话号码
+(BOOL)checkPhone:(NSString*)number
{
    return [self checkFormat:number withformat:PHONE_NUMBER_FORMAT];
}

#pragma mark-  usefull code segment of string operations
//字符串去空格
-(NSString *)trimString
{
    NSString *trimmedmailtext = [NSString stringWithFormat:@"%@",[self stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]];
    return trimmedmailtext;
}

//字符串截取
- (NSString *)subString:(int)pos for:(int)length
{
    if(pos>=0&&length>0)
    {
        NSRange range = NSMakeRange(pos, length);
        NSString* substr = [self substringWithRange:range];
        return substr;
    }
    return nil;
}

////字符串逆序
//-(id)reverseString
//{
//    NSUInteger len = [self length];
//    // self 表示字符串本身
//    NSMutableString *retStr = [NSMutableString stringWithCapacity:len];
//    while (len > 0) {
//        unichar c = [self characterAtIndex:--len];
//        // 从后取一个字符 unicode
//        NSLog(@" c is %C", c);
//        NSString *s = [NSString stringWithFormat:
//                       @"%C", c];
//        [retStr appendString:s];
//    }
//    return retStr;
//}

////字符串特殊符号替换
//- (NSString *)URLencode:(NSStringEncoding)stringEncoding {
//    //!  @  $  &  (  )  =  +  ~  `  ;  '  :  ,  /  ?
//    //%21%40%24%26%28%29%3D%2B%7E%60%3B%27%3A%2C%2F%3F
//    NSArray *escapeChars = [NSArray arrayWithObjects:@";" , @"/" , @"?" , @":" ,
//                            @"@" , @"&" , @"=" , @"+" ,    @"$" , @"," ,
//                            @"!", @"'", @"(", @")", @"*", nil];
//    
//    NSArray *replaceChars = [NSArray arrayWithObjects:@"%3B" , @"%2F", @"%3F" , @"%3A" ,
//                             @"%40" , @"%26" , @"%3D" , @"%2B" , @"%24" , @"%2C" ,
//                             @"%21", @"%27", @"%28", @"%29", @"%2A", nil];
//    
//    int len = [escapeChars count];
//    
//    NSMutableString *temp = [[self
//                              stringByAddingPercentEscapesUsingEncoding:stringEncoding]
//                             mutableCopy];
//    
//    int i;
//    for (i = 0; i < len; i++) {
//        
//        [temp replaceOccurrencesOfString:[escapeChars objectAtIndex:i]
//                              withString:[replaceChars objectAtIndex:i]
//                                 options:NSLiteralSearch
//                                   range:NSMakeRange(0, [temp length])];
//    }
//    
//    NSString *outStr = [NSString stringWithString: temp];
//    
//    return outStr;
//}

//字符串md5加密
- (NSString *)md5
{
    const char *original_str = [self UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(original_str, strlen(original_str), result);
    NSMutableString *hash = [NSMutableString string];
    for (int i = 0; i < 16; i++)
        [hash appendFormat:@"%02X", result[i]];
    
    return [hash lowercaseString];

}

@end

