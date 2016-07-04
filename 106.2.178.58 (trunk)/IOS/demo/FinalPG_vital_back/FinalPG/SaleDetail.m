
#import "SaleDetail.h"

@implementation SaleDetail
@synthesize    saleId;    
@synthesize    saleName;       
@synthesize    saleArtist; 
@synthesize    saleImage;


-(id)init{
    self = [super init];
    if (self) 
    {
        saleId = @"";    
        saleName = @"";       
        saleArtist = @"";
        saleImage = @"";  
    }    
    return self;
}



-(void)dealloc
{
    [saleId      release];    
    [saleName    release];
    [saleArtist  release];
    [saleImage   release];
    [super dealloc];
}

@end
