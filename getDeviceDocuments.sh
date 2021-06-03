#!/bin/sh

set -ue

OUTPUT_DIR="./DeviceDocuments"

if [ ! -d "${OUTPUT_DIR}" ]; then
	mkdir "${OUTPUT_DIR}";
fi

cd "${OUTPUT_DIR}";
# adb pull "/sdcard/Android/data/com.example.evaluate_sqlite3_android/files/Documents/logs" .
# adb pull "/sdcard/Android/data/com.example.evaluate_sqlite3_android/files/Documents/db.sqlite3" .
adb pull "/sdcard/Android/data/com.example.evaluate_sqlite3_android/files/Documents/." .
