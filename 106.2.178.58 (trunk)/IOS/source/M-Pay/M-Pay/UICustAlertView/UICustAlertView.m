#import "UICustAlertView.h"
#import "UIButton(addtion).h"
#import "UIImage(addition).h"
#import "UIView(category).h"


@implementation UICustAlertView

@synthesize YesBlock = _YesBlock;

- (id)initWithAlertContent:(UIView*)content title:(NSString*)str
{
    self = [super initWithFrame:CGRectMake(0, 0, 300, 110)];
    
    
    if(self)
{
    [self setBackgroundColor:[UIColor blackColor]];
    
    UILabel* tempLabel = [[UILabel alloc] initWithFrame:CGRectMake(7, 4, 150, 30)];
    [tempLabel setBackgroundColor:[UIColor clearColor]];
    [tempLabel setTextColor:[UIColor whiteColor]];
    [tempLabel setText:str];
    [tempLabel setFont:[UIFont boldSystemFontOfSize:15]];
    [self addSubview:tempLabel];
    
    
    UIView*  bgInputView = [[UIView alloc] initWithFrame:CGRectMake(0, tempLabel.frame.origin.y + tempLabel.frame.size.height + 5, self.frame.size.width, content.frame.size.height + 4)];
    [bgInputView setBackgroundColor:[UIColor whiteColor]];
    [self addSubview:bgInputView];
    [bgInputView addSubview:content];

    UIView*  bgInputView2 = [[UIView alloc] initWithFrame:CGRectMake(0, bgInputView.frame.origin.y + bgInputView.frame.size.height, bgInputView.frame.size.width, bgInputView.frame.size.height + 2)];
    [self addSubview:bgInputView2];
    [bgInputView2 setBackgroundColor:[UIColor lightGrayColor]];
    
    UIImage* normalBg = [UIImage generateFromColor:[UIColor colorWithRed:0.9 green:0.9 blue:0.9 alpha:1]];
    
    
    UIButton* YesButton = [UIButton ButtonWithParms:[UIColor blackColor] title:@"确定" bgnormal:normalBg imgHighlight:nil target:self action:@selector(ClickYes)];
    
    [YesButton setFrame:CGRectMake(4, 5, bgInputView2.frame.size.width/2 - 6, 33)];
    
    [YesButton ViewWithBorder:[UIColor blackColor]];
    
    [bgInputView2 addSubview:YesButton];
    
    
    UIButton*  NoButton = [UIButton ButtonWithParms:[UIColor blackColor] title:@"取消" bgnormal:normalBg imgHighlight:nil target:self action:@selector(viewdismiss)];
    [NoButton setFrame:CGRectMake(YesButton.frame.origin.x + YesButton.frame.size.width + 4, 5, bgInputView2.frame.size.width/2 - 6, 33)];
    
    [NoButton ViewWithBorder:[UIColor blackColor]];
    
    [bgInputView2 addSubview:NoButton];
    
    [self setFrame:CGRectMake(0, 0, 300, bgInputView2.frame.origin.y + bgInputView2.frame.size.height)];
    
    [self CoverMask];
}
    
    return self;
}



-(void)ClickYes
{
    if(self.YesBlock)
  {
      self.YesBlock();
  }
    [self viewdismiss];
}

-(void)viewdismiss
{
    NSLog(@"dismiss herere");
    
    UIView* maskView = [[[UIApplication sharedApplication] keyWindow] viewWithTag:2];
    [maskView removeFromSuperview];
}




@end
