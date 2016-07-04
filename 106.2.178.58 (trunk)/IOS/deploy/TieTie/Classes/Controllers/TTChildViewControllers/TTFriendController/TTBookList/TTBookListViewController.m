//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-6-4.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "UIImage(addition).h"
#import "UIView(category).h"
#import "UIView+ViewFrameGeometry.h"
#import "UIButton(addtion).h"
#import "TTBookListViewController.h"
#import "TTFriend.h"
#import "TTPersonViewController.h"
#import "UIProgressHud.h"

static UIProgressHud*  sharedHud3;

#define kFriendNameTag  0

@interface TTBookListViewController () <UITableViewDelegate,UITableViewDataSource>


@property (nonatomic,  strong)  UITableView*  BookTableView;

@property (nonatomic,  strong)  NSMutableArray*  addressBookArray;


- (void)ParseJsonData: (NSString*)JsonData;

- (void)LayoutSearchBar;

- (void)LayoutTableView;

- (void)readAddressBook;

@end


@implementation TTBookListViewController

@synthesize BookTableView = _BookTableView;

@synthesize addressBookArray = _addressBookArray;


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        self.title = @"通讯录朋友";
        self.addressBookArray = [[NSMutableArray alloc] init];
    }
    return self;
}
- (void)viewDidLoad
{
    [super viewDidLoad];
    [self NavigationViewBackBtn];
    [self.view setBackgroundColor:[UIColor whiteColor]];
    [self LayoutSearchBar];
    [self LayoutTableView];
    // Do any additional setup after loading the view.
}

- (void)readAddressBook
{
    if(!sharedHud3)
    {
        static dispatch_once_t oncePredicate4;
        dispatch_once(&oncePredicate4, ^{
            sharedHud3 = [[UIProgressHud alloc] init];
        });
    }
    

    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
    
    [sharedHud3 startWaiting];
        
    [self.addressBookArray removeAllObjects];
        
        ABAddressBookRef addressBook = nil;
        
        if ([[UIDevice currentDevice].systemVersion floatValue] >= 6.0)
        {
            addressBook = ABAddressBookCreateWithOptions(NULL, NULL);
            //等待同意后向下执行
            dispatch_semaphore_t sema = dispatch_semaphore_create(0);
            ABAddressBookRequestAccessWithCompletion(addressBook, ^(bool granted, CFErrorRef error)
                                                     {
                                                         dispatch_semaphore_signal(sema);
                                                     });
            dispatch_semaphore_wait(sema, DISPATCH_TIME_FOREVER);
        }
        
        else
        {
            addressBook = ABAddressBookCreate();
        }
        
        CFArrayRef results = ABAddressBookCopyArrayOfAllPeople(addressBook);
        
        for(int i = 0; i < CFArrayGetCount(results);  i++)
        {
            ABRecordRef person = CFArrayGetValueAtIndex(results, i);
            //读取firstname
            NSString *firstName = (NSString*)CFBridgingRelease(ABRecordCopyValue(person, kABPersonFirstNameProperty));
            //NSLog(@"firstName = %@",firstName);
            
            //读取lastname
            NSString *lastname = (NSString*)CFBridgingRelease(ABRecordCopyValue(person, kABPersonLastNameProperty));
            //NSLog(@"lastname = %@",lastname);
            
            NSString* fullname = @"";
            if(!IsEmptyString(firstName))
            {
               fullname =  [fullname stringByAppendingString:firstName];
            }
            
            if(!IsEmptyString(lastname))
            {
               fullname =  [fullname stringByAppendingString:lastname];
            }
            
            ABMultiValueRef phone = ABRecordCopyValue(person, kABPersonPhoneProperty);
            NSString * personPhone = (NSString*)CFBridgingRelease(ABMultiValueCopyValueAtIndex(phone, 0));
            NSLog(@"personPhone = %@",personPhone);
            
            
            ABMultiValueRef email = ABRecordCopyValue(person, kABPersonEmailProperty);
            NSString * personemail = (NSString*)CFBridgingRelease(ABMultiValueCopyValueAtIndex(email, 0));
            NSLog(@"personemail = %@",personemail);
            
            if(!IsEmptyString(personPhone))
          {
             TTFriend* valueObject = [[TTFriend alloc] init];
            if(!IsEmptyString(fullname))
                [valueObject setUserName:fullname];
            if(!IsEmptyString(personPhone))
                [valueObject setMobile:personPhone];
            
            if(!IsEmptyString(personemail))
                [valueObject setEmail:personemail];
            
            [self.addressBookArray addObject:valueObject];
          }
        }
        
        dispatch_async(dispatch_get_main_queue(),
                       ^{
                           [self.BookTableView reloadData];
                           [sharedHud3 stopWaiting];
                       });

    
    
    });
}




- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:NO];
    if([self.addressBookArray count]==0)
    [self  readAddressBook];
    
}


- (void)ParseJsonData: (NSString*)JsonData
{
    NSLog(@"parse begin");
}

- (void)LayoutSearchBar
{
    UISearchBar*  searchBar = [[UISearchBar alloc] initWithFrame:CGRectMake(0, 0, ScreenWidth, 40)];
    searchBar.placeholder=@"搜索";
    searchBar.keyboardType=UIKeyboardTypeDefault;
    
    UITextField *searchField = nil;
    NSUInteger numViews = [searchBar.subviews count];
    for(int i = 0; i < numViews; i++) {
        if([[searchBar.subviews objectAtIndex:i] isKindOfClass:[UITextField class]]) { //conform?
            searchField = [searchBar.subviews objectAtIndex:i];
        }
    }
    
    if(nil!=searchField)
    {
        searchField.textColor = [UIColor blackColor];
        [searchField setBorderStyle:UITextBorderStyleRoundedRect];
        [searchField ViewBorder:[UIColor clearColor] Radius:10.0f];
        searchField.layer.masksToBounds = YES;
    }
    //UIImage *image = [UIImage scaleToSize:[UIImage generateFromColor:[UIColor whiteColor]] size:CGSizeMake(1, 1)];
    //[searchBar setImage:image
    //forSearchBarIcon:UISearchBarIconSearch
    //state:UIControlStateNormal];
    [searchBar setPositionAdjustment:UIOffsetMake(0, 0) forSearchBarIcon:UISearchBarIconSearch];
    [searchBar setBackgroundColor:[UIColor groupTableViewBackgroundColor]];
    [self.view addSubview:searchBar];
}

- (void)LayoutTableView
{
    self.BookTableView = [[UITableView alloc] initWithFrame:CGRectMake(4, 46, ScreenWidth - 8, 380)];
    [_BookTableView setDataSource:self];
    [_BookTableView setDelegate:self];
    [_BookTableView setSeparatorStyle:UITableViewCellSeparatorStyleNone];
    [_BookTableView setBackgroundColor:[UIColor whiteColor]];
    [self.view addSubview:self.BookTableView];
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.addressBookArray count];
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 50;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    int n = [indexPath row];
    
    if(n>=0&&n<[self.addressBookArray count])
  {
      TTFriend* SelectedObject = self.addressBookArray[n];
      TTPersonViewController* ttc = [[TTPersonViewController alloc] initWithNibName:@"TTPersonViewController" bundle:nil];
      [self.navigationController pushViewController:ttc animated:YES];
      [ttc setMFriend:SelectedObject];
  }
  
}


#pragma mark -cell delegate
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static  NSString *identifier = @"BookList";
    int nRow = [indexPath row];
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    
    if(cell==nil)
    {
        cell = [[UITableViewCell alloc] init];
        [cell setEditing:NO];
        UILabel* nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 5, 150, 40)];
        [nameLabel setTextColor:[UIColor blackColor]];
        [nameLabel setText:@""];
        [nameLabel setBackgroundColor:[UIColor clearColor]];
        [nameLabel setTag:kFriendNameTag];
        [cell addSubview:nameLabel];
    
        UILabel* Indicator = [[UILabel alloc] initWithFrame:CGRectMake(242, 5, 80, 40)];
        [Indicator setTextColor:[UIColor colorWithPatternImage:[UIImage imageNamed:@"u184"]]];
        [Indicator setFont:[UIFont boldSystemFontOfSize:15.0f]];
        [Indicator setText:@"＋添加"];
        [Indicator setBackgroundColor:[UIColor clearColor]];
        [cell addSubview:Indicator];
        
        UIImageView*  imageView = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"ul_12"]];
        [imageView setFrame:CGRectMake(291, 13, 18, 24)];
        [cell addSubview:imageView];
        
        UIImageView*  Line = [[UIImageView alloc] initWithFrame:CGRectMake(0, 49, 320, 1)];
        [Line setBackgroundColor:[UIColor lightGrayColor]];
        [cell addSubview:Line];
    }
    
    
    if(nRow>=0&&nRow<[self.addressBookArray count])
{
    UILabel*  Label = (UILabel*)[cell viewWithTag:kFriendNameTag];
    TTFriend* object = self.addressBookArray[nRow];
    [Label setText:object.userName];
}
    
    
    return cell;
}




@end
