//
//  SystemDialog.h
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "CustomDialog.h"
@interface UIView(dialog)
- (void)makeToast:(NSString *)text;
@end


@interface SystemDialog : CustomDialog
- (id)initWithText:(NSString *)text;
- (void) show;
@end
