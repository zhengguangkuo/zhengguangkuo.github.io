//
//  TTModifyInfoViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTModifyInfoViewController.h"
#import "TTModifyPwdViewController.h"
#import "TTModifyPhoneNumViewController.h"
#import "TTModifyEmailViewController.h"
#import "TTViewHeaderTableViewCell.h"
#import "TTXMPPTool.h"
#import "TTEditUserInfoViewController.h"
#import "NSString(Additions).h"

#define kModifyHeaderImage  0
#define kModifyLoginPwd     1
#define kModifyPhoneNumber  2
#define kModifyEmailAddress 3
@interface TTModifyInfoViewController ()
{
    NSArray *arrayData;
    NSMutableArray *mutableArrayData;
    
    NSIndexPath *currentIndexPath;
    XMPPvCardTemp* myvCardTemp;
    UIImage *imageHeader;
}
- (void)updateMyvCardTempAfternotification;
- (void)updateMyvCardTempWtihMyvCard;

- (void)updateUserInfoPlist;
@end

@implementation TTModifyInfoViewController

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
    // Do any additional setup after loading the view from its nib.
    self.table.backgroundColor = TTGlobalBg;
    self.title = @"个人信息";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(updateUserInfoPlist)];
    
    [self setArrayDataFromPlist];
//    [self loadMyvCardTemp];
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

- (void)updateMyvCardTempAfternotification
{
    [self loadMyvCardTemp];
    [self.table reloadData];
    
//    NSIndexPath *indextPath = [NSIndexPath indexPathForRow:0 inSection:0];
//    [self.table reloadRowsAtIndexPaths:[NSArray arrayWithObject:indextPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}

- (void)setArrayDataFromMMyvCardTemp:(XMPPvCardTemp*)vCard
{
    UIImage *photo = [UIImage imageWithData:vCard.photo] ? [UIImage imageWithData:vCard.photo] : [UIImage imageNamed:@"Icon-72.png"];
    NSString *name = vCard.name ? vCard.name : @"";
    NSString *phone = vCard.note ? vCard.note : @"";
    NSString *email = vCard.mailer ? vCard.mailer : @"";
    NSString *title = vCard.title ? vCard.title : @"";
    NSString *orgName = vCard.orgName ? vCard.orgName : @"";
    
    NSMutableArray *arraySection01 = [[NSMutableArray alloc]initWithObjects:photo, nil];

    NSMutableArray *arraySection02 = [[NSMutableArray alloc]initWithObjects:name,phone,phone,
                             email,title,orgName, nil];
    
    arrayData = @[@[@"头像"],@[@"姓名",@"手机",@"宅电",@"邮箱",@"职位",@"公司"]];
    mutableArrayData = [[NSMutableArray alloc] initWithObjects:arraySection01,arraySection02, nil];
}

- (void)loadMyvCardTemp
{
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    myvCardTemp = [xmppTool.xmppvCardTempModule myvCardTemp];
    [self setArrayDataFromMMyvCardTemp:myvCardTemp];
}

- (void)setArrayDataFromPlist
{
    arrayData = @[@[@"头像",@"姓名",@"昵称"],@[@"手机",@"宅电",@"邮箱"],@[@"职务",@"公司"]];
    NSMutableDictionary *data = [self loadInfoFromPlist];
    
    NSData *imageData = data[@"image"];
    UIImage *image = [UIImage imageWithData:imageData] ? [UIImage imageWithData:imageData] : [UIImage imageNamed:@"Icon-72.png"];
    NSString *name = data[@"name"] ? data[@"name"] : @"";
    NSString *nickName = data[@"nickName"] ? data[@"nickName"] : @"";
    
    NSString *phone = data[@"phone"] ? data[@"phone"] : @"";
    NSString *tel = data[@"tel"] ? data[@"tel"] : @"";
    NSString *mail = data[@"mail"] ? data[@"mail"] : @"";
    
    NSString *title = data[@"title"] ? data[@"title"] : @"";
    NSString *company = data[@"company"] ? data[@"company"] : @"";

    NSMutableArray *sectionFirst = [[NSMutableArray alloc]initWithObjects:image,name,nickName, nil];
    NSMutableArray *sectionSecond = [[NSMutableArray alloc]initWithObjects:phone,tel,mail, nil];
    NSMutableArray *sectionThird = [[NSMutableArray alloc]initWithObjects:title,company, nil];
    
    mutableArrayData = [[NSMutableArray alloc] initWithObjects:sectionFirst,sectionSecond,sectionThird, nil];
}

- (NSMutableDictionary *)loadInfoFromPlist
{
    NSArray *paths=NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *plistPath = [[paths objectAtIndex:0] stringByAppendingPathComponent:TTUserInfoPlist];
    NSMutableDictionary *data = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath];
    NSMutableDictionary *info = [data objectForKey:[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId];
    return info;
}

- (void)updateUserInfoPlist
{
    NSArray *paths=NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *plistPath = [[paths objectAtIndex:0] stringByAppendingPathComponent:TTUserInfoPlist];
    
    NSMutableDictionary *data = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath];
    if (!data) {
        data = [[NSMutableDictionary alloc]init];
    }
    NSMutableDictionary *info = [data objectForKey:[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId];
    if (!info) {
        info = [[NSMutableDictionary alloc]init];
    }
    
    NSData *imageData = UIImagePNGRepresentation(mutableArrayData[0][0]);
    NSString *name = mutableArrayData[0][1];
    NSString *nickName = mutableArrayData[0][2];
    
    NSString *phone = mutableArrayData[1][0];
    NSString *tel = mutableArrayData[1][1];
    NSString *mail = mutableArrayData[1][2];
    
    NSString *title = mutableArrayData[2][0];
    NSString *company = mutableArrayData[2][1];
    
    [info setObject:imageData forKey:@"image"];
    [info setObject:name forKey:@"name"];
    [info setObject:nickName forKey:@"nickName"];
    
    [info setObject:phone forKey:@"phone"];
    [info setObject:tel forKey:@"tel"];
    [info setObject:mail forKey:@"mail"];
    
    [info setObject:title forKey:@"title"];
    [info setObject:company forKey:@"company"];
    
    if (phone == nil || [phone isEqualToString:@""]||name == nil || [name isEqualToString:@""]) {
        [SystemDialog alert:@"手机号或名称不能为空!"];
        return;
    }
    
    [data setObject:info forKey:[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId];
    
    [data writeToFile:plistPath atomically:YES];
    
    [SystemDialog alert:@"已经更新个人名片"];
}

#pragma mark - UITableDataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return [arrayData count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [[arrayData objectAtIndex:section] count];
}

- (float)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([indexPath section] == 0 && [indexPath row] == 0) {
        return 85;
    } else {
        return 44;
    }
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([indexPath section]==0 && [indexPath row] == 0) {
        TTViewHeaderTableViewCell *cell = [[[NSBundle mainBundle] loadNibNamed:@"TTViewHeaderTableViewCell" owner:self options:nil] lastObject];
        
        cell.headerViewImg.image = [[mutableArrayData objectAtIndex:[indexPath section]] objectAtIndex:[indexPath row]];
//        if (myvCardTemp) {
//            UIImage *image = [[UIImage alloc]initWithData:myvCardTemp.photo];
//            [cell.headerViewImg setImage:image];
//            [[NSNotificationCenter defaultCenter] removeObserver:self name:KNOTIFICATIONCENTER_UDATEMYVCARDTEMP object:nil];
//        } else {
//            [cell.headerViewImg setImage:[UIImage imageNamed:@"Icon-72.png"]];
//            [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateMyvCardTempAfternotification) name:KNOTIFICATIONCENTER_UDATEMYVCARDTEMP object:nil];
//        }
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        return cell;

    } else {
        static NSString *kIdentifier = @"Cell";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kIdentifier];
        if (!cell) {
            cell = [[UITableViewCell alloc]initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:kIdentifier];
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }

        cell.textLabel.text = [[arrayData objectAtIndex:[indexPath section]] objectAtIndex:[indexPath row]];
        cell.detailTextLabel.text = [[mutableArrayData objectAtIndex:[indexPath section]] objectAtIndex:[indexPath row]];
        
        return cell;
    }
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger section = [indexPath section];
    NSInteger row = [indexPath row];
    currentIndexPath = indexPath;
    if (section == 0 && row == 0) {
        [self updateHeaderImage];
    } else {
        TTEditUserInfoViewController *editVC = [[TTEditUserInfoViewController alloc]init];
        UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
        editVC.contentLable = cell.detailTextLabel;
        editVC.title = arrayData[section][row];//[[arrayData objectAtIndex:section] objectAtIndex:row];
        editVC.block = ^(NSString *value){
            NSMutableArray *arr = [mutableArrayData objectAtIndex:[indexPath section]];
            [arr setObject:value atIndexedSubscript:[indexPath row]];
            [tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
        };
        [self.navigationController pushViewController:editVC animated:YES];
    }
    
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 15;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return 1;
}

