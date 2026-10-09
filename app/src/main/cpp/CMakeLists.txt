cmake_minimum_required(VERSION 3.22.1)

project(virtual_xbox LANGUAGES CXX)

add_library(virtual_xbox SHARED
    virtual_xbox_device.cpp
)

find_library(log-lib log)

target_link_libraries(virtual_xbox
    ${log-lib}
)
