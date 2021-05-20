#include <jni.h>
#include <string>
#include <map>
#include <sys/epoll.h>
#include <unistd.h>
#include <vsomeip/vsomeip.hpp>

#ifndef VSOMEIP_CONTEXT_H
#define VSOMEIP_CONTEXT_H

struct context{
    JavaVM* vm;
    std::shared_ptr< vsomeip::application > app;
    char* app_name;
    jobject app_object; // 用于回调
};

#endif //VSOMEIP_CONTEXT_H
