#!/bin/sh

echo "Start Spotless Apply"

STAGED_FILES=$(git --no-pager diff --name-only --cached | tr '\n' ' ')
echo "All staged files: $STAGED_FILES"

./gradlew spotlessApply --daemon
SPOTLESS_RESULT=$?
if [ $SPOTLESS_RESULT -ne 0 ]
then
        echo "spotlessApply failed"
fi

echo "git add $STAGED_FILES"
git add $STAGED_FILES

echo "End Spotless Apply"

exit $SPOTLESS_RESULT
