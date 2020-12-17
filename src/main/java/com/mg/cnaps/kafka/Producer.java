package com.mg.cnaps.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void send(String key,String topic,String data) {
	    kafkaTemplate.send(topic,key, data);
	}
}
