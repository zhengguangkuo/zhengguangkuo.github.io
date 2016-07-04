#define  FULL_SCREEN_Bounds    [UIScreen mainScreen].bounds

#define  FULL_SCREEN_Width    [UIScreen mainScreen].applicationFrame.size.width

#define  FULL_SCREEN_Height   [UIScreen mainScreen].applicationFrame.size.height

#define  TAB_SIZE_Height      49

#define  NAVI_SIZE_Height     44

//#define  WEB_SERVICE_ENV_VAR   @"http://192.168.16.115:8090/miteno-mobile/"

//#define  WEB_SERVICE_ENV_VAR   @"http://www.nfclive.com.cn/miteno-mobile/"

//#define  WEB_SERVICE_ENV_VAR   @"http://192.168.16.97:8080/miteno-mobile/"

#define    WEB_SERVICE_ENV_VAR   @"http://192.168.16.27:8092/miteno-mobile/"

//#define WEB_SERVICE_ENV_VAR      @"http://app.modepay.cn/miteno-mobile/"



// IPhone5 screen Adjust for UI
// When Size of Height less than 1136 return
// NO or Return Act Size
#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)

// BackGround-color for UIView,
// input the value of red/green/blue To
// mix into  BackGround-color


#ifdef DEBUG
#define DLog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__,##__VA_ARGS__);
#else
#define DLog(...)
#endif
// ALog always displays output regardless of the DEBUG setting
#define ALog(fmt, ...) NSLog((@"%s [Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__);


static NSString *const TopPaidAppsFeed =
@"http://phobos.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=75/xml";

static NSString *const PostUrl = @"http://allseeing-i.com/ignore";









