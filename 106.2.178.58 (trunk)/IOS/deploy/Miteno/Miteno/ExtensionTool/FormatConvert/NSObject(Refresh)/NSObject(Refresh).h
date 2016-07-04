#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>
#import <UIKit/UIKit.h>

typedef enum
{
    UIRefreshUp,
    UIRefreshStart,
    UIRefreshDown
}UIRefreshStyle;

#define CurPage(curTotal,PageMax)  (curTotal%PageMax==0?(curTotal/PageMax + 1):(curTotal/PageMax + 2))

#define PageNo(nTotal,PageMax) (nTotal%PageMax==0?(nTotal/PageMax):(nTotal/PageMax + 1))

@interface NSObject(Refresh) <UIScrollViewDelegate>

- (void)RefreshData:(UITableView*)view block:(void(^)(void))RefreshBlock;

- (void)RefreshData:(UITableView *)view up:(void(^)(void))RefreshUp down:(void(^)(void))RefreshDown;

- (BOOL)CheckRefresh:(int)cur total:(int)total pageMax:(int)pageMax style:(UIRefreshStyle)style;

- (int)RefreshIndex:(UIRefreshStyle)style curr:(int)nIndex;

- (int)NewCurrIndex:(int)cur total:(int)total pageMax:(int)pageMax style:(UIRefreshStyle)style;

@end
