//
//  NewFeatureViewController.m
//  Miteno
//
//  Created by wg on 14-3-28.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "NewFeatureViewController.h"
#import <QuartzCore/QuartzCore.h>
#define kCount 2    //页数
@interface NewFeatureViewController ()
{
    UIPageControl   *_pageControl;
    UIButton        *_share;
}
@end

@implementation NewFeatureViewController

#pragma mark 自定义控制器的view
- (void)loadView
{
    UIImageView *imageView = [[UIImageView alloc] init];
    imageView.frame = [UIScreen mainScreen].bounds;
    imageView.image = [UIImage fullscreenImageWithName:@"new_feature_background.png"];
    imageView.userInteractionEnabled = YES;
    self.view = imageView;
}

#pragma mark view加载完毕
- (void)viewDidLoad
{
    [super viewDidLoad];
    
    CGSize viewSize = self.view.bounds.size;
    
    //加载UIScrollView
    UIScrollView *scrollView = [[UIScrollView alloc] init];
    scrollView.frame = self.view.bounds;
    scrollView.showsHorizontalScrollIndicator = NO;
    scrollView.pagingEnabled = YES;
    scrollView.contentSize = CGSizeMake(kCount * viewSize.width, 0);
    scrollView.delegate = self;
    [self.view addSubview:scrollView];
    
    //添加UIImageView
    for (int i = 0; i<kCount; i++) {
        [self addImageViewAtIndex:i inView:scrollView];
    }
    
    //加载UIPageControl
    UIPageControl *pageControl = [[UIPageControl alloc] init];
    pageControl.center = CGPointMake(viewSize.width * 0.5, viewSize.height * 0.95);
    pageControl.bounds = CGRectMake(0, 0, 100, 0);
    pageControl.numberOfPages = kCount;
//    pageControl.pageIndicatorTintColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"dot_black.png"]];
//    pageControl.currentPageIndicatorTintColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"dot_orenge.png"]];
    pageControl.userInteractionEnabled = NO;
    [self.view addSubview:pageControl];
    _pageControl = pageControl;
}

#pragma mark 添加scrollview里面的imageview
- (void)addImageViewAtIndex:(int)index inView:(UIView *)view
{
    CGSize viewSize = self.view.frame.size;
    
    //创建imageview
    UIImageView *imageView = [[UIImageView alloc] init];
    imageView.frame = (CGRect){{index * viewSize.width, 0} , viewSize};
    //imageView.frame = CGRectMake(index * viewSize.width, 0, viewSize.width, viewSize.height);
    
    //设置图片
    NSString *name = [NSString stringWithFormat:@"help%d.png", index + 1];
    
    imageView.image = [UIImage imageNamed:name];
    //添加
    [view addSubview:imageView];
    
    //判断最后一页添加按钮（分享、开始）
    if (index == kCount - 1) {
        [self addBtnInView:imageView];
    }
}

#pragma mark 添加按钮
- (void)addBtnInView:(UIView *)view
{
    CGSize viewSize = self.view.frame.size;
    view.userInteractionEnabled = YES;
    /*
     开始
     */
    UIButton *start = [UIButton buttonWithType:UIButtonTypeCustom];
    [view addSubview:start];
    //设置背景图片
    CGSize startSize = [start setAllStateBg:@"new_feature_finish_button.png"];
    //    UIImage *startNormal = [UIImage imageNamed:@"new_feature_finish_button.png"];
    //    UIImage *startHighlighted = [UIImage imageNamed:@"new_feature_finish_button_highlighted.png"];
    //    [start setBackgroundImage:startNormal forState:UIControlStateNormal];
    //    [start setBackgroundImage:startHighlighted forState:UIControlStateHighlighted];
    start.center = CGPointMake(viewSize.width * 0.5, viewSize.height * 0.85);
    start.bounds = (CGRect){CGPointZero, startSize};
    // start.bounds = CGRectMake(0, 0, startNormal.size.width, startNormal.size.height);
    //监听
    [start addTarget:self action:@selector(start) forControlEvents:UIControlEventTouchUpInside];
    
    /*
     分享
     */
    UIButton *share = [UIButton buttonWithType:UIButtonTypeCustom];
    [view addSubview:share];
    //设置背景图片
    UIImage *shareNormal = [UIImage imageNamed:@"new_feature_share_true.png"];
    [share setBackgroundImage:shareNormal forState:UIControlStateNormal];
     [share setBackgroundImage:[UIImage imageNamed:@"new_feature_share_falses.png"] forState:UIControlStateSelected];
    share.center = CGPointMake(viewSize.width * 0.5, viewSize.height * 0.75);
    share.bounds = (CGRect){CGPointZero, shareNormal.size};
    [share addTarget:self action:@selector(share) forControlEvents:UIControlEventTouchUpInside];
    share.adjustsImageWhenHighlighted = NO;
    //默认选中
    share.selected = YES;
    _share = share;
}

#pragma mark 分享
- (void)share
{
    _share.selected = !_share.isSelected;
    MyLog(@"分享");
}

#pragma mark 开始 判断是否登录过
- (void)start
{
    if (_startBlock) {
        _startBlock(_share.isSelected);
    }
}

#pragma mark scrollView滚动代理
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    _pageControl.currentPage = scrollView.contentOffset.x / scrollView.frame.size.width;
}
@end