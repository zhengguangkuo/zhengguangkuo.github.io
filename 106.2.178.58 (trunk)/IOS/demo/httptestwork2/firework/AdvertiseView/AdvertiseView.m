//
//  AdvertiseView.m
//  firework
//
//  Created by guorong on 14-2-17.
//  Copyright miteno 2014年. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "AdvertiseView.h"

@interface AdvertiseView() <TouchControllerDelegate>

@property  (nonatomic, strong)  UIPageControl*  dotVC;

@property  (nonatomic, strong)  NSMutableArray* imageArray;

@property  (nonatomic, strong)  NSTimer*  timer;

@property  (nonatomic, assign)  BOOL bFlag;

@property  (nonatomic, assign)  CGFloat timerValue;

@property  (nonatomic, assign)  int  currentIndex;

@property  (nonatomic, strong)  NSString* defaultName;

@property  (nonatomic, strong)  UIImageView*  defaultIndexPage;

- (void)LoadDefaultIndexPage;

- (void)LoadImageViewArray:(NSArray*)NameArray;

- (void)ViewNextImage;

- (void)ViewForwardImage;

- (void)LoadPageDotView;

- (void)AdverViewInTurn;

- (void)checkNumberAvailable;

- (void)start;

@end



@implementation AdvertiseView

@synthesize imageArray = imageArray;

@synthesize dotVC = _dotVC;

@synthesize timer = _timer;

@synthesize bFlag = _bFlag;

@synthesize timerValue = _timerValue;

@synthesize currentIndex = _currentIndex;

@synthesize defaultName = _defaultName;

@synthesize defaultIndexPage = _defaultIndexPage;

#pragma mark- AdvertiseView loadsubview.
//初始化广告视图，入口参数:图片url数组和默认的占位图
- (id)initWithFrame:(CGRect)frame  defaultImage:(NSString*)name
{
    self = [super initWithFrame:frame];
    if(self)
    {
        self.userInteractionEnabled = YES;
        self.clipsToBounds = YES;
        self.bFlag = FALSE;
        self.timerValue = 0.0f;
        self.currentIndex = 0;
        self.defaultName = name;
        self.imageArray = [[NSMutableArray alloc] init];
        [self LoadDefaultIndexPage];
        [self LoadPageDotView];
        [self addObserver:self forKeyPath:@"imageArray" options:NSKeyValueObservingOptionNew|NSKeyValueObservingOptionOld context:NULL];
    }
    return self;
}

//kvo监视队列任务是否全部完成
-(void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void *)context
{
    if (object == self && [keyPath isEqualToString:@"imageArray"])
    {
            dispatch_async(dispatch_get_main_queue(),
     ^{
           int n = [self arrayCount];
        if(self.defaultIndexPage.superview&&n>1)
       {
           [self.defaultIndexPage removeFromSuperview];
           [self start];
       }
      });
        
    }
    
}

//重写add和remove方法否则无法使用kvo
- (void)removeObject:(id)obj
{
    NSMutableArray* arrayName = [self mutableArrayValueForKey:@"imageArray"];
    [arrayName removeObject: obj];
}

- (void)addObject:(id)obj
{
    NSMutableArray* arrayName = [self mutableArrayValueForKey:@"imageArray"];
    [arrayName addObject:obj];
}

- (int)arrayCount
{
    NSMutableArray* arrayName = [self mutableArrayValueForKey:@"imageArray"];
    return [arrayName count];
}








//当无图片下载完成时候载入默认页
- (void)LoadDefaultIndexPage
{
    UIImage*  image = [UIImage imageNamed:self.defaultName];
    self.defaultIndexPage = [[UIImageView alloc] initWithImage:image];
    [_defaultIndexPage setFrame:self.bounds];
    [self addSubview:self.defaultIndexPage];
}


//启动计时器
-(void)start
{
    if([self.timer isValid])
    {
        [self.timer invalidate];
        _timer = nil;
    }
    _timer = [NSTimer scheduledTimerWithTimeInterval:1 target:self selector:@selector(AdverViewInTurn) userInfo:nil repeats:YES];
}




//载入数据
-(void)LoadData:(NSArray *)NameArray
{
    [self LoadImageViewArray:NameArray];
}