- (void)updateHeaderImage
{
    UIActionSheet *choosePhotoActionSheet;
    
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        choosePhotoActionSheet = [[UIActionSheet alloc] initWithTitle:@"选择照片"
                                                             delegate:self
                                                    cancelButtonTitle:@"取消"
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:@"拍照",@"从手机相册中选择", nil];
    } else {
        choosePhotoActionSheet = [[UIActionSheet alloc] initWithTitle:@"选择照片"
                                                             delegate:self
                                                    cancelButtonTitle:@"取消"
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:@"从手机相册中选择", nil];
    }
    
    [choosePhotoActionSheet showInView:self.view];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
	NSUInteger sourceType = 0;
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        switch (buttonIndex) {
            case 0:
                sourceType = UIImagePickerControllerSourceTypeCamera;
                break;
            case 1:
                sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
                break;
            case 2:
                return;
        }
    } else {
        if (buttonIndex == 1) {
            return;
        } else {
            sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
        }
    }

	UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
	imagePickerController.delegate = self;
	imagePickerController.allowsEditing = YES;
    imagePickerController.sourceType = sourceType;
	[self presentViewController:imagePickerController animated:YES completion:nil];
}

#pragma mark - UIImagePickerControllerDelegate

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
	[picker dismissViewControllerAnimated:YES completion:nil];
	UIImage *image = [info objectForKey:UIImagePickerControllerEditedImage];
    
    TTViewHeaderTableViewCell *cell = (TTViewHeaderTableViewCell*)[self.table cellForRowAtIndexPath:currentIndexPath];
    [cell.headerViewImg setImage:image];
//    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
//    [xmppTool updateCardPhoto:image];
    self.block_setImage(image);
    
    NSMutableArray *arr = [mutableArrayData objectAtIndex:0];
    [arr setObject:image atIndexedSubscript:0];
}


- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
	[self dismissViewControllerAnimated:YES completion:nil];
}

- (void)updateMyvCardTempWtihMyvCard
{
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    myvCardTemp = [xmppTool.xmppvCardTempModule myvCardTemp];
    
    if(!myvCardTemp)
    {
        //新建电子名片。
        myvCardTemp = [XMPPvCardTemp vCardTemp];
    }
    //设置头像
    UIImage *image = [[mutableArrayData firstObject] firstObject];
    myvCardTemp.photo = UIImagePNGRepresentation(image);
    myvCardTemp.name = [[mutableArrayData lastObject] objectAtIndex:0];
    myvCardTemp.note = [[mutableArrayData lastObject] objectAtIndex:1];
    myvCardTemp.mailer = [[mutableArrayData lastObject] objectAtIndex:3];
    myvCardTemp.title = [[mutableArrayData lastObject] objectAtIndex:4];
    myvCardTemp.orgName = [[mutableArrayData lastObject] objectAtIndex:5];
    
    if (!myvCardTemp.jid) {
        myvCardTemp.jid = xmppTool.xmppStream.myJID;
    }
    
    [xmppTool.xmppvCardTempModule updateMyvCardTemp:myvCardTemp];

}

@end
