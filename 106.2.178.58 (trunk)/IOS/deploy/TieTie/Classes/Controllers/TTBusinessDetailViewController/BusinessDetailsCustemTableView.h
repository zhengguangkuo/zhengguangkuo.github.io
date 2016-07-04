//
//  BusinessDetailsCustemTableView.h
//  Miteno
//
//  Created by APPLE on 14-6-16.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BusinessDetailsCustemTableView : UITableView<UITableViewDataSource>
{
    NSArray * _arr;
}
-(id)initWithDictionary:(NSDictionary *)dict andSwitchNum:(int)num;
@end
