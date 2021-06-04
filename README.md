# evaluate-sqlite3-android

## setup

```
$ git submodule init
$ git submodule update
```

## ビルド方法

以下のコマンドでC言語ファイルをビルドし共有ライブラリを作成する

```
$ sh ./ndk-build.sh
```

Android Studio などから app を作成する

## databaseファイルコピー

```
$ cp -p jni/testmain/db.sqlite3 app/src/main/assets/
```

## 初期処理

```
$ git submodule add https://github.com/akinori-nagano/evaluate-sqlite3.git ./dependency/evaluate-sqlite3
```
