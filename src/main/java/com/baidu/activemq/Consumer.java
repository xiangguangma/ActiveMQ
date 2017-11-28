package com.baidu.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by dllo on 17/11/28.
 */
public class Consumer {

    /**
     * 获取activeMQ默认的登录用户名
     */
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public void receiveMessage(String queueName) {

        try {
            // 创建连接工厂
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);

            // 创建连接
            Connection connection = connectionFactory.createConnection();

            connection.start();
            // 获取事务, 参数(false:不需要事务, Session.CLIENT_ACKNOWLEDGE:手动处理消息)
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // 创建消息队列
            Queue queue = session.createQueue(queueName);

            // 创建消息的消费者
            MessageConsumer messageConsumer = session.createConsumer(queue);

            while (true){
                Thread.sleep(1000);

                // 接收消息
                TextMessage textMessage = (TextMessage) messageConsumer.receive();

                if (textMessage != null) {
                    // 告诉ActiveMQ, 消息处理完毕
                    textMessage.acknowledge();

                    System.out.println("处理消息: ====> " + textMessage.getText() + " <===");
                }
            }


//            connection.close();

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();

        consumer.receiveMessage("J0703");
    }


}
