package com.qihoo.finance.msf.register;

public interface RegisterService {

    void registerServiceOfAppUse(String appName,String serviceName);

    void registerAppOfServiceBeUsed(String service,String app);
}
