//
//  TTPwdPretectViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTPwdPretectViewController.h"
#import "TTPayPwdTableViewController.h"
#import "TTResetPayPwdViewController.h"

#define kFirstProblemBtn    1
#define kSecondProblemBtn   2
#define kThirdProblemBtn    3
@interface TTPwdPretectViewController ()
{
    UITableView *table;
    NSArray *arrayData;
    
}

- (void)savePwdProject;
@end

@implementation TTPwdPretectViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        [self queryMipauQuestion];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.view.backgroundColor = TTGlobalBg;
    [self addLeftView:self.firstAnswerTF];
    
    self.title = @"密保设置";
     self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc]initWithTitle:@"提交" style:UIBarButtonItemStylePlain target:self action:@selector(savePwdProject)];
    
    [self.commitBtn setTitle:(self.isFromSetPayPwd ? @"提交" : @"下一步") forState:UIControlStateNormal];
}

- (void)addLeftView:(UITextField*)textField
{
    UILabel *leftLab = [[UILabel alloc]initWithFrame:CGRectMake(0, 0, 10, 44)];
    textField.leftView = leftLab;
    textField.leftViewMode = UITextFieldViewModeAlways;
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


- (IBAction)firstProblemClicked:(id)sender {
    [self createTableView:sender];
    table.tag = kFirstProblemBtn;
    
    [self.firstAnswerTF resignFirstResponder];
}

- (IBAction)secondProblemClicked:(id)sender {
    [self createTableView:sender];
    table.tag = kSecondProblemBtn;
}

- (IBAction)thirdProblemClicked:(id)sender {
    [self createTableView:sender];
    table.tag = kThirdProblemBtn;
}

- (IBAction)commit:(id)sender {
//    if (self.isFromSetPayPwd) {
//        NSArray *VCViews = self.navigationController.viewControllers;
//        for (UIViewController *VC in VCViews) {
//            if ([VC isKindOfClass:[TTPayPwdTableViewController class]]) {
//                [(TTPayPwdTableViewController*)VC setArrayData:YES];
//                [self.navigationController popToViewController:VC animated:YES];
//                break;
//            }
//        }
//    } else {
//        TTResetPayPwdViewController *payVC = [[TTResetPayPwdViewController alloc]initWithNibName:@"TTResetPayPwdViewController" bundle:nil];
//        [self.navigationController pushViewController:payVC animated:YES];
//    }
    
}

- (void)savePwdProject
{
    [self addMipauRequest];
}

- (void)createTableView:(UIButton*)btn
{
    CGFloat top = btn.frame.origin.y+btn.frame.size.height;
    if (!table) {
        table = [[UITableView alloc]initWithFrame:CGRectMake(20, top, 280, 300) style:UITableViewStylePlain];
        table.dataSource = self;
        table.delegate = self;
        [self.view addSubview:table];
    } else {
        table.hidden = !table.hidden;
        [table setFrame:CGRectMake(20, top, 280, 300)];
    }
}


#pragma mark - UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return arrayData ? [arrayData count] : 0;
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *kIdentifier = @"Cell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
    if (!cell) {
        cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleDefault reuseIdentifier:kIdentifier];
    }
    NSDictionary *questionDic = [arrayData objectAtIndex:[indexPath row]];
    cell.textLabel.text = questionDic[@"miPauQuestion"];
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSDictionary *questionDic = [arrayData objectAtIndex:[indexPath row]];
    switch (table.tag) {
        case kFirstProblemBtn:
            [self.firstProblemBtn setTitle:questionDic[@"miPauQuestion"] forState:UIControlStateNormal];
            break;
        case kSecondProblemBtn:
            [self.secondProblemBtn setTitle:questionDic[@"miPauQuestion"] forState:UIControlStateNormal];
            break;
        case kThirdProblemBtn:
            [self.thirdProblemBtn setTitle:questionDic[@"miPauQuestion"] forState:UIControlStateNormal];
            break;
    }
    [table setHidden:YES];
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

- (BOOL)checkQA:(NSString *)question anwser:(NSString*)anwser
{
    if (!question || question.length == 0 || [question isEqualToString:@"请选择您的密保问题"]) {
        [SystemDialog alert:@"请选择密保问题"];
        return NO;
    }
    
    if (!anwser || anwser.length == 0) {
        [SystemDialog alert:@"请设置您的答案"];
        return NO;
    }
    return YES;
}

- (void)addMipauRequest
{
    NSString *question = self.firstProblemBtn.titleLabel.text;
    
    NSString *mobile = [TTAccountTool sharedTTAccountTool].currentAccount.userPhone;
    
    NSString *answer = self.firstAnswerTF.text;
    if (![self checkQA:question anwser:answer]) {
        return;
    }
    NSDictionary *jsonDic = @{@"Mobile":mobile,
                              @"Question":question,
                              @"Answer":answer,
                              @"sysPlat": @"5"};
    [self showLoading:YES];
    [TieTieTool tietieWitheEncodParameterMarked:TTAction_addMipau dict:jsonDic succes:^(id responseObject) {
        [self showLoading:NO];
        NSStringEncoding enc = CFStringConvertEncodingToNSStringEncoding(kCFStringEncodingGB_18030_2000);
        NSString *datas = [[NSString alloc] initWithData:responseObject encoding:enc];
        NSDictionary *d = [datas objectFromJSONString];
        TTLog(@"addMipau:%@",[d objectForKey:rspDesc]);
        if ([[d objectForKey:rspCode] isEqualToString:rspCode_success]) {
            [SystemDialog alert:@"密保设置成功"];
            [TTAccountTool sharedTTAccountTool].currentAccount.mipauFlag = @"0";
            self.arrayDataBlock();
            [self.navigationController popViewControllerAnimated:YES];
        }else{
            [SystemDialog alert:[responseObject objectForKey:rspDesc]];
        }
    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"addMipou: error");
    }];
}

//获取可选的密保问题
- (void)queryMipauQuestion
{
    NSDictionary *jsonDic = @{@"sysPlat": @"5"};
    [self showLoading:YES];
    [TieTieTool tietieWithParameterMarked:TTAction_queryMipauQuestion dict:jsonDic succes:^(id respondObject) {
        //{"miPauQuestionList":[{"queNo":"序号","miPauQuestion":"密保问题"},{"queNo":"序号","miPauQuestion":"密保问题"},{"queNo":"序号","miPauQuestion":"密保问题"}],"rspCode":"-100","rspDesc":"网络连接异常"}
        [self showLoading:NO];
        TTLog(@"=====queryMipauQuestion=====%@",respondObject[@"rspDesc"]);
        if ([[respondObject objectForKey:rspCode] isEqualToString:rspCode_success]) {
            NSArray *questions = respondObject[@"miPauQuestionList"];
            arrayData = questions;
        }

    } fail:^(NSError *error) {
        [self showLoading:NO];
        TTLog(@"%@",error);
    }];
}
@end
