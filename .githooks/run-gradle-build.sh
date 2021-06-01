#!/bin/sh

echo "Start run gradle build"
echo "Current Dir: $(pwd)"
./gradlew build --info

RESULT=$?

echo "End run gradle build"

exit $RESULT
