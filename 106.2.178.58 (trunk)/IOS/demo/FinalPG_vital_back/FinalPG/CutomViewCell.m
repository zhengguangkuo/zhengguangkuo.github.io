//
//  ImageTableViewCell.m
//  NSOperationTest
//
//  Created by jhwang on 11-10-30.
//  Copyright 2011年 __MyCompanyName__. All rights reserved.
//
#import "CutomViewCell.h"
#import "SaleDetail.h"
#import "UIImageView+DispatchLoad.h"
#import "FileManager.h"


@implementation CutomViewCell
@synthesize  nameLabel = nameLabel;
@synthesize  artistLabel = artistLabel;
@synthesize  cusImageview = cusImageview;
//@synthesize  cellBlock = _cellBlock;

- (void)dealloc
{
    [nameLabel    release];
    [artistLabel  release];
    [cusImageview release];
    [super dealloc];
}



- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
        [self LoadCellImage];
        
        [self LoadCellLabel];
    }
    return self;
}


-(void)LoadCellImage
{
    self.cusImageview = [[UIImageView alloc] initWithFrame:CGRectMake(8, 5, 64, 64)];
    CALayer* layer = [self.cusImageview layer];
    
    [layer setMasksToBounds:YES];
    
    [layer setCornerRadius:8.0];
    
    [layer setBorderWidth:1];
    
    [layer setBorderColor:[[UIColor blackColor] CGColor]];
    
    [self.contentView addSubview:self.cusImageview];
    [cusImageview release];
}



-(void)LoadCellLabel
{
    self.nameLabel = [[UILabel alloc] initWithFrame:CGRectMake(80, 5, 240, 30)];
    
    [self.contentView addSubview:self.nameLabel];
    [nameLabel release];
    
    self.artistLabel = [[UILabel alloc] initWithFrame:CGRectMake(80, 35, 240, 30)];
    
    [self.contentView addSubview:self.artistLabel];
    [artistLabel release];
}




-(void)setDetailObject:(SaleDetail*)Object
{
    [self.nameLabel setText:Object.saleName];
    [self.artistLabel setText:Object.saleArtist];
    [self.cusImageview setImage:nil];
    
    NSString*  fullname = [FileManager GetFullPathofFile:Object.saleImage];
    if([FileManager FileExistCheck:Object.saleImage])
    {
        UIImage *avatarImage = [UIImage imageWithContentsOfFile:fullname];
        
        if (avatarImage)
        {
            [self.cusImageview setImage:avatarImage];
        }
    }
}







- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

}

@end
