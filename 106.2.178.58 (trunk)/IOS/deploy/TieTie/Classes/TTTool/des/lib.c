//
//  lib.c
//  demo
//
//  Created by liuzhuan on 14-9-3.
//  Copyright (c) 2014年 hengdawb. All rights reserved.
//

#include <stdio.h>
#include <string.h>
#include "lib.h"
char* say_hello(void){
    char *b = "HEELO";
    printf("hello world");
    return b;
}

/*****************************************************************************
 ** ∫Ø ˝£∫tStrCpy( )                                                         **
 ** ¿‡–Õ£∫void                                                               **
 ** »Î≤Œ:                                                                    **
 **      void *pvTag ( ƒø±Í◊÷∑˚¥Æ )                                          **
 **      void *pvSrc ( ‘¥◊÷∑˚¥Æ )                                            **
 **      int iLen ( øΩ±¥≥§∂» )                                               **
 ** ≥ˆ≤Œ: Œﬁ                                                                 **
 ** ∑µªÿ÷µ£∫ Œﬁ                                                              **
 ** π¶ƒ‹: Ω´‘¥◊÷∑˚¥Æƒ⁄»›øΩ±¥÷¡ƒø±Í◊÷∑˚¥Æ÷–,‘⁄ƒø±Í◊÷∑˚¥ÆŒ≤◊‘∂ØÃÌº”'\0'        **
 *****************************************************************************/
void    tStrCpy( void *pvTag, void *pvSrc, int iLen )
{
    memcpy( pvTag, pvSrc, iLen );
    ( (char *)pvTag )[iLen] = '\0';
}


/*****************************************************************************
 ** ∫Ø ˝£∫tAsc2Bcd( )                                                        **
 ** ¿‡–Õ£∫void                                                               **
 ** »Î≤Œ:                                                                    **
 **      unsigned char *pcAsc  ASCII ˝æ›ª∫≥Â«¯ )                             **
 **      int iLen (ASCII ˝æ›≥§∂» )                                           **
 **      char cAlign ( ∂‘∆Î∑Ω Ω )                                            **
 ** ≥ˆ≤Œ:                                                                    **
 **      unsigned char *pcBcd ( BCD ˝æ›ª∫≥Â«¯ )                              **
 ** ∑µªÿ÷µ£∫ Œﬁ                                                              **
 ** π¶ƒ‹: Ω´ASCII◊÷∑˚¥Æ◊™ªª≥…BCD±‡¬Îµƒ ˝æ›                                   **
 *****************************************************************************/
void tAsc2Bcd( unsigned char *pcBcd, unsigned char *pcAsc, int iLen, char cAlign )
{
    int     i, flag = 0;
    unsigned char ch;
    
    memset( pcBcd, 0, (iLen + 1) / 2 );
    
    if ( (iLen % 2) && cAlign == LEFT_ALIGN )
        flag = 1;
    
    for ( i = 0; i < iLen; i++ )
    {
        if ( pcAsc[i] >= 'a' )
            ch = pcAsc[i] - 'a' + 10;
        else if ( pcAsc[i] >= 'A' )
            ch = pcAsc[i] - 'A' + 10;
        else if ( pcAsc[i] >= '0' )
            ch = pcAsc[i] - '0';
        else
            ch = 0;
        if ( (i + flag) % 2 )
            pcBcd[(i + flag) / 2] |= (ch & 0x0F);
        else
            pcBcd[(i + flag) / 2] |= (ch << 4);
    }
}


/*****************************************************************************
 ** ∫Ø ˝£∫tBcd2Asc( )                                                        **
 ** ¿‡–Õ£∫void                                                               **
 ** »Î≤Œ:                                                                    **
 **      unsigned char *pcBcd ( BCD ˝æ›ª∫≥Â«¯ )                              **
 **      int iLen (ASCII ˝æ›≥§∂» )                                           **
 **      char cAlign ( ∂‘∆Î∑Ω Ω )                                            **
 ** ≥ˆ≤Œ:                                                                    **
 **      unsigned char *pcAsc  ASCII ˝æ›ª∫≥Â«¯ )                             **
 ** ∑µªÿ÷µ£∫ Œﬁ                                                              **
 ** π¶ƒ‹: Ω´BCD±‡¬Îµƒ ˝æ›◊™ªª≥…ASCII±‡¬Îµƒ ˝æ›                               **
 *****************************************************************************/
