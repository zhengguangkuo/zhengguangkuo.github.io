//
//  TTSuggestionFeedBackViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-6-13.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

//#import <UIKit/UIKit.h>
#import "TTRootViewController.h"

@interface TTSuggestionFeedBackViewController : TTRootViewController<UITextViewDelegate>
//@property (weak, nonatomic) IBOutlet UITextField *suggestionTF;
@property (weak, nonatomic) IBOutlet UITextView *suggestionTF;

- (IBAction)commit:(id)sender;
@end
