#! /bin/sh
#
# Script to generate distribution package
#

PRG="$0"
PRGDIR=`dirname "$PRG"`

echo 'making distribution package ...'
if test ! -f "$PRGDIR"/"dist"; then
  rm -rf "$PRGDIR"/dist/
fi
mkdir "$PRGDIR"/dist

# add  --refresh-dependencies if you want to ignore dependency caches
"$PRGDIR"/gradlew clean build -x test

echo 'copying executable ...'
cp "$PRGDIR"/build/libs/quick-mvc-*.war "$PRGDIR"/dist/app.war

echo 'copying startup scripts ...'
cp "$PRGDIR"/scripts/* "$PRGDIR"/dist/

echo 'copying README.md ...'
cp "$PRGDIR"/README.md "$PRGDIR"/dist/

echo 'done!'