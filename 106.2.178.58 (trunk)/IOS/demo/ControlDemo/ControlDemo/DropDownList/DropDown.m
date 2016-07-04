//
//  DropDown.m
//  ControlDemo
//
//  Created by HWG on 14-1-20.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "DropDown.h"

@implementation DropDown
- (id)initWithFrame:(CGRect)frame
{
    if (frame.size.height<200) {
        _frameHeight = 200;
    }else{
        _frameHeight = frame.size.height;
    }
    _tabHeight = _frameHeight-30;
    
    frame.size.height = 30.0f;
    
    self=[super initWithFrame:frame];
    
    if(self){
        self.showList = NO; //默认不显示下拉框
        
        _tableView = [[UITableView alloc] initWithFrame:CGRectMake(0, 30, frame.size.width, 0)];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.backgroundColor = [UIColor grayColor];
        _tableView.separatorColor = [UIColor lightGrayColor];
        _tableView.hidden = YES;
        [self addSubview:_tableView];
        
        _textField = [[UITextField alloc] initWithFrame:CGRectMake(0, 0, frame.size.width, 30)];
        _textField.borderStyle=UITextBorderStyleRoundedRect;//设置文本框的边框风格
        _textField.clearButtonMode = UITextFieldViewModeWhileEditing;
        [_textField addTarget:self action:@selector(dropdown) forControlEvents:UIControlEventAllTouchEvents];
        [self addSubview:_textField];
        
    }
    return self;
}
-(void)dropdown{
    [_textField resignFirstResponder];
    [self.superview bringSubviewToFront:self];
        if (_tableView.hidden==YES) {
    //把dropdownList放到前面，防止下拉框被别的控件遮住
            _tableView.hidden = NO;
        }
    if (_showList) {//如果下拉框已显示，什么都不做
        return;
    }else {//如果下拉框尚未显示，则进行显示
        
        CGRect sf = self.frame;
        sf.size.height = _frameHeight;

        _showList = YES;//显示下拉框
        
        CGRect frame = _tableView.frame;
        frame.size.height = 0;
        _tableView.frame = frame;
        frame.size.height = _tabHeight;
        [UIView beginAnimations:@"ResizeForKeyBoard" context:nil];
        [UIView setAnimationCurve:UIViewAnimationCurveLinear];
        self.frame = sf;
        _tableView.frame = frame;
        [UIView commitAnimations];
    }
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_tableArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    cell.textLabel.text = [_tableArray objectAtIndex:[indexPath row]];
    cell.textLabel.font = [UIFont systemFontOfSize:16.0f];
    cell.accessoryType = UITableViewCellAccessoryNone;
    cell.selectionStyle = UITableViewCellSelectionStyleGray;
    
    return cell;
}
-(CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    _textField.text = [_tableArray objectAtIndex:[indexPath row]];
    
    [_delegate selectedValue:_textField.text];
    [_delegate getIndex:indexPath.row];
    
    _showList = NO;
    _tableView.hidden = YES;
    
    CGRect sf = self.frame;
    sf.size.height = 30;
    self.frame = sf;
    CGRect frame = _tableView.frame;
    frame.size.height = 0;
    _tableView.frame = frame;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}
@end
