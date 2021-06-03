# evaluate-sqlite3-android

# ビルド方法

以下のコマンドでC言語ファイルをビルドし共有ライブラリを作成する

```
$ sh ./ndk-build.sh
```

Android Studio などから app を作成する

# databaseファイルコピー

```
cp -p jni/testmain/db.sqlite3 app/src/main/assets/
```
