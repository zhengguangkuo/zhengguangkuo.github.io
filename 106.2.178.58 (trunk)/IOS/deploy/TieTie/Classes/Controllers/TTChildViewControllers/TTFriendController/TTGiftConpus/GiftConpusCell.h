//
//  ImageTableViewCell.h
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>
@class TTConpus;

@interface GiftConpusCell : UITableViewCell{

}
@property (weak, nonatomic) IBOutlet UIImageView *LeftImageView;
@property (weak, nonatomic) IBOutlet UILabel *ConpusName;
@property (weak, nonatomic) IBOutlet UILabel *ActiveName;

@property (weak, nonatomic) IBOutlet UILabel *ApplyCount;

@property (weak, nonatomic) IBOutlet UILabel *ExpireData;
@property (strong, nonatomic) TTConpus* mTTConpus;

-(id)initCustom;
-(void)SetGiftObject:(TTConpus*)object;

@end
