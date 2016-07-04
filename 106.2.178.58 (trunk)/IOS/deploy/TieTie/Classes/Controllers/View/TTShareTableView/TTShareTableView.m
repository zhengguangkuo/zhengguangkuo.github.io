//
//  TTTTShareTableView.m
//  Miteno
//
//  Created by wg on 14-6-9.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTShareTableView.h"
#define kBottomdis   ScreenHeight/2                     //tableview与底部间距
#define kAnimaDuration  0.3

#define kType 1       //全部类型
#define kOrders  2      //排序

#define TTAreaLevelPath   [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTAreaLevelList]
#define TTAreaLevelList @"TTAreaLevelList.plist"
#define TTCatLevelPath   [NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES)[0] stringByAppendingPathComponent:TTCateLevelList]
#define TTCateLevelList @"TTCateLevelList.plist"
@interface TTShareTableView()<UITableViewDataSource,UITableViewDelegate,UIGestureRecognizerDelegate>
{
    UIImageView        *  _bgView;       //背景图
    NSMutableArray     *  _areas;        //区域数据
    NSMutableArray     *  _areaRight;    //right数据
    NSMutableArray     *  _cates;        //类别数据
    NSMutableArray     *  _cateRight;    //right类别
    UIView             *  _replace;      //标记转换frame、
    
    NSUserDefaults     *  _userDefault;
    
    NSInteger             _leftRegionRow;
    NSInteger             _rightRegionRow;
    NSInteger             _leftCateRow;
    NSInteger             _rightCateRow;
}
@end
@implementation TTShareTableView
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.userInteractionEnabled = YES;

        _userDefault = [NSUserDefaults standardUserDefaults];
        
        _leftRegionRow = [[_userDefault objectForKey:kLeftRegionRow] integerValue];
        _leftCateRow = [[_userDefault objectForKey:kLeftCateRow] integerValue];
        _rightRegionRow = [[_userDefault objectForKey:kRightRegionRow] integerValue];
        _rightCateRow = [[_userDefault objectForKey:kRightCateRow] integerValue];
    }
    return self;
}
- (void)addClildsView:(CGRect)frame index:(int)index data:(NSMutableArray *)data
{
    TTLog(@" shareView = %d",data.count);
    //Addself
    CGFloat ownX = frame.origin.x;
    CGFloat ownY = CGRectGetMaxY(frame);
    CGFloat ownW = frame.size.width;
    self.frame = CGRectMake(ownX,ownY,ownW, 0);
    
    //imageView
    CGFloat bgW = self.frame.size.width - 2*kSpaceTab;
    CGFloat bgH = ScreenHeight - kBottomdis;
    _bgView = [[UIImageView alloc] initWithFrame:CGRectMake(kSpaceTab,0,bgW,0)];
    //        _bgView.image = [UIImage stretchImageWithName:@"login_input_bg"];
    self.backgroundColor = kGlobalBg;
    _bgView.userInteractionEnabled = YES;
    
    //区域
    CGFloat tabY = kSpaceTab + 2*kSpaceTab;
    _regionView = [[UITableView alloc] initWithFrame:CGRectMake(kSpaceTab,tabY,bgW/2,0)];
    _regionView.tag = kTTShareTableViewRegion;
    _regionView.delegate = self;
    _regionView.dataSource = self;
    UIImageView *view = [[UIImageView alloc] init];
    view.image = [UIImage stretchImageWithName:@"bg"];
    _regionView.backgroundView = view;
    _categoryView = [[UITableView alloc] initWithFrame:CGRectMake(_regionView.size.width+kSpaceTab,tabY,bgW/2,0)];
    
    //类别
    _categoryView.delegate = self;
    _categoryView.dataSource =self;
    _categoryView.tag = kTTShareTableViewCategory;
    _categoryView.separatorStyle = UITableViewCellSeparatorStyleNone;
    
    [_bgView addSubview:_regionView];
    [_bgView addSubview:_categoryView];
    
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:kAnimaDuration];
    self.frame = CGRectMake(ownX,ownY,ownW,ScreenHeight);
    _bgView.frame = CGRectMake(kSpaceTab,0,ScreenWidth-2*kSpaceTab,bgH);
    _regionView.frame = CGRectMake(kSpaceTab,tabY, bgW/2, _bgView.height-4*kSpaceTab);
    _categoryView.frame = CGRectMake(CGRectGetMaxX(_regionView.frame),tabY,bgW/2,_regionView.height);
    _orderView.frame = CGRectMake(0,0,bgW,_bgView.height-4*kSpaceTab-44);
    [UIView commitAnimations];
    
    [self addSubview:_bgView];
    
    if (data.count<=0) {
        return;
    }
    if ([data[0] isKindOfClass:[Area class]]) {

        Area *area = [self customAreaModel];
        [data insertObject:area atIndex:0];
        _leftDatas = data;

    }else{
        cateGory *cate = [self customCateModel];
        [data insertObject:cate atIndex:0];
        _leftDatas = data;
        
    }
    
        if ([self.regionView.delegate respondsToSelector:@selector(tableView:didSelectRowAtIndexPath:)]) {
            
            NSInteger selectRegionRow = 0;
            if (!_leftDatas) {
                return;
            }
            if ([_leftDatas[0] isKindOfClass:[Area class]]) {
                selectRegionRow = [[_userDefault objectForKey:kLeftRegionRow] intValue];
            }else{
                selectRegionRow = [[_userDefault objectForKey:kLeftCateRow] intValue];
            }
            [_regionView.delegate tableView:_regionView didSelectRowAtIndexPath:[NSIndexPath indexPathForRow:selectRegionRow inSection:0]];
        }
    
