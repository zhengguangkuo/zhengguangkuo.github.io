#import "UITextFiledController.h"
#import "AppDelegate.h"

@interface UITextFiledController()

@end


@implementation UITextFiledController
@synthesize txtFiled;

const int movementDistance = 80;
const float movementDuration = 0.3f;


- (id)initWithCustomFrame:(CGRect)frame{
    self = [super init];
    if(self)
  {
    self.txtFiled = [[UITextField alloc] initWithFrame:frame];
      
    self.txtFiled.keyboardType = UIKeyboardTypeDefault;
    self.txtFiled.returnKeyType = UIReturnKeyDefault;
    self.txtFiled.delegate = self;
    [self.txtFiled setFont:[UIFont systemFontOfSize:15.0f]];
    self.txtFiled.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:self.txtFiled];
      [self.view setBackgroundColor:[UIColor clearColor]];
  
  }
    return self;
}


-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    NSLog(@"begin .....");
    [self animateTextField:textField up:YES];
}




- (void)textFieldDidEndEditing:(UITextField *)textField
{
    [self animateTextField:textField up:NO];
}




- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    NSLog(@"should return");
    return YES;
}


-(void)animateTextField:(UITextField *)textField up:(BOOL)up
{
    int movement = (up?-movementDistance:movementDistance);
    [UIView beginAnimations:@"anim" context:nil];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:movementDuration];
    NSLog(@"End");
    
    AppDelegate* app = [AppDelegate getApp];
    app.window.frame = CGRectOffset(app.window.frame, 0, movement);
    [UIView commitAnimations];
}





@end
