//
//  TTCommentViewController.m
//  Miteno
//
//  Created by wg on 14-8-5.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTCommentViewController.h"

@interface TTCommentViewController ()
@property (weak, nonatomic) IBOutlet UITextField *commentName;

@end

@implementation TTCommentViewController

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
    
    self.view.backgroundColor = TTGlobalBg;
    self.title = @"备注";

    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"提交" style:UIBarButtonItemStylePlain target:self action:@selector(submit)];
}
- (void)submit
{

}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
