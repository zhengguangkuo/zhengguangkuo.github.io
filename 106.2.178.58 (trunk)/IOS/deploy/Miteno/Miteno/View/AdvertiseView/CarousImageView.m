//
//  CarousImageView.m
//  M-Pay
//
//  Created by guorong on 14-2-24.
//  Copyright miteno 2014年. All rights reserved.
//

#import "CarousImageView.h"
#import "UIImageView+DispatchLoad.h"


@interface CarousImageView()

@property  (nonatomic, strong)  NSString*  imageUrl;

@property  (nonatomic, copy)  void (^finishblock)(id object);

@end


@implementation CarousImageView
@synthesize imageUrl = _imageUrl;
@synthesize controllerDelegate;
@synthesize finishblock = _finishblock;
@synthesize nTag;

CGPoint  TouchPoint;
CGPoint  MovePoint;

#pragma mark- imageview init and download image.
//初始化方法，入口参数：图片URL地址，以及tag表示
-(id)initWithMyFrame:(CGRect)aRect  x:(NSString*)ImageName Tag:(int)tag block:(void(^)(id object))finish
{
	self = [super initWithFrame:aRect];
    self.nTag = tag;
    self.imageUrl = ImageName;
    self.userInteractionEnabled = YES;
    self.layer.masksToBounds = YES;//设为NO去试试
    self.layer.shadowColor = [UIColor blackColor].CGColor;
    self.layer.shadowOffset = CGSizeMake(2.0f,2.0f);   
    self.finishblock = finish;
    
    self.layer.shadowRadius = 2.0;
    self.clipsToBounds = NO;
    [self becomeFirstResponder];
    return self;
}

//根据url下载图片
-(void)downLoadImage
{
    NSLog(@"dfdfd");
   [self setImageFromUrl:_imageUrl completion:_finishblock];
}
#pragma mark------------------------------------------


#pragma mark- imageview touch method.
//触摸方法
- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    NSSet *allTouches = [event allTouches];
	int  nCount = [allTouches  count];
    
    switch (nCount)
    {
        case 1:
        {
            UITouch *touch1 = [[allTouches allObjects] objectAtIndex:0];
            TouchPoint = [touch1 locationInView: self.superview];
            if([self.controllerDelegate respondsToSelector:@selector(TouchesBeganEvent:)])
            [self.controllerDelegate TouchesBeganEvent:self];
        }
            break;
        
        default:
            break;
    }      
    

}


- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
    NSSet *allTouches = [event allTouches];
	int  nCount = [allTouches  count];
    
    switch (nCount)
{
        case 1:
      {
            UITouch *touch1 = [[allTouches allObjects] objectAtIndex:0];
            MovePoint = [touch1 locationInView: self.superview];
            
            int delta = MovePoint.x - TouchPoint.x;
            
            if(delta>=3)
            {
                if([self.controllerDelegate respondsToSelector:@selector(TouchesMoveEvent:direction:)])
                [self.controllerDelegate TouchesMoveEvent:self
                                direction:kTouchRight];
            }
            else
            if(delta<=-3)
            {
                if([self.controllerDelegate respondsToSelector:@selector(TouchesMoveEvent:direction:)])
                [self.controllerDelegate TouchesMoveEvent:self
                                direction:kTouchLeft];
            }
                else
            {
                if([self.controllerDelegate respondsToSelector:@selector(TouchesEndEvent:)])
                [self.controllerDelegate TouchesEndEvent:self];
            }
      }
          break;
            
        default:
            break;
   }
}

#pragma mark------------------------------------------


#pragma mark- imageview guesture recognizer method.
//手势识别的代理方法
- (BOOL) gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch
{
    return NO;
}

- (BOOL) gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldRecognizeSimultaneouslyWithGestureRecognizer:(UIGestureRecognizer *)otherGestureRecognizer
{
    return NO;
}


- (BOOL) gestureRecognizerShouldBegin:(UIGestureRecognizer *)gestureRecognizer
{
    return NO;
}

#pragma mark------------------------------------------

@end

