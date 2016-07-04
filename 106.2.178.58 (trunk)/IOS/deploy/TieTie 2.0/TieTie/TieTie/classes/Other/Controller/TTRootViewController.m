//
//  TTRootViewController.m
//  TieTie
//
//  Created by wg on 14-9-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "TTRootViewController.h"
#import <CoreGraphics/CoreGraphics.h>
#import <CoreLocation/CoreLocation.h>
#import "Utilities.h"
#import "BMKGeometry.h"


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
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    static dispatch_once_t token;
    dispatch_once(&token, ^{
        //自动定位当前位置
        [self autoLocation];
        
    });
#ifdef __IPHONE_7_0
    if (IOS7) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        for (UIViewController *vc  in self.childViewControllers) {
            //            vc.edgesForExtendedLayout = UIRectEdgeNone;
            //            TTLog(@"---%@ = %@",vc,NSStringFromCGRect(vc.view.frame));
            CGRect frame = vc.view.frame;
            frame.origin.y-=64;
            vc.view.frame =frame;
        }
    }
#endif
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
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
        [loadLabel setCenter:CGPointMake(logoImage.frame.size.width/2+20, logoImage.frame.size.height/2)];
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
        CGRect acframe = activityView.frame;
        acframe.origin.x = (logoImage.frame.size.width - activityView.frame.size.width)/2-45;
        
        acframe.origin.y = (logoImage.frame.size.height-activityView.frame.size.height)/2;
        
        activityView.frame = acframe;
    
//        activityView.left = (logoImage.width - activityView.width)/2-45;
//        activityView.top = (logoImage.height-activityView.height)/2;
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
#pragma mark toast框
- (void)makeBottomToast:(NSString *)message
{
     [self.view makeToast:message];
}

- (void)makeTopToast:(NSString *)message
{
     [self.view makeToast:message duration:0.4 position:@"top"];
}

- (void)backToPrevious
{
    [self.navigationController  popViewControllerAnimated:YES];
}

#pragma mark -地图定位
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
    _userCoordinate = BMKCoorDictionaryDecode(BMKConvertBaiduCoorFrom(location.coordinate,BMK_COORDTYPE_COMMON));
    [_locatitonManager stopUpdatingLocation];
    
    TTLog(@"用户当前经纬度:%f,%f",_userCoordinate.longitude,_userCoordinate.latitude);
    
    [TTSettingTool setFloat:_userCoordinate.longitude forKey:TTLongitude];//经度
    [TTSettingTool setFloat:_userCoordinate.latitude forKey:TTLatitude];  //纬度
}
- (void)autoLocation
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
//                    /Users/huwg/Desktop
                NSArray *citys = responseObject[@"areaList"];
                
                //存储城市列表数据

//                [citys writeToFile:@"/users/huwg/Desktop/ceshi.plist" atomically:NO];
                [citys writeToFile:TTCityFilePath atomically:YES];
                
            }else{
//                [SystemDialog alert:result];
                return ;
            }
        } fail:^(NSError *error) {
            TTLog(@"cityList error :%@",error);
            return ;
        }];
    }
}
@end
