//
//  TTRootViewController.m
//  WG_lottery(彩票)
//
//  Created by wg on 14-5-22.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"
#import "UIImage(addition).h"
#import <CoreGraphics/CoreGraphics.h>
#import <CoreLocation/CoreLocation.h>
#import "Appdelegate.h"
#import "TTAlertView.h"
@interface TTRootViewController ()<CLLocationManagerDelegate>
{
    UIView  *_loadingView;
}
@property (nonatomic, strong) CLLocationManager    * locatitonManager;
@property (nonatomic, assign) CLLocationCoordinate2D  userCoordinate;
@end


@implementation TTRootViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {


    }
    return self;
}
- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}
- (void)viewDidLoad
{
    [super viewDidLoad];
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
    
    __unsafe_unretained TTRootViewController *root = self;
    static dispatch_once_t token;
    dispatch_once(&token, ^{
        //根据当前位置获取城市列表数据
        [root getUserLocation]; //定位
        
        [root loadCitylist];    //根据当前位置定位城市 默认北京
    });

}

/*
 *      贴贴
 */
//删除
- (void)removeDown
{
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    for (UIButton *btn in window.subviews) {
        [[btn viewWithTag:1000] removeFromSuperview];
    }
}
//是否显示loding图标
- (void)showLoading:(BOOL)show {
    
    if (_loadingView == nil) {
        _loadingView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, ScreenWidth, ScreenHeight)];
        _loadingView.backgroundColor = [UIColor clearColor];
        
        //logo图标
        UIImageView *logoImage = [[UIImageView alloc]initWithFrame:CGRectMake(0, 0, 150, 60)];//[[UIImageView alloc] initWithFrame:CGRectMake((ScreenWidth-80)/2, (_loadingView.height-40)/2, 80, 60)];
        [logoImage setCenter:CGPointMake(ScreenWidth/2, ScreenHeight/2-80)];
//        logoImage.backgroundColor = [UIColor colorWithWhite:0 alpha:0.8];
        [logoImage setImage:[UIImage imageNamed:@"laoding_bg"]];
        logoImage.layer.masksToBounds = YES;
        logoImage.layer.cornerRadius = 8;
        
        
        //loading视图
        UIActivityIndicatorView *activityView = [[UIActivityIndicatorView alloc]
                                                 initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
        activityView.color = [UIColor redColor];//[UIColor colorWithWhite:0.9 alpha:1];    //5.0的方法
        //        activityView.size = CGSizeMake(5, 5);
        [activityView startAnimating];
        
        //正在加载的Label
        UILabel *loadLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 80, 20)];
        [loadLabel setCenter:CGPointMake(logoImage.width/2+20, logoImage.height/2)];
        loadLabel.backgroundColor = [UIColor clearColor];
//        loadLabel.text = @"正在加载...";
        loadLabel.text = self.loginText!=nil?_loginText:@"正在加载...";
        loadLabel.font = [UIFont boldSystemFontOfSize:14.0];
        loadLabel.textColor = [UIColor grayColor];//[UIColor colorWithWhite:0.9 alpha:1];
        [loadLabel sizeToFit];
        
        
//        logoImage.width = loadLabel.width+activityView.width+25;
//        logoImage.height = logoImage.width-20;
//        logoImage.top = (_loadingView.height-logoImage.height)/2-64;
//        logoImage.left = (ScreenWidth-logoImage.width)/2;
        activityView.left = (logoImage.width - activityView.width)/2-45;
        activityView.top = (logoImage.height-activityView.height)/2;
//        loadLabel.top = activityView.bottom+5;
//        loadLabel.left = (logoImage.width-loadLabel.width)/2;
        
        [_loadingView addSubview:logoImage];
        [logoImage addSubview:loadLabel];
        [logoImage addSubview:activityView];
    }
    if (show) {
        if (![_loadingView superview]) {
            [self.view addSubview:_loadingView];
            //            [[UIApplication sharedApplication].keyWindow addSubview:_loadView];
        }
    } else {
            [_loadingView removeFromSuperview];
    }
}
//------
- (void)SetNaviationRightButtons:(NSArray*)buttons
{
    NSMutableArray* array = [[NSMutableArray alloc] init];
    for(UIButton* btn in buttons)
    {
        UIBarButtonItem* item = [[UIBarButtonItem alloc] initWithCustomView:btn];
        [array addObject:item];
    }
    [self.navigationItem setRightBarButtonItems:array];
}

