#import "CustomTextFiled.h"

@implementation CustomTextFiled

@synthesize  target = _target;

-(id)initWithCustFrame:(CGRect)frame target:(id)target
{
    self = [super initWithFrame:frame];
    if(self)
  {
      self.keyboardType = UIKeyboardTypeDefault;
      self.returnKeyType = UIReturnKeyDefault;
      [self setFont:[UIFont systemFontOfSize:15.0f]];
      self.backgroundColor = [UIColor whiteColor];
      self.target = target;
      [self setDelegate:target];
      
  }
    return self;

}

+(id)CustomViewWithFrame:(CGRect)frame target:(id)target
{
    return [[CustomTextFiled alloc] initWithCustFrame:frame target:target];
}






@end
