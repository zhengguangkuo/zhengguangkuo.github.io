//
//  SystemDialog.h
//  firework
//
//  Created by guorong on 14-2-17.
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
