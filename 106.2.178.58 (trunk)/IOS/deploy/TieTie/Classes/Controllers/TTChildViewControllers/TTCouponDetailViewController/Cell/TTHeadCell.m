//
//  TTHeadCell.m
//  Miteno
//
//  Created by wg on 14-7-2.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTHeadCell.h"
#import "TTCouponDetail.h"
#import "TTHeadCellFrame.h"
#define kActDetailSpace 5
@implementation TTHeadCell

+ (instancetype)headCellWithTableView:(UITableView *)tableView
{
    static NSString *ID = @"TTHeadCell";
    TTHeadCell *cell = [tableView dequeueReusableCellWithIdentifier:ID];
    
    if (cell == nil) {
        cell = [[TTHeadCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:ID];
        cell.backgroundColor = [UIColor clearColor];
        UIImageView *img = [[UIImageView alloc] initWithImage:[UIImage stretchImageWithName:@"graybg_b2_border_radius"]];
        cell.backgroundView = img;
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        
    }
    
    return cell;
}
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        
        [self addChildViews];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    return self;
}
- (void)addChildViews
{
    _couTitleLabel = [[UILabel alloc]init];
    [self.contentView addSubview:_couTitleLabel];
    
    _couSubTitleLabel = [[UILabel alloc] init];
    [self.contentView addSubview:_couSubTitleLabel];
    
    _couDisContent = [[UILabel alloc] init];
//    [self.contentView addSubview:_couDisContent];
}
- (void)setHeadMegFrame:(TTHeadCellFrame *)headMegFrame
{
    _headMegFrame = headMegFrame;
    
    TTCouponDetail *headDisMeg = _headMegFrame.headMeg;
    
    //设置数据
    _couTitleLabel.text = headDisMeg.actName;
    //领用多少张
//    if (headDisMeg.sendType.intValue == 0) {
//    
//        _couSubTitleLabel.text = [NSString stringWithFormat:@"领用%@张",headDisMeg.Rule];
//    }
    _couSubTitleLabel.textAlignment = NSTextAlignmentRight;

    //活动内容
    _couDisContent.text = headDisMeg.actDetail;

    if ([headDisMeg.actDetail rangeOfString:@"<"].location != NSNotFound) {
        dispatch_async(dispatch_get_main_queue(), ^{
           self.web = [[UIWebView alloc] init];
            self.web.delegate = self;
            self.web.backgroundColor = [UIColor whiteColor];
            self.web.frame = CGRectMake(0, 0,self.width,self.height);
            [self.web loadHTMLString:headDisMeg.actDetail baseURL:nil];
            [self.contentView addSubview:_web];    // *** 将UI操作放到主线程中执行 ***
             _couDisContent.frame = CGRectMake(0, 0, self.width,150 );
        });

    }else{
    _couDisContent.numberOfLines = 0;
    [_couDisContent setFont:kHeadFont];
        _couDisContent.frame = headMegFrame.couDisContent;
    }
    [_couSubTitleLabel setFont:kHeadSubFont];
    _couSubTitleLabel.textColor = [UIColor grayColor];
    //设置frame
    _couTitleLabel.frame = headMegFrame.couTitleLabel;
    _couSubTitleLabel.frame = headMegFrame.couSubTitleLabel;

}
- (void)setFrame:(CGRect)frame
{
    frame.origin.x = kActDetailSpace;
    frame.size.width -= 2*kActDetailSpace;
    [super setFrame:frame];
}

- (void)awakeFromNib
{
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
