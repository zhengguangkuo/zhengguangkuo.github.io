//
//  TTAndFriendsViewController.m
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAndFriendsViewController.h"
#import "TTSetCell.h"
#import "TTAdBookFriendsViewController.h"
#import "TTFriendsDetailViewController.h"
@interface TTAndFriendsViewController ()<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UIImageView *TTPhoneFrame;
@property (weak, nonatomic) IBOutlet UITextField *phoneNumber;
@property (weak, nonatomic) IBOutlet UITableView *tableView;
- (IBAction)searchPhone:(id)sender;

@end

@implementation TTAndFriendsViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
       
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
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"添加好友";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
}

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    NSString * tobeStr = [textField.text stringByReplacingCharactersInRange:range withString:string];
    
    if (tobeStr.length>11)
    {
        return NO;
    }
    
    return YES;
}


#pragma mark -tableViewdelegate And datasource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return 1;
}

#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    TTSetCell *cell = [TTSetCell SetCellWithTableView:tableView];
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [self.tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    //添加手机联系人
    TTAdBookFriendsViewController *adBookF = [[TTAdBookFriendsViewController alloc] init];
    [self.navigationController pushViewController:adBookF animated:YES];
}
//搜索手机号
- (IBAction)searchPhone:(id)sender {
    [self.view endEditing:YES];
    //判断手机号
    if (![self judgePhoneText]) {
        return;
    };
    TTLog(@"phoneNumber : %@",self.phoneNumber.text);
    TTFriendsDetailViewController *fDetail = [[TTFriendsDetailViewController alloc] init];
    fDetail.friendMobile = self.phoneNumber.text;
    [self.navigationController pushViewController:fDetail animated:YES];
}
- (BOOL)judgePhoneText
{
//    BOOL isPhoneText = YES;
    //判断手机号
    NSString * regexPhone = @"^((13[0-9])|(147)|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    if (self.phoneNumber.text==nil||[self.phoneNumber.text isEqualToString:@""]) {
        [SystemDialog alert:@"请输入手机号！\n"];
//        isPhoneText = NO;
        return NO;
    }
    
    if (![self.phoneNumber.text isMatchedByRegex:regexPhone]) {
        [SystemDialog alert:@"手机号格式不正确！\n"];
//        isPhoneText = NO;
        return NO;
    }
    return YES;//isPhoneText;
}


@end
