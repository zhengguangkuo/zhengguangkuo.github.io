//
//  TTSearchViewController.h
//  Miteno
//
//  Created by wg on 14-6-7.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTSearchViewController : UIViewController<UITableViewDelegate,UITableViewDataSource>
- (IBAction)searchGO:(id)sender;


@property (weak, nonatomic) IBOutlet UISearchBar *searchTF;
@end
