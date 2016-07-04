//
//  TTTTShareTableView.h
//  Miteno
//
//  Created by wg on 14-6-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Area.h"
#import "cateGory.h"
#define kSpaceTab 0
#define kLeftRegionRow     @"leftRegionRow"
#define kRightRegionRow    @"rightRegionRow"
#define kLeftCateRow       @"leftCateRow"
#define kRightCateRow      @"rightCateRow"
#define rightArea          @"rightArea"
#define rightCat           @"rightCat"
typedef enum{
    kTTShareTableViewRegion = 100,
    kTTShareTableViewCategory,
    kTTShareTableViewOrder,
}kTTShareTableView;
@protocol TTShareTableViewDelegate <NSObject>
//- (void)clickResultText:(NSString *)text codeLevel:(NSString *)codeLevel flagTag:(int)flagTag;
@optional
- (void)didChangeContentSuper:(id)supContent indexPath:(NSIndexPath *)indexPath;   //返回父级编码
- (void)didChangeContent:(id)childcontent indexPath:(NSIndexPath *)indexPath;   //返回一级内容
//- (void)didChangeContent:(id)childcontent indexPath:(NSIndexPath *)indexPath all:(NSArray *)all;   //返回一级内容
@end
@interface TTShareTableView : UIView
@property (nonatomic, strong) UITableView     * regionView;
@property (nonatomic, strong) UITableView     * categoryView;
@property (nonatomic, strong) UITableView     * orderView;

@property (nonatomic, strong) NSArray         * leftDatas;   //左边地区数据
@property (nonatomic, strong) NSMutableArray  * rightDatas;  //左边分类数据
@property (nonatomic, assign) CGRect            barFrame;
@property (nonatomic, copy) NSString          * selectAreaCode;   //右边边默认选定行号
@property (nonatomic, copy) NSString          * selectCateCode;  //左边默认选定行号

@property (nonatomic, assign) id <TTShareTableViewDelegate>delegate;

-(void)hideDropDown:(UIView *)bar;
//- (void)addClildsView:(CGRect)frame index:(int)index;
- (void)addClildsView:(CGRect)frame index:(int)index data:(NSMutableArray *)data;
@end
