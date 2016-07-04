#import "NSObject(UITextFieldDelegate).h"
#import "AppDelegate.h"

@implementation NSObject(UITextFieldDelegate)

const int movementDistance0 = 80;
const float movementDuration0 = 0.3f;


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
    int movement = (up?-movementDistance0:movementDistance0);
    [UIView beginAnimations:@"anim" context:nil];
    [UIView setAnimationBeginsFromCurrentState:YES];
    [UIView setAnimationDuration:movementDuration0];
    NSLog(@"End");
    
    AppDelegate* app = [AppDelegate getApp];
    app.window.frame = CGRectOffset(app.window.frame, 0, movement);
    [UIView commitAnimations];
}

@end
