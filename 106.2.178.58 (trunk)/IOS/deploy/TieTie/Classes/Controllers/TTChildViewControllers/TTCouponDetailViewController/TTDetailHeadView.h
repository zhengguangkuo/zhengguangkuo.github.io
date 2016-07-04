//
//  TTDetailHeadView.h
//  Miteno
//
//  Created by wg on 14-6-10.
//  Copyright (c) 2014年 wenguang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface TTDetailHeadView : UIView
@property (weak, nonatomic) IBOutlet UIImageView    * headImageView;       //头部大图
@property (weak, nonatomic) IBOutlet UIImageView    * headIcon;            //头部小图
@property (weak, nonatomic) IBOutlet UIView         * headBg;              //背景图
@property (weak, nonatomic) IBOutlet UILabel        * headDate;            //
@property (weak, nonatomic) IBOutlet UILabel        * headMerTitle;        //商家标题
@property (weak, nonatomic) IBOutlet UIButton       * headMerDetail;       //商家详情按钮
@property (weak, nonatomic) IBOutlet UIButton       * headClaim;           //领、返标示

@property (weak, nonatomic) IBOutlet UIButton       * headShare;           //分享



@property (weak, nonatomic) IBOutlet UIImageView    * headDownBg;          //条背景

+(instancetype)detailHeadView;
@end
