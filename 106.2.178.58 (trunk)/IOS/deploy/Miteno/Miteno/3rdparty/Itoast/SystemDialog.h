//
//  SystemDialog.h
//  Mpay
//
//  Created by HWG on 13-12-19.
//  Copyright (c) 2013年 miteno. All rights reserved.
//

#import "CustomDialog.h"
@interface UIView(dialog)
- (void)makeToast:(NSString *)text;
@end



@interface SystemDialog : CustomDialog
- (id)initWithText:(NSString *)text;
- (void) show;
@end
