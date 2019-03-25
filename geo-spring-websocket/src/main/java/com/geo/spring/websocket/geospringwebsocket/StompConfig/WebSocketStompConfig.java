package com.geo.spring.websocket.geospringwebsocket.StompConfig;

import com.geo.spring.websocket.geospringwebsocket.handler.HttpSessionIdHandshakeInterceptor;
import com.geo.spring.websocket.geospringwebsocket.handler.PresenceChannelInterceptor;
import com.geo.spring.websocket.geospringwebsocket.handler.SystemWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp").setAllowedOrigins("*")
				.setHandshakeHandler(new DefaultHandshakeHandler() {
			@Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                  //���ͻ��˱�ʶ��װΪPrincipal���󣬴Ӷ��÷������ͨ��getName()�����ҵ�ָ���ͻ���
                  Object o = attributes.get("name");
                  return new FastPrincipal(o.toString());
            }
      })
      //���socket�����������ڴ������л�ȡ�ͻ��˱�ʶ����
				.addInterceptors(new HandleShakeInterceptors()).withSockJS();
		
	}

	/**
	 * ��Ϣ�����������
	 */
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(8192) //������Ϣ�ֽ�����С
				.setSendBufferSizeLimit(8192)//������Ϣ�����С
				.setSendTimeLimit(10000); //������Ϣ����ʱ�����ƺ���
		WebSocketHandlerDecoratorFactory factory = new WebSocketHandlerDecoratorFactory() {
			@Override
			public WebSocketHandler decorate(WebSocketHandler systemWebSocketHandler) {
				return systemWebSocketHandler;
			}
		};
		registry.setDecoratorFactories(factory);

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//�ͻ��˷�����Ϣ������ǰ׺
		registry.setApplicationDestinationPrefixes("/app");
        //�ͻ��˶�����Ϣ������ǰ׺��topicһ�����ڹ㲥���ͣ�queue���ڵ�Ե�����
		registry.enableSimpleBroker("/topic", "/queue");
        //�����֪ͨ�ͻ��˵�ǰ׺�����Բ����ã�Ĭ��Ϊuser
		registry.setUserDestinationPrefix("/user");
		/*	��������Լ�����Ϣ�м�������������ȥ���ã�ɾ�����������
		 *	 registry.enableStompBrokerRelay("/topic", "/queue")
			.setRelayHost("rabbit.someotherserver")
			.setRelayPort(62623)
			.setClientLogin("marcopolo")
			.setClientPasscode("letmein01");
			registry.setApplicationDestinationPrefixes("/app", "/foo");
		 * */
		

	}
//����һ���Լ���Ȩ����֤��
	class FastPrincipal implements Principal {

	    private final String name;

	    public FastPrincipal(String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }
	}





	/**
	 * ����ͨ����������
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(4) //������Ϣ����ͨ�����̳߳��߳���
				.maxPoolSize(8)//����߳���
				.keepAliveSeconds(60);//�̻߳ʱ��
		registration.interceptors(presenceChannelInterceptor());
	}

	/**
	 * ���ͨ����������
	 */
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
		registration.interceptors(presenceChannelInterceptor());
	}

	@Bean
	public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor() {
		return new HttpSessionIdHandshakeInterceptor();
	}

	@Bean
	public PresenceChannelInterceptor presenceChannelInterceptor() {
		return new PresenceChannelInterceptor();
	}


	@Bean
	public WebSocketHandler systemWebSocketHandler() {
		//return new InfoSocketEndPoint();
		return new SystemWebSocketHandler();
	}
}
