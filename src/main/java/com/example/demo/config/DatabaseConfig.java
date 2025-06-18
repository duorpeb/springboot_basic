package com.example.demo.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/* DatabaseConfig class

  > DatabaseConfig class 의 목적
   #1_ application.properties 설정을 기반으로 HikariCP를 이용해 커넥션 풀을 설정

   #2_ MyBatis 를 통해 Mapper XML 을 읽어 SQL 실행 준비 SqlSessionTemplate을 통해 Mapper Interface 사용 가능하도록 설정

   ====================================================================================================================
  > Annotation
   # @PropertySource("classpath:/application.properties") 는 application.properties 의 속성 값을 @Value로 읽을 수
     있게하는 Annotation

   #
*/
@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfig {
  // 초기화
    /*  @Value("${mybatis.mapper-locations}")
        private String mapperLocations;

      > application.properties 의 mybatis.mapper-locations 의 value 를 mapperLocations 에 할당한다는 의미
    */
  @Value("${mybatis.mapper-locations}")
  private String mapperLocations;

  // 스프링 컨텍스트 객체 (Spring 의 Bean 관리 객체) 를 주입
  @Autowired
  private ApplicationContext applicationContext;

  // application.properties 의 spring.datasource 로 시작하는 설정을 읽어와 HicariCP 설정 객체로 변환
   // spring.datasource.driver-class-name / url / username / password 의 value 를 읽어옴
  @Bean
  @ConfigurationProperties(prefix="spring.datasource")
  public HikariConfig hikariConfig(){ return new HikariConfig(); }


  // 위에서 만든 HikariConfig를 이용해 실제 커넥션풀을 생성
  @Bean
  public DataSource dataSource(){
    return new HikariDataSource(hikariConfig());
  }


  // MyBatis가 사용할 DB 연결 + Mapper 위치 설정
  @Bean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    // DB 와 연결하기 위한 인스턴스 생성
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

    // dataSource 연결
    sqlSessionFactoryBean.setDataSource(dataSource);

//     mybatis-config.xml 이 존재하는 경우 작성
    sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatisConfig.xml"));

    // mapperLocation 위치 설정
     // setMapperLocation(Resource mapperLocation) 형태이기에 기존에 선언해놓은 String mapperLocations 를
     // PathMatchingResourcePatternResolver() 를 통해 Resource 형으로 변환해주어야 함
    sqlSessionFactoryBean.setMapperLocations(
        // 단일 파일만 처리하려면 .getResource(...), 여러 파일(/**/*.xml) 패턴은 .getResources(...) 를 써야 함
        new PathMatchingResourcePatternResolver().getResources(mapperLocations)
    );

    return sqlSessionFactoryBean.getObject();
  }


  // SqlSessionTemplate은 MyBatis Mapper 인터페이스를 사용할 때 필요한 객체로
  // 트랜잭션, 커넥션 풀, 예외 처리까지 통합 관리
  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
