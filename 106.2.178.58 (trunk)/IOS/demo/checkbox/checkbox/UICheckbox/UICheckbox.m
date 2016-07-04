//
//  UIProgressHud.m
//  firesisiter
//
//  Created by guorong on 14-1-21.
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

//初始化checkview容器，传参tag标示
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

//将checkview加入容器
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

//返回所有被选中的checkview的下标
- (NSArray* )getSelectedItemsIndex
{
    return self.GroupIndexesArray;
}

//将选中的checkview重置为unselected状态
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

//改变checkview状态，selected -> unselected, unselected -> selected
//同时将selected状态的下标加入数组
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

//初始化checkview,同时传参标示
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

//checkview控件的绘制
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

//绘制checkview勾选以及未勾选状态
- (void)setSelected:(BOOL)bFlag
{
    self.bCheckFlag = bFlag;
    UIImage* selectedImage = SelectPic(bFlag, @"checkbox_normal.png", @"checkbox_pressed.png");
    
    [_checkButton setImage:selectedImage forState:UIControlStateNormal];
}

//checkview添加容器标示
- (void)setGroupTag:(int)Tag
{
    self.nArrayTag = Tag;
}

- (NSString*)getCheckedText
{
    return checkText;
}

//返回checkview路径，包括自己的标示和所在容器的标示
- (NSIndexPath*)getCheckedIndexpath
{
    self.ItemPath = [NSIndexPath indexPathForRow:self.nIndex inSection:self.nArrayTag];
    return ItemPath;
}

//返回checkview是否被选中
- (BOOL)isChecked
{
    return bCheckFlag;
}

//点击事件调用委托
-(void)checkEvent:(id)sender
{
    if(self.checkblock)
        self.checkblock(self);
    
    if([self.delegate respondsToSelector:@selector(checkSelected:checked:path:)])
      [self.delegate checkSelected:[self getCheckedText] checked:[self isChecked] path:[self getCheckedIndexpath]];
}

@end
