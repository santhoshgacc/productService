package com.ibm.shoppingCart.productService.messaging;

import java.sql.SQLException;
import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.shoppingCart.productService.model.Product;
import com.ibm.shoppingCart.productService.service.ProductService;

public class ProductDLQProcessor implements Runnable {

	private static ProductDLQProcessor instance;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private ProductService productService;

	private ProductDLQProcessor() {
	}

	public static ProductDLQProcessor getInstance() {
		if (instance == null) {
			instance = new ProductDLQProcessor();
		}

		return instance;
	}

	@Override
	public void run() {

		/// reading message from DLQ and saving to DB
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://localhost:61616?jms.redeliveryPolicy.maximumRedeliveries=1");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("ProductTransactionQueue.dlq");
			MessageConsumer consumer = session.createConsumer(destination);

			QueueBrowser browser = session.createBrowser((Queue) destination);
			Enumeration elems = browser.getEnumeration();
			Product product;
			while (elems.hasMoreElements()) {
				Message message = (Message) consumer.receive();

				product = (Product) this.jmsTemplate.getMessageConverter().fromMessage(message);
				System.out.println("Product from DLQ : " + product);
				productService.saveProduct(product);
				message.acknowledge();

			}
			connection.close();

		} catch (JMSException jmsEx) {

			System.out.println("JMS Exception : " + jmsEx);
			
		} catch (SQLException sqlEx) {
			
			System.out.println("SQL Exception : " + sqlEx);
			
		}

	}
}