//    if ([self.categoryView.delegate respondsToSelector:@selector(tableView:didSelectRowAtIndexPath:)]) {
//        
//        NSInteger selectCatRow = 0;
//        if (!_leftDatas) {
//            return;
//        }
//        if ([_leftDatas[0] isKindOfClass:[Area class]]) {
//            selectCatRow = [[_userDefault objectForKey:kRightRegionRow] intValue];
//        }else{
//            selectCatRow = [[_userDefault objectForKey:kRightCateRow] intValue];
//        }
//        [_categoryView.delegate tableView:_categoryView didSelectRowAtIndexPath:[NSIndexPath indexPathForRow:selectCatRow inSection:0]];
//    }

}

#pragma mark -delegate And Datasources
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (tableView.tag == kTTShareTableViewRegion) {
        return _leftDatas.count;
    }else{
        return _rightDatas.count;
    }
}
#pragma mark 设置cell数据
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{

    static NSString *identifier = @"UITableViewCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:identifier];
    if (cell==nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:identifier];

    }
    
    if (tableView.tag==kTTShareTableViewRegion) {
        tableView.separatorInset =  UIEdgeInsetsZero;

        UIImageView *selectedView = [[UIImageView alloc] init];
        selectedView.backgroundColor = white2;
        cell.selectedBackgroundView = selectedView;
        if (_leftDatas.count==0) return cell;
        

        if ([_leftDatas[indexPath.row] isKindOfClass:[Area class]]) {
            Area *area = _leftDatas[indexPath.row];
            cell.textLabel.text = area.areaName;
            if (indexPath.row == [[_userDefault objectForKey:kLeftRegionRow]intValue]) {
                [self selectTable:_regionView atIndex:indexPath.row];
            }
        }else{
            cateGory *cate = _leftDatas[indexPath.row];
            cell.textLabel.text = cate.catName;
            
            if (indexPath.row == [[_userDefault objectForKey:kLeftCateRow]intValue]) {
                
                [self selectTable:_regionView atIndex:indexPath.row];
            }

        }

        return  cell;
    }else{
//        UIImageView *selectedView = [[UIImageView alloc] init];
//        selectedView.backgroundColor = white2;
//        cell.selectedBackgroundView = selectedView;
        if (_rightDatas.count==0) return cell;
        
        if ([_rightDatas[indexPath.row] isKindOfClass:[Area class]]) {
            Area *area = _rightDatas[indexPath.row];
            cell.textLabel.text = area.areaName;
//            if (indexPath.row == [[_userDefault objectForKey:kRightRegionRow]intValue]) {
//                
//                [self selectTable:_categoryView atIndex:indexPath.row];
//            }
            
        }else{
            cateGory *cate = _rightDatas[indexPath.row];
            cell.textLabel.text = cate.catName;
//            if (indexPath.row == [[_userDefault objectForKey:kRightCateRow]intValue]) {
//                
//                [self selectTable:_categoryView atIndex:indexPath.row];
//            }
            
        }
        return cell;
    }
}
//选中cell
- (void)selectTable:(UITableView *)tab atIndex:(NSInteger )index
{
    [tab selectRowAtIndexPath:[NSIndexPath indexPathForRow:index inSection:0] animated:NO scrollPosition:UITableViewScrollPositionNone];
}
#pragma mark -选中cell
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (!_leftDatas) {
        return;
    }

    if (tableView.tag == kTTShareTableViewRegion) {

        
        //加载右边数据
        [self loadRightobj:_leftDatas[indexPath.row] indexpath:indexPath];
        
//        [self selectTable:_regionView atIndex:indexPath.row];

        
        //delegate = leftData
      if ([self.delegate respondsToSelector:@selector(didChangeContentSuper:indexPath:)]) {
        
          [self.delegate didChangeContentSuper:_leftDatas[indexPath.row] indexPath:indexPath];
      }
        
        //滚动到指定行数
    [_regionView scrollToRowAtIndexPath:[NSIndexPath indexPathForItem:indexPath.row inSection:0] atScrollPosition:UITableViewScrollPositionNone animated:YES];
    }else{
        //RightView
        //类别
        id obj = @"";
        if ([_rightDatas[indexPath.row] isKindOfClass:[Area class]]) {
            obj = _rightDatas[indexPath.row];
            

        }else{
            obj = _rightDatas[indexPath.row];

        }

        //delegate = rightData
        if ([self.delegate respondsToSelector:@selector(didChangeContent:indexPath:)]) {
           
            [self.delegate didChangeContent:_rightDatas[indexPath.row] indexPath:indexPath];
            
            //保存当前选定的二级内容 计算父级内容所在行数
            [self querySupercontentRow:obj row:indexPath.row];
            
        }
        //滚动到指定行数
//        [_categoryView scrollToRowAtIndexPath:[NSIndexPath indexPathForItem:indexPath.row inSection:0] atScrollPosition:UITableViewScrollPositionNone animated:YES];
        
    }
    
    
}
- (void)querySupercontentRow:(id)obj row:(NSInteger )row
{
    if ([obj isKindOfClass:[Area class]]&&[[_leftDatas firstObject] isKindOfClass:[Area class]]) {
        Area *area = obj;
        
        TTLog(@"%@--%d",area.areaName,row);
        
        //存储二级行
        [_userDefault setInteger:row forKey:kRightRegionRow];
        if (row == 0) {
            [_userDefault setInteger:0 forKey:kLeftRegionRow];
        }
   
        int i = 0;
            for (Area *aSuper in _leftDatas) {
                i++;
                if ([area.superArea isEqualToString:aSuper.areaCode]) {
                    //保存选定的左边行数
                    [_userDefault setInteger:i-1 forKey:kLeftRegionRow];
                    TTLog(@"左边行号  - %d",i);
                    return;
                }
            }
    }
    if ([obj isKindOfClass:[cateGory class]]&&[[_leftDatas firstObject] isKindOfClass:[cateGory class]]){
        cateGory *cat = obj;
        
        //存储二级行
        [_userDefault setInteger:row forKey:kRightCateRow];
        
        if (row == 0) {
            [_userDefault setInteger:0 forKey:kLeftCateRow];
        }
        int i = 0;
        for (cateGory *cSuper in _leftDatas) {
            i++;
            if ([cat.superCat isEqualToString:cSuper.catCode]) {
                //保存选定的左边行数
                [_userDefault setInteger:i-1 forKey:kLeftCateRow];
                return;
            }
        }
    }
}

