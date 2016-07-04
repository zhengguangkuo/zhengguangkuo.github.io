//
//  CustomAnnotationView.h
//  Miteno
//
//  Created by zhengguangkuo on 14-4-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "BMKAnnotationView.h"
#import <MapKit/MapKit.h>

@interface CustomAnnotation : NSObject<MKAnnotation>

- (id)initWithCoordinate:(CLLocationCoordinate2D)coords;

@property (nonatomic)CLLocationCoordinate2D coordinate;

@property (nonatomic,copy) NSString *title;
@property (nonatomic,copy) NSString *subTitle;

@end

