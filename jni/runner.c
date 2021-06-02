#include <stdio.h>
#include "Test.h"

//#define JNIFUNC(v)  Java_com_example_evaluate_sqlite3_android_##v
#define JNIFUNC(v)  Java_com_example_evaluate_1sqlite3_1android_MainActivity_##v

void
JNIFUNC(TestFunc)(){
        printf("v\n");
}

#if 0
void JNIFUNC(ExecTestInit)(char *dbPath, char *logDirPath)
{
    TestInit(dbPath, logDirPath);
}

void JNIFUNC(ExecTest001_01)(char *dbPath, char *logDirPath, char *logTag, int testCount, long tid)
{
    Test001_01(dbPath, logDirPath, logTag, testCount, tid);
}

void JNIFUNC(ExecTest002_01)(char *dbPath, char *logDirPath, char *logTag, int testCount, long tid)
{
    Test002_01(dbPath, logDirPath, logTag, testCount, tid);
}

void JNIFUNC(ExecTest002_02)(char *dbPath, char *logDirPath, char *logTag, int testCount, long tid)
{
    Test002_02(dbPath, logDirPath, logTag, testCount, tid);
}
#endif
