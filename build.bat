@echo off
if not exist classes mkdir classes
SET GLOBAL_FLAGS=-Xlint -cp lib/jade.jar 


if "%1"=="three" (
    javac %GLOBAL_FLAGS% src/com/challenge/three/*.java -d classes/
) else (
    echo Pass number of challenge to build
)
