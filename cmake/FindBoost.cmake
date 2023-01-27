set(Boost_FOUND TRUE)
set(Boost_INCLUDE_DIR ${CMAKE_BINARY_DIR}/_deps/boost-src)

# Detect Boost version
file(STRINGS "${Boost_INCLUDE_DIR}/boost/version.hpp" boost_version_raw
   REGEX "define BOOST_VERSION "
)
string(REGEX MATCH "[0-9]+" boost_version_raw "${boost_version_raw}")
set(Boost_VERSION ${boost_version_raw})

foreach(comp ${Boost_FIND_COMPONENTS})
   list(APPEND Boost_LIBRARIES Boost::${comp})
endforeach()