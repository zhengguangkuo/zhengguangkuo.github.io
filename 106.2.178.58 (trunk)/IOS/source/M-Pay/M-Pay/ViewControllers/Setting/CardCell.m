//
//  ImageTableViewCell.m
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import "CardCell.h"
#import "NSObject(UITextFieldDelegate).h"
#import "BasicCard.h"
#import "UIImage(addition).h"
#import "UIImageView+DispatchLoad.h"
#import "UICustAlertView.h"
#import "UIView(category).h"
#import "HttpService.h"
#import "Config.h"
#import "SystemDialog+Show.h"
#import "NSDictionary(JSON).h"
#import "RespInfo.h"
#import "UIAlertView(category).h"




@class RootViewController;

@interface CardCell()


@property  (nonatomic, strong) BasicCard*  basic_object;

@end



@implementation CardCell
@synthesize ImageView;
@synthesize NameLabel;
@synthesize NumLabel;
@synthesize LockButton;
@synthesize Line;
@synthesize basic_object;

enum{
    BasicCardBind,
    BasicCardUnbind
};


-(id)initCustom
{
    self = [super init];
    if(self)
    {
        self = [[[NSBundle mainBundle] loadNibNamed:@"CardCell" owner:self options:nil] lastObject];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
       UIImage*  tempImage = [UIImage UIImageScretchImage:@"bt_bg"];
        [self.LockButton  setBackgroundImage:tempImage forState:UIControlStateNormal];
        [self.LockButton addTarget:self action:@selector(ClickEvent) forControlEvents:UIControlEventTouchUpInside];
        [self setBackgroundColor:[UIColor clearColor]];
        [self.contentView setBackgroundColor:[UIColor clearColor]];
    }
    return self;
}


-(void)setBasicCard:(BasicCard*)object
{
    self.basic_object = object;
    
    [NameLabel setText:@""];
    [NumLabel setText:@""];
    [ImageView setImage:nil];
    
    if(![object.card_name isKindOfClass:[NSNull class]])
        [NameLabel setText:object.card_name];

    if(![object.card_no isKindOfClass:[NSNull class]])    [NumLabel  setText:object.card_no];
    
    if(![object.pic_path isKindOfClass:[NSNull class]])
    [ImageView setImageFromUrl:object.pic_path];

    [self.LockButton setTitle:@"" forState:UIControlStateNormal];
    
    if([object.bind_flag isEqualToString:@"0"])
    {
        [self.LockButton setTitle:@"绑定" forState:UIControlStateNormal];
        self.NumLabel.hidden = TRUE;
    }
    else
    if([object.bind_flag isEqualToString:@"1"])
    {
        [self.LockButton setTitle:@"解锁" forState:UIControlStateNormal];
        self.NumLabel.hidden = FALSE;
    }

}


-(void)ClickEvent
{
   NSUserDefaults  *ud = [NSUserDefaults standardUserDefaults];
   BOOL flag = [ud boolForKey:@"ValideKey"];
   
   if(flag==YES)
{
       if([self.LockButton.titleLabel.text
         isEqualToString:@"绑定"])
  {
      [self drawinputBox];
  }
     else
  {
      [self UnBind];
  }
}
    else
      [UIAlertView DrawValidBox];


}




-(void)drawinputBox
{
    UIView* bgview = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 300, 38)];
    [bgview setBackgroundColor:[UIColor whiteColor]];
    
    
    UITextField* input = [[UITextField alloc] initWithFrame:CGRectMake(5, 4, 289, 34)];
    
    input.keyboardType = UIKeyboardTypeDefault;
    input.returnKeyType = UIReturnKeyDefault;
    
    [input setFont:[UIFont systemFontOfSize:15.0f]];

    [input setDelegate:self];
    
    [input ViewWithBorder:[UIColor orangeColor]];
    
    [bgview addSubview:input];
    
    
    dispatch_async(dispatch_get_main_queue(),
   ^{
        UICustAlertView*  alertview = [[UICustAlertView alloc]initWithAlertContent:bgview title:self.basic_object.input_tip];
        [alertview setYesBlock:^{
           [self BindCard:[input text]];
        }];
    });
}



-(void)BindCard:(NSString*)card
{
     if(![basic_object.bind_flag isEqualToString:@"1"])
   {
      [self requestBindingBasicCard:card];
   }
}


-(void)UnBind
{
     if([basic_object.bind_flag isEqualToString:@"1"])
   {
      [self requestBindingBasicCard:self.basic_object.card_no];
   }
}


-(void)requestBindingBasicCard:(NSString*)cardNumber
{
    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_Basic_Card_Binding];
    NSLog(@"testURL = %@",testURL);
    
       NSString* actionName = nil;
       NSInteger activeID;

       if([basic_object.bind_flag isEqualToString:@"1"]) //已绑定
    {
       actionName = @"unBind";
       activeID = BasicCardUnbind;
    }
       else
    {
       actionName = @"bind";
       activeID = BasicCardBind;
    }
    
    NSDictionary*  dic = [NSDictionary dictionaryWithObjectsAndKeys:
            cardNumber,@"card_no",
            basic_object.card_type,@"card_type",
            actionName,@"action",
        
        nil];
    
    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
        body:dic
        withHud:NO];
    
    [tempservice setActiveMethodID:activeID];
    
    [tempservice  setReceiveHandler:^(NSString* data, NSInteger activateID)
     {
         NSLog(@"data = %@",data);
         NSLog(@"activateID = %d",activateID);
         id object = [NSDictionary dictionaryWithString:data];
         if(![object isKindOfClass:[NSNull class]])
       {
          NSDictionary* dic = (NSDictionary*)object;
           RespInfo* respObject = [[RespInfo alloc] initWithDictionary:dic];
           if([respObject.respcode isEqualToString:@"0"])
           {
              if(activateID==BasicCardBind)
              {
                [self.basic_object setBind_flag:@"1"];
                [self.basic_object setCard_no:cardNumber];
              }
              else
              {
                [self.basic_object setBind_flag:@"0"];
                [self.basic_object setCard_no:NULL];
              }
               [self setBasicCard:self.basic_object];
           }
        }
         
         
         
         
         
     }
     ];
    
    [tempservice setErrorHandler:^(NSError* error)
    {
        dispatch_async(dispatch_get_main_queue(),
      ^{
          [self makeToast:@"网络状况不佳"];
       });
     }];
    
    [tempservice startOperation];
}



@end
