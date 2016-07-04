//
//  RootViewController.m
//  sqlitetest
//
//  Created by guorong on 14-1-2.
//  Copyright guorong 2014年. All rights reserved.
//

#import <Foundation/NSObject.h>
#import "RootViewController.h"
#import "MainNavigation.h"
#import "DBManager.h"
#import "NSObject(Dictionary).h"


@implementation testObject
@synthesize name = _name;
@synthesize age = _age;

-(id)init
{
    self = [super init];
    if(self)
    {
       _name = @"124";
        _age = 21;
    }
    return self;
}
@end



@interface RootViewController ()

-(void)SetLeftButton;

-(void)SetBackgroundImage;

@end






@implementation RootViewController

#define  FULL_SCREEN_Frame    [UIScreen mainScreen].applicationFrame

#define RGBCOLOR(r,g,b) [UIColor colorWithRed:(r)/255.0 green:(g)/255.0 blue:(b)/255.0 alpha:1]



- (void)viewDidLoad
{
    [super viewDidLoad];
    if([self.navigationController isKindOfClass:[MainNavigation class]])
    {
        [self SetBackgroundImage];
        [self SetLeftButton];
        DBManager*  tempDMG = [[DBManager alloc] init];
        [tempDMG createDataBase:@"test.db"];
//        [temp createTable];
//        [temp insertTable];
//        [temp deleteTable];
//        [temp updateTable];
        
        NSDictionary*  tableStruct = [[NSDictionary alloc] initWithObjectsAndKeys:@"varchar(70)",@"name",@"integer",@"age",nil];
        
        [tempDMG createTable:@"fire" sqldic:tableStruct];
//        NSArray* temparray = [tempDMG queryTable];
//        for(NSDictionary* temp in temparray)
//      {
//         NSLog(@"dic = %@",temp); 
//      }
    
    testObject*  tempobject = [[testObject alloc] init];
    NSDictionary* tempdic = [tempobject ConvertToDicionary];
    NSLog(@"tempdic = %@",tempdic);
    
    NSDictionary* tempdic2 = [[NSDictionary alloc] initWithObjectsAndKeys:@"string",@"name",[NSNumber numberWithInt:41],@"age",nil];
    testObject*  tempobject2 = [[testObject alloc] initWithDictionary:tempdic2];
    NSLog(@"tempobject name = %@",tempobject2.name);
    NSLog(@"tempobject age = %d",tempobject2.age);
    
    
    NSMutableArray*  temparray = [[NSMutableArray alloc]init];
    
    NSDictionary* tempdic3 = [[NSDictionary alloc] initWithObjectsAndKeys:@"string4",@"name",[NSNumber numberWithInt:21],@"age",nil];
    testObject*  tempobject3 = [[testObject alloc] initWithDictionary:tempdic3];
    [temparray addObject:tempobject3];
    
    
    NSDictionary* tempdic4 = [[NSDictionary alloc] initWithObjectsAndKeys:@"string6",@"name",[NSNumber numberWithInt:11],@"age",nil];
    testObject*  tempobject4 = [[testObject alloc] initWithDictionary:tempdic4];
    [temparray addObject:tempobject4];
        
     
        testObject*  tempobject21 = [[testObject alloc] init];
        [tempobject21 setName:@"string6"];
        [tempobject21 setAge:41];
        [temparray addObject:tempobject21];
   
        
    
        testObject* tempobject6 = [[testObject alloc] init];
        //[temparray addObject:tempobject6];
       
        
        [tempDMG insertTable:@"fire" object:tempobject6];
 //       [tempDMG insertTable:@"fire" array:temparray];
    
        
        
        testObject* tempobject0 = [[testObject alloc] init];
        [tempobject0 setAge:101];
        [tempobject0 setName:@"string5"];
        
        [tempDMG insertTable:@"fire" object:tempobject0];
        [tempDMG insertTable:@"fire" array:temparray];

        
        testObject*  tempobject10 = [[testObject alloc]init];
        [tempobject10 setAge:1000];
        [tempobject10 setName:@"vampires"];
        
        [tempDMG updateTable:@"fire" where:[NSDictionary dictionaryWithObjectsAndKeys:@"string5",@"name", nil] object:tempobject10];
        
        [tempDMG deleteTable:@"fire" where:[NSDictionary dictionaryWithObjectsAndKeys:@"string4",@"name",nil]];
        
        
         NSArray* testArray = [tempDMG queryTable:@"fire" where:[NSDictionary dictionaryWithObjectsAndKeys:@"string6",@"name",nil]];
        
        
          for(id temp1 in testArray)
         {
             testObject* tempobject = [[testObject alloc] initWithDictionary:(NSDictionary*)temp1];
             NSLog(@"this name = %@",tempobject.name);
             NSLog(@"this age = %d",tempobject.age);
         }
        
        
        
        
        
        
    
    }
}


-(void)SetNaviationTitleName:(NSString*)str
{
    if([self.navigationController isKindOfClass:[MainNavigation class]])
    {
        MainNavigation* navigation = (MainNavigation*)self.navigationController;
        [navigation NavigationTitle:str];
    }
}


-(void)SetNaviationRightButtons:(NSArray*)buttons
{
    if([self.navigationController isKindOfClass:[MainNavigation class]])
    {
        MainNavigation* navigation = (MainNavigation*)self.navigationController;
        [navigation NavigationViewRightBtns:buttons];
    }
}


-(void)SetLeftButton
{
    UIButton*  barbutton = nil;
    if([self.navigationController isKindOfClass:[MainNavigation class]])
{
    MainNavigation* navigation = (MainNavigation*)self.navigationController;
     if(navigation.topViewController!=self)
    {
       barbutton=[UIButton buttonWithType:UIButtonTypeCustom];
       
       [barbutton setFrame:CGRectMake(10, 0, 60, 36)];
        UIImage*  iconImage = [UIImage imageNamed:@"top_bt_bg"];
       [barbutton setBackgroundImage:iconImage forState:UIControlStateNormal];
       [barbutton setBackgroundImage:iconImage forState:UIControlStateHighlighted];

       UIImage*  iconImage2 = [[UIImage imageNamed:@"gb_button"] stretchableImageWithLeftCapWidth:1 topCapHeight:1];
       [barbutton setImage:iconImage2 forState:UIControlStateNormal];
       [barbutton setImage:iconImage2 forState:UIControlStateHighlighted];
    }
    [navigation NavigationViewBackBtn:barbutton];
}
}

-(void)SetBackgroundImage
{
    if([self.navigationController isKindOfClass:[MainNavigation class]])
    {
        MainNavigation* navigation = (MainNavigation*)self.navigationController;
        [navigation NavigationBgImage:@"main_top_bg"];
    }
}
    
    

- (void)viewDidUnload
{
    [super viewDidUnload];
}


-(void)viewDidAppear:(BOOL)animated
{
    [super viewWillAppear:NO];
}


-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:NO];
}


@end
