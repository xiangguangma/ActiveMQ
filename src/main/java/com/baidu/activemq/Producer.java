package com.baidu.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by dllo on 17/11/28.
 */
public class Producer {

    /**
     *  获取activeMQ默认的登录用户名
     */
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public void sendMessage(String queueName) {

        try {

            // 1. 创建一个连接工厂
            ConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
            // 2. 创建一个连接
            Connection connection = connectionFactory.createConnection();

            // 3. 开启连接
            connection.start();

            // 4. 获取事务(true:需要事务, Session.SESSION_TRANSACTED:枚举)
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

            // 5. 创建消息队列
            Queue queue = session.createQueue(queueName);

            // 6. 消息生成器
            MessageProducer messageProducer = session.createProducer(queue);

            int count = 0;

            while (true){

                Thread.sleep(1000);

                // 7. 创建消息
                TextMessage textMessage = session.createTextMessage("这是一条消息" + ++count);

                System.out.println("发送消息: " + count);

                // 8. 发送消息
                messageProducer.send(textMessage);

                // 9. 提交事务
                session.commit();

            }


        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Producer producer = new Producer();

        producer.sendMessage("J0703");
    }

}
