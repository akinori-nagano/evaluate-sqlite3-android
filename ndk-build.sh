#!/bin/sh

SCRIPT_DIR=$(cd $(dirname $0); pwd)
cd "${SCRIPT_DIR}"

. ./jni/environment.source

set -ue

export NDK_PROJECT_PATH="${SCRIPT_DIR}"

arch -x86_64 /bin/bash -c \
	ndk-build \
	NDK_PROJECT_PATH=${NDK_PROJECT_PATH} \
	APP_BUILD_SCRIPT=${NDK_PROJECT_PATH}/Android.mk \
	NDK_APPLICATION_MK=${NDK_PROJECT_PATH}/Application.mk

#arch -x86_64 /bin/bash -c ndk-build

cp -pr ./libs/* app/src/main/jniLibs/