void tBcd2Asc( unsigned char *pcAsc, unsigned char *pcBcd, int iLen, char cAlign )
{
    int i, flag = 0;
    
    if ( (iLen % 2) && cAlign == LEFT_ALIGN )
        flag = 1;
    
    for ( i = 0; i < iLen; i++ )
    {
        if ( (i + flag) % 2 )
            pcAsc[i] = pcBcd[(i + flag) / 2] & 0x0F;
        else
            pcAsc[i] = (pcBcd[(i + flag) / 2] >> 4);
        pcAsc[i] += (pcAsc[i] > 9) ? ('A' - 10) : '0';
    }
}


void tPrintHex( char *pcTitle, char *pcData, int iLen )
{
    char sTmp[2048] = {0};
    int i;
    for ( i=0; i<iLen; i++ )
    {
        sprintf( sTmp+i*2, "%02X", (unsigned char)pcData[i] );
    }
    fprintf( stderr, "%s:%s\n", pcTitle, sTmp );
}

static void encrypt0(unsigned char *text, unsigned char *mtext);
static void discrypt0(unsigned char *mtext, unsigned char *text);
static void expand0(unsigned char *in, unsigned char *out);
static void compress0(unsigned char *out, unsigned char *in);
static void compress016(unsigned char *out, unsigned char *in);
static void setkeystar(unsigned char *bits);
static void LS(unsigned char *bits, unsigned char *buffer, int count);
static void son(unsigned char *cc, unsigned char *dd, unsigned char *kk);
static void ip(unsigned char *text, unsigned char *ll, unsigned char *rr);
static void _ip(unsigned char *text, unsigned char *ll, unsigned char *rr);
static void F(int n, unsigned char *ll, unsigned char *rr, unsigned char *LL, unsigned char *RR);
static void s_box(unsigned char *aa, unsigned char *bb);

#define PIN_LEN         8

static unsigned char C[17][28], D[17][28], K[17][48];

static int e_r[48] = {
    32,  1,  2,  3,  4,  5,  4,  5,  6,  7,  8,  9,
    8,  9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17,
    16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25,
    24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32,  1
};

static int P[32] = {
    16,  7, 20, 21, 29, 12, 28, 17,
    1, 15, 23, 26,  5, 18, 31, 10,
    2,  8, 24, 14, 32, 27,  3,  9,
    19, 13, 30,  6, 22, 11,  4, 25
};

static int SSS[16][4][16] = {
    14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,
    0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,/* err on */
    4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
    15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13,
    
    15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
    3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
    0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
    13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9,
    
    10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
    13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
    13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
    1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12,
    
    7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
    13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
    10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,
    3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14, /* err on */
    
    2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,
    14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6, /* err on */
    4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
    11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3,
    
    12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,
    10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
    9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
    4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13,
    
    4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
    13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
    1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
    6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12,
    
    13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
    1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
    7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
    2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11
};

static int	pc_1_c[28] = {
    57, 49, 41, 33, 25, 17,  9,
    1, 58, 50, 42, 34, 26, 18,
    10,  2, 59, 51, 43, 35, 27,
    19, 11,  3, 60, 52, 44, 36
};

static int	pc_1_d[28] = {
    63, 55, 47, 39, 31, 23, 15,
    7, 62, 54, 46, 38, 30, 22,
    14,  6, 61, 53, 45, 37, 29,
    21, 13,  5, 28, 20, 12,  4
};

static int	pc_2[48] = {
    14, 17, 11, 24,  1,  5,
    3, 28, 15,  6, 21, 10,
    23, 19, 12,  4, 26,  8,
    16,  7, 27, 20, 13,  2,
    41, 52, 31, 37, 47, 55,
    30, 40, 51, 45, 33, 48,
    44, 49, 39, 56, 34, 53,
    46, 42, 50, 36, 29, 32
};

static int	ls_count[16] = {
    1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
};

static int	ip_tab[64] = {
    58, 50, 42, 34, 26, 18, 10,  2,
    60, 52, 44, 36, 28, 20, 12,  4,
    62, 54, 46, 38, 30, 22, 14,  6,
    64, 56, 48, 40, 32, 24, 16,  8,
    57, 49, 41, 33, 25, 17,  9,  1,
    59, 51, 43, 35, 27, 19, 11,  3,
    61, 53, 45, 37, 29, 21, 13,  5,
    63, 55, 47, 39, 31, 23, 15,  7
};

