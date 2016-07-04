//
//  BusinessDetailsFirstView.h
//  BusinessDetailsViewController
//
//  Created by APPLE on 14-6-10.
//  Copyright (c) 2014年 APPLE. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BusinessDetailsFirstView : UIView<UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate>
{
    UIImageView * _businessIV;
    UILabel * _businessNameLB;
    //UILabel * _keyWordsLB;
    UIButton * _collectBTN;
    UITableView * _FirstViewTV;
 
  //  NSMutableArray * _arr;
   
    NSString * _businessImageURL;
    NSString * _businessName;
    NSString * _businessAddress;
    NSString * _businessTelphone;
   // NSString * _keyWords;
    
    NSDictionary * _mapDic;
    
    UIViewController * _VC;
}
//-(id)initWithBusinessImage:(NSString*)businessImage andBusinessName:(NSString*)businessName andKeyWords:(NSString*)keyWords andAdressAndPhoneArr:(NSMutableArray*)arr andFrame:(CGRect)rect;
-(id)initWithFrame:(CGRect)frame andDic:(NSDictionary*)dic andImageURL:(NSString*)ImageURL andBusinessDetailsViewController:(UIViewController*)VC;
@end