#pragma mark -右侧view数据
- (void)loadRightobj:(id)obj indexpath:(NSIndexPath*)indexpath
{
     NSString *cacheData = [NSString stringWithFormat:@"rightDatas%d",indexpath.row];
    NSMutableArray *subDatas = (NSMutableArray *)[[EGOCache globalCache]objectForKey:cacheData];
    
    NSString *typeCode = @"";
    NSString *marked = @"";
    NSString *typeStr = @"";
    if ([obj isKindOfClass:[Area class]]) {
        
        Area *area = (Area *)obj;
        
        if ([subDatas[0] isKindOfClass:[Area class]]) {
            Area *aSub = (Area *)subDatas[0];
            if ([aSub.superArea isEqualToString:area.areaCode]) {
                self.rightDatas = subDatas;
                
            [_regionView scrollToRowAtIndexPath:[NSIndexPath indexPathForItem:indexpath.row inSection:0] atScrollPosition:UITableViewScrollPositionNone animated:YES];
                
                [self.categoryView reloadData];
                return;
            }
        }
        
        //设置地区参数
        typeCode = area.areaCode;
        marked = TTAction_queryArea;
        typeStr = @"superArea";
        
        
    }else{
        
        cateGory *cate = (cateGory *)obj;
        
        if ([subDatas[0] isKindOfClass:[cateGory class]]) {
            cateGory *cSub = (cateGory *)subDatas[0];
            if ([cSub.superCat isEqualToString:cate.catCode]) {
                self.rightDatas = subDatas;
                [self.categoryView reloadData];
                return;
            }
        }
        
        //设置分类参数
        typeCode = cate.catCode;
        marked = TTAction_CouponAct_queryCategory;
        typeStr = @"superCat";
    }


    //加载二级列表
    NSDictionary *dict = @{typeStr    : typeCode,
                           @"sysPlat" :  @"5",
                           };
    [self loadLevellistWithindex:indexpath obj:obj Marked:marked Dict:dict];

}

- (void)loadLevellistWithindex:(NSIndexPath *)indexpath obj:(id)obj Marked:(NSString *)marked Dict:(NSDictionary *)dict
{
        [TieTieTool tietieWithParameterMarked:marked dict:dict succes:^(id responseObject) {
            NSArray *arr = [NSArray array];
            NSMutableArray *rightDatas = [NSMutableArray array];
            if ([obj isKindOfClass:[Area class]]) {
                arr = responseObject[@"areaList"];
                for (NSDictionary *dict in arr) {
                    Area *area = [[Area alloc] initWithDict:dict];
                    [rightDatas addObject:area];
                    
                }
                if (indexpath.row==0) {
                    [rightDatas insertObject:[self customAreaModel] atIndex:0];
                }
                
            }else{
                arr = responseObject[@"catList"];
                for (NSDictionary *dict in arr) {
                    cateGory *cate = [[cateGory alloc] initWithDict:dict];
                    [rightDatas addObject:cate];
                }
                if (indexpath.row==0) {
                    [rightDatas insertObject:[self customCateModel] atIndex:0];
                }
            }
            
            [[EGOCache globalCache] setObject:rightDatas forKey:@"rightDatas"];
            self.rightDatas = rightDatas;
            [self.categoryView reloadData];
            
        } fail:^(NSError *error) {
            TTLog(@"CouponAct_queryCategory : error");
            
        }];
}
/*
 *  自定义模型添加至首行
 */
- (Area *)customAreaModel
{
    Area *area = [[Area alloc] init];
    area.superArea = @"";
    area.areaLevel = @"";
    area.areaCode = @"areaCode";
    area.areaName = [NSString stringWithFormat:@"%@全范围",[TTAccountTool sharedTTAccountTool].currentCity.areaName];
    
    return area;
}
- (cateGory *)customCateModel
{
    cateGory *cate = [[cateGory alloc] init];
    cate.superCat = @"";
    cate.catLevel = @"";
    cate.catCode = @"catCode";
    cate.catName = @"全部";
    return cate;
}
#pragma mark -隐藏
-(void)hideDropDown:(UIView *)bar {
    //    CGRect barFrame = bar.frame;
    CGFloat bgW = self.frame.size.width - 2*kSpaceTab;
    self.alpha = 0;
    [UIView beginAnimations:nil context:nil];
    [UIView setAnimationDuration:kAnimaDuration];
    //    self.frame = CGRectMake(barFrame.origin.x, barFrame.origin.y+barFrame.size.height, barFrame.size.width, 0);
    _bgView.frame = CGRectMake(kSpaceTab,0, ScreenWidth-2*kSpaceTab,0);
    _regionView.frame = CGRectMake(kSpaceTab,2*kSpaceTab,bgW/2,0);
    _categoryView.frame = CGRectMake(CGRectGetMaxX(_regionView.frame),2*kSpaceTab,bgW/2,0);
    [UIView commitAnimations];
}
@end
