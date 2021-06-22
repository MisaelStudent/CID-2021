@echo off
if not exist classes mkdir classes
SET GLOBAL_FLAGS=-Xlint -cp lib/jade.jar 
SET GLOBAL_SRC=src/com/util/math/matrix/*.java


if "%1"=="three" (
    javac %GLOBAL_FLAGS% src/com/challenge/three/*.java -d classes/
) else if "%1"=="six" (
    javac %GLOBAL_FLAGS% %GLOBAL_SRC% src/com/challenge/six/*.java -d classes/
) else (
    echo Pass number of challenge to build
)
