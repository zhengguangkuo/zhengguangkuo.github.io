//
//  MapsViewController.m
//  Miteno
//
//  Created by zhengguangkuo on 14-4-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "MapsViewController.h"
#import "BMKPinAnnotationView.h"
#import "Utilities.h"


#define IOS7 ([[[UIDevice currentDevice] systemVersion] floatValue]>= 7.0 ? YES : NO)
#define ScreenWidth  [[UIScreen mainScreen] bounds].size.width
#define kStatusbarHeight (IOS7 ? 20 : 0)
#define ScreenHeight [[UIScreen mainScreen] bounds].size.height

@interface MapsViewController ()

@property (nonatomic, strong)UIStepper *stepper;

- (void)backButtonClicked;
- (void)stepperClicked:(id)sender;
@end

@implementation MapsViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (id)init
{
    if (self = [super init]) {
        [self addMapView];
        //[self addHeaderView];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    [self.mapView viewWillAppear];
    self.mapView.delegate = self;
}

- (void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    [self.mapView viewWillDisappear];
    self.mapView.delegate = nil;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)addHeaderView
{
     UIView *statusView = [[UIView alloc]initWithFrame:CGRectMake(0, 0, ScreenWidth, kStatusbarHeight)];
    [statusView setBackgroundColor:[UIColor blackColor]];
    
    UIImage *image = [UIImage imageNamed:@"nav_image_bg"];
    UIImageView *headView = [[UIImageView alloc]initWithFrame:CGRectMake(0, kStatusbarHeight, ScreenWidth, 44)];
    [headView setImage:image];
    
    UIButton *backButton = [[UIButton alloc]initWithFrame:CGRectMake(10, kStatusbarHeight + 5, 58, 34)];
    [backButton setBackgroundImage:[UIImage imageNamed:@"top_bt_bg.png"] forState:UIControlStateNormal];
    [backButton setImage:[UIImage imageNamed:@"gb_button"] forState:UIControlStateNormal];
    [backButton addTarget:self action:@selector(backButtonClicked) forControlEvents:UIControlEventTouchUpInside];
    
    UILabel *titleLable = [[UILabel alloc]initWithFrame:CGRectMake(ScreenWidth/2 - 34, kStatusbarHeight + 10, 80, 24)];
    [titleLable setBackgroundColor:[UIColor clearColor]];
    titleLable.text = @"地图展示";
    titleLable.font =  [UIFont fontWithName:@"TrebuchetMS-Bold" size:17];
    
    [self.view addSubview:statusView];
    [self.view addSubview:headView];
    [self.view addSubview:backButton];
    [self.view addSubview:titleLable];
}

- (void)backButtonClicked
{
    [self dismissViewControllerAnimated:NO completion:nil];
}

- (void)stepperClicked:(id)sender
{
    UIStepper *tmpStepper = (UIStepper *)sender;
    self.mapView.zoomLevel = tmpStepper.value;
}

- (void)addMapView
{
    self.mapView = [[BMKMapView alloc] initWithFrame:CGRectMake(0, /*kStatusbarHeight + 44*/0, ScreenWidth, 640)];
    [self.view addSubview:self.mapView];
    
    self.mapView.delegate = self;
    self.mapView.zoomLevel = 16;
    self.mapView.showsUserLocation = YES;
    self.stepper = [[UIStepper alloc]initWithFrame:CGRectMake(ScreenWidth-140, ScreenHeight-50, 120, 30)];
    self.stepper.value = self.mapView.zoomLevel;
    self.stepper.minimumValue = 3;
    self.stepper.maximumValue = 19;
    self.stepper.stepValue = 1;
    [self.stepper addTarget:self action:@selector(stepperClicked:) forControlEvents:UIControlEventValueChanged];
    [self.view addSubview:self.stepper];
}

- (void)addAnnotationView:(NSString *)coordinate andMerchName:(NSString *)name
{
//    if (ChNil(coordinate)) {
//        return;
//    }

    CLLocationCoordinate2D coord = [Utilities stringToCLLocationCoordinate2D:coordinate];
    [self.mapView setCenterCoordinate:coord];
    
    BMKPointAnnotation *annotation = [[BMKPointAnnotation alloc]init];
    annotation.coordinate = coord;
    annotation.title = name?name:@"";
    [self.mapView addAnnotation:annotation];
}

- (void)addAnnotationViewWithCLLocationCoordinate2D:(CLLocationCoordinate2D)coordinate andMerchName:(NSString *)name
{
//    if (coordinate.latitude == 0 && coordinate.longitude == 0) {
//        return;
//    }
    [self.mapView setCenterCoordinate:coordinate];
    
    BMKPointAnnotation *annotation = [[BMKPointAnnotation alloc]init];
    annotation.coordinate = coordinate;
    annotation.title = name;
    [self.mapView addAnnotation:annotation];
}

- (void)addAnnotationViews:(NSMutableArray *)coordinateAndTitle center:(CLLocationCoordinate2D)centerCoordinate
{
    NSMutableArray *annotations = [[NSMutableArray alloc]init];
    for (NSArray *annotationView in coordinateAndTitle) {
        CLLocationCoordinate2D coordinate = [Utilities stringToCLLocationCoordinate2D:[annotationView objectAtIndex:0]];
        NSString *title = [annotationView objectAtIndex:1];
        BMKPointAnnotation *annotation = [[BMKPointAnnotation alloc]init];
        annotation.coordinate = coordinate;
        annotation.title = title;
        [annotations addObject:annotation];
    }
    [self.mapView setCenterCoordinate:centerCoordinate];

    [self.mapView addAnnotations:annotations];
}

#pragma map view delegate methods

-(BMKAnnotationView *)mapView:(BMKMapView *)mapView viewForAnnotation:(id<BMKAnnotation>)annotation
{
    if ([annotation isKindOfClass:[BMKPointAnnotation class]]) {
        BMKPinAnnotationView *annotationView = [[BMKPinAnnotationView alloc]initWithAnnotation:annotation reuseIdentifier:@"myAnnationView"];
        annotationView.pinColor = BMKPinAnnotationColorPurple;
        annotationView.animatesDrop = YES;
        return annotationView;
    }
    return nil;
}

- (void)mapView:(BMKMapView *)mapView regionDidChangeAnimated:(BOOL)animated
{
//    MyLog(@"map zoom level:%f",mapView.zoomLevel);
    self.stepper.value = mapView.zoomLevel;
}

- (void)mapView:(BMKMapView *)mapView annotationViewForBubble:(BMKAnnotationView *)view
{
//    NSLog(@"click paoao view!!");
    [self.delegate clickPaopaoView:view];
}

@end
