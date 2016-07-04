当前版本为：iOS SDK v2.2.0

较上个版本的功能升级点如下：

新增：
	新增地图多实例能力，开发者可在同一个页面上构建多张相互独立的地图，各地图上的覆盖物互不干扰；
	新增检索多实例能力，开发者可并行发起多个检索来满足自己实际的业务需求
	由于新增检索多实例能力，因此需要在BMKSearchDelegate的回调中增加searcher参数来表明是哪个检索对象发起的检索。所以应用检索多实例时需要将检索结果和searcher来进行一一对应。	
	示例如下：
	- (void)onGetPoiResult:(BMKSearch*)searcher result:(NSArray*)poiResultListsearchType:(int)type errorCode:(int)error{
		if(searcher==_search){
			NSLog(@"这是_search 对应的POI搜索结果");
	 	}
		else if(searcher==_search2){
			NSLog(@"这是_search2对应的POI搜索结果");        
		}
	}
	新增地图最大、最小缩放等级的控制方法
		在类BMKMapView中新增属性@property (nonatomic) float minZoomLevel;来设定地图的自定义最小比例尺级别
		在类BMKMapView中新增属性@property (nonatomic) float maxZoomLevel;来设定地图的自定义最大比例尺级别
	新增地图操作的手势控制开关
		在类BMKMapView中新增属性@property(nonatomic, getter=isZoomEnabledWithTap) BOOL zoomEnabledWithTap;来设定地图View能否支持用户单指双击放大地图，双指单击缩小地图
		在类BMKMapView中新增属性@property(nonatomic, getter=isOverlookEnabled) BOOL overlookEnabled;来设定地图View能否支持俯仰角
		在类BMKMapView中新增属性@property(nonatomic, getter=isRotateEnabled) BOOL rotateEnabled;来设定地图View能否支持旋转
修复：
	修复遗留zip库冲突问题
	解决Documents下的非用户数据上传iCloud的问题
	修复BMKMapViewDelegate中regionDidChangeAnimated / regionWillChangeAnimated图区变化问题

