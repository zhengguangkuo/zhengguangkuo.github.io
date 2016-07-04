#import <Foundation/Foundation.h>


@interface SaleDetail: NSObject {
    NSString*   saleId;
    NSString*   saleName;
    NSString*   saleArtist;
    NSString*   saleImage;
}
@property   (nonatomic, copy)   NSString*   saleId;    
@property   (nonatomic, copy)   NSString*   saleName;       
@property   (nonatomic, copy)   NSString*   saleArtist;
@property   (nonatomic, copy)   NSString*   saleImage;  
@end

