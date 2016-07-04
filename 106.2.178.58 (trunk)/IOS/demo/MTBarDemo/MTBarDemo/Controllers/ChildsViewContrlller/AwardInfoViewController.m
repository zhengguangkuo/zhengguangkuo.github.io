//
//  AwardInfoViewController.m
//  WG_lottery(彩票)
//
//  Created by wg on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "AwardInfoViewController.h"
@interface AwardInfoViewController ()<UIActionSheetDelegate,UIImagePickerControllerDelegate>
{
    UIImageView *_head;
}
@end

@implementation AwardInfoViewController

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
	
    self.title = @"开奖信息";
    
    
    UIImageView *head = [[UIImageView alloc] initWithFrame:CGRectMake(230, 20, 80, 80)];
    head.backgroundColor = [UIColor grayColor];
    [self.view addSubview:head];
    _head = head;
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"修改头像" style:UIBarButtonItemStylePlain target:self action:@selector(clickPush)];

}
- (void)clickPush{

    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:@"拍照" otherButtonTitles:@"从相册中选取", nil];
    [actionSheet showInView:[UIApplication sharedApplication].windows[0]];
}
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch (buttonIndex) {
        case 0: {
            MTLog(@"相机获取");
            //创建UIImagePickerController组件对象
            UIImagePickerController *userPicker  =[[UIImagePickerController alloc] init];
            //设置委托
            userPicker.delegate = self;
            userPicker.allowsEditing = YES;       //对图片进行裁剪
            //从相机取出图片
            if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
                userPicker.sourceType = UIImagePickerControllerSourceTypeCamera;
                userPicker.delegate = self;
                [self presentViewController:userPicker animated:YES completion:nil];
            }
            break;
        }
        case 1: {
            MTLog(@"进入本地相册");
            UIImagePickerController *userPicker = [[UIImagePickerController alloc] init];
            userPicker.delegate = self;
            userPicker.editing = YES;
            //PhotoLibrary照片库模式
            if ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary]){
                //上面這一行主要是在判斷裝置是否支援此項功能
                userPicker.sourceType=UIImagePickerControllerSourceTypePhotoLibrary;
                [self presentViewController:userPicker animated:YES completion:nil];
            }
            break;
        }
        default:
            break;
    }
}
/*
 *  不包含上传至服务器、保存操作。
 */
#pragma mark   UIImagePickerControllerDelegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingImage:(UIImage *)image editingInfo:(NSDictionary *)editingInfo{
    //保存图片
    [self performSelector:@selector(saveImage:) withObject:image afterDelay:0.001];
    [picker dismissViewControllerAnimated:YES completion:nil];
}
//changeHeadImage
- (void)saveImage:(UIImage *)image
{
    _head.image = image;
}
@end
