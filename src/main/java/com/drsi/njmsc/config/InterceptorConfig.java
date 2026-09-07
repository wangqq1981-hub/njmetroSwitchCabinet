package com.drsi.njmsc.config;

import com.drsi.njmsc.interceptor.AuthenticationInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler(new String[]{"/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
      registry.addResourceHandler(new String[]{"/swagger-ui/**"})
         .addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/springfox-swagger-ui/"});
   }

   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(this.authenticationInterceptor()).addPathPatterns(new String[]{"/**"}).excludePathPatterns(new String[]{"/swagger-ui/**"});
      super.addInterceptors(registry);
   }

   @Bean
   public AuthenticationInterceptor authenticationInterceptor() {
      return new AuthenticationInterceptor();
   }

   @Bean
   public ObjectMapper jacksonObjectMapperCustomization() {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
      format.setTimeZone(timeZone);
      Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder().timeZone(timeZone).dateFormat(format);
      return builder.build();
   }

   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.removeIf(c -> c instanceof MappingJackson2HttpMessageConverter);
      converters.add(new MappingJackson2HttpMessageConverter(this.jacksonObjectMapperCustomization()));
   }

   protected void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
         .allowedOriginPatterns(new String[]{"*"})
         .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"})
         .allowCredentials(true)
         .maxAge(3600L)
         .exposedHeaders(new String[]{"authorization"});
   }
}
