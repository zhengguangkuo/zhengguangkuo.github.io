��ǰ�汾Ϊ��iOS SDK v2.2.0

���ϸ��汾�Ĺ������������£�

������
	������ͼ��ʵ�������������߿���ͬһ��ҳ���Ϲ��������໥�����ĵ�ͼ������ͼ�ϵĸ����ﻥ�����ţ�
	����������ʵ�������������߿ɲ��з����������������Լ�ʵ�ʵ�ҵ������
	��������������ʵ�������������Ҫ��BMKSearchDelegate�Ļص�������searcher�������������ĸ�����������ļ���������Ӧ�ü�����ʵ��ʱ��Ҫ�����������searcher������һһ��Ӧ��	
	ʾ�����£�
	- (void)onGetPoiResult:(BMKSearch*)searcher result:(NSArray*)poiResultListsearchType:(int)type errorCode:(int)error{
		if(searcher==_search){
			NSLog(@"����_search ��Ӧ��POI�������");
	 	}
		else if(searcher==_search2){
			NSLog(@"����_search2��Ӧ��POI�������");        
		}
	}
	������ͼ�����С���ŵȼ��Ŀ��Ʒ���
		����BMKMapView����������@property (nonatomic) float minZoomLevel;���趨��ͼ���Զ�����С�����߼���
		����BMKMapView����������@property (nonatomic) float maxZoomLevel;���趨��ͼ���Զ����������߼���
	������ͼ���������ƿ��ƿ���
		����BMKMapView����������@property(nonatomic, getter=isZoomEnabledWithTap) BOOL zoomEnabledWithTap;���趨��ͼView�ܷ�֧���û���ָ˫���Ŵ��ͼ��˫ָ������С��ͼ
		����BMKMapView����������@property(nonatomic, getter=isOverlookEnabled) BOOL overlookEnabled;���趨��ͼView�ܷ�֧�ָ�����
		����BMKMapView����������@property(nonatomic, getter=isRotateEnabled) BOOL rotateEnabled;���趨��ͼView�ܷ�֧����ת
�޸���
	�޸�����zip���ͻ����
	���Documents�µķ��û������ϴ�iCloud������
	�޸�BMKMapViewDelegate��regionDidChangeAnimated / regionWillChangeAnimatedͼ���仯����

