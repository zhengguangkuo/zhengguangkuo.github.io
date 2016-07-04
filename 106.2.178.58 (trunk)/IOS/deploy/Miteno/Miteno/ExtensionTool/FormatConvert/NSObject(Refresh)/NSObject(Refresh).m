#import "NSObject(Refresh).h"

void (^UpRefreshTable)(void);

void (^DownRefreshTable)(void);

__strong UITableView*  RefreshView;

CGPoint  beginPoint;

CGPoint  endPoint;

@implementation NSObject(Refresh)

- (void)RefreshData:(UITableView*)view block:(void(^)(void))RefreshBlock
{
    RefreshView = view;
    UpRefreshTable = [RefreshBlock copy];
}


- (void)RefreshData:(UITableView *)view up:(void(^)(void))RefreshUp down:(void(^)(void))RefreshDown
{
    RefreshView = view;
    UpRefreshTable = [RefreshUp copy];
    DownRefreshTable = [RefreshDown copy];
}


- (BOOL)CheckRefresh:(int)cur total:(int)total pageMax:(int)pageMax style:(UIRefreshStyle)style
{
    if(style==UIRefreshDown)
    {
        if(cur<=1)
          return FALSE;
    }
    else
    if(style==UIRefreshUp)
    {
        if(cur>=PageNo(total, pageMax))
          return FALSE;
    }
    return TRUE;
}

- (int)RefreshIndex:(UIRefreshStyle)style curr:(int)nIndex
{
    if(style==UIRefreshDown)
    {
        --nIndex;
    }
    else
    if(style==UIRefreshUp)
    {
        ++nIndex;
    }
    return nIndex;
}

- (int)NewCurrIndex:(int)cur total:(int)total pageMax:(int)pageMax style:(UIRefreshStyle)style
{
    int NewCurrent = cur;
    if(style==UIRefreshUp)
    {
       if(cur<PageNo(total, pageMax))
            ++NewCurrent;
    }
    else
    if(style==UIRefreshDown)
    {
       if(cur>1)
            --NewCurrent;
    }
    
    return NewCurrent;
}


- (void)scrollViewDidScroll:(UIScrollView *)scrollView
{


    
}

- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView
{
    if(RefreshView.contentSize.height>=RefreshView.frame.size.height)
        beginPoint = CGPointMake(0,RefreshView.contentSize.height - RefreshView.frame.size.height);
    else
        beginPoint = CGPointMake(0, 0);
}


-(void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
    endPoint = RefreshView.contentOffset;
    NSLog(@"endPoint.y = %f",endPoint.y);
    
    if(endPoint.y - beginPoint.y>0)
{
        if (endPoint.y - beginPoint.y >= 15.0)
        {
            UpRefreshTable();
            NSLog(@"上拉刷新");
        }
}
    else
{
        NSLog(@"下拉刷新");
        if(endPoint.y - beginPoint.y <= -15.0)
        {
            NSLog(@"下拉刷新2");
            DownRefreshTable();
        }
}
        

}
    

-(void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
    
}


@end
