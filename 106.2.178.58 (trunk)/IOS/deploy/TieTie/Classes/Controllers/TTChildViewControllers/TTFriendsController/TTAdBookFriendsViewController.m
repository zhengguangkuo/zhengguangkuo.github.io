//
//  TTAdBookFriendsViewController.m
//  Miteno
//
//  Created by wg on 14-8-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTAdBookFriendsViewController.h"
#import <AddressBook/AddressBook.h>
#import "AddressBookCell.h"
#import "TTFriends.h"
#import "TTFriendsMgr.h"
#import "TTFriendsDetailViewController.h"
@interface TTAdBookFriendsViewController ()<UITableViewDataSource,UITableViewDelegate,UISearchBarDelegate>
{
    NSArray  *   _addressBookTemp;
    UITableView     *   _tableView;
    TTFriendsMgr        *   _db;
}
@property (weak, nonatomic) IBOutlet UISearchBar *searchBar;

@end

@implementation TTAdBookFriendsViewController

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
    
    self.title = @"通讯录好友";
    self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(backToPrevious) direction:ItemDirectionLeft];
    
    
    self.view.backgroundColor = TTGlobalBg;
    _addressBookTemp = [NSMutableArray array];
    _db = [TTFriendsMgr sharedTTFriendsMgr];
    //读取通讯录
    [self ReadAllPeoples];
    
    
    //添加tableview
    [self addTableView];
    
    self.searchBar.delegate = self;
}
#pragma mark -AddTableView And Datasource And Delegate
- (void)addTableView
{
    _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0,self.searchBar.frame.size.height, ScreenWidth, self.view.frame.size.height-self.searchBar.frame.size.height)];
    _tableView.delegate =self;
    _tableView.dataSource =self;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [self.view addSubview:_tableView];
}
//tableViewdelegate And datasource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return _addressBookTemp.count;
}
//cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    AddressBookCell *cell = [AddressBookCell AddressBookCellWithTableView:tableView];
    UIImageView *img = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"whightbg2"]];
    cell.backgroundView = img;
    TTFriends *friend = _addressBookTemp[indexPath.row];
    cell.friendName.text = friend.userName;
    return cell;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    [_tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    TTFriendsDetailViewController *fd = [[TTFriendsDetailViewController alloc] init];
    fd.friends = _addressBookTemp[indexPath.row];
    [self.navigationController pushViewController:fd animated:YES];
}
#pragma mark -SearchDelegate
- (void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText;
{
    _addressBookTemp = [[TTFriendsMgr sharedTTFriendsMgr] queryFriendsWithCondition:searchText];
    [_tableView reloadData];
    
}

-(void) searchBarSearchButtonClicked:(UISearchBar *)searchBar {
    [self searchBar:self.searchBar textDidChange:nil];
    [self.searchBar resignFirstResponder];
}

- (void)searchBarCancelButtonClicked:(UISearchBar *) searchBar
{
    [self searchBar:self.searchBar textDidChange:nil];
    [_searchBar resignFirstResponder];
}

#pragma mark -读取通讯录
-(void)ReadAllPeoples{
    
    if ([_db queryFriends].count > 0){
        
        _addressBookTemp = [_db queryFriends];
        return;
    }
    
    ABAddressBookRef addressBooks = nil;
    if ([[UIDevice currentDevice].systemVersion floatValue] >= 6.0){
        addressBooks =  ABAddressBookCreateWithOptions(NULL, NULL);
        //获取通讯录权限
        dispatch_semaphore_t sema = dispatch_semaphore_create(0);
        ABAddressBookRequestAccessWithCompletion(addressBooks, ^(bool granted, CFErrorRef error){dispatch_semaphore_signal(sema);});
        dispatch_semaphore_wait(sema, DISPATCH_TIME_FOREVER);
    }else{
//        addressBooks = ABAddressBookCreate();
    }
    //获取通讯录中的所有
    CFArrayRef allPeople = ABAddressBookCopyArrayOfAllPeople(addressBooks);
    //通讯录中人数
    CFIndex nPeople = ABAddressBookGetPersonCount(addressBooks);
    //循环，获取每个人的个人信息
    
    for (NSInteger i = 0; i < nPeople; i++ ){
        
        TTFriends *adBookF = [[TTFriends alloc] init];
        //获取个人
        
        ABRecordRef person = CFArrayGetValueAtIndex(allPeople, i);
        
        //获取个人名字
        
        CFTypeRef abName = ABRecordCopyValue(person, kABPersonFirstNameProperty);
        
        CFTypeRef abLastName = ABRecordCopyValue(person, kABPersonLastNameProperty);
        
        CFStringRef abFullName = ABRecordCopyCompositeName(person);
        
        NSString *nameString = (__bridge NSString *)abName;
        
        NSString *lastNameString = (__bridge NSString *)abLastName;

        if ((__bridge id)abFullName != nil) {
            nameString = (__bridge NSString *)abFullName;
            
        } else {
            if ((__bridge id)abLastName != nil)  {
                nameString = [NSString stringWithFormat:@"%@ %@", nameString, lastNameString];
            }
        }
        adBookF.userName = nameString;
//        addressBook.recordID = (int)ABRecordGetRecordID(person);
        
        ABPropertyID multiProperties[] = {
            kABPersonPhoneProperty,
            
            kABPersonEmailProperty
            
        };
        
        NSInteger multiPropertiesTotal = sizeof(multiProperties) / sizeof(ABPropertyID);
        
        for (NSInteger j = 0; j < multiPropertiesTotal; j++ ) {
            
            ABPropertyID property = multiProperties[j];
            
            ABMultiValueRef valuesRef = ABRecordCopyValue(person, property);
            
            NSInteger valuesCount = 0;
        
            if (valuesRef != nil) valuesCount = ABMultiValueGetCount(valuesRef);
            if (valuesCount == 0) {
                continue;
                
            }
            //获取电话号码和email
            for (NSInteger k = 0; k < valuesCount; k++ ) {
                adBookF.userID = k;
                CFTypeRef value = ABMultiValueCopyValueAtIndex(valuesRef, k);
                
                switch (j) {
                    case 0: {// Phone number
//                        adBookF.tel = (__bridge NSString*)value;
                        NSString *phoneNumber = [NSString stringWithFormat:@"%@",(__bridge NSString*)value];
                  
                            NSArray *params = [phoneNumber componentsSeparatedByString:@"-"];
                        adBookF.tel = [NSString stringWithFormat:@"%@",params[0]];
                        break;
                        
                    }case 1: {// Email
                        adBookF.email = (__bridge NSString*)value;
                        break;
                    }
                }
            }
        }
     
        //将个人信息添加到数组中，循环完成后addressBookTemp中包含所有联系人的信息
//        [_addressBookTemp addObject:adBookF];
        [_db addFriend:adBookF];
    }
    _addressBookTemp = [_db queryFriends];
    
    CFRelease(addressBooks);
    CFRelease(allPeople);
}
@end
