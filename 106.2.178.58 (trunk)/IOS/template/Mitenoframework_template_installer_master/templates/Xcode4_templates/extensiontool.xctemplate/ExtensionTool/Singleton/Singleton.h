//
//  Singleton.h
//  ___PROJECTNAME___
//
//  Created by ___FULLUSERNAME___ on ___DATE___.
//  Copyright ___ORGANIZATIONNAME___ ___YEAR___. All rights reserved.
//

#ifndef Singleton_h
#define Singleton_h

#define singleton_for_interface(className) +(className*)shared##className ;


#define singleton_for_implementation(className) static className* _instance ;\
+(id)allocWithZone:(NSZone *)zone{ \
static dispatch_once_t onceToken; \
dispatch_once(&onceToken, ^{ \
_instance = [super allocWithZone:zone];\
});\
return _instance;\
}\
+(className *)shared##className{\
static dispatch_once_t onceToken;\
dispatch_once(&onceToken, ^{\
_instance = [[className alloc]init];\
});\
return _instance ;\
}


#endif
