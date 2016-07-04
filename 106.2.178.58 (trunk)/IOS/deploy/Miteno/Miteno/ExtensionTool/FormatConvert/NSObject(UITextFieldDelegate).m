#import "NSObject(UITextFieldDelegate).h"
#import "UIView(category).h"

@implementation NSObject(UITextFieldDelegate)

int movementDistance0 = 0;
const float movementDuration0 = 0.3f;


-(void)textFieldDidBeginEditing:(UITextField *)textField
{
    movementDistance0 = 0;
    UIViewController* ctr = [textField viewController];
    CGRect convertRect =  [textField.superview convertRect:textField.frame toView:ctr.view];
    NSLog(@"origin.y = %f",convertRect.origin.y);
    
    
    float textY = convertRect.origin.y+convertRect.size.height;
    float bottomY = ctr.view.frame.size.height-textY;
    
    NSLog(@"bottomy = %f",bottomY);
    
    
    if(bottomY <= 236)
    {
        movementDistance0 = 236 - bottomY;
    
    }
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
    
    UIViewController* ctr = [textField viewController];
    ctr.view.frame = CGRectOffset(ctr.view.frame, 0, movement);
    [UIView commitAnimations];
}


@end
