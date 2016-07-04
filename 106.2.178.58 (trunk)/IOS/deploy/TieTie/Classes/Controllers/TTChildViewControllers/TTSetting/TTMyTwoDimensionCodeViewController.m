//
//  TTMyTwoDimensionCodeViewController.m
//  Miteno
//
//  Created by wg on 14-8-11.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTMyTwoDimensionCodeViewController.h"
#import "QRCodeGenerator.h"
#import "TTMyQRView.h"
#import <AddressBook/AddressBook.h>
#import <AddressBookUI/AddressBookUI.h>
#import "TTUserCard.h"

@interface TTMyTwoDimensionCodeViewController ()
{
    TTUserCard  *   _userCard;
}
- (IBAction)generateQRCode:(id)sender;
@property (weak, nonatomic) IBOutlet UILabel *textMeg;
@property (weak, nonatomic) IBOutlet UIButton *qrBtn;
@property (weak, nonatomic) IBOutlet UIButton *qrImage;
@end

@implementation TTMyTwoDimensionCodeViewController

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
    
    
    self.title = @"二维码名片";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];

    
    
    //获取个人名片
    [self getUserCard];
    
    
    if ([[TTSettingTool objectForKey:TTQrCode] integerValue] == 1) {
        [self generateQRCode:@""];
    }
}
- (NSDictionary *)getUserMeg
{
    NSArray *paths=NSSearchPathForDirectoriesInDomains(NSDocumentDirectory,NSUserDomainMask,YES);
    NSString *plistPath = [[paths objectAtIndex:0] stringByAppendingPathComponent:TTUserInfoPlist];
    NSMutableDictionary *data = [[NSMutableDictionary alloc] initWithContentsOfFile:plistPath];
    NSMutableDictionary *info = [data objectForKey:[TTAccountTool sharedTTAccountTool].currentAccount.nowUserId];
       TTLog(@"当前名片手机 : %@",[info objectForKey:@"phone"]);
    return info;
}
- (void)getUserCard
{
    NSDictionary *dict = [self getUserMeg];
    //获取个人名片信息
    if (!dict) {
        return;
    }
    _userCard = [[TTUserCard alloc] initWithDict:dict];
    
    [self addImageView];
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

- (IBAction)generateQRCode:(id)sender {


    if (![self getUserMeg]) {
        [SystemDialog alert:@"请您先完善个人信息！"];
        return ;
    }else{
        [self.textMeg removeFromSuperview];
        [self.qrBtn removeFromSuperview];
        [self.qrImage removeFromSuperview];
        [self addImageView];
    }
}

- (void)addImageView
{
    TTMyQRView *qrView =[TTMyQRView qrcodeView];
    qrView.center = CGPointMake(ScreenWidth/2,ScreenHeight/2-44);
    UIImage *photo = [UIImage imageWithData:_userCard.image];
    qrView.userPhone.text = [NSString stringWithFormat:@"手机:%@",_userCard.phone];
    qrView.nickName.text = [NSString stringWithFormat:@"%@",_userCard.name];
    qrView.userIcon.image = photo;
    
    if (_userCard == nil) {
        [SystemDialog alert:@"请您先完善个人信息！"];
        return ;
    }
    NSString *vcard = [self generateVCardStringWithContacts:[self createPerson]];
    qrView.qrImg.image = [self qrImage:vcard];
    
    UIImageView *imageView = [[UIImageView alloc]init];
    imageView.center = CGPointMake(qrView.size.width/2,qrView.size.height/2);
    imageView.bounds = CGRectMake(0, 0, 30, 30);
    imageView.image = photo;
    [qrView addSubview:imageView];
    
    [TTSettingTool setInteger:1 forKey:TTQrCode];
    [self.view addSubview:qrView];
    
    
}
//创建个人信息
- (ABRecordRef)createPerson
{

    ABRecordRef tmpRecord = ABPersonCreate();
    CFErrorRef error;
    BOOL tmpSuccess = NO;

    //昵称
    CFStringRef tmpNickname = CFBridgingRetain(_userCard.nickName);
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonNicknameProperty, tmpNickname, &error);
    CFRelease(tmpNickname);
    //姓
    CFStringRef tmpFirstName = CFBridgingRetain(_userCard.name);
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonFirstNameProperty, tmpFirstName, &error);
    CFRelease(tmpFirstName);
    //名
    CFStringRef tmpLastName = CFBridgingRetain(@"");;
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonLastNameProperty, tmpLastName, &error);
    CFRelease(tmpLastName);