static int	_ip_tab[64] = {
    40,  8, 48, 16, 56, 24, 64, 32,
    39,  7, 47, 15, 55, 23, 63, 31,
    38,  6, 46, 14, 54, 22, 62, 30,
    37,  5, 45, 13, 53, 21, 61, 29,
    36,  4, 44, 12, 52, 20, 60, 28,
    35,  3, 43, 11, 51, 19, 59, 27,
    34,  2, 42, 10, 50, 18, 58, 26,
    33,  1, 41,  9, 49, 17, 57, 25
};

unsigned char lrc(char *str, int len)
{
    unsigned char   uc = 0;
    int     i;
    for (i = 0; i < len; i++)  uc ^= str[i];
    return(uc);
}

void Des(unsigned char *key, unsigned char *text, unsigned char *mtext)
{
    unsigned char	tmp[64];
    
    expand0(key, tmp);
    setkeystar(tmp);
    encrypt0(text, mtext);
}

void _Des(unsigned char *key, unsigned char *text, unsigned char *mtext)
{
    unsigned char	tmp[64];
    
    expand0(key, tmp);
    setkeystar(tmp);
    discrypt0(text, mtext);
}

static void encrypt0(unsigned char *text, unsigned char *mtext)
{
    unsigned char	ll[64], rr[64], LL[64], RR[64];
    unsigned char	tmp[64];
    int	i, j;
    
    ip(text, ll, rr);
    
    for (i = 1; i < 17; i++)
    {
        F(i, ll, rr, LL, RR);
        for (j = 0; j < 32; j++)
        {
            ll[j] = LL[j];
            rr[j] = RR[j];
        }
    }
    
    _ip(tmp, rr, ll);
    
    compress0(tmp, mtext);
}

static void discrypt0(unsigned char *mtext, unsigned char *text)
{
    unsigned char	ll[64], rr[64], LL[64], RR[64];
    unsigned char	tmp[64];
    int	i, j;
    
    ip(mtext, ll, rr);
    
    for (i = 16; i > 0; i--)
    {
        F(i, ll, rr, LL, RR);
        
        for (j = 0; j < 32; j++)
        {
            ll[j] = LL[j];
            rr[j] = RR[j];
        }
    }
    
    _ip(tmp, rr, ll);
    
    compress0(tmp, text);
}

static void expand0(unsigned char *in, unsigned char *out)
{
    int	divide;
    int	i, j;
    
    for (i = 0; i < 8; i++)
    {
        divide = 0x80;
        for (j = 0; j < 8; j++)
        {
            *out++ = ((in[i] / divide) & 1);
            divide /= 2;
        }
    }
}

static void compress0(unsigned char *out, unsigned char *in)
{
    int	times;
    int	i, j;
    
    for (i = 0; i < 8; i++)
    {
        times = 0x80;
        in[i] = 0;
        for (j = 0; j < 8; j++)
        {
            in[i] += (*out++) * times;
            times /= 2;
        }
    }
}

static void compress016(unsigned char *out, unsigned char *in)
{
    int	times;
    int	i, j;
    
    for (i = 0; i < 16; i++)
    {
        times = 0x8;
        in[i] = '0';
        for (j = 0; j < 4; j++)
        {
            in[i] += (*out++) * times;
            times /= 2;
        }
    }
}

static void setkeystar(unsigned char *bits)
{
    int	i, j;
    
    for (i = 0; i < 28; i++)
        C[0][i] = bits[pc_1_c[i] - 1];
    for (i = 0; i < 28; i++)
        D[0][i] = bits[pc_1_d[i] - 1];
    for (j = 0; j < 16; j++)
    {
        LS(C[j], C[j+1], ls_count[j]);
        LS(D[j], D[j+1], ls_count[j]);
        son(C[j+1], D[j+1], K[j+1]);
    }
}

static void LS(unsigned char *bits, unsigned char *buffer, int count)
{
    int	i;
    
    for (i = 0; i < 28; i++)
    {
        buffer[i] = bits[(i + count) % 28];
    }
}

static void son(unsigned char *cc, unsigned char *dd, unsigned char *kk)
{
    int	i;
    unsigned char	buffer[56];
    
    for (i = 0; i < 28; i++)
        buffer[i] = *cc++;
    
    for (i = 28; i < 56; i++)
        buffer[i] = *dd++;
    
    for (i = 0; i < 48; i++)
        *kk++ = buffer[pc_2[i] - 1];
}

