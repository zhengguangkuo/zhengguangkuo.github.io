//
//  TTTieFenAlertView.h
//  TTTieFenViewController
//
//  Created by APPLE on 14-6-9.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTTieFenAlertView : UIView<UITableViewDelegate,UITableViewDataSource>
{
    NSMutableArray * _timeArr;
    NSMutableArray * _chalengeListArr;
    NSMutableArray * _numArr;
}

-(id)initWithFrame:(CGRect)frame andFenDetailListArr:(NSMutableArray*)arr;
@end
