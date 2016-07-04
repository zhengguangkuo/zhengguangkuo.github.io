#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>


@interface MenuView  : UITableView<UITableViewDataSource, UITableViewDelegate>
{

}

- (id)initWithFrame:(CGRect)frame  dic:(NSDictionary*)dic array:(NSArray*)nameArray bg:(UIColor*)color selectedBlock:(void (^)(int nIndex))block;


@end