static void ip(unsigned char *text, unsigned char *ll, unsigned char *rr)
{
    int	i;
    unsigned char	buffer[64];
    
    expand0(text, buffer);
    
    for (i = 0; i < 32; i++)
        ll[i] = buffer[ip_tab[i] - 1];
    
    for (i = 0; i < 32; i++)
        rr[i] = buffer[ip_tab[i+32] - 1];
}

static void _ip(unsigned char *text, unsigned char *ll, unsigned char *rr)
{
    int	i;
    unsigned char	tmp[64];
    
    for (i = 0; i < 32; i++)
        tmp[i] = ll[i];
    for (i = 32; i < 64; i++)
        tmp[i] = rr[i - 32];
    for (i = 0; i < 64; i++)
        text[i]=tmp[_ip_tab[i]-1];
}

static void F(int n, unsigned char *ll, unsigned char *rr, unsigned char *LL, unsigned char *RR)
{
    int	i;
    unsigned char	buffer[64], tmp[64];
    
    for (i = 0; i < 48; i++)
        buffer[i] = rr[e_r[i]-1];
    for (i = 0; i < 48; i++)
        buffer[i] = (buffer[i] + K[n][i]) & 1;
    
    s_box(buffer, tmp);
    
    for (i = 0; i < 32; i++)
        buffer[i] = tmp[P[i] - 1];
    
    for (i = 0; i < 32; i++)
        RR[i] = (buffer[i] + ll[i]) & 1;
    
    for (i = 0; i < 32; i++)
        LL[i] = rr[i];
}

static void s_box(unsigned char *aa, unsigned char *bb)
{
    int	i, j, k, m;
    int	y, z;
    unsigned char	ss[8];
    
    m = 0;
    for (i = 0; i < 8; i++)
    {
        j = 6 * i;
        y = aa[j] * 2 + aa[j+5];
        z = aa[j+1] * 8 + aa[j+2] * 4 + aa[j+3] * 2 + aa[j+4];
        ss[i] = SSS[i][y][z];
        y = 0x08;
        for (k = 0; k < 4; k++)
        {
            bb[m++] = (ss[i] / y) & 1;
            y /= 2;
        }
        
    }
}

void Ansix98( char *pcAcctInfo, char *pcPwd, unsigned char *pcPinBlock )
{
    int		i, iAcctLen, iPwdLen;
    char	sAcctPart[17], sPwdPart[17];
    unsigned char	sAcctBcd[8], sPwdBcd[8];
    
    
    iAcctLen = strlen( pcAcctInfo );
    iPwdLen = strlen( pcPwd );
    
    memset( sAcctPart, '0', sizeof(sAcctPart) );
    memcpy( sAcctPart + 4, pcAcctInfo + iAcctLen - 13, 12 );
    sAcctPart[16] = '\0';
    tAsc2Bcd( sAcctBcd, (unsigned char *)sAcctPart, 16, LEFT_ALIGN );
    
    sprintf( sPwdPart, "%02d", iPwdLen );
    memset( sPwdPart + 2, 0x3F, 14 );
    memcpy( sPwdPart + 2, pcPwd, iPwdLen );
    sPwdPart[16] = '\0';
    tAsc2Bcd( sPwdBcd, (unsigned char *)sPwdPart, 16, LEFT_ALIGN );
    
    /* ansix9.8±Í◊ºÀ„∑® */
    for ( i = 0; i < 8; i++ )
        pcPinBlock[i] = sAcctBcd[i] ^ sPwdBcd[i];
}

void _Ansix98( char *pcAcctInfo, unsigned char *pcPinBlock, char *pcPwd )
{
    int		i, iAcctLen, iPwdLen;
    unsigned char	sAcctPart[17], sPwdPart[17], sTmp[3];
    unsigned char	sAcctBcd[8], sPwdBcd[8];
    
    iAcctLen = strlen( pcAcctInfo );
    memset( sAcctPart, '0', sizeof(sAcctPart) );
    memcpy( sAcctPart + 4, pcAcctInfo + iAcctLen - 13, 12 );
    sAcctPart[16] = '\0';
    tAsc2Bcd( sAcctBcd, (unsigned char *)sAcctPart, 16, LEFT_ALIGN );
    
    for ( i = 0; i < 8; i++ )
        sPwdBcd[i] = pcPinBlock[i] ^ sAcctBcd[i];
    
    tBcd2Asc( sPwdPart, (unsigned char *)sPwdBcd, 16, LEFT_ALIGN );
    tStrCpy( sTmp, sPwdPart, 2 );
    iPwdLen = atoi( (char *)sTmp );
    tStrCpy( pcPwd, sPwdPart + 2, iPwdLen );
}

