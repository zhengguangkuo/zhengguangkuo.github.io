//
//  CustomAnnotationView.m
//  Miteno
//
//  Created by zhengguangkuo on 14-4-17.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import "CustomAnnotationView.h"

@implementation CustomAnnotation

- (id)initWithCoordinate:(CLLocationCoordinate2D)coords
{
    self = [super init];
    if (self) {
        self.title = @"title";
        self.subTitle = @"subtitle";
        self.coordinate = coords;
    }
    return self;
}



@end
