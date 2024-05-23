package uk.mushow.client.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "gateway", url = "${gateway}")
public class WebProxy {

    //Stuff to do here
    //Interact with other services

}