- (void)backToPrevious
{
    [self.navigationController  popViewControllerAnimated:YES];
}

- (void)NavigationViewBackBtn
{
//    UIBarButtonItem *item =
//    [UIBarButtonItem barButtonItemWithBackgroudImage:[UIImage generateFromColor:[UIColor clearColor]]
//                                            andTitle:nil
//                                            andImage:[UIImage imageNamed:@"gb_button"]
//                                           addTarget:self
//                                           addAction:@selector(backToPrevious)];
//    
//    UIBarButtonItem *negativeSpacer = [UIBarButtonItem barButtonItemWithNegativeSpacer];
    
//    self.navigationItem.leftBarButtonItems = [NSArray arrayWithObjects:negativeSpacer,item, nil];
}






- (void)PopBoxMsg:(NSString *)WarningMsg
{
    dispatch_async(dispatch_get_main_queue(),
                   ^{
                       [self.view  makeToast:WarningMsg];
                   });
}



- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
#pragma mark -地图相关
- (void)getUserLocation
{
    if (!_locatitonManager) {
        _locatitonManager = [[CLLocationManager alloc]init];
    }
    if ([CLLocationManager locationServicesEnabled]) {
        _locatitonManager.delegate = self;
        _locatitonManager.desiredAccuracy = kCLLocationAccuracyBest;
        _locatitonManager.distanceFilter = 10.0f;
        [_locatitonManager startUpdatingLocation];
    }
}
#pragma location manager methods
- (void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations
{
    CLLocation *location = [locations lastObject];
    _userCoordinate = BMKCoorDictionaryDecode(BMKBaiduCoorForWgs84(location.coordinate));
    [_locatitonManager stopUpdatingLocation];
  
    TTLog(@"用户当前经纬度:%f,%f",_userCoordinate.longitude,_userCoordinate.latitude);
    
    [TTSettingTool setFloat:_userCoordinate.longitude forKey:TTLongitude];//经度
    [TTSettingTool setFloat:_userCoordinate.latitude forKey:TTLatitude];  //纬度
}
- (void)loadCitylist
{
    NSString    * latitude = @"";
    NSString    * longitude = @"";
    if ([[TTSettingTool objectForKey:TTLatitude] floatValue] > 0) {
        latitude = [NSString stringWithFormat:@"%f",[[TTSettingTool objectForKey:TTLatitude] floatValue]];
        
        longitude = [NSString stringWithFormat:@"%f",[[TTSettingTool objectForKey:TTLongitude] floatValue]];
    }
    
    NSFileManager *manager = [NSFileManager defaultManager];
    BOOL isFile = [manager fileExistsAtPath:TTCityFilePath];
    
    NSDictionary *dict = @{@"latitude" :latitude,
                           @"longitude":longitude,
                           @"sysPlat"  :@"5"};
    
    if (!isFile) {
        [TieTieTool tietieWithParameterMarked:TTAction_queryOpenCity dict:dict succes:^(id responseObject) {
            NSString *key = [responseObject objectForKey:rspCode];
            NSString *result = [responseObject objectForKey:rspDesc];
            if ([key isEqualToString:rspCode_success]) {
                
                NSArray *citys = responseObject[@"areaList"];
                [citys writeToFile:TTCityFilePath atomically:YES];
                
            }else{
                [SystemDialog alert:result];
                return ;
            }
        } fail:^(NSError *error) {
            TTLog(@"cityList error :%@",error);
            return ;
        }];
    }
}
- (void)dealloc
{
    TTLog(@"dealloc = %@",self.view.window.superview);
}
@end
