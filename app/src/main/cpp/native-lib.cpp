#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_qamar_cryptoexchange_MainActivity_stringFromJNICrypto(
        JNIEnv *env,
        jobject /* this */,jobject cryptoObject, jstring ob) {
    jclass clazz = env->GetObjectClass(cryptoObject);

    //value
    jfieldID value = env->GetFieldID(clazz, "priceUSD", "D");
    jdouble dVal = env->GetDoubleField(cryptoObject, value);
    //name
    jfieldID nameField = env->GetFieldID(clazz, "name", "Ljava/lang/String;");
    jstring nameValue = (jstring) env->GetObjectField(cryptoObject, nameField);
    //unit
    jfieldID unitField = env->GetFieldID(clazz, "unit", "Ljava/lang/String;");
    jstring unitValue = (jstring) env->GetObjectField(cryptoObject, unitField);

    std::string output =   env->GetStringUTFChars(ob, NULL) + std::to_string(dVal) + " "
                        +" "+ env->GetStringUTFChars(unitValue, NULL);

    std::string output1 =  env->GetStringUTFChars(ob, NULL) + (std::to_string(dVal)) + " "
                           +" "+ env->GetStringUTFChars(unitValue, NULL);

    return env->NewStringUTF(output.c_str());
}