//    CFStringRef tmpLastName = CFSTR("13691322323");;
//    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonLastNameProperty, tmpLastName, &error);
//    CFRelease(tmpLastName);

    
    //手机\ 电话
    CFTypeRef tmpHomePhones = CFBridgingRetain(_userCard.tel);
    CFTypeRef tmpPhones = CFBridgingRetain(_userCard.phone);
    ABMutableMultiValueRef tmpMutableMultiPhones = ABMultiValueCreateMutable(kABPersonPhoneProperty);
    ABMultiValueAddValueAndLabel(tmpMutableMultiPhones, tmpHomePhones, kABPersonPhoneMainLabel, NULL);
    ABMultiValueAddValueAndLabel(tmpMutableMultiPhones, tmpPhones, kABPersonPhoneMobileLabel, NULL);
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonPhoneProperty, tmpMutableMultiPhones, &error);
    CFRelease(tmpMutableMultiPhones);

    //头像
    ABPersonSetImageData(tmpRecord, CFBridgingRetain(_userCard.image), &error);
    
//    CFTypeRef tmpPhoto = CFBridgingRetain(_userCard.image);
//    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonImageFormatThumbnail, tmpPhoto, &error);
//    ABMutableMultiValueRef tmpMutableMultiPotho = ABMultiValueCreateMutable(kABPersonImageFormatOriginalSize);

    
    
    //邮件
    CFTypeRef tmpEmail = CFBridgingRetain(_userCard.mail);
    ABMutableMultiValueRef tmpMutableMultmail = ABMultiValueCreateMutable(kABPersonEmailProperty);
    ABMultiValueAddValueAndLabel(tmpMutableMultmail, tmpEmail, kABPersonPhoneMobileLabel, NULL);
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonEmailProperty, tmpMutableMultmail, &error);
    CFRelease(tmpMutableMultmail);
    
    //职务
    CFTypeRef tmpjoptitle = CFBridgingRetain(_userCard.title);
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonJobTitleProperty, tmpjoptitle, &error);
    CFRelease(tmpjoptitle);
    
    //公司
    CFTypeRef tmpcompanys = CFBridgingRetain(_userCard.company);
    tmpSuccess = ABRecordSetValue(tmpRecord, kABPersonOrganizationProperty, tmpcompanys, &error);
    CFRelease(tmpcompanys);

    return tmpRecord;
    
}
- (UIImage *)qrImage:(NSString *)vcard
{
    UIImage *img = [QRCodeGenerator qrImageForString:vcard imageSize:200];
    return img;
}
-(NSString*)generateVCardStringWithContacts:(ABRecordRef)contacts {
    NSInteger counter  = 0;
    NSString *vcard = @"";
    
//    for(CFIndex i = 0; i < CFArrayGetCount(contacts); i++) {
    
//        ABRecordRef person = CFArrayGetValueAtIndex(contacts, i);
    ABRecordRef person = contacts;
        NSString *firstName = (NSString*)CFBridgingRelease(ABRecordCopyValue(person, kABPersonFirstNameProperty));
        firstName = (firstName ? firstName : @"");
        NSString *lastName = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonLastNameProperty));
        lastName = (lastName ? lastName : @"");
        NSString *middleName = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonMiddleNameProperty));
        NSString *prefix = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonPrefixProperty));
        NSString *suffix = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonSuffixProperty));
        NSString *nickName = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonNicknameProperty));
        NSString *firstNamePhonetic = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonFirstNamePhoneticProperty));
        NSString *lastNamePhonetic = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonLastNamePhoneticProperty));
        
        NSString *organization = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonOrganizationProperty));
        NSString *jobTitle = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonJobTitleProperty));
        NSString *department = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonDepartmentProperty));
    
