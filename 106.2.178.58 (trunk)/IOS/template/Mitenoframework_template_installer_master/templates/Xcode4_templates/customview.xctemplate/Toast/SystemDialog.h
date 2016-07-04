//
//  SystemDialog.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#import "CustomDialog.h"
@interface UIView(dialog)
- (void)makeToast:(NSString *)text;
@end


@interface SystemDialog : CustomDialog
- (id)initWithText:(NSString *)text;
- (void) show;
@end
