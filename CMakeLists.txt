cmake_minimum_required(VERSION 3.4.1)

project(NDK-SOMEIPLIB)

list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)
add_subdirectory(external/boost-cmake)
add_subdirectory(external/vsomeip)
add_subdirectory(ndk-vsomeip/someiplib/src/main/cpp)