void tDes( unsigned char *pcClearBlock, unsigned char *pcKey, unsigned char cDesType, unsigned char *pcEncryptBlock )
{
    unsigned char	sKey1[30], sKey2[30], sBlock1[30], sBlock2[30];
    
    if ( cDesType == TRIMPLE_DES )
    {
        memcpy( sKey1, pcKey, 8 );
        memcpy( sKey2, pcKey + 8, 8 );
        Des( sKey1, pcClearBlock, sBlock1 );
        _Des( sKey2, sBlock1, sBlock2 );
        Des( sKey1, sBlock2, sBlock1 );
    }
    else
    {
        memcpy( sKey1, pcKey, 8 );
        Des( sKey1, pcClearBlock, sBlock1 );
    }
    memcpy( pcEncryptBlock, sBlock1, 8 );
}

void tDesAsc( unsigned char *pcClearBlock, unsigned char *pcKey, unsigned char cDesType, unsigned char *pcEncryptBlock )
{
    unsigned char  sClearBlockBcd[30], sKeyBcd[30], sEncryptBlockBcd[30];
    
    tAsc2Bcd( sKeyBcd, pcKey, strlen(pcKey), LEFT_ALIGN );
    tAsc2Bcd( sClearBlockBcd, pcClearBlock, strlen(pcClearBlock), LEFT_ALIGN );
    tDes( sClearBlockBcd, sKeyBcd, cDesType, sEncryptBlockBcd );
    tBcd2Asc( pcEncryptBlock, sEncryptBlockBcd, 16, LEFT_ALIGN );
}

void _tDes( unsigned char *pcEncryptBlock, unsigned char *pcKey, unsigned char cDesType, unsigned char *pcClearBlock )
{
    unsigned char	sKey1[8], sKey2[8], sBlock1[8], sBlock2[8];
    
    if ( cDesType == TRIMPLE_DES )
    {
        memcpy( sKey1, pcKey, 8 );
        memcpy( sKey2, pcKey + 8, 8 );
        _Des( sKey1, pcEncryptBlock, sBlock1 );
        Des( sKey2, sBlock1, sBlock2 );
        _Des( sKey1, sBlock2, sBlock1 );
    }
    else
    {
        memcpy( sKey1, pcKey, 8 );
        _Des( sKey1, pcEncryptBlock, sBlock1 );
    }
    memcpy( pcClearBlock, sBlock1, 8 );
}

void _tDesAsc( unsigned char *pcEncryptBlock, unsigned char *pcKey, unsigned char cDesType, unsigned char *pcClearBlock )
{
    unsigned char  sClearBlockBcd[30], sKeyBcd[30], sEncryptBlockBcd[30];
    
    tAsc2Bcd( sKeyBcd, pcKey, strlen(pcKey), LEFT_ALIGN );
    tAsc2Bcd( sEncryptBlockBcd, pcEncryptBlock, strlen(pcEncryptBlock), LEFT_ALIGN );
    _tDes( sEncryptBlockBcd, sKeyBcd, cDesType, sClearBlockBcd );	
    tBcd2Asc( pcClearBlock, sClearBlockBcd, 16, LEFT_ALIGN );
}

void tPin( unsigned char *pcPinBlockBcd, char *pcCardNo, char *pcPwd, unsigned char cDesType, unsigned char *pcKeyBcd )
{
    unsigned char	sPinBlock[8];
    
    Ansix98( pcCardNo, pcPwd, sPinBlock );
    tDes( sPinBlock, pcKeyBcd, cDesType, pcPinBlockBcd );
}

void _tPin( char *pcPwdAsc, char *pcCardNo, unsigned char *pcPinBlockBcd, unsigned char cDesType, unsigned char *pcKeyBcd )
{
    unsigned char	sPinBlock[8];
    
    _tDes( pcPinBlockBcd, pcKeyBcd, cDesType, sPinBlock );
    _Ansix98( pcCardNo, sPinBlock, pcPwdAsc );	
}
