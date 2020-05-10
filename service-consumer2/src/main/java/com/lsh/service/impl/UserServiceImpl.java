package com.lsh.service.impl;

import com.lsh.pojo.User;
import com.lsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Description TODO
 * @Author LSH
 * @Date 2020/2/17 17:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LoadBalancerClient loadBalancerClient; // Ribbon 负载均衡器


    @Override
    public List<User> selectUserList() {
//        return selectUserListByLoadBalancerAnnotation();
        return selectUserListByLoadBalancerClient();
    }

    private List<User> selectUserListByDiscoveryClient() {
        StringBuffer sb = null;
        // 获取服务列表
        List<String> serviceIds = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(serviceIds))
            return null;
        // 根据服务名称获取服务
        List<ServiceInstance> serviceInstances =
                discoveryClient.getInstances("service-provider");
        if (CollectionUtils.isEmpty(serviceInstances))
            return null;
        ServiceInstance si = serviceInstances.get(0);
        sb = new StringBuffer();
        sb.append("http://" + si.getHost() + ":" + si.getPort() + "/user/list");
        // ResponseEntity: 封装了返回数据
        ResponseEntity<List<User>> response = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {});
        return response.getBody();

    }

    private List<User> selectUserListByLoadBalancerClient() {
        StringBuffer sb = null;
        // 根据服务名称获取服务
        ServiceInstance si = loadBalancerClient.choose("service-provider");
        if (null == si)
            return null;
        sb = new StringBuffer();
        sb.append("http://" + si.getHost() + ":" + si.getPort() + "/user/list");
        System.out.println(sb);
        // ResponseEntity: 封装了返回数据
        ResponseEntity<List<User>> response = restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {});
        return response.getBody();
    }
    private List<User> selectUserListByLoadBalancerAnnotation() {
        // ResponseEntity: 封装了返回数据
        ResponseEntity<List<User>> response = restTemplate.exchange(
                "http://service-provider/user/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {});
        return response.getBody();
    }

}