//分页控制器(圆点)
- (void)LoadPageDotView
{
    self.dotVC = [[UIPageControl alloc] init];
    [_dotVC setFrame:CGRectMake(0, self.bounds.size.height - 30 , self.bounds.size.width, 10)];
    [_dotVC setUserInteractionEnabled:NO];
    [_dotVC setCurrentPage:0];
    [_dotVC setNumberOfPages:0];
    [self addSubview:_dotVC];
}

//根据url下载图片
- (void)LoadImageViewArray:(NSArray*)nameArray
{
    for(int i = 0; i<[nameArray count]; i++)
    {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0),
     ^{
        
        CarousImageView*  imageView =  [[CarousImageView alloc] initWithMyFrame:self.bounds x:nameArray[i] Tag:i block:^(id object) {
            if(![object  isKindOfClass:[NSNull class]])
         {
            UIView* tempview = (UIView*)object;
            [self insertSubview:tempview atIndex:0];
            [self addObject:object];
         }
            dispatch_async(dispatch_get_main_queue(),
        ^{
            _dotVC.currentPage = 0;
            _dotVC.numberOfPages = [self arrayCount];
         });
            
        }];
        
        
        [imageView setControllerDelegate:self];
        [imageView setImage:[UIImage imageNamed:_defaultName]];
        [imageView downLoadImage];
       
     });
        
    }
}
#pragma mark------------------------------------------





#pragma mark- AdvertiseView imageview take on in turn.
//图片轮播
- (void)AdverViewInTurn
{
    self.timerValue+=1.0;
    
    if(self.timerValue>=2.0)
    {
        if(!_bFlag){
            [self ViewNextImage];
        }
        else{
            [self ViewForwardImage];
        }
        self.timerValue = 0.0;
        [self checkNumberAvailable];
    }
    
}



//滚动到下一张图片
- (void)ViewNextImage
{
    if(self.dotVC.numberOfPages>=2)
    {
      
        int n = ++_currentIndex;
        
        self.dotVC.currentPage = n%[self arrayCount];
        
        if([self arrayCount]>1)
      {
        UIView*  subview = [self.subviews objectAtIndex:([self arrayCount] - 1)];
        [self insertSubview:subview atIndex:0];
      }
    }
    
}


//滚动到前一张图片
- (void)ViewForwardImage
{
    if(self.dotVC.numberOfPages>=2)
    {
        int n = --_currentIndex;
        
        self.dotVC.currentPage = n%[self arrayCount];
        
        if([self arrayCount]>1)
      {
        UIView*  subview = [self.subviews objectAtIndex:0];
        [self insertSubview:subview atIndex:([self arrayCount] - 1)];
      }
    }
    
}


//根据下载完成的图片设置分页数量
- (void)checkNumberAvailable
{
    if(self.dotVC.numberOfPages<[self arrayCount])
    {
        self.dotVC.numberOfPages = 0;
        for(UIImageView* imageView in self.imageArray)
        {
            UIImage*  viewImage = [imageView image];
            if(![viewImage isEqual:[UIImage imageNamed:_defaultName]]){
                ++self.dotVC.numberOfPages;
            }
        }
    }
}
#pragma mark------------------------------------------


#pragma mark- AdvertiseView touch delegate method.
//实现自定义的委托方法实现
-(void)TouchesBeganEvent:(CarousImageView*)controller
{
    NSLog(@"touchbegin");
    self.timerValue = 0.0f;
    [self.timer setFireDate:[NSDate distantFuture]];
}

-(void)TouchesEndEvent: (CarousImageView*)controller
{
    NSLog(@"endtouch");
    self.timerValue = 0.0f;
    [self.timer setFireDate:[NSDate date]];
    
}

-(void)TouchesMoveEvent:(CarousImageView *)controller direction:(UITouchDirection)direction
{
    if(direction==kTouchLeft)
    {
        NSLog(@"向左移动");
        [self ViewForwardImage];
        _bFlag = TRUE;
    }
    else
    {
        NSLog(@"向右移动");
        [self ViewNextImage];
        _bFlag = FALSE;
    }
    self.timerValue = -1.0;
    [self.timer setFireDate:[NSDate date]];
}
#pragma mark------------------------------------------



@end

