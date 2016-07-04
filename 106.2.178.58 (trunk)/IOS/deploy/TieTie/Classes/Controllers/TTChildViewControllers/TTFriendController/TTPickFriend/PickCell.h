//
//  ImageTableViewCell.h
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@protocol checkdelegate <NSObject>

@optional
- (void)checkSelected:(BOOL)bflag
                 path:(int)row;

@end


@class TTPickFriend;

@interface PickCell : UITableViewCell{

}
@property (weak, nonatomic) IBOutlet UIButton *pickButton;

@property (weak, nonatomic) IBOutlet UIImageView *HeadImage;

@property (weak, nonatomic) IBOutlet UILabel *FriendName;

@property (strong, nonatomic) TTPickFriend* mFriend;

@property (nonatomic, assign) id<checkdelegate> delegate;

@property int mRow;


- (id)initCustom;

- (void)DrawCheckBox;

- (void)ChangeState;

- (void)SetObject:(TTPickFriend*)object nRowIndex:(int)Value;

@end
