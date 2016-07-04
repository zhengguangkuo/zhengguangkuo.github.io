//
//  lib.h
//  demo
//
//  Created by liuzhuan on 14-9-3.
//  Copyright (c) 2014年 hengdawb. All rights reserved.
//

#ifndef demo_lib_h
#define demo_lib_h
typedef unsigned char   UCHAR;

/* Œ™DES(),_DES()∫Ø ˝µƒ»Î≤ŒcDesType◊÷∂Œ∂®“Â */
#define SIMPLE_DES '1' /* 单倍加密 */
#define TRIMPLE_DES '2' /* 三倍加密 */

/* Œ™∫Ø ˝tAsc2Bcd( )µƒ»Î≤ŒcAlign∂®“Â */
#define LEFT_ALIGN  0x01            /* ◊Û∂‘∆Î       */
#define RIGHT_ALIGN 0x00            /* ”“∂‘∆Î       */

void tDes( UCHAR *pcClearBlockBcd, UCHAR *pcKeyBcd, UCHAR cDesType, UCHAR *pcEncBlockBcd );
void _tDes( UCHAR *pcEncBlockBcd, UCHAR *pcKeyBcd, UCHAR cDesType, UCHAR *pcClearBlockBcd );

void tDesAsc( UCHAR *pcClearBlockAsc, UCHAR *pcKeyAsc, UCHAR cDesType, UCHAR *pcEncBlockAsc );
void _tDesAsc( UCHAR *pcEncBlockAsc, UCHAR *pcKeyAsc, UCHAR cDesType, UCHAR *pcClearBlockAsc );

void tPin( UCHAR *pcPinBlockBcd, char *pcCardNo, char *pcPwd, UCHAR cDesType, UCHAR *pcKeyBcd );
void _tPin( char *pcPwdAsc, char *pcCardNo, UCHAR *pcPinBlockBcd, UCHAR cDesType, UCHAR *pcKeyBcd );

void tPrintHex( char *pcTitle, char *pcData, int iLen );

char* say_hello(void);

void tDes( unsigned char *pcClearBlock, unsigned char *pcKey, unsigned char cDesType, unsigned char *pcEncryptBlock );
#endif
