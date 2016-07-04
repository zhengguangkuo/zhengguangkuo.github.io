//
//  ViewController.m
//  ttyhthfhfh
//
//  Created by guorong on 13-9-23.
//  Copyright (c) 2013年 guorong. All rights reserved.
//
#import <Foundation/Foundation.h>
#import "BlueViewController.h"
#import "TouchXML.h"
#import "SaleDetail.h"
#import "FileManager.h"
#import "HttpService.h"
#import "UIImageView+DispatchLoad.h"
//#import "UIImageView+MKNetworkKitAdditions.h"

//#import "OperationQueue+KVO.h"


static  NSString *kIDStr    = @"id";
static  NSString *kNameStr   = @"im:name";
static  NSString *kImageStr  = @"im:image";
static  NSString *kArtistStr = @"im:artist";
static  NSString *kEntryStr  = @"entry";


#define  kCustomRowCount    5

#define  Table_Height (FULL_SCREEN_Height - TAB_SIZE_Height - NAVI_SIZE_Height)


@interface BlueViewController ()

@property  (nonatomic,retain) UITableView*  TableView;

@property  (nonatomic,retain)  NSMutableArray*  sourceArray;

//@property  (nonatomic,retain)  HttpServiceQueue*  enQueueImage;

@property  (nonatomic,retain)  HttpServiceQueue*  imageQueue;


- (void)parseData:(CXMLDocument *)document;

@end


@implementation BlueViewController

@synthesize sourceArray = _sourceArray;

//@synthesize enQueueImage = _enQueueImage;

@synthesize imageQueue = _imageQueue;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self  SetTitleText:@"TableView"];
    
    [self.view setBackgroundColor:[UIColor whiteColor]];
    
    self.sourceArray = [[NSMutableArray alloc] initWithCapacity:0];
    
//    self.enQueueImage = [[HttpServiceQueue alloc] custom_init];
    
//    [self.enQueueImage setMaxConcurrentOperationCount:kCustomRowCount];
    
    self.imageQueue = [[HttpServiceQueue alloc] custom_init];
    
    [self.imageQueue setMaxConcurrentOperationCount:kCustomRowCount];
    
    self.TableView =  [[UITableView alloc] init];
    
    [self.TableView setFrame:CGRectMake(0, 0, FULL_SCREEN_Width, Table_Height)];
    
    [self.TableView setBackgroundColor:[UIColor clearColor]];
    
    [_TableView setDataSource:self];
    
    [_TableView setDelegate:self];
    
    [self.view addSubview:self.TableView];
    
    [_TableView release];
    
    // Do any additional setup after loading the view, typically from a nib.
}


- (void)dealloc
{
    [_sourceArray release];
    [_imageQueue release];
    [_TableView release];
    [super dealloc];
}




- (void)viewDidUnload
{
    [super viewDidUnload];
}


-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:YES];
    [self  RequestData];
}


-(void)RequestData
{
    NSAutoreleasePool*  pool = [[NSAutoreleasePool alloc] init];
    HttpService* requestForXML = [HttpService HttpInitWithUrl:TopPaidAppsFeed withHud:YES];
    [requestForXML  setDataHandler:^(NSString* data)
    {
        [self handleData:data];
    }
    ];
    
    [requestForXML setErrorHandler:^(NSError* error)
    {
        dispatch_async(dispatch_get_main_queue(), ^{
            [self handleError:error];
        });
    }
    ];
    
    [requestForXML startOperation];
    [pool release];
}


- (void)handleError:(NSError *)error
{
    NSLog(@"ttt handleerror");
    NSString *errorMessage = [error localizedDescription];
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"网络连接失败"
														message:errorMessage
													   delegate:self
											  cancelButtonTitle:@"取消"
											  otherButtonTitles:nil];
    [alertView show];
    [alertView release];
}



- (void)handleData: (NSString*)data
{
    NSLog(@"data = %@",data);
    CXMLDocument *document = [[CXMLDocument alloc] initWithXMLString:data options:0 error:nil];
    [self parseData:document];
    [document release];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        NSLog(@"number = %d",[self.sourceArray count]);
        [self.TableView reloadData];
    });
    
}


- (void)parseData:(CXMLDocument *)document
{
    [self.sourceArray removeAllObjects];
    CXMLElement* root = [document rootElement];
    NSArray* books = [root childrenForName:kEntryStr];
    NSLog(@"number of books = %d",[books count]);
    
    NSString* (^ParseNode)(NSString*,CXMLElement*) = ^(NSString* key, CXMLElement* element)
  {
        NSArray*   tempArray = [element childrenForName:key];
        CXMLNode*  node = (CXMLNode*)[tempArray lastObject];
        return [node stringValue];
  };

    for(CXMLElement *element in books)
  {
      SaleDetail* tempobject = [[SaleDetail alloc] init];
      [tempobject setSaleId:ParseNode(kIDStr,element)];
      [tempobject setSaleName:ParseNode(kNameStr,element)];
      [tempobject setSaleArtist:ParseNode(kArtistStr,element)];
      [tempobject setSaleImage:ParseNode(kImageStr,element)];
      [self.sourceArray addObject:tempobject];
      [tempobject release];
  }
}




-(NSInteger)tableView:(UITableView *)tableView
numberOfRowsInSection:(NSInteger)section
{
    return [self.sourceArray count];
    
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return (CGFloat)(Table_Height/5);
}


-(UITableViewCell *)tableView:(UITableView *)tableView
		cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"TestImageCell";
    int n = [indexPath row];
    SaleDetail* dataobject = [self.sourceArray objectAtIndex:n];
    
    CutomViewCell *cell = (CutomViewCell*)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if(cell==nil)
    {
        cell = [[[CutomViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier]autorelease];
    }
    [cell setTag:n];
    
    [cell setDetailObject:dataobject];
    
     if(![FileManager FileExistCheck:dataobject.saleImage])
    {
        HttpService*  tempservice = [HttpService HttpInitWithUrl:dataobject.saleImage withHud:NO];
        tempservice.bytyesHandler = ^(NSData* data)
        {
            if(cell.tag==indexPath.row)
            {
                [cell.cusImageview setImageFromData:data targetPath:dataobject.saleImage];
            }
        };
        [self.imageQueue addOperation:tempservice];
    }
    
    return cell;
}


-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:YES];
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


//
//-(void)handleSuccessWithData: (WebService *)theService data: (NSString *)data
//{
//
//
//}
@end
