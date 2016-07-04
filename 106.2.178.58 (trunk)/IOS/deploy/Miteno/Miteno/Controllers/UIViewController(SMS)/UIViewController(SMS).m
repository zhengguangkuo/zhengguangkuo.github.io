//
//  InAppSMSViewController.m
//  InAppSMS
//
//  Created by lv on 10-6-9.
//  Copyright CocoaChina.com 2010. All rights reserved.
//

#import "UIViewController(SMS).h"

@implementation UIViewController(SMS)

void (^SendSuccess)(void);

void (^SendCancel)(void);

void (^SendFail)(void);


- (void)finishSuccess:(void(^)(void))SuccessBlock
{
    if(!SendSuccess)
    {
        SendSuccess = NULL;
    }
    SendSuccess = SuccessBlock;
}

- (void)finishCancel:(void(^)(void))CancelBlock
{
    if(!SendCancel)
    {
        SendCancel = NULL;
    }
    SendCancel = CancelBlock;
}


- (void)finishFail:(void(^)(void))FailBlock
{
    if(!SendFail)
    {
        SendFail = NULL;
    }
    SendFail = FailBlock;
}


- (void)sendSMS:(NSString*)MsgBody array:(NSArray*)Contacts
{
    BOOL canSendSMS = [MFMessageComposeViewController canSendText];
	NSLog(@"send message");
	if (canSendSMS) {
	
		MFMessageComposeViewController *picker = [[MFMessageComposeViewController alloc] init];
		picker.messageComposeDelegate = self;
		picker.navigationBar.tintColor = [UIColor blackColor];
        picker.body = MsgBody;
		picker.recipients = Contacts;
		[self presentViewController:picker animated:NO completion:^{
            
        }];
    }
}

- (void)messageComposeViewController:(MFMessageComposeViewController *)controller didFinishWithResult:(MessageComposeResult)result {
	
	// Notifies users about errors associated with the interface
	switch (result) {
		case MessageComposeResultCancelled:
			NSLog(@"Result: canceled");
            if(SendCancel)
           {
              SendCancel();
           }
			break;
		case MessageComposeResultSent:
			NSLog(@"Result: Sent");
			if(SendSuccess)
           {
              SendSuccess();
           }
            break;
        case MessageComposeResultFailed:
			NSLog(@"Result: Failed");
		    if(SendFail)
           {
              SendFail();
           }
            break;
		default:
			break;
	}
	[self dismissViewControllerAnimated:NO completion:^{
        
    }];
    
    SendSuccess = NULL;
    
    SendCancel = NULL;
    
    SendFail = NULL;
}

@end
