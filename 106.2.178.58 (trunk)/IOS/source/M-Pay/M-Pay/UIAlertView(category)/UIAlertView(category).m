#import "UIAlertView(category).h"
#import "UIButton(addtion).h"
#import "UIView(category).h"
#import "UIImage(addition).h"
#import "UICustAlertView.h"
#import "Config.h"
#import "HttpService.h"
#import "NSObject(UITextFieldDelegate).h"


@implementation UIAlertView(category)

+(void)DrawValidBox
{
    NSUserDefaults  *ud= [NSUserDefaults standardUserDefaults];
    
    BOOL flag = [ud boolForKey:@"ValideKey"];
    
    if(flag==NO)
  {
    UIAlertView* alert = [[UIAlertView alloc] init];
    [alert initWithAlert];
  }
}


- (void)initWithAlert
{
     UIView* bgview = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 300, 38)];
     [bgview setBackgroundColor:[UIColor whiteColor]];
     
     UITextField* input = [[UITextField alloc] initWithFrame:CGRectMake(2, 5, bgview.frame.size.width/2 + 7, 32)];
     input.keyboardType = UIKeyboardTypeDefault;
     input.returnKeyType = UIReturnKeyDefault;
     [input setDelegate:self];
     [input setFont:[UIFont systemFontOfSize:15.0f]];
     [input  ViewWithBorder:[UIColor orangeColor]];
     [bgview addSubview:input];
     
     UIButton*  pickButton = [UIButton ButtonWithParms:[UIColor blackColor] title:@"获取" bgnormal:[UIImage generateFromColor:[UIColor colorWithRed:0.9 green:0.9 blue:0.9 alpha:1]] imgHighlight:nil target:self action:@selector(RequestGetValid)];
     [pickButton setFrame:CGRectMake(input.frame.origin.x + input.frame.size.width + 3, 5, bgview.frame.size.width - input.frame.origin.x - input.frame.size.width - 5 , 32)];
     [pickButton ViewWithBorder:[UIColor lightGrayColor]];
     [bgview addSubview:pickButton];
     
     dispatch_async(dispatch_get_main_queue(),
     ^{
        UICustAlertView*  alertview = [[UICustAlertView alloc]initWithAlertContent:bgview title:@"请输入验证码"];
        [alertview setYesBlock:^{
        [self RequestCheckValid:[input text]];
        }];
      });

}


-(void)RequestGetValid
{
    NSLog(@"get code");
    NSString*  validCodeURL =  [NSString stringWithFormat:@"%@mpayFront/getCode",  WEB_SERVICE_ENV_VAR];
    
    NSDictionary*  dic = [[NSDictionary alloc] initWithObjectsAndKeys:@"13910303432",@"mobile", nil];
    
    HttpService*  tempservice = [HttpService HttpInitPostForm:validCodeURL
                                                         body:dic
                                                      withHud:NO];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"response getcode str = %@",data);
         
     }
     ];
    [tempservice setErrorHandler:^(NSError* error) {
        
    }];
    
    [tempservice startOperation];
}




-(void)RequestCheckValid:(NSString*)ValidCode
{
    NSString*  validCodeURL =  [NSString stringWithFormat:@"%@mpayFront/checkValidateCode",  WEB_SERVICE_ENV_VAR];
    
    NSDictionary*  dic = [[NSDictionary alloc] initWithObjectsAndKeys:ValidCode,@"validateCode", nil];
    
    HttpService*  tempservice = [HttpService HttpInitPostForm:validCodeURL
                                                         body:dic
                                                      withHud:NO];
    
    [tempservice  setDataHandler:^(NSString* data)
     {
         NSLog(@"response checkValicode str = %@",data);
         NSUserDefaults  *ud= [NSUserDefaults standardUserDefaults];
         [ud setBool:YES forKey:@"ValideKey"];
     }
     ];
    [tempservice setErrorHandler:^(NSError* error) {
        
    }];
    
    [tempservice startOperation];
}

@end
