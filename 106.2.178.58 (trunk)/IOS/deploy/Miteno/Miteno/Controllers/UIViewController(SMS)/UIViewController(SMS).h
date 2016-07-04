//
//  InAppSMSViewController.h
//  InAppSMS
//
//  Created by lv on 10-6-9.
//  Copyright CocoaChina.com 2010. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MessageUI.h>


@interface UIViewController(SMS)
<MFMessageComposeViewControllerDelegate> {

}

- (void)sendSMS:(NSString*)MsgBody array:(NSArray*)Contacts;

- (void)finishSuccess:(void(^)(void))SuccessBlock;

- (void)finishCancel:(void(^)(void))CancelBlock;

- (void)finishFail:(void(^)(void))FailBlock;

@end

