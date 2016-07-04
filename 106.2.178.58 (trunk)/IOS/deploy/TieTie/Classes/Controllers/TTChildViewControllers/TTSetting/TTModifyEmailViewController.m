//
//  TTModifyEmailViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-18.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTModifyEmailViewController.h"
#import "NSString(Additions).h"
#import "AppDelegate.h"

@interface TTModifyEmailViewController ()

- (void)saveEmailInfo;
@end

@implementation TTModifyEmailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
    // Do any additional setup after loading the view from its nib.
//    UILabel *TitleLabel = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 50, 44)];
//    TitleLabel.font = [UIFont boldSystemFontOfSize:18];
//    TitleLabel.text = @"邮箱地址";
//    TitleLabel.textColor = [UIColor whiteColor];
//    TitleLabel.baselineAdjustment = UIBaselineAdjustmentAlignCenters;
//    self.navigationItem.titleView = TitleLabel;
    
    self.title = @"邮箱地址";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    UIButton * rightBarBTN = [[UIButton alloc]initWithFrame:CGRectMake(0, 0, 60, 20)];
    [rightBarBTN setTitle:@"下一步" forState:UIControlStateNormal];
    [rightBarBTN setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
    [rightBarBTN.titleLabel setFont:[UIFont systemFontOfSize:16]];
    [rightBarBTN addTarget:self action:@selector(saveEmailInfo) forControlEvents:UIControlEventTouchUpInside];
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithCustomView:rightBarBTN];
    
}

-(void)goBack
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)saveEmailInfo
{
//    if(IsEmptyString([self.theNewEmailAddressFT text])){
//        [self.view makeToast:@"电子邮箱不能为空!"];
//        return;
//    }else if(![NSString  checkEmail:[self.theNewEmailAddressFT text]]){
//        [self.view makeToast:@"请输入正确格式的电子邮箱!"];
//        return;
//    }
//    
//    NSString*  testURL =  [NSString stringWithFormat:@"%@%@",  WEB_SERVICE_ENV_VAR,Key_Secrity_UpdatePwd];
//    
//    AppDelegate* app = [AppDelegate getApp];
//    
//    NSDictionary* dic = [NSDictionary dictionaryWithObjectsAndKeys:
//                         app.userAccout.PassWord,@"originalpwd",
//                         app.userAccout.PassWord,@"newpwd",
//                         [self.theNewEmailAddressFT text],@"email",
//                         nil];
//    
//    NSLog(@"filed text = %@",[self.theNewEmailAddressFT text]);
//    
//    HttpService*  tempservice = [HttpService  HttpInitPostForm:testURL
//                                                          body:dic
//                                                       withHud:YES];
//    
//    [tempservice  setDataHandler:^(NSString* data)
//     {
//         NSLog(@"data = %@",data);
//         
//         
//     }
//     ];
//    [tempservice startOperation];

}

@end
