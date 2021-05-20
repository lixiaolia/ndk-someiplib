# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
CMAKE_MINIMUM_REQUIRED(VERSION 3.10.2)
# Declares and names the project.
PROJECT("VSomeIPJNI")
set(CMAKE_CXX_STANDARD 11)
# 设置头文件目录
SET(VSOMEIP_JNI_INC ${PROJECT_SOURCE_DIR}/include)
INCLUDE_DIRECTORIES("${VSOMEIP_JNI_INC}")
# 日志库
SET(NATIVE_LOG_LIB log)
# 设置源码目录
# AUX_SOURCE_DIRECTORY(${PROJECT_SOURCE_DIR} VSOMEIP_JNI_SRC)
# 生成JNI动态库
ADD_LIBRARY(VSomeIPServiceJNI SHARED VSomeIPServiceJNI.cpp)
TARGET_LINK_LIBRARIES(VSomeIPServiceJNI vsomeip3 vsomeip3-cfg vsomeip3-e2e vsomeip3-sd ${NATIVE_LOG_LIB})

ADD_LIBRARY(VSomeIPClientJNI SHARED VSomeIPClientJNI.cpp)
TARGET_LINK_LIBRARIES(VSomeIPClientJNI vsomeip3 vsomeip3-cfg vsomeip3-e2e vsomeip3-sd ${NATIVE_LOG_LIB})