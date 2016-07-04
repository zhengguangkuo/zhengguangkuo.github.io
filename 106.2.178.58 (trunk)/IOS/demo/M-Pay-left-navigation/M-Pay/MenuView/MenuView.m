#import "MenuView.h"

@interface MenuView()

@property  (nonatomic, strong) NSDictionary* menuDictionary;

@property  (nonatomic, strong) NSArray* keyArray;

@property  (nonatomic, strong) UIColor* bgColor;

@property (nonatomic, copy)   void (^selectedClick)(int nIndex);

@end


@implementation MenuView

- (id)initWithFrame:(CGRect)frame  dic:(NSDictionary*)dic array:(NSArray*)nameArray bg:(UIColor*)color selectedBlock:(void (^)(int nIndex))block
{
    self = [super initWithFrame:frame style:UITableViewStylePlain];
    
    self.menuDictionary = [[NSDictionary alloc] initWithDictionary:dic];
    
    self.keyArray = [[NSArray alloc] initWithArray:nameArray];
    
    self.bgColor = color;
    
    self.selectedClick = block;
    
    self.scrollEnabled = NO;
    
    [self setBackgroundColor:self.bgColor];
    
    [self setDataSource:self];
    
    [self setDelegate:self];
    
    return self;
}


-(NSInteger)tableView:(UITableView *)tableView
numberOfRowsInSection:(NSInteger)section
{
    return [self.keyArray count];
}


- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 44;
}


-(UITableViewCell *)tableView:(UITableView *)tableView
		cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"MenuCell";
    int n = [indexPath row];
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if(cell==nil)
    {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
        
        UILabel*  namelabel = [[UILabel alloc] initWithFrame:CGRectMake(30, 8, 200, 30)];
        [namelabel setBackgroundColor:[UIColor clearColor]];
        [namelabel setTextColor:[UIColor whiteColor]];
        [namelabel setFont:[UIFont boldSystemFontOfSize:16.0f]];
        [namelabel setTag:1];
        [cell.contentView addSubview:namelabel];
        [cell setBackgroundColor:self.bgColor];
    }
    
    UILabel* tempLabel = (UILabel*)[cell viewWithTag:1];
    [tempLabel setText:self.keyArray[n]];
    
    return cell;
}


- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{

    int rowNum = [indexPath row];
//    NSString* keyname = self.keyArray[rowNum];
//    
//    UIViewController* navVC = [self.menuDictionary objectForKey:keyname];
    
    if(self.selectedClick)
    {
        _selectedClick(rowNum);
    }
}

- (UIViewController *)viewController {
     for (UIView* next = [self superview]; next; next = next.superview) {
          UIResponder *nextResponder = [next nextResponder];
      if ([nextResponder isKindOfClass:[UIViewController class]]) {
                return (UIViewController *)nextResponder;
            }
      }
    return nil;
}

@end
