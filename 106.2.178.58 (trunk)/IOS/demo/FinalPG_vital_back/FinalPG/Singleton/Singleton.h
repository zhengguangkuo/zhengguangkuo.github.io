//
//  Singleton.h
//  FinalPG
//
//  Created by guorong on 13-11-20.
//  Copyright (c) 2013年 guorong. All rights reserved.
//

#ifndef Singleton_h
#define Singleton_h

#define singleton_for_interface(className) +(className*)shared##className ;



#define single_for_implementation(className) static className* _instance ;\
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