//        NSString *photo = (NSString *)CFBridgingRelease(ABRecordCopyValue(person, kABPersonImageFormatThumbnail));
    
//        NSString *compositeName = [NSString stringWithFormat:@"%@%@",firstName,lastName];

    
        vcard = [vcard stringByAppendingFormat:@"BEGIN:VCARD\nVERSION:3.0\nN:%@;%@;%@;%@;%@\n",
                 (firstName ? firstName : @""),
                 (lastName ? lastName : @""),
                 (middleName ? middleName : @""),
                 (prefix ? prefix : @""),
                 (suffix ? suffix : @"")
                 ];
        
//        vcard = [vcard stringByAppendingFormat:@"FN:%@\n",compositeName];
        if(nickName) vcard = [vcard stringByAppendingFormat:@"NICKNAME:%@\n",nickName];
        if(firstNamePhonetic) vcard = [vcard stringByAppendingFormat:@"X-PHONETIC-FIRST-NAME:%@\n",firstNamePhonetic];        if(lastNamePhonetic) vcard = [vcard stringByAppendingFormat:@"X-PHONETIC-LAST-NAME:%@\n",lastNamePhonetic];
    
        //photo
//    NSData
//    NSString *photos = [[NSString alloc] initWithData:(NSData *)_userCard.image encoding:NSUTF8StringEncoding];
//    UIImage *image = [UIImage imageNamed:@"about_phone_icon@2x.png"];
//    NSData *data = UIImagePNGRepresentation(image);
////    NSString *photos = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
//    data = [GTMBase64 encodeData:data];
//    vcard = [vcard stringByAppendingFormat:@"LOGO;ENCODING=b;TYPE=JPEG:%@\n",[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]];
//    TTLog(@"%@\n = %@",data,[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]);
        // Work
        if(organization) vcard = [vcard stringByAppendingFormat:@"ORG:%@%@\n",(organization?organization:@""),(department?department:@"")];
        
        if(jobTitle) vcard = [vcard stringByAppendingFormat:@"TITLE:%@\n",jobTitle];
        
        // Mail
        ABMultiValueRef emails = ABRecordCopyValue(person, kABPersonEmailProperty);
        if(emails) {
            for (int k = 0; k < ABMultiValueGetCount(emails); k++) {
                NSString *label = (NSString*)CFBridgingRelease(ABAddressBookCopyLocalizedLabel(ABMultiValueCopyLabelAtIndex(emails, k)));
                NSString *email = (NSString *)CFBridgingRelease(ABMultiValueCopyValueAtIndex(emails, k));
                NSString *labelLower = [label lowercaseString];
                
                vcard = [vcard stringByAppendingFormat:@"EMAIL;type=INTERNET;type=WORK:%@\n",email];
                
                if ([labelLower isEqualToString:@"home"]) vcard = [vcard stringByAppendingFormat:@"EMAIL;type=INTERNET;type=HOME:%@\n",email];
                else if ([labelLower isEqualToString:@"work"]) vcard = [vcard stringByAppendingFormat:@"EMAIL;type=INTERNET;type=WORK:%@\n",email];
                else {//类型解析不出来的
                    counter++;
                    vcard = [vcard stringByAppendingFormat:@"item%d.EMAIL;type=INTERNET:%@\nitem%d.X-ABLabel:%@\n",counter,email,counter,label];
                }
            }
        }
    // Tel
        ABMultiValueRef phoneNumbers = ABRecordCopyValue(person, kABPersonPhoneProperty);
        if(phoneNumbers) {
            for (int k = 0; k < ABMultiValueGetCount(phoneNumbers); k++) {
                
                NSString *label = (NSString*)CFBridgingRelease(ABAddressBookCopyLocalizedLabel(ABMultiValueCopyLabelAtIndex(phoneNumbers, k)));
                
                NSString *number = (NSString *)CFBridgingRelease(ABMultiValueCopyValueAtIndex(phoneNumbers, k));
                NSString *labelLower = [label lowercaseString];
                
                //待处理--start
                if ([labelLower isEqualToString:@"移动"]) {
                    labelLower = @"mobile";
                }
                if ([labelLower isEqualToString:@"主要"]) {
                    labelLower = @"home";
                }
                //--------end
                if ([labelLower isEqualToString:@"mobile"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=CELL:%@\n",number];
                else if ([labelLower isEqualToString:@"home"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=HOME:%@\n",number];
                else if ([labelLower isEqualToString:@"work"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=WORK:%@\n",number];
                else if ([labelLower isEqualToString:@"main"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=MAIN:%@\n",number];
                else if ([labelLower isEqualToString:@"homefax"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=HOME;type=FAX:%@\n",number];
                else if ([labelLower isEqualToString:@"workfax"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=WORK;type=FAX:%@\n",number];
                else if ([labelLower isEqualToString:@"pager"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=PAGER:%@\n",number];
                else if([labelLower isEqualToString:@"other"]) vcard = [vcard stringByAppendingFormat:@"TEL;type=OTHER:%@\n",number];
                else { //类型解析不出来的
                    counter++;
                    vcard = [vcard stringByAppendingFormat:@"item%d.TEL:%@\nitem%d.X-ABLabel:%@\n",counter,number,counter,label];
                }
            }
        }
        
        // Address
        ABMultiValueRef address = ABRecordCopyValue(person, kABPersonAddressProperty);
        if(address) {
            for (int k = 0; k < ABMultiValueGetCount(address); k++) {
            NSString *label = (NSString*)CFBridgingRelease(ABAddressBookCopyLocalizedLabel(ABMultiValueCopyLabelAtIndex(CFBridgingRetain(label), k)));
                NSDictionary *dic = (NSDictionary *)CFBridgingRelease(ABMultiValueCopyLabelAtIndex(address, k));
                NSString *labelLower = [label lowercaseString];
                NSString* country = [dic valueForKey:(NSString *)kABPersonAddressCountryKey];
                NSString* city = [dic valueForKey:(NSString *)kABPersonAddressCityKey];
                NSString* state = [dic valueForKey:(NSString *)kABPersonAddressStateKey];
                NSString* street = [dic valueForKey:(NSString *)kABPersonAddressStreetKey];
                NSString* zip = [dic valueForKey:(NSString *)kABPersonAddressZIPKey];
                NSString* countryCode = [dic valueForKey:(NSString *)kABPersonAddressCountryCodeKey];
                NSString *type = @"";
                NSString *labelField = @"";
                counter++;
                
                if([labelLower isEqualToString:@"work"]) type = @"WORK";
                else if([labelLower isEqualToString:@"home"]) type = @"HOME";
                else if(label && [label length] > 0)
                {
                    labelField = [NSString stringWithFormat:@"item%d.X-ABLabel:%@\n",counter,label];
                }
                
                vcard = [vcard stringByAppendingFormat:@"item%d.ADR;type=%@:;;%@;%@;%@;%@;%@\n%@item%d.X-ABADR:%@\n",
                         counter,
                         type,
                         (street ? street : @""),
                         (city ? city : @""),
                         (state ? state : @""),
                         (zip ? zip : @""),
                         (country ? country : @""),
                         labelField,
                         counter,
                         (countryCode ? countryCode : @"")
                         ];
            }
        }

        // url
        // TODO:
        
        // IM
        // TODO:
        
        // Photo
        // TODO:

        vcard = [vcard stringByAppendingString:@"END:VCARD"];
//    }
    TTLog(@"vcard = %@",vcard);
    return vcard;
}


@end
