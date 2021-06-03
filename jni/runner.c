#include <jni.h>
#include <android/log.h>
	#include <stdio.h>
	#include <stdlib.h>
	#include <time.h>
	#include <sys/stat.h>
#include "Test.h"

//#define JNIFUNC(v)  Java_com_example_evaluate_sqlite3_android_##v
#define JNIFUNC(v)  Java_com_example_evaluate_1sqlite3_1android_MainActivity_##v


#define JNI_GetStringUTFChars(__ENV__, __SRC__, __DEF__) (*(__ENV__))->GetStringUTFChars((__ENV__), (__SRC__), (__DEF__));
#define JNI_ReleaseStringUTFChars(__ENV__, __SRC__, __CSTR__) (*(__ENV__))->ReleaseStringUTFChars((__ENV__), (__SRC__), (__CSTR__));

JNIEXPORT jint JNICALL
JNIFUNC(TestFunc)(
    JNIEnv  *env,
	jobject thisobj,
    jstring dbPath,
    jstring logDirPath,
    jstring logTag,
    jint    testCount,
    jlong   tid)
{
	const char *cstrDbPath = JNI_GetStringUTFChars(env, dbPath, 0);
	const char *cstrLogDirPath = JNI_GetStringUTFChars(env, logDirPath, 0);
	const char *cstrLogTag = JNI_GetStringUTFChars(env, logTag, 0);
	const int cintTestCount = testCount;
	const long clongTid = tid;

	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:dbPath:     [%s]", cstrDbPath);
	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:logDirPath: [%s]", cstrLogDirPath);
	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:logTag:     [%s]", cstrLogTag);
	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:testCount:  [%d]", cintTestCount);
	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:tid:        [%ld]", clongTid);

	{
		struct stat stat_buf;
		if (stat(cstrDbPath, &stat_buf) == 0) {
			__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "BlockSize: %ld", stat_buf.st_blksize);
		} else {
			__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "stat failed.");
		}
	}

	JNI_ReleaseStringUTFChars(env, dbPath, cstrDbPath);
	JNI_ReleaseStringUTFChars(env, logDirPath, cstrLogDirPath);
	JNI_ReleaseStringUTFChars(env, logTag, cstrLogTag);

	jint status = 100;
	return status;
}

JNIEXPORT void JNICALL
JNIFUNC(ExecTestInit)(
		JNIEnv  *env,
		jobject thisobj,
		jstring dbPath,
		jstring logDirPath)
{
	const char *cstrDbPath = JNI_GetStringUTFChars(env, dbPath, 0);
	const char *cstrLogDirPath = JNI_GetStringUTFChars(env, logDirPath, 0);

	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:dbPath:     [%s]", cstrDbPath);
	__android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, "JNI:logDirPath: [%s]", cstrLogDirPath);

    TestInit(cstrDbPath, cstrLogDirPath);

	JNI_ReleaseStringUTFChars(env, logDirPath, cstrLogDirPath);
	JNI_ReleaseStringUTFChars(env, dbPath, cstrDbPath);
}

JNIEXPORT void JNICALL
JNIFUNC(ExecTest001N01)(
		JNIEnv  *env,
		jobject thisobj,
		jstring dbPath,
		jstring logDirPath,
		jstring logTag,
		jint    testCount,
		jlong   tid)
{
	const char *cstrDbPath = JNI_GetStringUTFChars(env, dbPath, 0);
	const char *cstrLogDirPath = JNI_GetStringUTFChars(env, logDirPath, 0);
	const char *cstrLogTag = JNI_GetStringUTFChars(env, logTag, 0);
	const int cintTestCount = testCount;
	const long clongTid = tid;

    Test001_01(cstrDbPath, cstrLogDirPath, cstrLogTag, cintTestCount, clongTid);

	JNI_ReleaseStringUTFChars(env, logTag, cstrLogTag);
	JNI_ReleaseStringUTFChars(env, logDirPath, cstrLogDirPath);
	JNI_ReleaseStringUTFChars(env, dbPath, cstrDbPath);
}

JNIEXPORT void JNICALL
JNIFUNC(ExecTest002N01)(
		JNIEnv  *env,
		jobject thisobj,
		jstring dbPath,
		jstring logDirPath,
		jstring logTag,
		jint    testCount,
		jlong   tid)
{
	const char *cstrDbPath = JNI_GetStringUTFChars(env, dbPath, 0);
	const char *cstrLogDirPath = JNI_GetStringUTFChars(env, logDirPath, 0);
	const char *cstrLogTag = JNI_GetStringUTFChars(env, logTag, 0);
	const int cintTestCount = testCount;
	const long clongTid = tid;

    Test002_01(cstrDbPath, cstrLogDirPath, cstrLogTag, testCount, tid);

	JNI_ReleaseStringUTFChars(env, logTag, cstrLogTag);
	JNI_ReleaseStringUTFChars(env, logDirPath, cstrLogDirPath);
	JNI_ReleaseStringUTFChars(env, dbPath, cstrDbPath);
}

JNIEXPORT void JNICALL
JNIFUNC(ExecTest002N02)(
		JNIEnv  *env,
		jobject thisobj,
		jstring dbPath,
		jstring logDirPath,
		jstring logTag,
		jint    testCount,
		jlong   tid)
{
	const char *cstrDbPath = JNI_GetStringUTFChars(env, dbPath, 0);
	const char *cstrLogDirPath = JNI_GetStringUTFChars(env, logDirPath, 0);
	const char *cstrLogTag = JNI_GetStringUTFChars(env, logTag, 0);
	const int cintTestCount = testCount;
	const long clongTid = tid;

    Test002_02(cstrDbPath, cstrLogDirPath, cstrLogTag, testCount, tid);

	JNI_ReleaseStringUTFChars(env, logTag, cstrLogTag);
	JNI_ReleaseStringUTFChars(env, logDirPath, cstrLogDirPath);
	JNI_ReleaseStringUTFChars(env, dbPath, cstrDbPath);
}
