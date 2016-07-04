//
//  CycleScrollView.m
//  PagedScrollView
//
//  Created by  on 14-1-23.
//  Copyright (c) 2014年 Apple Inc. All rights reserved.
//

#import "CycleScrollView.h"
#import "NSTimer+Addition.h"
#define kAppFrame [UIScreen mainScreen].applicationFrame

#define  kScreenBounds    [UIScreen mainScreen].bounds

#define ScreenHeight [[UIScreen mainScreen] bounds].size.height

#define ScreenWidth [[UIScreen mainScreen] bounds].size.width
#define RGBA(r,g,b,a) [UIColor colorWithRed:r/255.0 green:g/255.0 blue:b/255.0 alpha:a]
@interface CycleScrollView () <UIScrollViewDelegate>
{
    UIPageControl   *_pageControl;
    UIView          *_bgView;
}
@property (nonatomic , assign) NSInteger currentPageIndex;
@property (nonatomic , assign) NSInteger totalPageCount;
@property (nonatomic , strong) NSMutableArray *contentViews;
@property (nonatomic , strong) UIScrollView *scrollView;


@property (nonatomic , assign) NSTimeInterval animationDuration;

- (void)animationTimerDidFired:(NSTimer *)timer;

@end

@implementation CycleScrollView

- (void)setTotalPagesCount:(NSInteger (^)(void))totalPagesCount
{
    _totalPageCount = totalPagesCount();
    if (_totalPageCount >= 0) {
        [self configContentViews];
    }
    if (_totalPageCount > 1) {
        _pageControl.numberOfPages = _totalPageCount;
        [self.animationTimer resumeTimerAfterTimeInterval:self.animationDuration];
    } else {
        _pageControl.numberOfPages = 0;
        [self.animationTimer pauseTimer];
    }
}

- (id)initWithFrame:(CGRect)frame animationDuration:(NSTimeInterval)animationDuration
{
    self = [self initWithFrame:frame];
    if (animationDuration > 0.0) {
        self.animationDuration = animationDuration;
        self.animationTimer = [NSTimer scheduledTimerWithTimeInterval:(self.animationDuration = animationDuration)
                                                               target:self
                                                             selector:@selector(animationTimerDidFired:)
                                                             userInfo:nil
                                                              repeats:YES];
        [self.animationTimer pauseTimer];
    }
    return self;
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        self.autoresizesSubviews = YES;
        self.scrollView = [[UIScrollView alloc] initWithFrame:self.bounds];
        self.scrollView.autoresizingMask = 0xFF;
        self.scrollView.contentMode = UIViewContentModeCenter;
//        self.scrollView.contentSize = CGSizeMake(3 * CGRectGetWidth(self.scrollView.frame), CGRectGetHeight(self.scrollView.frame));
        self.scrollView.delegate = self;
//        self.scrollView.contentOffset = CGPointMake(CGRectGetWidth(self.scrollView.frame), 0);
        self.scrollView.pagingEnabled = YES;
        self.scrollView.showsHorizontalScrollIndicator = NO;
        //添加
        _bgView = [[UIView alloc] init];
        _bgView.frame = CGRectMake(0, self.frame.size.height-40, ScreenWidth, 40);
        _bgView.backgroundColor = RGBA(0.06, 0.06, 0.06, 0.06);
        //设置page
        _pageControl = [[UIPageControl alloc] init];
        CGRect frame = self.frame;
        _pageControl.frame = CGRectMake(frame.size.width/2-50,frame.size.height-70, 100, 100);
//        _pageControl.pageIndicatorTintColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"dot_black.png"]];
//        _pageControl.currentPageIndicatorTintColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"dot_orenge.png"]];
//        _pageControl.numberOfPages = 0;
        _pageControl.userInteractionEnabled = NO;
        [self addSubview:self.scrollView];
//        [self addSubview:_bgView];
        [self addSubview:_pageControl];
        self.currentPageIndex = 0;
    }
    return self;
}

- (void)setDataForView:(NSInteger)count
{
    _pageControl.numberOfPages = count;
    if (count < 3) {
        self.scrollView.contentSize = CGSizeMake(count * CGRectGetWidth(self.scrollView.frame), CGRectGetHeight(self.scrollView.frame));
    } else {
        self.scrollView.contentSize = CGSizeMake(3 * CGRectGetWidth(self.scrollView.frame), CGRectGetHeight(self.scrollView.frame));
    }

    self.scrollView.contentOffset = CGPointMake(CGRectGetWidth(self.scrollView.frame), 0);

}

#pragma mark -
#pragma mark - 私有函数

