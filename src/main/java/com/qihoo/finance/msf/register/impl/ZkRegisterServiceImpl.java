package com.qihoo.finance.msf.register.impl;

import com.qihoo.finance.msf.register.RegisterService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ZkRegisterServiceImpl implements RegisterService {

    private static final DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    private CuratorFramework curatorFramework;

    public void registerServiceOfAppUse(final String appName, final String serviceName) {
        byte[] data = dateformat.format(new Date()).getBytes();
        try {
            curatorFramework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)      //节点类型
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/traceGraph/servicesOfAppUse/" + appName + "/" + serviceName, data);
        } catch (Exception e) {
        }

    }

    public void registerAppOfServiceBeUsed(final String service, final String app) {

        byte[] data = dateformat.format(new Date()).getBytes();
        try {
            curatorFramework.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)      //节点类型
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/traceGraph/appOfServiceBeUsed/" + service + "/" + app, data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
