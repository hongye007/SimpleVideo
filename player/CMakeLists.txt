# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_C_FLAGS_DEBUG "${CMAKE_C_FLAGS_DEBUG} -g")
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -g")
set(CMAKE_BUILD_TYPE Debug)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#
#
set(distribution_DIR ${CMAKE_SOURCE_DIR}/src/main/jniLibs)

include_directories(
        ${CMAKE_SOURCE_DIR}/src/main/cpp64/include
        ${CMAKE_SOURCE_DIR}/src/main/cpp64/player
        ${CMAKE_SOURCE_DIR}/src/main/cpp64/editor
        ${CMAKE_SOURCE_DIR}/src/main/cpp64/tool
)

add_library(
        dpffmpeg
        SHARED
        IMPORTED
)
set_target_properties(
        dpffmpeg
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libdpffmpeg.so
)

# ffmpeg 的library end

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

add_library( # Sets the name of the library.
        dp-media

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp64/JniInit.cpp
        src/main/cpp64/player/MediaPlayer.cpp
        src/main/cpp64/editor/MediaEditor.cpp
        src/main/cpp64/editor/MediaHelper.cpp
        src/main/cpp64/editor/MediaTranscoder.cpp
        )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        dp-media

        log
        android
        OpenSLES
        # ffmpeg 相关库
        dpffmpeg
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
        )