- (void)configContentViews
{
    [self.scrollView.subviews makeObjectsPerformSelector:@selector(removeFromSuperview)];
    [self setScrollViewContentDataSource];
    
    NSInteger counter = 0;
    for (UIView *contentView in self.contentViews) {
        contentView.userInteractionEnabled = YES;
        UITapGestureRecognizer *tapGesture = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(contentViewTapAction:)];
        [contentView addGestureRecognizer:tapGesture];
        CGRect rightRect = contentView.frame;
        
//        rightRect.origin = CGPointMake(CGRectGetWidth(self.scrollView.frame) * (counter ++), 0);
        rightRect.origin = CGPointMake(CGRectGetWidth(self.scrollView.frame) * (_totalPageCount ==1 ? 1 : counter ++), 0);
        contentView.frame = rightRect;
        [self.scrollView addSubview:contentView];
    }
        [_scrollView setContentOffset:CGPointMake(_scrollView.frame.size.width, 0)];
}

/**
 *  设置scrollView的content数据源，即contentViews
 */
- (void)setScrollViewContentDataSource
{
    NSInteger previousPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex - 1];
    NSInteger rearPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex + 1];
    if (self.contentViews == nil) {
        self.contentViews = [@[] mutableCopy];
    }
    [self.contentViews removeAllObjects];
    
    if (self.fetchContentViewAtIndex) {
        if (_totalPageCount == 1) {
            [self.contentViews addObject:self.fetchContentViewAtIndex(_currentPageIndex)];
        } else if (_totalPageCount == 2){
            [self.contentViews addObject:self.fetchContentViewAtIndex(previousPageIndex)];
            [self.contentViews addObject:self.fetchContentViewAtIndex(_currentPageIndex)];
        } else {
            [self.contentViews addObject:self.fetchContentViewAtIndex(previousPageIndex)];
            [self.contentViews addObject:self.fetchContentViewAtIndex(_currentPageIndex)];
            [self.contentViews addObject:self.fetchContentViewAtIndex(rearPageIndex)];
            
        }
        
    }
}

- (NSInteger)getValidNextPageIndexWithPageIndex:(NSInteger)currentPageIndex;
{
    if(currentPageIndex == -1) {
        return self.totalPageCount - 1;
    } else if (currentPageIndex == self.totalPageCount) {
        return 0;
    } else {
        return currentPageIndex;
    }
}

#pragma mark -
#pragma mark - UIScrollViewDelegate

- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    [self.animationTimer pauseTimer];
}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
    [self.animationTimer resumeTimerAfterTimeInterval:self.animationDuration];
}

- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    if (_totalPageCount < 3) {
        return;
    }
    int contentOffsetX = scrollView.contentOffset.x;
    float width = 2 * CGRectGetWidth(scrollView.frame);
    if(contentOffsetX >= width) {
        self.currentPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex + 1];
        _pageControl.currentPage = self.currentPageIndex;
        [self configContentViews];
    }
    
    if(contentOffsetX <5) {
        self.currentPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex - 1];
        _pageControl.currentPage = self.currentPageIndex;
        [self configContentViews];
    }
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    int contentOffsetX = scrollView.contentOffset.x;
    NSInteger width = _totalPageCount == 2 ? CGRectGetWidth(scrollView.frame) : 2 * CGRectGetWidth(scrollView.frame);
    if(contentOffsetX >= width) {
        self.currentPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex + 1];
        _pageControl.currentPage = self.currentPageIndex;
//        MyLog(@"next，当前页:%d",self.currentPageIndex);
        [self configContentViews];
    }
    
    if(contentOffsetX <5) {
        self.currentPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex - 1];
//        MyLog(@"previous，当前页:%d",self.currentPageIndex);
        _pageControl.currentPage = self.currentPageIndex;
        [self configContentViews];
    }

    TTLog(@"scrollViewDelegate:%@",NSStringFromCGRect(scrollView.frame));
    [scrollView setContentOffset:CGPointMake(CGRectGetWidth(scrollView.frame), 0) animated:YES];
}

#pragma mark -
#pragma mark - 响应事件

- (void)animationTimerDidFired:(NSTimer *)timer
{
    if (_totalPageCount == 1) {
        [self.animationTimer setFireDate:[NSDate distantFuture]];
    }
    if (_totalPageCount < 3) {
        self.currentPageIndex = [self getValidNextPageIndexWithPageIndex:self.currentPageIndex+1];
        TTLog(@"previous，当前页:%d",self.currentPageIndex);
        _pageControl.currentPage = self.currentPageIndex;
    }
    NSInteger maxOffSet = _totalPageCount == 2 ? CGRectGetWidth(_scrollView.frame) : 2 * CGRectGetWidth(_scrollView.frame);
    CGPoint newOffset = CGPointMake(self.scrollView.contentOffset.x >= maxOffSet ? 0 : self.scrollView.contentOffset.x + CGRectGetWidth(self.scrollView.frame), self.scrollView.contentOffset.y);
    [self.scrollView setContentOffset:newOffset animated:YES];
}

- (void)contentViewTapAction:(UITapGestureRecognizer *)tap
{
    if (self.TapActionBlock) {
        self.TapActionBlock(self.currentPageIndex);
    }
}


@end
