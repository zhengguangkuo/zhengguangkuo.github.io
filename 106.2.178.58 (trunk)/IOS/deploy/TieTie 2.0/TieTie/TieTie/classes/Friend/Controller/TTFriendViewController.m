//
//  TTFriendViewController.m
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTFriendViewController.h"

@interface TTFriendViewController ()
{
//    UILabel         * _navTitle;
}
@end

@implementation TTFriendViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    self.title = @"贴友";
    self.view.backgroundColor = [UIColor redColor];
    
//    _navTitle = [[UILabel alloc] init];
//    _navTitle.frame = CGRectMake(0, 0, 40, 30);
//    self.navigationItem.titleView = _navTitle;
//    _navTitle.text = @"贴友";
  
    
}
- (void)changeTitle
{

}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
