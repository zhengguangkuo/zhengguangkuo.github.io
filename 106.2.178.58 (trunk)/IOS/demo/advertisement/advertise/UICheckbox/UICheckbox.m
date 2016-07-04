//
//  UICheckbox.m
//  advertise
//
//  Created by guorong on 14-2-11.
//  Copyright miteno 2014年. All rights reserved.
//

#import "UICheckbox.h"

@interface UICheckbox()

@property (nonatomic, strong) UIButton* checkButton;
@property (nonatomic, strong) NSString* checkText;
@property (nonatomic, assign) BOOL bCheckFlag;
@property (nonatomic, assign) NSIndexPath* ItemPath;
@property (nonatomic, assign) int  nArrayTag;
@property (nonatomic, assign) int  nIndex;

@end


@implementation CheckViewGroup

@synthesize CheckViewArray;

@synthesize GroupIndexesArray;

@synthesize style;

@synthesize nTag;


- (id)initWithTag:(int)Tag type:(UIGroupStyle)type
{
     self = [super init];
     if(self)
  {
      self.nTag = Tag;
      self.style = type;
      self.CheckViewArray = [NSMutableArray array];
      self.GroupIndexesArray = [NSMutableArray array];
  }
     return  self;
}

- (void)AddCheckView:(UICheckbox*)object
{
    [self.CheckViewArray addObject:object];
    [object setNArrayTag:self.nTag];
    
    if([object isChecked])
    [self.GroupIndexesArray addObject:
    [NSNumber numberWithInt:[object getCheckedIndexpath].row]];
    
    void (^block)(UICheckbox* checkbox) =
    ^(UICheckbox* checkbox)
    {
        NSIndexPath* path = [checkbox getCheckedIndexpath];
        
        if(path.section==self.nTag)
       {
          //int n = path.row;
          if(self.style==UICheckGroupBox)
              [self setSelectedItemsUnselected:checkbox];
          else
          if(self.style==UIMutipleGroupBox)
              ;
          
          [self checkSelectedItem:checkbox];
       }
    };
    
    [object setCheckblock:block];

}

- (NSArray*)getSelectedItemsIndex
{
    return self.GroupIndexesArray;
}


- (void)setSelectedItemsUnselected:(UICheckbox*)check
{
    for(id nIndexValue in self.GroupIndexesArray)
  {
      int n = [nIndexValue intValue];
      UICheckbox* checkbox = self.CheckViewArray[n];
      if(n!=check.getCheckedIndexpath.row)
      [checkbox setSelected:NO];
  }
    [self.GroupIndexesArray removeAllObjects];
}


- (void)checkSelectedItem:(UICheckbox*)checkbox
{
    UICheckbox* tempobject = checkbox;
    NSIndexPath* path = [checkbox getCheckedIndexpath];
    int index = [path row];
    
    BOOL bFlag = (tempobject.bCheckFlag?NO:YES);
    [tempobject setSelected:bFlag];
    
    if([tempobject isChecked])
    [self.GroupIndexesArray addObject:[NSNumber numberWithInt:index]];
}

@end


@implementation UICheckbox
@synthesize bCheckFlag;
@synthesize delegate;
@synthesize ItemPath;
@synthesize checkText;
@synthesize checkblock;


#define SelectPic(bflag,normal,selected) [UIImage imageNamed:((bflag==YES)?selected:normal)]

- (id)initWithFrame:(CGRect)frame text:(NSString*)text nTag:(int)tag
{
    CGRect tempFrame = CGRectMake(frame.origin.x, frame.origin.y, 100, 25);
    self = [super initWithFrame:tempFrame];
    if(self)
  {
    self.checkText = text;
    self.nIndex = tag;
    self.nArrayTag = 0;
    [self customsize];
  }
    return self;
}

- (void)customsize
{
    _checkButton = [UIButton buttonWithType:UIButtonTypeCustom];
    [_checkButton setFrame:CGRectMake(0, 4, 25, 25)];
    [_checkButton addTarget:self action:@selector(checkEvent:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:_checkButton];
    
    
    UILabel* textLabel= [[UILabel alloc] init];
    [textLabel setFrame:CGRectMake(35, 3, self.bounds.size.width - 30, 25)];
    [textLabel setFont:[UIFont systemFontOfSize:15.0f]];
    [textLabel setBackgroundColor:[UIColor clearColor]];
    [textLabel setTextColor:[UIColor blackColor]];
    [textLabel setText:self.checkText];
    [self addSubview:textLabel];
    
    [self setSelected:NO];
}

- (void)setSelected:(BOOL)bFlag
{
    self.bCheckFlag = bFlag;
    UIImage* selectedImage = SelectPic(bFlag, @"checkbox_normal.png", @"checkbox_pressed.png");
    
    [_checkButton setImage:selectedImage forState:UIControlStateNormal];
}

- (void)setGroupTag:(int)Tag
{
    self.nArrayTag = Tag;
}

- (NSString*)getCheckedText
{
    return checkText;
}

- (NSIndexPath*)getCheckedIndexpath
{
    self.ItemPath = [NSIndexPath indexPathForRow:self.nIndex inSection:self.nArrayTag];
    return ItemPath;
}

- (BOOL)isChecked
{
    return bCheckFlag;
}

-(void)checkEvent:(id)sender
{
    if(self.checkblock)
        self.checkblock(self);
    
    if([self.delegate respondsToSelector:@selector(checkSelected:checked:path:)])
      [self.delegate checkSelected:[self getCheckedText] checked:[self isChecked] path:[self getCheckedIndexpath]];
}

@end
