//
//  TTSuggestionFeedBackViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTSuggestionFeedBackViewController.h"

@interface TTSuggestionFeedBackViewController ()

@end

@implementation TTSuggestionFeedBackViewController

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
    self.view.backgroundColor = TTGlobalBg;
    self.title =@"意见反馈";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithTitle:@"发送" style:UIBarButtonItemStylePlain target:self action:@selector(sendInfo)];
    
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

- (void)sendInfo
{
    NSString *feedbackText = self.suggestionTF.text;
    if (CNil(feedbackText)||IsEmptyString(feedbackText)) {
        [[SystemDialog alloc] makeToast:@"反馈意见不能为空！"];
        return;
    }
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    NSDictionary *jsonDic = @{@"mobile":mobile,@"content":feedbackText,@"sysPlat":@"5"};
    [self showLoading:YES];
    
    [TieTieTool tietieWitheEncodParameterMarked:TTAction_feedback dict:jsonDic succes:^(id respondObject) {
        [self showLoading:NO];
        NSStringEncoding enc = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
        NSString *datas = [[NSString alloc] initWithData:respondObject encoding:enc];
        NSDictionary *d = [datas objectFromJSONString];

        TTLog(@"%@",[d objectForKey:rspDesc]);
        if ([[d objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"感谢您的宝贵意见，我们会及时改进。"];
            [self.navigationController popViewControllerAnimated:YES];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}

- (IBAction)commit:(id)sender {
}

#pragma mark - UITextViewDelegate

@end
