package com.ibm.shoppingCart.productService;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.shoppingCart.productService.model.Product;
import com.ibm.shoppingCart.productService.ui.model.ProductModel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceInAShoppingCartApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceInAShoppingCartApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper objectMapper;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Test
	public void getBusSearchResultsTest() throws Exception {

		ProductModel product = new ProductModel("P1", "Product Name-1", "S1", 10);

		HttpEntity<ProductModel> entity = new HttpEntity<ProductModel>(product, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("product/send"), HttpMethod.POST,
				entity, String.class);
//		jmsTemplate.convertAndSend("ProductTransactionQueue", product);
		
		
		assertTrue(response.getStatusCode() == HttpStatus.OK);
		
	}
	
//	@Bean
//	public BrokerService broker() throws Exception {
//		final BrokerService broker = new BrokerService();
//		broker.addConnector("tcp://localhost:61616?wireFormat.maxInactivityDuration=0");
//		broker.addConnector("vm://localhost");
//		broker.setPersistent(false);
//		broker.setDestinationPolicy(policyMap());
//		return broker;
//	}
//
//	@Bean
//	public PolicyMap policyMap() {
//		PolicyMap destinationPolicy = new PolicyMap();
//		List<PolicyEntry> entries = new ArrayList<PolicyEntry>();
//		PolicyEntry queueEntry = new PolicyEntry();
//		queueEntry.setQueue(">");
//		queueEntry.setDeadLetterStrategy(deadLetterStrategy());
//		PolicyEntry topicEntry = new PolicyEntry();
//		topicEntry.setTopic(">");
//		topicEntry.setDeadLetterStrategy(deadLetterStrategy());
//		entries.add(queueEntry);
//		entries.add(topicEntry);
//		destinationPolicy.setPolicyEntries(entries);
//		return destinationPolicy;
//
//	}
//
//	@Bean
//	public DeadLetterStrategy deadLetterStrategy() {
//		IndividualDeadLetterStrategy deadLetterStrategy = new IndividualDeadLetterStrategy();
//		deadLetterStrategy.setQueueSuffix(".dlq");
//		deadLetterStrategy.setUseQueueForQueueMessages(true);
//		return deadLetterStrategy;
//	}
//
//	@Bean
//	public RedeliveryPolicy redeliveryPolicy() {
//		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
//		redeliveryPolicy.setInitialRedeliveryDelay(5000);
//		redeliveryPolicy.setBackOffMultiplier(2);
//		redeliveryPolicy.setUseExponentialBackOff(true);
//		redeliveryPolicy.setMaximumRedeliveries(3);
//		return redeliveryPolicy;
//	}
//
//	@Bean
//	public ConnectionFactory jmsConnectionFactory() {
//		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//		connectionFactory.setRedeliveryPolicy(redeliveryPolicy());
//		return connectionFactory;
//	}
//
//	@Bean
//	public MessageConverter jacksonJmsMessageConverter() {
//		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//		converter.setTargetType(MessageType.TEXT);
//		converter.setTypeIdPropertyName("_type");
//		return converter;
//	}
//
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
