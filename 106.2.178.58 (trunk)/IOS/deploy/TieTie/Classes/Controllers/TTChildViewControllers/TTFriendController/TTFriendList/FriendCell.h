//
//  ImageTableViewCell.h
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
@class TTFriend;

@interface FriendCell : UITableViewCell{

}
@property (weak, nonatomic) IBOutlet UIImageView *headpic;
@property (weak, nonatomic) IBOutlet UIButton *giftButton;
@property (weak, nonatomic) IBOutlet UIButton *editButton;
@property (weak, nonatomic) IBOutlet UILabel *friendNick;

@property (strong, nonatomic) TTFriend* mFriend;

-(id)initCustom;
-(void)SetObject:(TTFriend*)object;

@end
