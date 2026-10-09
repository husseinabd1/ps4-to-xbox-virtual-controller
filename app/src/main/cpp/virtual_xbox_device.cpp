#include <jni.h>
#include <android/log.h>
#include <fcntl.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <linux/uinput.h>
#include <linux/input.h>

#define LOG_TAG "VirtualXboxNative"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_ps4toxbox_MainActivity_createNativeVirtualXbox(JNIEnv* env, jobject thiz) {
    int fd = open("/dev/uinput", O_WRONLY | O_NONBLOCK);
    if (fd < 0) {
        LOGE("Failed to open /dev/uinput: %s", strerror(errno));
        return JNI_FALSE;
    }

    // Enable EV_KEY and EV_ABS for uinput
    ioctl(fd, UI_SET_EVBIT, EV_KEY);
    ioctl(fd, UI_SET_EVBIT, EV_ABS);
    ioctl(fd, UI_SET_EVBIT, EV_SYN);

    // Xbox controller buttons
    for (int code : {
        BTN_A, BTN_B, BTN_X, BTN_Y,
        BTN_TL, BTN_TR,
        BTN_SELECT, BTN_START,
        BTN_THUMBL, BTN_THUMBR,
        BTN_MODE
    }) {
        ioctl(fd, UI_SET_KEYBIT, code);
    }

    // Axes
    ioctl(fd, UI_SET_ABSBIT, ABS_X);
    ioctl(fd, UI_SET_ABSBIT, ABS_Y);
    ioctl(fd, UI_SET_ABSBIT, ABS_RX);
    ioctl(fd, UI_SET_ABSBIT, ABS_RY);
    ioctl(fd, UI_SET_ABSBIT, ABS_Z);
    ioctl(fd, UI_SET_ABSBIT, ABS_RZ);

    uinput_user_dev uidev{};
    memset(&uidev, 0, sizeof(uidev));
    snprintf(uidev.name, UINPUT_MAX_NAME_SIZE, "Virtual Xbox Controller");
    uidev.id.bustype = BUS_USB;
    uidev.id.vendor = 0x045E;
    uidev.id.product = 0x02EA;
    uidev.id.version = 1;

    uidev.absmin[ABS_X] = -32768;
    uidev.absmax[ABS_X] = 32767;
    uidev.absmin[ABS_Y] = -32768;
    uidev.absmax[ABS_Y] = 32767;
    uidev.absmin[ABS_RX] = -32768;
    uidev.absmax[ABS_RX] = 32767;
    uidev.absmin[ABS_RY] = -32768;
    uidev.absmax[ABS_RY] = 32767;
    uidev.absmin[ABS_Z] = 0;
    uidev.absmax[ABS_Z] = 255;
    uidev.absmin[ABS_RZ] = 0;
    uidev.absmax[ABS_RZ] = 255;

    if (write(fd, &uidev, sizeof(uidev)) < 0) {
        LOGE("Failed to write uinput device definition: %s", strerror(errno));
        close(fd);
        return JNI_FALSE;
    }

    if (ioctl(fd, UI_DEV_CREATE) < 0) {
        LOGE("UI_DEV_CREATE failed: %s", strerror(errno));
        close(fd);
        return JNI_FALSE;
    }

    LOGI("Virtual Xbox device created successfully");
    close(fd);
    return JNI_TRUE;
}
