package com.lagou.rpc.zookeeper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionZookeeper {

    private String BASE = "/netty";
    private ZkClient zkClient;

    private List<String> remoteNodeList = new ArrayList<>();

    public void connect(String connectString, boolean flag) {
        zkClient = new ZkClient(connectString);

        if (!existsNode(BASE)) {
            this.createNode(CreateMode.PERSISTENT, BASE, null);
        }

        if (flag) {
            zkClient.subscribeChildChanges(BASE, new IZkChildListener() {
                @Override
                public void handleChildChange(String s, List<String> list) throws Exception {
                    remoteNodeList = list;
                    System.out.println(list);
                }
            });

            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                List<String> childrenNode = getChildrenNode(getBASE());
                long currentTime = System.currentTimeMillis();
                for (String path : childrenNode) {
                    String value = getNode(getBASE() + "/" + path).toString();
                    String[] array = value.split("#");
                    long beforeTime = Long.parseLong(array[1]);
                    if (currentTime - beforeTime > 5000) {
                        updateNode(getBASE() + "/" + path, "0#0");
                    }
                }
            }, 5, 5, TimeUnit.SECONDS);
        }
    }

    public void createNode(CreateMode createMode, String path, Object value) {
        zkClient.create(path, value, createMode);
    }

    public void deleteNode(String path) {
        zkClient.deleteRecursive(path);
    }

    public Object getNode(String path) {
        return zkClient.readData(path);
    }

    public List<String> getChildrenNode(String path) {
        return zkClient.getChildren(path);
    }

    public void updateNode(String path, Object value) {
        zkClient.writeData(path, value);
    }

    public boolean existsNode(String path) {
        return zkClient.exists(path);
    }
}
