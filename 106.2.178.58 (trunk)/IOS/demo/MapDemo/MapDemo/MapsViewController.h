//
//  MapsViewController.h
//  Miteno
//
//  Created by zhengguangkuo on 14-4-21.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"
#import "BMKMapView.h"
#import "BMKPointAnnotation.h"
#import "BMKAnnotationView.h"

@protocol MapsViewControllerDelegate <NSObject>

@optional

- (void)clickPaopaoView:(BMKAnnotationView *)view;

@end

@interface MapsViewController : UIViewController<BMKMapViewDelegate>

@property (nonatomic, strong)BMKMapView *mapView;

@property (nonatomic,weak) id<MapsViewControllerDelegate> delegate;

- (void)addAnnotationView:(NSString *)coordinate andMerchName:(NSString *)name;

- (void)addAnnotationViewWithCLLocationCoordinate2D:(CLLocationCoordinate2D)coordinate andMerchName:(NSString *)name;

- (void)addAnnotationViews:(NSMutableArray *)coordinateAndTitle center:(CLLocationCoordinate2D)centerCoordinate;
@end
