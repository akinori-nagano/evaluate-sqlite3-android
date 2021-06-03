#!/bin/sh

SCRIPT_DIR=$(cd $(dirname $0); pwd)
cd "${SCRIPT_DIR}"

. ./jni/environment.source

set -ue

export NDK_PROJECT_PATH="${SCRIPT_DIR}"

if [ `uname` = "Darwin" ] && [ `uname -m` = "arm64" ]; then
	### M1 mac の場合、rosetta2 で x86バイナリを実行する必要がある
	arch -x86_64 /bin/bash -c \
		ndk-build \
		NDK_PROJECT_PATH=${NDK_PROJECT_PATH} \
		APP_BUILD_SCRIPT=${NDK_PROJECT_PATH}/Android.mk \
		NDK_APPLICATION_MK=${NDK_PROJECT_PATH}/Application.mk
else
	ndk-build \
		NDK_PROJECT_PATH=${NDK_PROJECT_PATH} \
		APP_BUILD_SCRIPT=${NDK_PROJECT_PATH}/Android.mk \
		NDK_APPLICATION_MK=${NDK_PROJECT_PATH}/Application.mk
fi

cp -pr ./libs/* app/src/main/jniLibs/
