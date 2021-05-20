cmake_minimum_required(VERSION 3.4.1)

project(NDK-SOMEIPLIB)

set(FETCHCONTENT_SOURCE_DIR_BOOST /home/lxl/work/boost_1_71_0)
list(APPEND CMAKE_MODULE_PATH ${CMAKE_SOURCE_DIR}/cmake)
add_subdirectory(external/boost-cmake)
add_subdirectory(external/vsomeip)
add_subdirectory(ndk-vsomeip/someiplib/src/main/cpp)
