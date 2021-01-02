#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_nathasyaeliora_Today_MainActivity_getGreeting(
        JNIEnv *env, jobject thiz, jint hour) {

    if (hour >= 0 && hour < 12){
        return env->NewStringUTF("Good morning!");
    } else if(hour >= 12 && hour < 16){
        return env->NewStringUTF("Good afternoon!");
    } else if(hour >= 16 && hour < 21){
        return env->NewStringUTF("Good evening!");
    } else{
        return env->NewStringUTF("Good night!");
    }

}