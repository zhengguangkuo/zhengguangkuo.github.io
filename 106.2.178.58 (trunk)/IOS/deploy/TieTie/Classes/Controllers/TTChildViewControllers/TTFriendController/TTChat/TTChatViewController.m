//
//  TTChatViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-9-29.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTChatViewController.h"
#import "MessageFrame.h"
#import "Message.h"
#import "MessageCell.h"
#import "DBManager.h"

@interface TTChatViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>
{
    NSMutableArray *allMessageFrame;
}
@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UITextField *editMsgTF;

- (IBAction)sendCouponBtn:(id)sender;
- (IBAction)sendMsgBtn:(id)sender;

- (void)handleMessageFromXmpp:(NSNotification*)userInfo;
@end

@implementation TTChatViewController

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
     self.navigationItem.leftBarButtonItem = [UIBarButtonItem barButtonItemWithIcon:@"top_back_" target:self action:@selector(goBack) direction:ItemDirectionLeft];
    self.navigationItem.title = self.friendData.nickName;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleMessageFromXmpp:) name:KNOTIFICATIONCENTER_CHATMESSAGEFROMXMPP object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillShow:) name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHide:) name:UIKeyboardWillHideNotification object:nil];
    
    UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc]initWithTarget:self action:@selector(viewTapGesture)];
    tapGesture.cancelsTouchesInView = NO;
    [self.tableView addGestureRecognizer:tapGesture];
    
    if (!allMessageFrame) {
        allMessageFrame = [[NSMutableArray alloc] init];
    }
//    [self addTestData];
    [self getChatRecordMsgFromSqlite];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    NSInteger count = allMessageFrame.count;
    if (count > 0) {
        NSIndexPath *indexPath = [NSIndexPath indexPathForRow:count-1 inSection:0];
        [self.tableView scrollToRowAtIndexPath:indexPath atScrollPosition:UITableViewScrollPositionBottom animated:YES];
    }
}

- (void)goBack
{
    self.tableView.delegate = nil;
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)addTestData
{
    NSArray *array = [NSArray arrayWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"messages" ofType:@"plist"]];
    
    NSString *previousTime = nil;
    for (NSDictionary *dict in array) {
        MessageFrame *messageFrame = [[MessageFrame alloc]init];
        Message *message = [[Message alloc]init];
        message.dict = dict;
        messageFrame.showTime = ![previousTime isEqualToString:message.time];
        messageFrame.message = message;
        
        previousTime = message.time;
        
        [allMessageFrame addObject:messageFrame];
    }
}

- (void)getChatRecordMsgFromSqlite
{
    DBManager *dbManager = [DBManager sharedDBManager];
    NSString *tableName = [NSString stringWithFormat:@"chat_%@",self.friendData.ID];
    [dbManager createChatTable:tableName];
    NSArray *array = [dbManager queryChatTable:tableName];
    
    NSString *previousTime = nil;
    for (NSDictionary *dict in array) {
        MessageFrame *messageFrame = [[MessageFrame alloc]init];
        Message *message = [[Message alloc]initWithDict:dict];
        message.icon = @"icon_user2.png";
        messageFrame.showTime = ![previousTime isEqualToString:message.time];
        messageFrame.message = message;
        
        previousTime = message.time;
        
        [allMessageFrame addObject:messageFrame];
    }

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIKeyboardWillHideNotification object:nil];
}

- (IBAction)sendCouponBtn:(id)sender {
}

- (IBAction)sendMsgBtn:(id)sender {
    NSString *content = self.editMsgTF.text;
    NSDateFormatter *fmt = [[NSDateFormatter alloc]init];
    fmt.dateFormat = @"MM-dd";
    NSString *time = [fmt stringFromDate:[NSDate date]];
    [self addMessageWithContent:content time:time];
    
    [self.tableView reloadData];
    NSInteger count = allMessageFrame.count;
    if (count > 0) {
        NSIndexPath *indexPath = [NSIndexPath indexPathForRow:count-1 inSection:0];
        [self.tableView scrollToRowAtIndexPath:indexPath atScrollPosition:UITableViewScrollPositionBottom animated:YES];
    }

    
    TTXMPPTool *xmppTool = [TTXMPPTool sharedInstance];
    NSString *jidStr = [NSString stringWithFormat:@"%@@m.tie1tie.com",self.friendData.ID];
    [xmppTool sendMessage:content toUser:jidStr];
    
    self.editMsgTF.text = nil;
    [self.editMsgTF resignFirstResponder];
}

#pragma mark - custom methods
- (void)handleMessageFromXmpp:(NSNotification*)userInfo
{
    NSDictionary *msgDict = userInfo.object;
//    DBManager *dbmanager = [DBManager sharedDBManager];
//    NSString *chatTableName = [NSString stringWithFormat:@"chat_%@",msgDict[@"from"]];
//    [dbmanager createChatTable:chatTableName];
//    [dbmanager insertTable:chatTableName object:msgDict];
    
    MessageFrame * mf = [[MessageFrame alloc]init];
    Message *msg = [[Message alloc]initWithDict:msgDict];
//    msg.content = msgDict[@"message"];
//    msg.time = msgDict[@"time"];
    msg.icon = @"icon_user2.png";
//    msg.type = MessageTypeOther;
    mf.message = msg;
    
    [allMessageFrame addObject:mf];
    
    [self.tableView reloadData];
    NSInteger count = allMessageFrame.count;
    if (count > 0) {
        NSIndexPath *indexPath = [NSIndexPath indexPathForRow:count-1 inSection:0];
        [self.tableView scrollToRowAtIndexPath:indexPath atScrollPosition:UITableViewScrollPositionBottom animated:YES];
    }
}

- (void)addMessageWithContent:(NSString *)content time:(NSString*)time
{
    MessageFrame * mf = [[MessageFrame alloc]init];
    Message *msg = [[Message alloc]init];
    msg.content = content;
    msg.time = time;
    msg.icon = @"icon_user2.png";
    msg.type = MessageTypeMe;
    mf.message = msg;
    
    [allMessageFrame addObject:mf];
}

- (void)keyboardWillShow:(NSNotification*)notification
{
    NSDictionary *userInfo = [notification userInfo];
    NSValue *value = [userInfo objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGRect keyboardRect = [value CGRectValue];
    CGFloat height = keyboardRect.size.height;
    
    [self changeFrameWithKeyboardHeight:height];
}

- (void)keyboardWillHide:(NSNotification*)notification
{
//    NSDictionary *userInfo = [notification userInfo];
//    NSValue *value = [userInfo objectForKey:UIKeyboardFrameEndUserInfoKey];
//    CGRect keyboardRect = [value CGRectValue];
//    CGFloat height = keyboardRect.size.height;
    
    [self changeFrameWithKeyboardHeight:0];
}

- (void)changeFrameWithKeyboardHeight:(CGFloat)height
{
    NSTimeInterval animationDuration = 0.3f;
    [UIView beginAnimations:@"ResizeForKeyboard" context:nil];
    [UIView setAnimationDuration:animationDuration];
    
    CGSize size = self.view.frame.size;
    self.view.frame = CGRectMake(0.0f, 0-height, size.width, size.height);
    
    [UIView commitAnimations];
}

- (void)viewTapGesture
{
    [self.editMsgTF resignFirstResponder];
}



#pragma mark - UITableViewDataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [allMessageFrame count];
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *identifier = @"cell";
    MessageCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (!cell) {
        cell = [[MessageCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];
    }
    
    cell.messageFrame = allMessageFrame[indexPath.row];
    return cell;
}

#pragma mark - UITableViewDelegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{

}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return [allMessageFrame[indexPath.row] cellHeight];
}

@end
